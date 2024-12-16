
import java.sql.*;
import java.util.Collection;
import java.util.ArrayList;
import java.time.LocalDate;
public class ControlPrenota {
    private ModuloPrenotazione moduloPrenotazione;
    private ModuloScadenza moduloScadenza;
    private ModuloRiepilogo moduloRiepilogo;
    private Notifica notifica;
    private ListaFarmaci listaFarmaci;
    private Collection<Object[]> lista;
    private String dati;
    private String dataScadenza;
    private Collection<Object> datiRiepilogativi;
    private Collection<Object> dataScadenzaFarmaci;
    private Collection<Object[]> prenotazioniPeriodiche;
    private Connection connessione;
    private PreparedStatement statement;
    private ResultSet res;
    private String query;
    private int idUtente;
    
    public ControlPrenota(int id,Collection<Object> row, ListaFarmaci listaFarmaci){
        this.datiRiepilogativi=row;
        this.listaFarmaci=listaFarmaci;
        this.idUtente=id;
        this.lista= new ArrayList<Object[]>();
        this.moduloPrenotazione=new ModuloPrenotazione(this.idUtente, this.datiRiepilogativi, this);
    }
    
    public ControlPrenota(Collection<Object> row){
        this.datiRiepilogativi=row;
        this.lista= new ArrayList<Object[]>();
        this.moduloPrenotazione= new ModuloPrenotazione(this.idUtente, this.datiRiepilogativi, this);
    }
    
    public ControlPrenota(int idFarmacista){
        this.idUtente=idFarmacista;
        this.lista= new ArrayList<Object[]>();
        this.datiRiepilogativi= new ArrayList<Object>();
        String url="jdbc:mysql://localhost:3306/";
        String user="root";
        String pass="root";
        int idFarmacia;
        try{
            this.connessione=DriverManager.getConnection(url+"azienda",user,pass);
            this.query="select l.IDfarmacia from Lavora l where l.IDFarmacista='"+idFarmacista+"'";
            this.statement=this.connessione.prepareStatement(query);
            this.res=this.statement.executeQuery();
            this.res.next();
            idFarmacia=this.res.getInt(1);
            this.query="select f.ID, f.Nome, f.PrincipioAttivo, c.DataScadenza from Farmaco f, Continene c where f.ID=c.IDfarmaco and c.IDfarmacia="+idFarmacia;
            this.connessione=DriverManager.getConnection(url+"farmacia",user,pass);
            this.statement=this.connessione.prepareStatement(this.query);
            this.res=this.statement.executeQuery();
            while(res.next()){
                Object row[]=new Object[3];
                row[0]=this.res.getInt(1);
                row[1]=this.res.getString(2);
                row[2]=this.res.getString(3);
                this.lista.add(row);
                this.dataScadenzaFarmaci.add(this.res.getDate(4));
            }
            
            for(int i=0; i<this.dataScadenzaFarmaci.size(); i++){
                if(this.dataScadenzaFarmaci.toArray()[i].equals(this.getData())){
                    this.datiRiepilogativi.clear();
                    Object row[]=(Object[])this.lista.toArray()[i];
                    for(int k=0; k<row.length-1; k++){
                        this.datiRiepilogativi.add(row[k]);
                    }
                    this.moduloPrenotazione=new ModuloPrenotazione(idFarmacista, this.datiRiepilogativi, this);
                }
            }
            
            this.connessione=DriverManager.getConnection(url+"azienda",user,pass);
            this.query="select f.ID, f.Nome, f.PrincipioAttivo, p.Ripetizione from Farmaco f, Periodico p, Farmacista fa, Lavora l where f.ID=p.IDfarmaco and p.IDfarmacista=fa.ID and fa.ID=l.IDfarmacista and l.IDfarmacia="+idFarmacia;
            this.statement=this.connessione.prepareStatement(this.query);
            this.res=this.statement.executeQuery();
            Object prenotazionePeriodica[]=new Object[5]; 
            while(this.res.next()){
                prenotazionePeriodica[0]=this.res.getInt(1);
                prenotazionePeriodica[1]=this.res.getString(2);
                prenotazionePeriodica[2]=this.res.getString(3);
                prenotazionePeriodica[3]=this.res.getInt(4);
                prenotazionePeriodica[4]=idFarmacia;
                this.prenotazioniPeriodiche.add(prenotazionePeriodica);
            }
            for(int i=0; i<this.prenotazioniPeriodiche.size(); i++){
                prenotazionePeriodica=(Object[])this.prenotazioniPeriodiche.toArray()[i];
                if(this.getIntervalloMesi(this.getData().toString(), this.getUltimaConsegna(prenotazionePeriodica))<=this.getRipetizione(prenotazionePeriodica)){
                    for(int j=0; j<prenotazionePeriodica.length-2; i++){
                        this.datiRiepilogativi.add(prenotazionePeriodica[j]);
                    }
                    this.moduloPrenotazione=new ModuloPrenotazione(idFarmacista, this.datiRiepilogativi, this);
                }
            }
            
        }catch(SQLException e){
        }
    }
    
