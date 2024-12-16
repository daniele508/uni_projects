import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;

public class Renderer extends DefaultTableCellRenderer{


	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){

		if(value instanceof JButton){

			return (JButton)value;

		}

		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
}