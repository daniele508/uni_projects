package com.wsda.project.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Movimento {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private float importo;
    private LocalDate data;
    private String numero;

    @ManyToOne
    @JoinColumn(name="card_id")
    private Carta carta;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Movimento(){}

    public Movimento(int id, float importo, LocalDate data, String numero, Carta carta, User user){
        this.setId(id);
        this.setImporto(importo);
        this.setData(data);
        this.setNumero(numero);
        this.setCarta(carta);
        this.setUser(user);
    }

    public Movimento(float importo, LocalDate data, String numero, Carta carta, User user){
        this.setImporto(importo);
        this.setData(data);
        this.setNumero(numero);
        this.setCarta(carta);
        this.setUser(user);
    }

    private void setId(int id){
        this.id=id;
    }

    private void setImporto(float importo){
        this.importo=importo;
    }

    private void setData(LocalDate data){
        this.data=data;
    }

    private void setNumero(String numero){
        this.numero=numero;
    }

    private void setCarta(Carta carta){
        this.carta=carta;
    }

    private void setUser(User user){
        this.user=user;
    }

    public int getId(){
        return this.id;
    }

    public float getImporto(){
        return this.importo;
    }

    public LocalDate getData(){
        return this.data;
    }

    public String getNumero(){
        return this.numero;
    }

    public Carta getCarta(){
        return this.carta;
    }

    public User getUser(){
        return this.user;
    }
}
