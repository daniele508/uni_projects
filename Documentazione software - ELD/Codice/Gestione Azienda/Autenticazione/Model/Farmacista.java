public class Farmacista extends Utente{
	private int idFarmacia;
	private String nome;
	private String cognome;
	private String telefono;

	public Farmacista(int ID, String password, String email, int idFarmacia, String nome, String cognome, String telefono){
		super(ID, password, email);
		this.idFarmacia = idFarmacia;
		this.nome = nome;
		this.cognome = cognome;
		this.telefono = telefono;
	}

	public Farmacista(){
		this(0, "Farmacista", "farmacista@eld.it", 0, "nome", "cognome","0");
	}

	public int getIDFarmacia(){
		return this.idFarmacia;
	}

	public String getNome(){
		return this.nome;
	}

	public String getCognome(){
		return this.cognome;
	}

	public String getTelefono(){
		return this.telefono;
	}
}