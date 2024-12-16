import java.sql.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.ArrayList;
public class ControlVisualizza {
   private Collection<Object[]> lista;
   private String nomeLista;
   private ListaMagazzino listaMagazzino=null;
   private ListaFarmaci listaFarmaci=null;
   private ListaOrdini listaOrdini=null;
   private ListaPrenotazioni listaPrenotazioni=null;
   private ListaSegnalazioni listaSegnalazioni=null;
   private ListaConsegne listaConsegne=null;
   private String query;
   private Connection connessione=null;
   private String filtro="";
   private int idUtente;
   private ResultSet res;
   private PreparedStatement statement;
   
   public ControlVisualizza(int idUtente, String nomeLista){
       this.idUtente=idUtente;
       this.nomeLista=nomeLista;
       this.lista=new ArrayList<Object[]>();
   }
   public LocalDate getData(){
       return LocalDate.now();
   }
   
   public void setFiltro(String filtro){
       this.filtro=filtro;
   }
   
   public void run(){
       String url="jdbc:mysql://localhost:3306/azienda";
       String user="root";
       String pass="root";
       try{
            this.connessione=DriverManager.getConnection(url,user,pass);
            switch(this.nomeLista){
                
                case "consegne":
                    this.query="select max(ID) from Consegna";
                    this.statement=this.connessione.prepareStatement(this.query);
                    this.res=this.statement.executeQuery();
                    this.res.next();
                    String idc=this.res.getString(1);
                    this.query="select fa.Indirizzo, fa.Nome from SiOccupa si, Farmacia fa, Lavora l, Farmacista f, Prenota p, Ordina o where si.IDcorriere='000"+this.idUtente+"' and ((si.IDfarmacia=fa.ID and fa.ID=l.IDfarmacia and l.IDfarmacista=f.ID and f.ID=p.IDfarmacista and p.DataConsegnaPrevista='"+this.getData().toString()+"') or (si.IDfarmacia=fa.ID and fa.ID=o.IDfarmacia and o.DataConsegnaPrevista='"+this.getData().toString()+"'))";
                    if(!this.filtro.isBlank()){
                        this.query= this.query+"and (fa.Indirizzo='"+this.filtro+"' or fa.Nome='"+this.filtro+"')";
                    }
                    this.statement=this.connessione.prepareStatement(this.query);
                    this.res=this.statement.executeQuery();
                    while(res.next()){
                        Object row1[]=new Object[3];
                        idc=String.valueOf(Integer.parseInt(idc)+1);
                        row1[0]=idc;
                        row1[1]=this.res.getString(1);
                        row1[2]=this.res.getString(2);
                        this.lista.add(row1);
                    }
                    this.listaConsegne=new ListaConsegne(this.lista, this.idUtente);
                    if(!this.filtro.isBlank()){
                        this.listaConsegne.aggiorna(this.lista);
                    }
                    break;
                    
                case "farmaci":
                    this.query="select l.IDfarmacia from Lavora l where l.IDfarmacista='"+this.idUtente+"'";
                    this.statement=this.connessione.prepareStatement(query);
                    this.res=this.statement.executeQuery();
                    this.res.next();
                    int idFarmacia=this.res.getInt(1);
                    url="jdbc:mysql://localhost:3306/farmacia";
                    this.connessione=DriverManager.getConnection(url,user,pass);
                    this.query="select distinct IDfarmaco from Contiene where IDfarmacia="+idFarmacia;
                    this.statement=this.connessione.prepareStatement(this.query);
                    this.res=this.statement.executeQuery();
                    Collection<Integer> id=new ArrayList<Integer>();
                    while(this.res.next()){
                        id.add(this.res.getInt(1));
                    }
                    for(int i=0; i<id.size(); i++){
                        this.query="select f.ID, f.Nome, f.PrincipioAttivo, sum(c.Quantità) from Farmaco f, Contiene c where f.ID=c.IDfarmaco and c.IDfarmacia="+idFarmacia+" and f.ID="+String.valueOf(id.toArray()[i]);
                        if(!this.filtro.isBlank()){
                            this.query=this.query+" and (or f.ID="+this.filtro+" or f.Nome="+this.filtro+" or f.PrincipioAttivo="+this.filtro+")";
                        }
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
                    if(!this.filtro.isBlank()){
                        this.listaFarmaci.aggiorna(this.lista);
                    }
                    break;
                    
                case "prenotazioni":
                    this.query="select l.IDfarmacia from Lavora l where l.IDFarmacista='"+this.idUtente+"'";
                    this.statement=this.connessione.prepareStatement(query);
                    this.res=this.statement.executeQuery();
                    this.res.next();
                    int idFar=this.res.getInt(1);
                    this.query="select p.ID, fa.Nome, p.Quantità, p.DataConsegnaPrevista from Prenota p, Farmacista f, Lavora l, Farmaco fa where p.IDfarmaco=fa.ID and p.IDfarmacista=f.ID and f.ID=l.IDfarmacista and l.IDfarmacia="+idFar;
                    if(!this.filtro.isBlank()){
                        this.query=this.query+" and (fa.Nome="+this.filtro+" or p.Quantità="+this.filtro+")";
                    }
                    this.statement=this.connessione.prepareStatement(query);
                    this.res=this.statement.executeQuery();
                    while(res.next()){
                        Object row3[]=new Object[5];
                        row3[0]=this.res.getInt(1);
                        row3[1]=this.res.getString(2);
                        row3[2]=this.res.getInt(3);
                        row3[3]=this.res.getDate(4).toString();
                        row3[4]=this.getData().until(LocalDate.parse((String)row3[3])).getDays()>2;
                        this.lista.add(row3);
                    }
                    this.query="select o.ID, f.Nome, o.Quantità, o.DataConsegnaPrevista from Ordina o, Farmaco f where o.IDFarmacia="+idFar+" and o.IDFarmaco=f.ID";
                    if(!this.filtro.isBlank()){
                        this.query=this.query+"and (fa.Nome="+this.filtro+" o.Quantità="+this.filtro+")";
                    }
                    this.statement=this.connessione.prepareStatement(query);
                    this.res=this.statement.executeQuery();
                    while(res.next()){
                        Object row32[]=new Object[5];
                        row32[0]=this.res.getInt(1);
                        row32[1]=this.res.getString(2);
                        row32[2]=this.res.getInt(3);
                        row32[3]=this.res.getDate(4).toString();
                        row32[4]=this.getData().until(LocalDate.parse((String)row32[3])).getDays()>2;
                        this.lista.add(row32);
                    }
                    this.listaPrenotazioni=new ListaPrenotazioni(this.lista, this.idUtente);
                    if(!this.filtro.isBlank()){
                        this.listaPrenotazioni.aggiorna(this.lista);
                    }
                    break;
                    
                case "segnalazioni":
                    this.query="select s.ID, l.IDfarmacia, p.ID, s.Data from Segnalazione s, Segnala se, Farmacista f, Prenota p, Lavora l where s.ID=se.IDsegnalazione and se.IDfarmacista=f.ID and f.ID=p.IDfarmacista and l.IDfarmacista=f.ID and f.ID=p.IDfarmacista";
                    if(!this.filtro.isBlank()){
                        this.query=this.query+"and (s.ID="+this.filtro+" or l.IDfarmacia="+this.filtro+" or p.ID="+this.filtro+" or s.Data="+this.filtro+")";
                    }
                    this.statement=this.connessione.prepareStatement(query);
                    this.res=this.statement.executeQuery();
                    while(res.next()){
                        Object row4[]=new Object[4];
                        row4[0]=this.res.getInt(1);
                        row4[1]=this.res.getInt(2);
                        row4[2]=this.res.getInt(3);
                        row4[3]=this.res.getDate(4).toString();
                        this.lista.add(row4);
                    }
                    this.query="select s.ID, fa.ID, o.ID, s.Data from Segnalazione s, Segnala se, Farmacista f, Lavora l, Farmacia fa, Ordina o where s.ID=se.IDsegnalazione and se.IDfarmacista=f.ID and f.ID=l.IDFarmacista and l.IDFarmacia=fa.ID and fa.ID=o.IDFarmacia";
                    if(!this.filtro.isBlank()){
                        this.query=this.query+" and (s.ID="+this.filtro+" or fa.ID="+this.filtro+" or o.ID="+this.filtro+" or s.Data="+this.filtro+")";
                    }
                    this.statement=this.connessione.prepareStatement(query);
                    this.res=this.statement.executeQuery();
                    while(res.next()){
                        Object row15[]=new Object[4];
                        row15[0]=this.res.getInt(1);
                        row15[1]=this.res.getInt(2);
                        row15[2]=this.res.getInt(3);
                        row15[3]=this.res.getDate(4).toString();
                        this.lista.add(row15);
                    }
                    this.listaSegnalazioni= new ListaSegnalazioni(this.lista, this.idUtente);
                    if(!this.filtro.isBlank()){
                        this.listaSegnalazioni.aggiorna(this.lista);
                    }
                    break;
                
                case "magazzino":
                    this.query="select distinct ID from Farmaco";
                    this.statement=this.connessione.prepareStatement(this.query);
                    this.res=this.statement.executeQuery();
                    Collection<Integer> idi=new ArrayList<Integer>();
                    while(this.res.next()){
                        idi.add(this.res.getInt(1));
                    }
                    for(int i=0; i<idi.size(); i++){
                        this.query="select f.ID, f.Nome, f.PrincipioAttivo, sum(f.QuantitàMagazzino) from Farmaco f where f.ID="+String.valueOf(idi.toArray()[i]);
                        if(!this.filtro.isBlank()){
                            this.query=this.query+" and f.ID="+this.filtro+" or f.Nome="+this.filtro+" or f.PrincipioAttivo="+this.filtro;
                        }
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
                    if(!this.filtro.isBlank()){
                        this.listaMagazzino.aggiorna(this.lista);
                    }
                    break;
                
                case "ordini":
                    this.query="select o.ID, o.IDfarmacia, f.Nome, o.Quantità, o.DataConsegnaPrevista from Ordina o, Farmaco f where o.IDFarmaco=f.ID";
                    if(!this.filtro.isBlank()){
                        this.query=this.query+" and (o.ID="+this.filtro+" or o.IDfarmacia="+this.filtro+" or f.Nome="+this.filtro+" or o.Quantità="+this.filtro+")";
                    }
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
                    if(!this.filtro.isBlank()){
                        this.query=this.query+" and (p.ID="+this.filtro+" or f.ID="+this.filtro+" or fa.Nome="+this.filtro+" or p.Quantità="+this.filtro+")";
                    }
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
                    if(!this.filtro.isBlank()){
                        this.listaOrdini.aggiorna(this.lista);
                    }
                    break; 
            }
       }catch(SQLException e){
           e.printStackTrace();
       }
   }
} 