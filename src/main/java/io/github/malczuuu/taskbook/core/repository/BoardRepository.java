package io.github.malczuuu.taskbook.core.repository;

import io.github.malczuuu.taskbook.core.entity.BoardEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface BoardRepository extends Repository<BoardEntity, Long> {

  Page<BoardEntity> findAllByArchivedTimeNull(Pageable pageable);

  Optional<BoardEntity> findByUid(String uid);

  BoardEntity save(BoardEntity board);
}
