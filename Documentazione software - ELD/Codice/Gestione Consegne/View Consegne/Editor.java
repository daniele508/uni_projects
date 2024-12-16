import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Editor extends DefaultCellEditor implements ActionListener{

	private JFrame lista;
	private JButton pulsante;

	public Editor(JFrame lista){
		super(new JTextField());
		this.lista=lista;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){

		if(value instanceof JButton){

			this.pulsante = (JButton)value;
			this.pulsante.setFont(new java.awt.Font("Segoe UI", 0, 12));
			this.pulsante.addActionListener(this);
			return this.pulsante;


		}

		return super.getTableCellEditorComponent(table, value, isSelected, row, column);
	}

	public void actionPerformed(ActionEvent evt){
		switch(this.pulsante.getText()){

			case "Carico":
				if(this.lista instanceof ListaFarmaci){
					((ListaFarmaci)this.lista).carico(evt);
				}else{
					((ListaMagazzino)this.lista).carico(evt);
				}
				break;

			case "Scarico":
				if(this.lista instanceof ListaFarmaci){
					((ListaFarmaci)this.lista).scarico(evt);
				}else{
					((ListaMagazzino)this.lista).scarico(evt);
				}
				break;

			case "Prenota":
				((ListaFarmaci)this.lista).prenota(evt);
				break;

			case "Effettua ordine":
				((ListaMagazzino)this.lista).effettuaOrdine(evt);
				break;

			case "Modifica":
				if(this.lista instanceof ListaPrenotazioni){
					((ListaPrenotazioni)this.lista).modifica(evt);
				}else{
					((ListaOrdini)this.lista).modifica(evt);
				}
				break;

			case "Annulla":
				if(this.lista instanceof ListaPrenotazioni){
					((ListaPrenotazioni)this.lista).annulla(evt);
				}else{
					((ListaOrdini)this.lista).annulla(evt);
				}
				break;

			case "Firma":
				((ListaConsegne)this.lista).firma(evt);
				break;
		}
	}

}

