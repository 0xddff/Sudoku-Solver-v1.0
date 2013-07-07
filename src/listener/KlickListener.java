package listener;

import gui.GUISudokuAuswahl;
import gui.GUISudokuBoard;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Die Klasse KlickListener implementiert das Interface ActionListener um für jeden
 * Button der 3x3 Auswahlbox eine Aktion durchführen zu können.
 */
public class KlickListener implements ActionListener {
	
	/**
	 * @param auswahl, GUISudokuAuswahl-Objekt das uns die Eingabe von Zahlen ermöglicht
	 * @param sudokuBoard GUISudokuBoard-Objekt das Manipulationen an GUI und Logik ermöglicht
	 * 
	 * In unserem KlickListener Konstruktor erfolgt eine Speicherung des übergebenen
	 * GUISudokuBoard-Objekts sudokuBoard in der Variable _sudokuBoard sowie des
	 * GUISudokuAuswahl-Objekts auswahl in der Variable _auswahl. 
	 */
	public KlickListener(GUISudokuAuswahl auswahl, GUISudokuBoard sudokuBoard) {
		_sudokuBoard = sudokuBoard;
		_auswahl = auswahl;
	}

	/**
	 * Immer wenn ein JButton aus unserer 3x3 Auswahlbox gedrückt wird, wird diese Methode
	 * durchlaufen. Sie speichert sich in einer temporären JButton-Variable den aktuell gedrückten
	 * JButton und holt sich die in der Klasse SudokuBoard gespeicherten Zeilen- und Spalten-Koordinaten,
	 * des in der Klasse GUISudokuBoard gedrückten JButtons, und speicherte diese in temporären int-Variablen.
	 * Außerdem speichern wir uns in einer temporären int-Variable den Zahlenwert des in der Auswahlbox gedrückten
	 * JButtons.
	 * 
	 * Die ermittelten Werte werden an setzeGUIZellenWert aus der Klasse GUISudokuBoard weitergereicht die nun die
	 * Aktualisierung der GUI und Logik vornimmt. Desweiteren ändern wir die Font des in GUISudokuBoards gedrückten
	 * Buttons um ihn von den anderen abzuheben.
	 * Da diese Methode nur durchlaufen wenn in der Auswahlbox ein JButton gedrückt wurde können wir hier ohne
	 * Überprüfung den "Züge"-Zähler um 1 erhöhen.
	 * Die Auswahlbox wird automatisch durch die Methode dispose() geschlossen.
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		int x = _sudokuBoard.getZeileMarkiert();
		int y = _sudokuBoard.getSpalteMarkiert();
		int zahl = Integer.parseInt(button.getText());

		_sudokuBoard.setzeGUIZellenWert(zahl, x, y);
		_sudokuBoard.getButtons()[x][y].setFont(_sudokuBoard.getButtons()[x][y].getFont().deriveFont(Font.BOLD));

		_sudokuBoard.getStatistik().erhoeheMoveCount();
		_auswahl.getDialog().dispose();
	}

	private GUISudokuBoard		_sudokuBoard;
	private GUISudokuAuswahl	_auswahl;
}
