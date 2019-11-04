package io.github.malczuuu.taskbook.core.repository;

import io.github.malczuuu.taskbook.core.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<UserEntity, Long> {

  Page<UserEntity> findAll(Pageable pageable);

  Optional<UserEntity> findById(Long id);

  Optional<UserEntity> findByUid(String uid);

  Optional<UserEntity> findByEmail(String email);

  UserEntity save(UserEntity user);

  void delete(UserEntity user);

  long count();
}
