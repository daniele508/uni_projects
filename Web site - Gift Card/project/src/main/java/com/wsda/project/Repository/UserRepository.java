package com.wsda.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wsda.project.Model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    User findByUsername(String username);
}
