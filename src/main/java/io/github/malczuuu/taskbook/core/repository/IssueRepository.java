package io.github.malczuuu.taskbook.core.repository;

import io.github.malczuuu.taskbook.core.entity.BoardEntity;
import io.github.malczuuu.taskbook.core.entity.IssueEntity;
import io.github.malczuuu.taskbook.core.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface IssueRepository extends Repository<IssueEntity, Long> {

  Page<IssueEntity> findAllByBoardAndArchivedTimeNull(BoardEntity board, Pageable pageable);

  Page<IssueEntity> findAllByBoardAndTitleAndArchivedTimeNull(
      BoardEntity board, String title, Pageable pageable);

  Optional<IssueEntity> findByBoardAndUid(BoardEntity board, String uid);

  IssueEntity save(IssueEntity issue);

  long countByBoard(BoardEntity board);

  long countByBoardAndArchivedTimeNull(BoardEntity board);

  Page<IssueEntity> findAllByAssigneeAndArchivedTimeNull(UserEntity assignee, Pageable pageable);

  Page<IssueEntity> findAllByAssigneeEmailAndArchivedTimeNull(
      String assigneeEmail, Pageable pageable);
}
