import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.sql.*;

public class ControlSegnala{
	private ModuloSegnalazione moduloSegnalazione;
	private Notifica notifica;
	private MenuPrincipale menuPrincipale;
	private String dataConsegna;
	private Collection<Object> dati;
	private Collection<Object[]> datiRiepilogativi;
	private Connection connessione;
	private PreparedStatement statement;
	private String query;
	private ResultSet res;

	public ControlSegnala(int idUtente){
		this.idUtente=idUtente;
		this.datiRiepilogativi=new ArrayList<Object[]>();
	}

	public LocalDate getData(){
		return LocalDate.now();
	}

	public void setDati(Collection<Obejct> dati){
		this.dati=dati;
	}

	public LocalTime getOrario(){
		return LocalTime.now();
	}

	public void run1(){
		if(this.getOrario().toString()=="20:00:00"){
			try{
				this.connessione=DriverManager.getConnection("jdbc:mysql::/localhost:3306/azienda","root","root");
				this.query="select IDfarmacia from lavora where idfarmacista='"+this.idUtente+"'";
				this.statement=this.connessione.prepareStatement(this.query);
				this.res=this.statement.executeQuery();
				this.res.next();
				int idFarmacia=this.res.getInt(1);
				this.query="select p.id, f.nome, p.quantità, p.dataConsegnaprevista form prenota p, farmaco f where DataConsegnaPrevista='"+this.getData().toString()+"' and idfarmacista='"+this.idUtente+"' and p.idfarmaco=f.id";
				this.statement=this.connessione.prepareStatement(this.query);
				this.res=this.statement.executeQuery();
				while(this.res.next()){
					Object row[]=Object row[4];
					row[0]=this.res.getInt();
					row[1]=this.res.getString();
					row[2]=this.res.getInt();
					row[3]=this.res.getDate().toString();
					this.datiRiepilogativi.add(row);
				}
				this.moduloSegnalazione=new ModuloSegnalazione(this.idUtente, this.datiRiepilogativi, this);

			}catch(SQLException e){
			}
		}
	}

	public void run2(){
		try{
			int id=this.dati.toArray()[0];
			this.query="insert into segnalazione(id, dataSegnalazione) values('"+id+"'','"+this.getData().toString()+"')";
			this.statement=this.connessione.prepareStatement(this.query);
			this.res=this.statement.executeUpdate();
			this.query="insert into segnala(id, idfarmacista) values('"+id+"','"+this.idUtente+"')";
			this.statement=this.connessione.prepareStatement(this.query);
			this.res=this.statement.executeUpdate();
		}catch(SQLException e){
		}
		this.menuPrincipale=new MenuPrincipale(this.idUtente);
		this.notifica= new Notifica("Segnalazione avvenuta con successo!");	
	}

}