package listener;

import gui.GUISudokuBoard;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 * Die Klasse ButtonMouseListener erbt alle Methoden von MouseAdapter um die Anzeige
 * eines Steuerkreuzes in unserem Sudoku-Solver zu ermöglichen.
 */

public class ButtonMouseListener extends MouseAdapter {

	/**
	 * @param sudokuBoard GUISudokuBoard-Objekt das Manipulationen an GUI und Logik ermöglicht
	 * 
	 * In unserem ButtonMouseListener Konstruktor erfolgt eine Speicherung des übergebenen
	 * GUISudokuBoard-Objekts sudokuBoard in der Variable _sudokuBoard.
	 */
	public ButtonMouseListener(GUISudokuBoard sudokuBoard) {
		_sudokuBoard = sudokuBoard;
	}

	/**
	 * Wir überschreiben die Methode mouseMoved aus der Klasse MouseAdapter um ein Steuerkreuz
	 * in unserem Sudoku-Solver anzeigen zu können.
	 * 
	 * Immer wenn die Maus über einen JButton aus unserem 9x9 Sudokufeld fährt wird diese Methode
	 * durchlaufen. Sie speichert sich in einer temporären JButton-Variable den aktuell gedrückten
	 * JButton und holt sich außerdem aus der Klasse GUISudokuBoard alle in der GUI vorhandenen JButtons.
	 * 
	 * Wir iterieren nun über alle Zeilen und Spalten um herauszufinden welche Koordinaten unseres
	 * 9x9 Sudokufelds zu dem "überfahrenen" Button gehören, setzen dabei bei allen JButtons den Hintergrund
	 * auf Standart und speichern die Koordinaten in temporären int-Variablen _zeile, _spalte.
	 * 
	 * Mit der ermittelten Zeile und Spalte rufen wir die Methoden markiereHorizontal(int, JButton[]) und
	 * markiereVertikal(int, JButton[]) auf.
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		JButton tmp = (JButton) e.getSource();
		JButton[][] buttonArray = _sudokuBoard.getButtons();

		int _zeile = 0, _spalte = 0;
		for (int zeile = 0; zeile < buttonArray.length; zeile++) {
			for (int spalte = 0; spalte < buttonArray[zeile].length; spalte++) {
				buttonArray[zeile][spalte].setBackground(null);
				if (tmp.equals(buttonArray[zeile][spalte])) {
					_zeile = zeile;
					_spalte = spalte;
				}
			}
		}

		markiereHorizontal(_zeile, buttonArray);
		markiereVertikal(_spalte, buttonArray);
	}

	/**
	 * @param zeile int-Wert der angibt, welche Zeile markiert werden muss
	 * @param buttonArray JButton-Array das alle JButtons aus unserem 9x9 Sudokufeld enthält
	 * 
	 * Wir iterieren über alle Buttons die sich in der Zeile befinden und setzen den
	 * Hintergrund auf Gelb.
	 */
	private void markiereHorizontal(int zeile, JButton[][] buttonArray) {
		for (int spalte = 0; spalte < 9; spalte++) {
			buttonArray[zeile][spalte].setBackground(_sudokuBoard.YELLOW);
		}
	}

	/**
	 * @param spalte int-Wert der angibt, welche Spalte markiert werden muss
	 * @param buttonArray JButton-Array das alle JButtons aus unserem 9x9 Sudokufeld enthält
	 *	
	 * Wir iterieren über alle Buttons die sich in der Spalte befinden und setzen den
	 * Hintergrund auf Gelb.
	 */
	private void markiereVertikal(int spalte, JButton[][] buttonArray) {
		for (int zeile = 0; zeile < 9; zeile++) {
			buttonArray[zeile][spalte].setBackground(_sudokuBoard.YELLOW);
		}
	}

	private GUISudokuBoard	_sudokuBoard;
}
