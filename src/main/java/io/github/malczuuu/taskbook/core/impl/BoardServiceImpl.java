package io.github.malczuuu.taskbook.core.impl;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.taskbook.core.entity.BoardEntity;
import io.github.malczuuu.taskbook.core.exception.BoardGoneException;
import io.github.malczuuu.taskbook.core.exception.BoardNotFoundException;
import io.github.malczuuu.taskbook.core.exception.BoardUidConflictException;
import io.github.malczuuu.taskbook.core.repository.BoardRepository;
import io.github.malczuuu.taskbook.core.service.BoardService;
import io.github.malczuuu.taskbook.model.BoardModel;
import io.github.malczuuu.taskbook.model.BoardUpdateModel;
import java.time.Clock;
import java.time.Instant;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardServiceImpl implements BoardService {

  private final BoardRepository boardRepository;
  private final Clock clock;

  public BoardServiceImpl(BoardRepository boardRepository, Clock clock) {
    this.boardRepository = boardRepository;
    this.clock = clock;
  }

  @Override
  public Page<BoardModel> findAll(int page, int size) {
    return boardRepository
        .findAllByArchivedTimeNull(PageRequest.of(page, size))
        .map(this::toBoardModel);
  }

  private BoardModel toBoardModel(BoardEntity board) {
    return new BoardModel(board.getUid(), board.getName(), board.getDescription());
  }

  @Override
  public BoardModel findByUid(String uid) {
    return toBoardModel(fetchByUid(uid));
  }

  @Override
  public Page<BoardModel> findAllFilterByName(String name, int page, int size) {
    return boardRepository
        .findAllByNameAndArchivedTimeNull(name, PageRequest.of(page, size))
        .map(this::toBoardModel);
  }

  private BoardEntity fetchByUid(String uid) {
    BoardEntity board = boardRepository.findByUid(uid).orElseThrow(BoardNotFoundException::new);
    if (board.getArchivedTime() != null) {
      throw new BoardGoneException(Problem.extension("time", board.getArchivedTime()));
    }
    return board;
  }

  @Override
  public BoardModel create(BoardModel board) {
    BoardEntity entity =
        new BoardEntity(
            board.getUid().toUpperCase(), board.getName().trim(), board.getDescription().trim());

    try {
      entity = boardRepository.save(entity);
    } catch (DataIntegrityViolationException e) {
      if (e.getCause() instanceof ConstraintViolationException) {
        String constraint = ((ConstraintViolationException) e.getCause()).getConstraintName();
        if ("unique_board_uid".equals(constraint)) {
          throw new BoardUidConflictException();
        }
      }
      throw e;
    }

    return toBoardModel(entity);
  }

  @Override
  @Transactional
  public BoardModel updateByUid(String uid, BoardUpdateModel board) {
    BoardEntity entity = fetchByUid(uid);

    entity.setName(board.getName().trim());
    entity.setDescription(board.getDescription().trim());

    return toBoardModel(entity);
  }

  @Override
  @Transactional
  public void deleteByUid(String uid) {
    BoardEntity entity = fetchByUid(uid);
    entity.setArchivedTime(Instant.now(clock));
  }
}
