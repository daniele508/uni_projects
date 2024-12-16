import javax.swing.*;
import java.awk.*;
public class ModuloSegnalazione extends JFrame{
	private JButton pulsanteInvia;
	private JButton pulsanteConferma;
	private JButton pulsanteSegnala;
	private JCheckBox checkBox[];
	private Collection<Object[]> datiRiepilogativi;
	private Collection<Object> dati =new Arraylist<Object>();
	private int idUtente;
	private ControlSegnala controlSegnala;
	private JTable tabella;

	public ModuloSegnalazione(int idUtente, ControlSegnala segnala){
		this.controlSegnala=segnala;
		this.idUtente=idUtente;
		initComponents();
		this.setVisible(true);
	}

	public ModuloSegnalazione(int id, Collection<Object[]> lista, ControlSegnala control){
		this.controlSegnala=control;
		this.idUtente=idUtente;
		this.datiRiepilogativi=lista;
		this.checkBox= new JCheckBox[lista.size()];
		mostraNuoviCampi();
		this.setVisible(true);
	}

	public void segnala(ActionEvent evt){
		this.controlSegnala.run1();
		this.dispose();
	}

	public void conferma(ActionEvent evt){
		MenuPrincipale menu= new MenuPrincipale(this.idUtente);
		this.dispose();
	}

	public void invia(ActionEvent evt){
		this.compila();
		this.controlSegnala.run2();
		this.dispose();
	}

	public Collection<Object> getDati(){
		return this.dati;

	}

	public void compila(){
		this.controlSegnala.setDati(this.getDati());
	}

	public void selezione(ActionEvent evt){
		this.dati.add(tabella.getValueAt(tabella.selectedRow(), 0));
	}





	private void initcomponents(){}
	private void mostraNuoviCampi(){}



}