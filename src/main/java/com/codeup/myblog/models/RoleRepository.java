package com.codeup.myblog.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {
    @Query("select ur.role from UserRole ur, User u where u.userName=?1 and ur.userId = u.id")
    List<String> ofUserWith(String username);
}
