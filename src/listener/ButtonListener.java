package listener;

import gui.GUISudokuAuswahl;
import gui.GUISudokuBoard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Die Klasse ButtonListener implementiert das Interface ActionListener um für jeden
 * Button des 9x9 Sudokufelds eine Aktion durchführen zu können.
 */
public class ButtonListener implements ActionListener {

	/**
	 * @param sudokuBoard GUISudokuBoard-Objekt das Manipulationen an GUI und Logik ermöglicht
	 * 
	 * In unserem ButtonListener Konstruktor erfolgt eine Speicherung des übergebenen
	 * GUISudokuBoard-Objekts sudokuBoard in der Variable _sudokuBoard. Dadurch können wir uns in
	 * der ButtonListener-Klasse weitere Methoden schreiben und auf GUI und Logik zugreifen.
	 * Außerdem initialiseren wir einmalig unser Auswahlfenster das zur Eingabe von Zahlen
	 * in unser Sudoku dient.
	 */
	public ButtonListener(GUISudokuBoard sudokuBoard) {
		_sudokuBoard = sudokuBoard;
		_auswahl = new GUISudokuAuswahl(_sudokuBoard);
	}

	/**
	 * Immer wenn ein JButton aus unserem 9x9 Sudokufeld gedrückt wird, wird diese Methode
	 * durchlaufen. Sie speichert sich in einer temporären JButton-Variable den aktuell gedrückten
	 * JButton und holt sich außerdem aus der Klasse GUISudokuBoard alle in der GUI vorhandenen JButtons.
	 * 
	 * Wir iterieren nun über alle Zeilen und Spalten um herauszufinden welche Koordinaten unseres
	 * 9x9 Sudokufelds zu dem gedrückten Button gehören und speichern diese in der Klasse SudokuBoard.
	 * 
	 * Anschließend lassen wir unser Auswahlfenster erscheinen.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton tmp = (JButton) e.getSource();
		JButton[][] buttonArray = _sudokuBoard.getButtons();
		
		for (int zeile = 0; zeile < buttonArray.length; zeile++) {
			for (int spalte = 0; spalte < buttonArray[zeile].length; spalte++) {
				if (tmp.equals(buttonArray[zeile][spalte])) {
					_sudokuBoard.setZeileMarkiert(zeile);
					_sudokuBoard.setSpalteMarkiert(spalte);
				}
			}
		}
		
		_auswahl.showGUI();
	}
	
	private GUISudokuBoard	_sudokuBoard;
	private GUISudokuAuswahl _auswahl;
}
