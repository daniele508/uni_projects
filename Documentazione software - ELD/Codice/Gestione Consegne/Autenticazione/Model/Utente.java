public class Utente{
	private int ID;
	private String password;
	private String email;

	public Utente(int ID, String password, String email){
		this.ID = ID;
		this.password = password;
		this.email = email;
	}

	public Utente(){
		this(0, "utente", "utente@eld.it");
	}

	public int getID(){
		return this.ID;
	}

	public String getPassword(){
		return this.password;
	}

	public String getEmail(){
		return this.email;
	}
}