package com.wsda.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wsda.project.Model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findByName(String name);
}
