public class Impiegato extends Utente{
	private String nome;
	private String cognome;

	public Impiegato(int ID, String password, String email, String nome, String cognome){
		super(ID, password, email);
		this.nome = nome;
		this.cognome = cognome;
	}

	public Impiegato(){
		this(0, "Impiegato", "impiegato@eld.it", "nome", "cognome");
	}

	public String getNome(){
		return this.nome;
	}

	public String getCognome(){
		return this.cognome;
	}
}