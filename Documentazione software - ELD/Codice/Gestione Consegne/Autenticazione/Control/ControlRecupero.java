
import java.util.*;
//import javax.mail.*;
import java.sql.*;
import java.text.DecimalFormat;

public class ControlRecupero {
    private ModuloRecupero moduloRecupero;
    private Notifica notifica;
    private ModuloLogin moduloLogin;
    private String codiceUtente;
    private String email;
    private String password;
    private Connection connessione;
    private String query;
    private PreparedStatement statement;
    private ResultSet res;
    
    public ControlRecupero(ModuloLogin moduloLogin){
        this.moduloLogin=moduloLogin;
        this.moduloRecupero=new ModuloRecupero(this); 
    }
    
    public void setPassword(String password){
        this.password=password;
    }
    public void setCodice(String codice){
        this.codiceUtente=codice;
    }
    
    public void run1(){
        String from="";
        String host="localhost";
        try{
            this.connessione=DriverManager.getConnection("jdbc:mysql://localhost:3306/azienda","root","root");
            this.query="SELECT Email FROM corriere,farmacista,impiegato WHERE ID="+this.codiceUtente;
            this.statement=connessione.prepareStatement(query);
            this.res=statement.executeQuery();
            if(res.next()){
                email=res.getString(1);
            }
            
            if(email== null){
                this.notifica= new Notifica("Errore nel codice immesso!");
                this.moduloRecupero=new ModuloRecupero(this);
            }else{
               /* String otp= new DecimalFormat("0000").format(new Random().nextInt(9999));
                        
                Properties properties = System.getProperties();  
                properties.setProperty("mail.smtp.host", host);  
                Session session = Session.getDefaultInstance(properties);
                
                MimeMessage message = new MimeMessage(session);  
                message.setFrom(new InternetAddress(from));  
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));  
                message.setSubject("Recupero password");  
                message.setText("Ecco il codice OTP per il recupero password: "+otp);  
                Transport.send(message);*/
                this.moduloRecupero=new ModuloRecupero(this, 2);
            }

        }catch(SQLException e){
        }
    }

    public void run2(){
        try{
            this.query= "UPDATE corriere,farmacista,impiegato SET Password ="+password+" WHERE ID="+codiceUtente;
            this.statement=connessione.prepareStatement(query);
            statement.executeUpdate();                   
        }catch(SQLException e){}
        this.moduloLogin.mostra();
    }
}
