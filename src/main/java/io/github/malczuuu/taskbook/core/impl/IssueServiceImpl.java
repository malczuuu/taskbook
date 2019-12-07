package io.github.malczuuu.taskbook.core.impl;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.taskbook.core.entity.BoardEntity;
import io.github.malczuuu.taskbook.core.entity.IssueEntity;
import io.github.malczuuu.taskbook.core.entity.UserEntity;
import io.github.malczuuu.taskbook.core.exception.BoardGoneException;
import io.github.malczuuu.taskbook.core.exception.BoardNotFoundException;
import io.github.malczuuu.taskbook.core.exception.IssueGoneException;
import io.github.malczuuu.taskbook.core.exception.IssueNotFoundException;
import io.github.malczuuu.taskbook.core.exception.UserDoesNotExistException;
import io.github.malczuuu.taskbook.core.repository.BoardRepository;
import io.github.malczuuu.taskbook.core.repository.IssueRepository;
import io.github.malczuuu.taskbook.core.repository.UserRepository;
import io.github.malczuuu.taskbook.core.service.IssueService;
import io.github.malczuuu.taskbook.model.IssueModel;
import io.github.malczuuu.taskbook.model.IssueUpdateModel;
import io.github.malczuuu.taskbook.model.NewIssueModel;
import io.github.malczuuu.taskbook.model.UserModel;
import java.time.Clock;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IssueServiceImpl implements IssueService {

  private final BoardRepository boardRepository;
  private final IssueRepository issueRepository;
  private final UserRepository userRepository;
  private final Clock clock;

  public IssueServiceImpl(
      BoardRepository boardRepository,
      IssueRepository issueRepository,
      UserRepository userRepository,
      Clock clock) {
    this.boardRepository = boardRepository;
    this.issueRepository = issueRepository;
    this.userRepository = userRepository;
    this.clock = clock;
  }

  @Override
  public Page<IssueModel> findAll(String board, int page, int size) {
    BoardEntity boardEntity = fetchBoard(board);
    return issueRepository
        .findAllByBoardAndArchivedTimeNull(boardEntity, PageRequest.of(page, size))
        .map(this::toIssueModel);
  }

  private BoardEntity fetchBoard(String uid) {
    BoardEntity board = boardRepository.findByUid(uid).orElseThrow(BoardNotFoundException::new);
    if (board.getArchivedTime() != null) {
      throw new BoardGoneException(Problem.extension("time", board.getArchivedTime()));
    }
    return board;
  }

  private IssueModel toIssueModel(IssueEntity issue) {
    return new IssueModel(
        issue.getUid(),
        issue.getTitle(),
        issue.getDetail(),
        issue.getAssignee() != null ? toUserModel(issue.getAssignee()) : null,
        issue.getStatus().toString().toLowerCase());
  }

  private UserModel toUserModel(UserEntity user) {
    return new UserModel(
        user.getUid(),
        user.getEmail(),
        user.getRole().name().toLowerCase(),
        user.getFirstName(),
        user.getLastName());
  }

  @Override
  public Page<IssueModel> findAllFilterByTitle(String board, String title, int page, int size) {
    BoardEntity boardEntity = fetchBoard(board);
    return issueRepository
        .findAllByBoardAndTitleAndArchivedTimeNull(boardEntity, title, PageRequest.of(page, size))
        .map(this::toIssueModel);
  }

  @Override
  public IssueModel findByUid(String board, String uid) {
    return toIssueModel(fetchIssue(fetchBoard(board), uid));
  }

  private IssueEntity fetchIssue(BoardEntity board, String uid) {
    IssueEntity issue =
        issueRepository.findByBoardAndUid(board, uid).orElseThrow(IssueNotFoundException::new);
    if (issue.getArchivedTime() != null) {
      throw new IssueGoneException(Problem.extension("time", issue.getArchivedTime()));
    }
    return issue;
  }

  @Override
  @Transactional
  public IssueModel create(String board, NewIssueModel issue) {
    BoardEntity boardEntity = fetchBoard(board);
    UserEntity assignee = issue.getAssignee() != null ? fetchAssignee(issue.getAssignee()) : null;
    long count = issueRepository.countByBoard(boardEntity);
    IssueEntity entity =
        new IssueEntity(
            boardEntity.getUid() + "-" + (count + 1),
            boardEntity,
            issue.getTitle(),
            issue.getDetail(),
            assignee,
            IssueEntity.Status.valueOf(issue.getStatus().toUpperCase()));
    entity = issueRepository.save(entity);
    return toIssueModel(entity);
  }

  private UserEntity fetchAssignee(String uid) {
    return userRepository.findByUid(uid).orElseThrow(UserDoesNotExistException::new);
  }

  @Override
  @Transactional
  public IssueModel updateByUid(String board, String uid, IssueUpdateModel issue) {
    BoardEntity boardEntity = fetchBoard(board);
    IssueEntity entity = fetchIssue(boardEntity, uid);

    entity.setTitle(issue.getTitle());
    entity.setDetail(issue.getDetail());
    entity.setStatus(IssueEntity.Status.valueOf(issue.getStatus().toUpperCase()));

    if (issue.getAssignee() == null) {
      entity.setAssignee(null);
    } else if (shouldUpdateAssignee(issue.getAssignee(), entity.getAssignee())) {
      entity.setAssignee(fetchAssignee(issue.getAssignee()));
    }

    return toIssueModel(entity);
  }

  private boolean shouldUpdateAssignee(String newAssigneeUid, UserEntity assignee) {
    return assignee == null || !newAssigneeUid.equals(assignee.getUid());
  }

  @Override
  @Transactional
  public void deleteByUid(String board, String uid) {
    BoardEntity boardEntity = fetchBoard(board);
    IssueEntity entity = fetchIssue(boardEntity, uid);
    entity.setArchivedTime(Instant.now(clock));
  }
}
