package io.github.malczuuu.taskbook.core.impl;

import io.github.malczuuu.taskbook.core.entity.BoardEntity;
import io.github.malczuuu.taskbook.core.entity.CommentEntity;
import io.github.malczuuu.taskbook.core.entity.IssueEntity;
import io.github.malczuuu.taskbook.core.entity.UserEntity;
import io.github.malczuuu.taskbook.core.exception.BoardGoneException;
import io.github.malczuuu.taskbook.core.exception.BoardNotFoundException;
import io.github.malczuuu.taskbook.core.exception.CommentNotFoundException;
import io.github.malczuuu.taskbook.core.exception.IssueGoneException;
import io.github.malczuuu.taskbook.core.exception.IssueNotFoundException;
import io.github.malczuuu.taskbook.core.exception.UserDoesNotExistException;
import io.github.malczuuu.taskbook.core.repository.BoardRepository;
import io.github.malczuuu.taskbook.core.repository.CommentRepository;
import io.github.malczuuu.taskbook.core.repository.IssueRepository;
import io.github.malczuuu.taskbook.core.repository.UserRepository;
import io.github.malczuuu.taskbook.core.service.CommentService;
import io.github.malczuuu.taskbook.model.CommentModel;
import io.github.malczuuu.taskbook.model.NewCommentModel;
import io.github.malczuuu.taskbook.model.UserModel;
import io.github.problem4j.core.Problem;
import java.time.Clock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

  private final BoardRepository boardRepository;
  private final IssueRepository issueRepository;
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;

  private final Clock clock;

  public CommentServiceImpl(
      BoardRepository boardRepository,
      IssueRepository issueRepository,
      CommentRepository commentRepository,
      UserRepository userRepository,
      Clock clock) {
    this.boardRepository = boardRepository;
    this.issueRepository = issueRepository;
    this.commentRepository = commentRepository;
    this.userRepository = userRepository;
    this.clock = clock;
  }

  @Override
  public Page<CommentModel> findAll(String board, String issue, int page, int size) {
    BoardEntity boardEntity = fetchBoard(board);
    IssueEntity issueEntity = fetchIssue(boardEntity, issue);
    return commentRepository
        .findAllByIssueOrderByCreatedTimeDesc(issueEntity, PageRequest.of(page, size))
        .map(this::toCommentModel);
  }

  private BoardEntity fetchBoard(String uid) {
    BoardEntity board = boardRepository.findByUid(uid).orElseThrow(BoardNotFoundException::new);
    if (board.getArchivedTime() != null) {
      throw new BoardGoneException(Problem.extension("time", board.getArchivedTime()));
    }
    return board;
  }

  private IssueEntity fetchIssue(BoardEntity board, String uid) {
    IssueEntity issue =
        issueRepository.findByBoardAndUid(board, uid).orElseThrow(IssueNotFoundException::new);
    if (issue.getArchivedTime() != null) {
      throw new IssueGoneException(Problem.extension("time", board.getArchivedTime()));
    }
    return issue;
  }

  private CommentModel toCommentModel(CommentEntity comment) {
    return new CommentModel(
        comment.getId().toString(),
        comment.getContent(),
        toUserModel(comment.getAuthor()),
        comment.getCreatedTime().toString(),
        comment.getUpdatedTime() != null ? comment.getUpdatedTime().toString() : null);
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
  public CommentModel findByUid(String board, String issue, String uid) {
    BoardEntity boardEntity = fetchBoard(board);
    IssueEntity issueEntity = fetchIssue(boardEntity, issue);
    CommentEntity commentEntity = fetchComment(issueEntity, uid);
    return toCommentModel(commentEntity);
  }

  private CommentEntity fetchComment(IssueEntity issue, String uid) {
    Long id = parseId(uid);
    return commentRepository.findByIssueAndId(issue, id).orElseThrow(CommentNotFoundException::new);
  }

  private Long parseId(String uid) {
    try {
      return Long.parseLong(uid);
    } catch (NumberFormatException e) {
      throw new CommentNotFoundException();
    }
  }

  @Override
  @Transactional
  public CommentModel create(
      String board, String issue, NewCommentModel comment, String principal) {
    BoardEntity boardEntity = fetchBoard(board);
    IssueEntity issueEntity = fetchIssue(boardEntity, issue);
    UserEntity userEntity = fetchUser(principal);

    CommentEntity commentEntity =
        new CommentEntity(issueEntity, comment.getContent(), userEntity, clock.instant());
    commentEntity = commentRepository.save(commentEntity);

    return toCommentModel(commentEntity);
  }

  private UserEntity fetchUser(String email) {
    return userRepository.findByEmail(email).orElseThrow(UserDoesNotExistException::new);
  }
}
