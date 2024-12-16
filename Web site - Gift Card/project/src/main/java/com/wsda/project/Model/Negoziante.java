package com.wsda.project.Model;
import java.util.List;

import jakarta.persistence.Entity;

@Entity
public class Negoziante extends User{

    private String negozio;

    public Negoziante(){}

    public Negoziante(String username, String password, String nome, String cognome, List<Carta> carte, Role role, String negozio){
        super(username, password, nome, cognome, carte, role);
        this.setNegozio(negozio);
    }

    public Negoziante(int id, String username, String password, String nome, String cognome, List<Carta> carte, Role role, String negozio){
        super(id, username, password, nome, cognome, carte, role);
        this.setNegozio(negozio);
    }


    private void setNegozio(String negozio){
        this.negozio=negozio;
    }

    public String getNegozio(){
        return this.negozio;
    }
}
