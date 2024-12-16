import java.util.*;
import java.sql.*;
import java.time.*;

public class ControlFirma {
    private ListaConsegne listaConsegne;
    private ModuloFirma moduloFirma;
    private Notifica notifica;
    private int idUtente;
    private String firma;
    private Collection<Object[]> lista;
    private Connection connessione;
    private String query;
    private PreparedStatement statement;
    private ResultSet res;
    
    public ControlFirma(int idUtente){
        this.idUtente=idUtente;
        this.moduloFirma=new ModuloFirma(this.idUtente, this);
        this.lista=new ArrayList<Object[]>();
    }
    
    public void setFirma(String firma){
        this.firma=firma;
    }
    public LocalDate getData(){
        return LocalDate.now();
    }
    public void run(){
        int idFarmacia;
        try{
            this.connessione=DriverManager.getConnection("jdbc:mysql://localhost:3306/azienda","root","root");
            this.query="SELECT l.IDfarmacia FROM lavora l WHERE l.IDfarmacista='"+this.firma+"'";
            this.statement=connessione.prepareStatement(query);
            this.res=statement.executeQuery();
            this.res.next();
            idFarmacia=res.getInt(1);
            
            this.query="INSERT INTO consegna(IDfarmacia,IDcorriere,DataConsegna) values('"+idFarmacia+"', '"+idUtente+"', '"+getData().toString()+"')";
            this.statement=connessione.prepareStatement(query);
            this.statement.executeUpdate();

            this.query="select fa.Indirizzo, fa.Nome from SiOccupa si, Farmacia fa, Lavora l, Farmacista f, Prenota p, Ordina o where si.IDcorriere="+this.idUtente+" and ((si.IDfarmacia=fa.ID and fa.ID=l.IDfarmacia and l.IDfarmacista=f.ID and f.ID=p.IDfarmacista and p.DataConsegnaPrevista="+this.getData().toString()+" and fa.ID<>"+idFarmacia+" ) or (si.IDfarmacia==fa.ID and fa.ID<>"+idFarmacia+" and fa.ID=o.IDfarmacia and o.DataConsegnaPrevista="+this.getData().toString()+"))";
            this.statement=this.connessione.prepareStatement(this.query);
            this.res=this.statement.executeQuery();
            while(res.next()){
                Object row[]=new Object[2];
                row[0]=this.res.getString(1);
                row[1]=this.res.getString(2);
                this.lista.add(row);
            }
        }catch(SQLException e){
        }
        this.listaConsegne=new ListaConsegne(this.lista, this.idUtente);
        this.notifica=new Notifica("Consegna avvenuta con successo!");        
    }
}
