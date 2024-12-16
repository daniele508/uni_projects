

import java.util.*;
import java.sql.*;
import java.time.*;

public class ControlOrdina {
    private ModuloSpedizione moduloSpedizione;
    private ModuloRiepilogo moduloRiepilogo;
    private ListaMagazzino listaMagazzino;
    private Notifica notifica;
    private String[] dati;
    private Collection<Object> datiRiepilogativi;
    private Connection connessione;
    private String query;
    private PreparedStatement statement;
    private ResultSet res;
    private int idUtente;
    
    public ControlOrdina(int id, Collection<Object> row, ListaMagazzino listaMagazzino){
        this.datiRiepilogativi= row;
        this.idUtente=id;
        this.listaMagazzino=listaMagazzino;
        this.moduloSpedizione=new ModuloSpedizione(this.idUtente, this.datiRiepilogativi,this);
    }
    
    public void setDati(String[] dati){
        this.dati=dati;
    }
    
    public void riepilogo(){
        this.datiRiepilogativi.add(this.dati[0]);
        this.datiRiepilogativi.add(this.dati[1]);
        this.moduloRiepilogo=new ModuloRiepilogo(this.idUtente, this.datiRiepilogativi, this);
    }
    
    public void run(){
        String dataConsegnaPrevista=LocalDate.now().plusDays(7).toString();
        int idPrenotazione=0;
        int idOrdine=0;
        int id;
        try{
            this.connessione=DriverManager.getConnection("jdbc:mysql://localhost:3306/azienda","root","root");
            this.query="SELECT MAX(ID) FROM ordina";
            this.statement=connessione.prepareStatement(query);
            this.res=statement.executeQuery();
            if(this.res.next()){
                idOrdine=res.getInt(1);
            }
            
            this.query="SELECT MAX(ID) FROM prenota ";
            this.statement=connessione.prepareStatement(query);
            this.res=statement.executeQuery();
            if(this.res.next()){
                idPrenotazione=res.getInt(1);
            }
            
            if(idOrdine> idPrenotazione){
                id=idOrdine+1;
            }else if(idOrdine < idPrenotazione){
                id=idPrenotazione+1;
            }else{
                id=1;
            }
                    
            this.query= "INSERT INTO ordina(ID,IDimpiegato,IDfarmaco,IDdfarmacia,Quantità,DataConsegnaPrevista) values('"+id+"','"+this.idUtente+"','"+String.valueOf(this.datiRiepilogativi.toArray()[0])+"','"+this.dati[0]+"','"+this.dati[1]+"','" +dataConsegnaPrevista+"') ";
            this.statement=connessione.prepareStatement(query);
            statement.executeUpdate();                           
        }catch(SQLException e){}
        
        this.listaMagazzino.mostra();
        this.notifica= new Notifica("Ordine effettuato con successo!");
    }
}
