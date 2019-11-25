package io.github.malczuuu.taskbook.core.service;

import io.github.malczuuu.taskbook.model.BoardModel;
import io.github.malczuuu.taskbook.model.BoardUpdateModel;
import org.springframework.data.domain.Page;

public interface BoardService {

  Page<BoardModel> findAll(int page, int size);

  BoardModel findByUid(String uid);

  Page<BoardModel> findAllFilterByName(String name, int page, int size);

  BoardModel create(BoardModel board);

  BoardModel updateByUid(String uid, BoardUpdateModel board);

  void deleteByUid(String uid);
}
