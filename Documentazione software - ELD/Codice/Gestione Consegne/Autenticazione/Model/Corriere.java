public class Corriere extends Utente{
	private String nome;
	private String cognome;

	public Corriere(int ID, String password, String email, String nome, String cognome){
		super(ID, password, email);
		this.nome = nome;
		this.cognome = cognome;
	}

	public Corriere(){
		this(0, "Corriere", "corriere@eld.it", "nome", "cognome");
	}

	public String getNome(){
		return this.nome;
	}

	public String getCognome(){
		return this.cognome;
	}
}