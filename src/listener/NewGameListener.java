package listener;

import gui.GUISudoku;
import gui.GUISudokuBoard;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

import org.omg.CosNaming.IstringHelper;

import logik.NewSudoku;

/**
 * Die Klasse NewGameListener implementiert das Interface ActionListener um auf das Dr�cken des
 * entsprechenden JMenuItems reagieren zu können.
 */
public class NewGameListener implements ActionListener {

	/**
	 * @param sudoku GUISudoku-Objekt das uns den Aufruf des Sudoku-Solver ermöglicht
	 * @param sudokuBoard GUISudokuBoard-Objekt das Manipulationen an GUI und Logik ermöglicht
	 * 
	 * In unserem NewGameListener Konstruktor erfolgt eine Speicherung des übergebenen
	 * GUISudoku-Objekts sudoku in der Variable _sudoku sowie des GUISudokuBoard-Objekts
	 * sudokuBoard in der Variable _sudokuBoard.
	 */

	public NewGameListener(GUISudokuBoard sudokuBoard, GUISudoku sudoku) {
		_sudokuBoard = sudokuBoard;
		_sudoku = sudoku;
	}

	/**
	 * Wird immer aufgerufen wenn das entsprechende JMenuItem geklickt wird.
	 * Wir erzeugen zunächst ein neues Objekt der Klasse NewSudoku und übergeben die Referenz
	 * von GUISudokuBoard an NewSudoku weiter. Zusätzlich rufen wir direkt, ohne Zuweisung
	 * des eben erzeugten NewSudoku-Objekts zu machen, die Methode erzeugeSudoku(int) auf
	 * die in unserer GUI und Logik das neue Feld initialisert. 
	 * Außerdem setzen wir jedes mal die JStatusBar zurück und lassen den Timer starten.
	 * Damit die JButtons, die unser Start-Sudoku darstellen, nicht änderbar sind, iterieren
	 * wir über alle JButtons und schauen, ob ein Wert in dem Feld gesetzt ist. Ist ein
	 * Wert gesetzt entfernen wir den am JButton angemeldeten ButtonListener. Ist kein Wert
	 * gesetzt und kein ActionListener angemeldet, melden wir den ButtonListener erneut an.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		new NewSudoku(_sudokuBoard).erzeugeSudoku(getSchwierigkeitsGrad());
		_sudokuBoard.getStatistik().loescheStatusBar();
		_sudokuBoard.getStatistik().starteTimer();

		JButton[][] button = _sudokuBoard.getButtons();
		for (int x = 0; x < button.length; x++) {
			for (int y = 0; y < button[x].length; y++) {
				if (_sudokuBoard.istZelleGesetzt(x, y))
					button[x][y].removeActionListener(_sudokuBoard.getButtonListener());
				else if(button[x][y].getActionListeners().length == 0)
					button[x][y].addActionListener(_sudokuBoard.getButtonListener());
				button[x][y].setFont(button[x][y].getFont().deriveFont(Font.PLAIN));
			}
		}

		_sudoku.getCheck().setEnabled(true);
		_sudoku.getHint().setEnabled(true);
		_sudoku.getSpielStandSpeichern().setEnabled(true);
		_sudoku.getZurueckSetzen().setEnabled(true);
		_sudoku.getLoesen().setEnabled(true);
	}

	/**
	 * In dieser Methode ermittelen wir uns �ber Iteration den im Men� eingestellten
	 * Schwierigkeitsgrad. Dazu durchlaufen wir alle in einer ButtonGroup enthalten JRadioButtons
	 * und �berpr�fen welcher selektiert ist. An dem derzeit selektiertem JRadioButton ermitteln wir
	 * �ber einen switch-case die sich hinter dem Schwierigkeitsgrad vorgegebene Anzahl von Feldern
	 * im 9x9 Sudokufeld.
	 * 
	 * @return int-Wert der die Anzahl der vorgegebenen Sudokufelder angibt
	 */
	private int getSchwierigkeitsGrad() {
		ButtonGroup buttonGroup = _sudoku.getButtonGroup();
		int schwierigkeitsgrad = 0;

		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				switch (button.getText()) {
					case "Leicht": {
						schwierigkeitsgrad = 25;
						break;
					}
					case "Mittel": {
						schwierigkeitsgrad = 21;
						break;
					}
					case "Schwer": {
						schwierigkeitsgrad = 17;
						break;
					}
				}
			}
		}
		return schwierigkeitsgrad;
	}

	private GUISudokuBoard	_sudokuBoard;
	private GUISudoku	_sudoku;
}
