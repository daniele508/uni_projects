

import java.util.*;
import java.sql.*;

public class ControlLogin {
    private Utente utente;
    private String[] credenziali=new String[2];
    private Connection connessione;
    private String query;
    private PreparedStatement statement;
    private ResultSet res;
    private Notifica notifica;
    private ModuloLogin moduloLogin;
    private MenuPrincipale menuPrincipale;
    
    public void setCredenziali(String[] credenziali){
        this.credenziali[0]=credenziali[0];
        this.credenziali[1]=credenziali[1];
    }
    
    public void run() {
        int tipoUtente=(Integer.parseInt(this.credenziali[0]))/1000;
        
        String nome,cognome,email;
        try{
            switch(tipoUtente){
                case 0:
                    this.connessione=DriverManager.getConnection("jdbc:mysql://localhost:3306/azienda","root","root");                          
                    this.query="SELECT ID, Password, Email, Nome, Cognome FROM Corriere WHERE ID='"+credenziali[0]+"' AND Password='"+credenziali[1]+"'";
                    this.statement=connessione.prepareStatement(query);
                    this.res=statement.executeQuery();        
                    res.next();
                    nome=res.getString("Nome");
                    cognome=res.getString("Cognome");
                    email=res.getString("Email");
                    this.utente=new Corriere(Integer.parseInt(this.credenziali[0]),this.credenziali[1],email,nome,cognome);
                    break;
                case 1:
                    this.connessione=DriverManager.getConnection("jdbc:mysql://localhost:3306/azienda","root","root");                          
                    this.query="SELECT ID, Password, Email, Nome, Cognome FROM Impiegato WHERE ID='"+credenziali[0]+"' AND Password='"+credenziali[1]+"'";
                    this.statement=connessione.prepareStatement(query);
                    this.res=statement.executeQuery();        
                    res.next();
                    nome=res.getString("Nome");
                    cognome=res.getString("Cognome");
                    email=res.getString("Email");
                    this.utente=new Impiegato(Integer.parseInt(this.credenziali[0]),this.credenziali[1],email,nome,cognome);
                    break;
                case 2:
                    this.connessione=DriverManager.getConnection("jdbc:mysql://localhost:3306/azienda","root","root");                          
                    this.query="SELECT ID, Password, Email, Nome, Cognome FROM Farmacista WHERE ID='"+credenziali[0]+"' AND Password='"+credenziali[1]+"'";
                    this.statement=connessione.prepareStatement(query);
                    this.res=statement.executeQuery();        
                    res.next();
                    nome=res.getString("Nome");
                    cognome=res.getString("Cognome");
                    email=res.getString("Email");
                    this.query="SELECT f.Telefono, l.IDfarmacia FROM Farmacista f, Lavora l WHERE f.ID='"+credenziali[0]+"' AND f.Password='"+credenziali[1]+"' AND l.IDfarmacista=f.ID";
                    this.statement=connessione.prepareStatement(query);
                    this.res=statement.executeQuery();
                    res.next();
                    this.utente=new Farmacista(Integer.parseInt(this.credenziali[0]),this.credenziali[1],email,res.getInt("IDFarmacia"),nome,cognome,res.getString("Telefono"));
                    break;
            }
        }catch(SQLException e){
        }
       if(this.utente==null){
           moduloLogin= new ModuloLogin();
           this.notifica=new Notifica("I dati inseriti non sono corretti");
       }
       else{
            this.menuPrincipale=new MenuPrincipale(this.utente);
            if(this.utente instanceof Farmacista){
                this.menuPrincipale.start();
            }
        }
    }
    
}
