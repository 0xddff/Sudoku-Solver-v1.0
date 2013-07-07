package listener;

import gui.GUISudokuBoard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Die Klasse DeleteListener implementiert das Interface ActionListener um auf das
 * Drücken des entsprechenden JMenuItems reagieren zu können.
 */
public class DeleteListener implements ActionListener {
	
	 /** @param sudokuBoard GUISudokuBoard-Objekt das Manipulationen an GUI und Logik ermöglicht
	 * 
	 * In unserem DeleteListener Konstruktor erfolgt eine Speicherung des übergebenen
	 * GUISudokuBoard-Objekts sudokuBoard in der Variable _sudokuBoard.
	 */
	public DeleteListener(GUISudokuBoard sudokuBoard) {
		_sudokuBoard = sudokuBoard;
	}

	/**
	 * Wird immer aufgerufen wenn das entsprechende JMenuItem geklickt wird.
	 * Wir rufen hier die Methode setzeGUIZellenWerte aus der Klasse GUISudokuBoard
	 * auf und geben als Übergabeparameter das bei Generierung des 9x9 Sudokufelds
	 * gespeicherte Sudokufeld mit.
	 * 
	 * setzeGUIZellenWerte schreibt nun die generierte Vorgabe in die GUI als auch
	 * in die Logik.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		_sudokuBoard.setzeGUIZellenWerte(_sudokuBoard.getVorgabe());
	}

	private GUISudokuBoard _sudokuBoard;

}
