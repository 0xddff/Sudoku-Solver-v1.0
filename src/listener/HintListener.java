package listener;

import gui.GUISudoku;
import gui.GUISudokuBoard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Die Klasse HintListener implementiert das Interface ActionListener um auf das Drücken des
 * entsprechenden JMenuItems reagieren zu können.
 */
public class HintListener implements ActionListener {

	/**
	 * @param sudoku GUISudoku-Objekt das uns den Aufruf des Sudoku-Solver ermöglicht
	 * @param sudokuBoard GUISudokuBoard-Objekt das Manipulationen an GUI und Logik ermöglicht
	 * 
	 * In unserem HintListener Konstruktor erfolgt eine Speicherung des übergebenen
	 * GUISudoku-Objekts sudoku in der Variable _sudoku sowie des GUISudokuBoard-Objekts
	 * sudokuBoard in der Variable _sudokuBoard.
	 */

	public HintListener(GUISudokuBoard sudokuBoard, GUISudoku sudoku) {
		_sudokuBoard = sudokuBoard;
		_sudoku = sudoku;
	}

	/**
	 * Wird immer aufgerufen wenn das entsprechende JMenuItem geklickt wird.
	 * Zuerst Überprüfen, ob das Board bereits vollständig gelöst ist da wir,
	 * falls das Board bereits gelöst ist, keinen Hint mehr ermitteln können / müssen.
	 * 
	 * In einer weiteren IF-Anweisung Überprüfen wir, ob das Sudoku überhaupt lösbar
	 * ist. Ist das nicht der Fall wird die Meldung ausgegeben, dass kein Hinweis gefunden
	 * werden konnte. Ist das Sudoku lösbar erzeugen wir jeweils eine Zufallszahl
	 * für die Zeile, Spalte und schauen, ob an dieser Stelle bereits eine Zahl steht.
	 * Wenn bereits eine Zahl vorhanden ist werden neue Zufallszahlen für Zeile und Spalte
	 * generiert ansonsten setzen wir über die Methode setzeGUIZellenWerte aus der Klasse
	 * GUISudokuBoard den Hinweis für das zu lösende Sudoku.
	 * 
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!_sudokuBoard.istBoardVollstaendig()) {
			int zeile = 0, spalte = 0;
			boolean check = false;

			if (!_sudoku.versucheZuLoesen(0, 0)) {
				JOptionPane.showMessageDialog(_sudoku.getFrame(), "Keinen Hinweis gefunden!");
			} else {
				do {
					zeile = (int) (Math.random() * 9);
					spalte = (int) (Math.random() * 9);
					if (_sudokuBoard.getZellenWert(zeile, spalte) == 0) {
						_sudokuBoard.setzeGUIZellenWert(_sudokuBoard.getLoesung()[zeile][spalte], zeile, spalte);
						_sudokuBoard.getStatistik().erhoeheHintCount();
						check = true;
					}
				} while (!check);
			}
		}
	}

	private GUISudoku	_sudoku;
	private GUISudokuBoard	_sudokuBoard;
}
