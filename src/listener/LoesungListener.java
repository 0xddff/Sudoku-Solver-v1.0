package listener;

import gui.GUISudoku;
import gui.GUISudokuBoard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Die Klasse LoesungListener implementiert das Interface ActionListener um auf das Drücken des
 * entsprechenden JMenuItems reagieren zu können.
 */
public class LoesungListener implements ActionListener {
	
	/**
	 * @param sudoku GUISudoku-Objekt das uns die Eingabe von Zahlen ermöglicht
	 * @param sudokuBoard GUISudokuBoard-Objekt das Manipulationen an GUI und Logik ermöglicht
	 * 
	 * In unserem LoesungListener Konstruktor erfolgt eine Speicherung des übergebenen
	 * GUISudoku-Objekts sudoku in der Variable _sudoku sowie des GUISudokuBoard-Objekts
	 * sudokuBoard in der Variable _sudokuBoard.
	 */
	public LoesungListener(GUISudokuBoard sudokuBoard, GUISudoku sudoku) {
		_sudokuBoard = sudokuBoard;
		_sudoku = sudoku;
	}

	/**
	 * Wird immer aufgerufen wenn das entsprechende JMenuItem geklickt wird.
	 * Wir rufen an unserer GUISudoku-Referent die Methode versucheZuLoesen(int,int) aus der Klasse
	 * SudokuSolver auf und werten den booleschen Parameter aus. Wenn das Sudoku nicht lösbar ist
	 * öffnen wir ein JOptionPane mit der dementsprechenden Meldung. Ist das Sudoku lösbar
	 * holen wir uns aus der Klasse SudokuBoard die Lösung und übergben diese Lösung der Methode
	 * setzeGUIZellenWerte die die Werte der Lösung in GUI und Logik setzt. Zusätzlich stoppen
	 * wir unseren Timer.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!_sudoku.versucheZuLoesen(0, 0)) {
			JOptionPane.showMessageDialog(_sudoku.getFrame(), "Keine Lösung gefunden!");
		} else {
			_sudokuBoard.setzeGUIZellenWerte(_sudokuBoard.getLoesung());
			_sudokuBoard.getStatistik().stoppeTimer();
		}
	}

	private GUISudoku	_sudoku;
	private GUISudokuBoard	_sudokuBoard;
}
