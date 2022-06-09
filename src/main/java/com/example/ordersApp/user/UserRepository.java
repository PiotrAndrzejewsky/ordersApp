package com.example.ordersApp.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    UserEntity findByUsername(String username);
}
