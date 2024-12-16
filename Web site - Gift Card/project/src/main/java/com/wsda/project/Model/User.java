package com.wsda.project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String nome;
    private String cognome;

    @OneToMany(mappedBy="proprietario")
    private List<Carta> carte;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    public User(){}

    public User(String username, String password, String nome, String cognome, List<Carta> carte, Role role){
        this.setUsername(username);
        this.setPassword(password);
        this.setNome(nome);
        this.setCognome(cognome);
        this.setListaCarte(carte);
        this.setRole(role);
    }

    public User(Integer id, String username, String password, String nome, String cognome, List<Carta> carte, Role role){
        this.setId(id);
        this.setUsername(username);
        this.setPassword(password);
        this.setNome(nome);
        this.setCognome(cognome);
        this.setListaCarte(carte);
        this.setRole(role);
    }

    private void setId(Integer id){
        this.id=id;
    }

    private void setUsername(String username){
        this.username=username;
    }

    private void setPassword(String password){
        this.password=password;
    }

    private void setNome(String nome){
        this.nome=nome;
    }

    private void setCognome(String cognome){
        this.cognome=cognome;
    }

    private void setListaCarte(List<Carta> carte){
        this.carte = carte;
    }

    private void setRole(Role role){
        this.role=role;
    }

    public Integer getId(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getNome(){
        return this.nome;
    }

    public String getCognome(){
        return this.cognome;
    }

    public List<Carta> getListaCarte(){
        return this.carte;
    }

    public Role getRole(){
        return this.role;
    }
}