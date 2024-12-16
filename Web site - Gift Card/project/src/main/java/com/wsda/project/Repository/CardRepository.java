package com.wsda.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wsda.project.Model.Carta;

import jakarta.transaction.Transactional;

public interface CardRepository extends JpaRepository<Carta, Integer>{

    Carta findByNumero(String numero);

    @Transactional
    @Modifying
    @Query(value = "update carta c set saldo = :saldo where c.numero = :numero", nativeQuery = true)
    int modifySaldo(@Param("numero") String numero, @Param("saldo") float saldo);

    @Transactional
    @Modifying
    @Query(value = "update carta c set block = :block where c.numero = :numero", nativeQuery = true)
    int modifyStatus(@Param("block") boolean block, @Param("numero") String numero);
}
