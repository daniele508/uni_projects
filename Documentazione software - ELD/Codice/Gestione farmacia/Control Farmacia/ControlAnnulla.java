import java.sql.*;
import java.util.Collection;
import java.util.ArrayList;
import java.time.LocalDate;
public class ControlAnnulla {
    private ModuloRiepilogo moduloRiepilogo;
    private Collection<Object> datiRiepilogativi;
    private Notifica notifica;
    private ListaOrdini listaOrdini;
    private ListaPrenotazioni listaPrenotazioni;
    private int idUtente;
    private Collection<Object[]> lista;
    private Connection connessione;
    private PreparedStatement statement;
    private ResultSet res;
    private String query;
    
    public ControlAnnulla(int idUtente, Collection<Object> row){
        this.idUtente=idUtente;
        this.datiRiepilogativi=row;
        this.lista= new ArrayList<Object[]>();
        this.moduloRiepilogo= new ModuloRiepilogo(this.idUtente, this.datiRiepilogativi, this);
    }
    
    public void run(){
        int tipoUtente=this.idUtente/1000;
        String url="jdbc:mysql://localhost:3306/azienda";
        String user="root";
        String pass="root";
        
        try{
            this.connessione=DriverManager.getConnection(url,user,pass);
            this.query="delete from Prenota where ID="+String.valueOf(this.datiRiepilogativi.toArray()[0])+"; delete from Ordina where ID="+String.valueOf(this.datiRiepilogativi.toArray()[0])+" and IDfarmacia="+String.valueOf(this.datiRiepilogativi.toArray()[1]);
            this.statement=this.connessione.prepareStatement(this.query);
            this.statement.executeUpdate();
            
            if(tipoUtente==2){
                this.query="select l.IDfarmacia from Lavora l where l.IDFarmacista='"+this.idUtente+"'";
                this.statement=this.connessione.prepareStatement(query);
                this.res=this.statement.executeQuery();
                this.res.next();
                int idFar=this.res.getInt(1);
                this.query="select p.ID, fa.Nome, p.Quantità, p.DataConsegnaPrevista from Prenota p, Farmacista f, Lavora l, Farmaco fa where p.IDfarmaco=fa.ID and p.IDfarmacista=f.ID and f.ID=l.IDfarmacista and l.IDfarmacia="+idFar;
                this.statement=this.connessione.prepareStatement(query);
                this.res=this.statement.executeQuery();
                while(res.next()){
                    Object row3[]=new Object[5];
                    row3[0]=this.res.getInt(1);
                    row3[1]=this.res.getString(2);
                    row3[2]=this.res.getInt(3);
                    row3[3]=this.res.getDate(4).toString();
                    row3[4]=LocalDate.now().until(LocalDate.parse((String)row3[3])).getDays()>2;
                    this.lista.add(row3);
                }
                this.query="select o.ID, f.Nome, o.Quantità, o.DataConsegnaPrevista from Ordina o, Farmaco f where o.IDFarmacia="+idFar+" and o.IDFarmaco=f.ID";
                this.statement=this.connessione.prepareStatement(query);
                this.res=this.statement.executeQuery();
                while(res.next()){
                    Object row32[]=new Object[5];
                    row32[0]=this.res.getInt(1);
                    row32[1]=this.res.getString(2);
                    row32[2]=this.res.getInt(3);
                    row32[3]=this.res.getDate(4).toString();
                    row32[4]=LocalDate.now().until(LocalDate.parse((String)row32[3])).getDays()>2;
                    this.lista.add(row32);
                }
                this.listaPrenotazioni=new ListaPrenotazioni(this.lista, this.idUtente);
            }else{
                this.query="select o.ID, o.IDfarmacia, f.Nome, o.Quantità, o.DataConsegnaPrevista from Ordina o, Farmaco f where o.IDFarmaco=f.ID";
                this.statement=this.connessione.prepareStatement(query);
                this.res=this.statement.executeQuery();
                while(this.res.next()){
                    Object row6[]=new Object[5];
                    row6[0]=this.res.getInt(1);
                    row6[1]=this.res.getInt(2);
                    row6[2]=this.res.getString(3);
                    row6[3]=this.res.getInt(4);
                    row6[4]=this.res.getDate(5).toString();
                    this.lista.add(row6);
                }
                this.query="select p.ID, f.ID, fa.Nome, p.Quantità, p.DataConsegnaPrevista from Farmacia f, Lavora l, Farmacista far, Prenota p, Farmaco fa where f.ID=l.IDFarmacia and l.IDfarmacista=far.ID and p.IDfarmacista=far.ID and p.IDfarmaco=fa.ID";
                this.statement=this.connessione.prepareStatement(query);
                this.res=this.statement.executeQuery();
                while(this.res.next()){
                    Object row62[]= new Object[5];
                    row62[0]=this.res.getInt(1);
                    row62[1]=this.res.getInt(2);
                    row62[2]=this.res.getString(3);
                    row62[3]=this.res.getInt(4);
                    row62[4]=this.res.getDate(5).toString();
                    this.lista.add(row62);
                }
                this.listaOrdini=new ListaOrdini(this.lista, this.idUtente);
            }
        }catch(SQLException e){
        }
        
        String messaggio;
        if(tipoUtente==1){
            messaggio="Ordine annullato con successo!";
        }else{
            messaggio="Prenotazione annullata con successo!";
        }
        this.notifica=new Notifica(messaggio);
        
    }
}
