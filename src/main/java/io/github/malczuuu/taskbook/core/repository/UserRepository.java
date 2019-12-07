package io.github.malczuuu.taskbook.core.repository;

import io.github.malczuuu.taskbook.core.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<UserEntity, Long> {

  Page<UserEntity> findAll(Pageable pageable);

  // lower(u.name) like lower(concat('%', ?1,'%'))
  @Query(
      "select u from UserEntity u where lower(u.firstName) like lower(concat(?1, '%')) or lower(u.lastName) like lower(concat(?1, '%')) or lower(u.email) like lower(concat(?1, '%'))")
  Page<UserEntity> findAllByQuery(String query, Pageable pageable);

  Optional<UserEntity> findById(Long id);

  Optional<UserEntity> findByUid(String uid);

  Optional<UserEntity> findByEmail(String email);

  UserEntity save(UserEntity user);

  void delete(UserEntity user);

  long count();
}
