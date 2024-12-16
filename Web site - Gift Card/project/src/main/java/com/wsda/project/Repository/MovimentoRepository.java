package com.wsda.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wsda.project.Model.Movimento;
import com.wsda.project.Model.User;

import java.util.List;


public interface MovimentoRepository extends JpaRepository<Movimento, Integer>{
    List<Movimento> findByUser(User user);
}
