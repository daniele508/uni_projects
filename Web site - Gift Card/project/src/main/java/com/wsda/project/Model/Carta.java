package com.wsda.project.Model;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Carta{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String numero;
    private LocalDate dataScadenza;
    private float saldo;
    private int cvv;
    private boolean block;

    @ManyToOne
	@JoinColumn(name="user_id")
    private User proprietario;

    @OneToMany(mappedBy = "carta")
    List<Movimento> movimenti;

    public Carta(){}

    public Carta(String numero, LocalDate data, float saldo, int cvv, boolean block, User proprietario, List<Movimento> movimenti){
        this.setNumero(numero);
        this.setData(data);
        this.setSaldo(saldo);
        this.setCvv(cvv);
        this.setBlock(block);
        this.setOwner(proprietario);
        this.setMovimenti(movimenti);
    }

    public Carta(int id, String numero, LocalDate data, float saldo, int cvv, boolean block, User proprietario, List<Movimento> movimenti){
        this(numero, data, saldo, cvv, block, proprietario, movimenti);
        this.setId(id);
    }

    private void setId(Integer id){
        this.id=id;
    }

    private void setNumero(String numero){
        this.numero=numero;
    }

    private void setData(LocalDate data){
        this.dataScadenza=data;
    }

    private void setSaldo(float saldo){
        this.saldo=saldo;
    }

    private void setCvv(int cvv){
        this.cvv=cvv;
    }

    private void setBlock(boolean block){
        this.block=block;
    }

    private void setOwner(User proprietario){
        this.proprietario=proprietario;
    }

    private void setMovimenti(List<Movimento> movimenti){
        this.movimenti=movimenti;
    }

    public Integer getId(){
        return this.id;
    }

    public String getNumero(){
        return this.numero;
    }

    public LocalDate getData(){
        return this.dataScadenza;
    }

    public float getSaldo(){
        return this.saldo;
    }

    public int getCvv(){
        return this.cvv;
    }

    public boolean getBlock(){
        return this.block;
    }

    public User getOwner(){
        return this.proprietario;
    }

    public List<Movimento> getMovimenti(){
        return this.movimenti;
    }
}
