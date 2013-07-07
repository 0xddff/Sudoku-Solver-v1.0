package listener;

import gui.GUISudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Die Klasse CheckListener implementiert das Interface ActionListener um auf das
 * Drücken des entsprechenden JMenuItems reagieren zu können.
 */
public class CheckListener implements ActionListener {

	 /** @param sudoku GUISudoku-Objekt das uns den Aufruf des Sudoku-Solver ermöglicht
	 * 
	 * In unserem CheckListener Konstruktor erfolgt eine Speicherung des übergebenen
	 * GUISudoku-Objekts sudoku in der Variable _sudoku.
	 */
	public CheckListener(GUISudoku sudoku) {
		_sudoku = sudoku;
	}

	/**
	 * Wird immer aufgerufen wenn das entsprechende JMenuItem geklickt wird.
	 * Wir rufen hier die Methode versucheZuLoesen(int, int) mit den Werten 0 und 0 aus der 
	 * Klasse SudokuSolver auf und werten den booleschen Rückgabeparameter aus.
	 * 
	 * Je nachdem, ob das Sudoku noch lösbar ist oder nicht, erscheint ein JOptionPane mit der 
	 * entsprechenden Meldung.
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (_sudoku.versucheZuLoesen(0, 0)) {
			JOptionPane.showMessageDialog(_sudoku.getFrame(), "Das Sudoku enthält keine Fehler!");
		} else
			JOptionPane.showMessageDialog(_sudoku.getFrame(), "Das Sudoku enthält Fehler!");
	}

	private GUISudoku	_sudoku;
}
