package gui;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import listener.KlickListener;

/**
 * Ist die grafische Oberfläche unseres 3x3 Eingabefeldes.
 * GUISudokuAuswahl erbt von der Klasse JDialog um die Möglichkeit zu haben ein modales Fenster
 * zu erzeugen.
 */

public class GUISudokuAuswahl extends JDialog{
	
	/**
	 * @param sudokuBoard das in GUISudokuBoard erzeugte 9x9 Sudokufeld
	 * 
	 * Unser parametrisierten Konstruktor von GUISudokuAuswahl bekommt ein Objekt vom Typ GUISudokuBoard übergeben.
	 * Dies ist notwendig um über Auswahlfenster später Manipulationen an GUI und Logik vornehmen zu können.
	 * 
	 * Desweiteren erzeugen wir uns ein JPanel das als Layoutmanager ein GridLayout von der Größe 3x3 übergeben bekommt.
	 * In einer for-Schleife werden die 9 benötigten Eingabefelder (Zahlen 1-9) als JButton initialisert und dem
	 * JPanel hinzugefügt. Die Methode setModal(boolean), die wir von JDialog erben, lässt uns unser Auswahlfenster
	 * modal setzen.
	 */
	public GUISudokuAuswahl(GUISudokuBoard sudokuBoard){
		this.setLocationRelativeTo(null);
		this.setTitle("Auswahlbox");
		_mainPanel = new JPanel(new GridLayout(sudokuBoard.BOX_SIZE, sudokuBoard.BOX_SIZE));
		
		for (int x = 0; x < _button.length; x++) {
			_button[x] = new JButton(String.valueOf(x + 1));
			_button[x].setPreferredSize(sudokuBoard.BUTTON_SIZE);
			_button[x].setFont(new Font("Dialog", Font.PLAIN, 24));
			_button[x].addActionListener(new KlickListener(this, sudokuBoard));

			_mainPanel.add(_button[x]);
		}
		
		this.add(_mainPanel);
		this.pack();
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * Liefert uns das erzeugte Fenster zurück.
	 * 
	 * @return JDialog der erzeugten Auswahlbox
	 */
	public JDialog getDialog(){
		return this;
	}
	
	/**
	 * Lässt unsere Auswahlbox erscheinen.
	 */
	public void showGUI() {
		this.setVisible(true);
	}

	private JPanel _mainPanel;
	private JButton[] _button = new JButton[9];
}
