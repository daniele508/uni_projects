import java.sql.*;
import java.util.Collection;
import java.util.ArrayList;
public class ControlCarico {
    private Connection connessione;
    private ListaFarmaci listaFarmaci;
    private ListaMagazzino listaMagazzino;
    private Notifica notifica;
    private String query;
    private PreparedStatement statement;
    private ResultSet res;
    private String dati[];
    private Collection<Object> datiRiepilogativi;
    private ModuloCarico moduloCarico;
    private int idUtente;
    private Collection<Object[]> lista;
    
    public void setDati(String dati[]){
        this.dati=dati;
    }
    
    public ControlCarico(int idUtente, Collection<Object> row){
        this.idUtente=idUtente;
        this.datiRiepilogativi=row;
        this.lista=new ArrayList<Object[]>();
        this.moduloCarico= new ModuloCarico(this.datiRiepilogativi, this.idUtente, this);
    }
    
    public void run(){
        int tipoUtente=this.idUtente/1000;
        String url="jdbc:mysql://localhost:3306/azienda"; 
        String user="root";
        String pass="root";
        
        try{
            int idFarmacia=0;
            this.connessione=DriverManager.getConnection(url,user,pass);
            if(tipoUtente==1){
                this.query="select QuantitàMagazzino from Farmaco where ID="+String.valueOf(this.datiRiepilogativi.toArray()[0]);
                this.statement=this.connessione.prepareStatement(query);
                this.res=this.statement.executeQuery();
                this.res.next();
                int quantita=this.res.getInt(1);
                this.query="update Farmaco set QuantitàMagazzino="+(quantita+Integer.parseInt(this.dati[0]))+" where ID="+String.valueOf(this.datiRiepilogativi.toArray()[0])+" and DataDiScadenza='"+this.dati[1]+"'";
            }else{
                this.query="select l.IDfarmacia from Lavora l where l.IDfarmacista='"+this.idUtente+"'";
                this.statement=this.connessione.prepareStatement(this.query);
                this.res=this.statement.executeQuery();
                this.res.next();
                idFarmacia=this.res.getInt(1);
                this.connessione=DriverManager.getConnection("jdbc:mysql://localhost:3306/farmacia",user,pass);
                this.query="select Quantità from Contiene where IDfarmaco="+String.valueOf(this.datiRiepilogativi.toArray()[0])+" and IDfarmacia="+idFarmacia;
                this.statement=this.connessione.prepareStatement(query);
                this.res=this.statement.executeQuery();
                this.res.next();
                int quantita=this.res.getInt(1);
                this.query="update Contiene set Quantità="+(quantita+Integer.parseInt(this.dati[0]))+" where IDfarmaco="+String.valueOf(this.datiRiepilogativi.toArray()[0])+" and DataDiScadenza='"+this.dati[1]+"' and IDfarmacia="+idFarmacia;
            }
            
            this.statement=this.connessione.prepareStatement(this.query);
            this.statement.executeUpdate();
            
            if(tipoUtente==1){
                this.query="select distinct ID from Farmaco";
                this.statement=this.connessione.prepareStatement(this.query);
                this.res=this.statement.executeQuery();
                Collection<Integer> idi=new ArrayList<Integer>();
                while(this.res.next()){
                    idi.add(this.res.getInt(1));
                }
                for(int i=0; i<idi.size(); i++){
                    this.query="select f.ID, f.Nome, f.PrincipioAttivo, sum(f.QuantitàMagazzino) from Farmaco f where f.ID="+String.valueOf(idi.toArray()[i]);
                    this.statement=this.connessione.prepareStatement(query);
                    this.res=this.statement.executeQuery();
                    this.res.next();
                    Object row5[]=new Object[4];
                    row5[0]=this.res.getInt(1);
                    row5[1]=this.res.getString(2);
                    row5[2]=this.res.getString(3);
                    row5[3]=this.res.getInt(4);
                    this.lista.add(row5);
                }
                this.listaMagazzino=new ListaMagazzino(this.lista, this.idUtente);
            }else{
                this.query="select distinct IDfarmaco from Contiene where IDfarmacia="+idFarmacia;
                this.statement=this.connessione.prepareStatement(this.query);
                this.res=this.statement.executeQuery();
                Collection<Integer> id=new ArrayList<Integer>();
                while(this.res.next()){
                    id.add(this.res.getInt(1));
                }
                for(int i=0; i<id.size(); i++){
                    this.query="select f.ID, f.Nome, f.PrincipioAttivo, sum(c.Quantità) from Farmaco f, Contiene c where f.ID=c.IDfarmaco and c.IDfarmacia="+idFarmacia+" and f.ID="+String.valueOf(id.toArray()[i]);
                    this.statement=this.connessione.prepareStatement(query);
                    this.res=this.statement.executeQuery();
                    this.res.next();
                    Object row2[]=new Object[4];
                    row2[0]=this.res.getInt(1);
                    row2[1]=this.res.getString(2);
                    row2[2]=this.res.getString(3);
                    row2[3]=this.res.getInt(4);
                    this.lista.add(row2);
                }
                this.listaFarmaci=new ListaFarmaci(this.lista, this.idUtente);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        this.notifica=new Notifica("Carico avvenuto con successo!");
        
    }
    
}
