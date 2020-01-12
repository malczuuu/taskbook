package io.github.malczuuu.taskbook.core.repository;

import io.github.malczuuu.taskbook.core.entity.CommentEntity;
import io.github.malczuuu.taskbook.core.entity.IssueEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface CommentRepository extends Repository<CommentEntity, Long> {

  Page<CommentEntity> findAllByIssueOrderByCreatedTimeDesc(IssueEntity issue, Pageable pageable);

  Optional<CommentEntity> findByIssueAndId(IssueEntity issue, Long id);

  CommentEntity save(CommentEntity comment);
}