    public int getIntervalloMesi(String data1, String data2){
        return LocalDate.parse(data1).until(LocalDate.parse(data2)).getMonths();
    }
    
    public LocalDate getData(){
        return LocalDate.now();
    }
    
    public int getRipetizione(Object[] prenotazionePeriodica){
        return (int)prenotazionePeriodica[3];
    }
    
    public String getUltimaConsegna(Object[] prenotazionePeriodica){
        String ultima="";
        this.query="select c.DataConsegna from Conegna c, Lavora l, Farmacista f, Periodico p where c.IDfarmacia="+prenotazionePeriodica[4]+" and l.IDfarmacia="+prenotazionePeriodica[4]+" and l.IDfarmacista=f.ID and f.ID=p.IDfarmacista and p.IDfarmaco="+prenotazionePeriodica[0]+" order by c.DataConsegna DESC";
        try{
            this.statement=this.connessione.prepareStatement(this.query);
            this.res=this.statement.executeQuery();
            this.res.next();
            ultima=this.res.getDate(1).toString();
        }catch(SQLException e){
        }    
        return ultima;
    }
    
    public void setDati(String dati){
        this.dati=dati;
    }
    
    public void scadenza(){
        this.query="select DataDiScadenza from Farmaco where ID="+String.valueOf(this.datiRiepilogativi.toArray()[0]);
        String url="jdbc:mysql://localhost:3306/azienda";
        String user="root";
        String pass="pass";
        try{
            this.connessione=DriverManager.getConnection(url,user,pass);
            this.statement=this.connessione.prepareStatement(this.query);
            this.res=this.statement.executeQuery();
            this.res.next();
            this.dataScadenza=this.res.getDate(1).toString();
            if(this.getIntervalloMesi(this.dataScadenza, this.getData().toString())<=2){
                this.moduloScadenza=new ModuloScadenza(this.idUtente, this);
            }
            else{
                this.riepilogo();
            }
        }catch(SQLException e){
        }
    }
    
    public void riepilogo(){
        this.moduloRiepilogo=new ModuloRiepilogo(this.idUtente,this.datiRiepilogativi, this.dati);
    }
    
    public void run(){
        int id;
        int maxp=0;
        int maxo=0;
        try{
            
            this.query="select max(ID) from Prenota";
            this.statement=this.connessione.prepareStatement(this.query);
            this.res=this.statement.executeQuery();
            if(this.res.next()){
                maxp=this.res.getInt(1);
            }
            this.query="select max(ID) from Ordina";
            this.statement=this.connessione.prepareStatement(this.query);
            this.res=this.statement.executeQuery();
            if(this.res.next()){
                maxo=this.res.getInt(1);
            }
            if(maxp>maxo){
                id=maxp+1;
            }else if(maxo>maxp){
                id=maxo+1;
            }else{
                id=1;
            }
            
            this.query="insert into Prenota (ID, IDfarmacista, IDfarmaco, Quantità, DataConsegnaPrevista) values ('"+id+"' , '"+this.idUtente+"' , '"+String.valueOf(this.datiRiepilogativi.toArray()[0])+"' , '"+this.dati+"' , '"+this.getData().plusDays(7).toString()+"')";
            this.statement=this.connessione.prepareStatement(this.query);
            this.statement.executeUpdate();
            
            if(this.listaFarmaci==null){
                this.query="select l.IDfarmacia from Lavora l where l.IDfarmacista='"+this.idUtente+"'";
                this.statement=this.connessione.prepareStatement(query);
                this.res=this.statement.executeQuery();
                this.res.next();
                int idFarmacia=this.res.getInt(1);
                this.connessione=DriverManager.getConnection("jdbc:mysql://localhost:3306/farmacia","root","root");
                this.query="select distinct IDfarmaco from Contiene where IDfarmacia="+idFarmacia;
                this.statement=this.connessione.prepareStatement(this.query);
                this.res=this.statement.executeQuery();
                Collection<Integer> idi=new ArrayList<Integer>();
                while(this.res.next()){
                    idi.add(this.res.getInt(1));
                }
                for(int i=0; i<idi.size(); i++){
                    this.query="select f.ID, f.Nome, f.PrincipioAttivo, sum(c.Quantità) from Farmaco f, Contiene c where f.ID=c.IDfarmaco and c.IDfarmacia="+idFarmacia+" and f.ID="+String.valueOf(idi.toArray()[i]);
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
            }else{
                this.listaFarmaci.mostra();
            }
        }catch(SQLException e){
        }
        this.notifica=new Notifica("Prenotazione avvenuta con successo!");
    }
    
    
}
