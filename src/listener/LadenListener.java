package listener;

import gui.GUISudoku;
import gui.GUISudokuBoard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import logik.SudokuBoard;

/**
 * Die Klasse LadenListener implementiert das Interface ActionListener um auf das Drücken des
 * entsprechenden JMenuItems reagieren zu können.
 */
public class LadenListener implements ActionListener {

	/**
	 * @param sudoku GUISudoku-Objekt das uns die Eingabe von Zahlen ermöglicht
	 * @param sudokuBoard GUISudokuBoard-Objekt das Manipulationen an GUI und Logik ermöglicht
	 * 
	 * In unserem LadenListener Konstruktor erfolgt eine Speicherung des übergebenen
	 * GUISudoku-Objekts sudoku in der Variable _sudoku sowie des GUISudokuBoard-Objekts
	 * sudokuBoard in der Variable _sudokuBoard.
	 */
	public LadenListener(GUISudoku sudoku, GUISudokuBoard sudokuBoard) {
		_sudokuBoard = sudokuBoard;
		_sudoku = sudoku;
	}

	/**
	 * Wird immer aufgerufen wenn das entsprechende JMenuItem geklickt wird. Wir erzeugen uns
	 * zunächst ein Objekt der Klasse FileChooser und setzen mit der Methode
	 * setFileFilter(FileNameExtensionsFilter) durch die temporäre Erzeugung eines
	 * FileNameExtensionFilter-Objekts einen Filter auf .sudoku-Dateien.
	 * 
	 * In einer IF-Anweisung rufen wir nun den JFileChooser Dialog auf und werten den
	 * Rückgabeparameter aus. Nur wenn eine .sudoku-Datei und der Button "Öffnen" des Dialog
	 * gedrückt wird erzeugen wir uns ein Objekt des Typs JAXBContext dem wir unsere
	 * SudokuBoard.class übergeben. Ein Unmarshaller-Objekt ermöglicht es nun über einen einfachen
	 * unmarshal(File)-Methodenaufruf die in der XML gespeicherten Werte auszulesen. Die Methode
	 * unmarshal(File) liefert uns ein Objekt vom Typ Object zurück. Da wir wissen, dass es sich
	 * dabei um ein SudokuBoard handelt casten wir das zurückgelieferte Objekt vom Typ Object zu
	 * SudokuBoard.
	 * 
	 * Nun haben wir ein vollständiges SudokuBoard-Objekt an dem wir über getZellenWert() ein
	 * int-Array des SudokuBoards zurückgeliefert bekommen und aus der Klasse GUISudokuBoard nur die
	 * Methode setzeGUIZellenWerte(int[][]) aufrufen müssen.
	 * 
	 * Zusätzlich aktivieren wir die zum spielen benötigten JMenuItems, setzen die JStatusBar zurück
	 * und starten den Timer.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter("*.sudoku", "sudoku"));

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				JAXBContext jc = JAXBContext.newInstance(SudokuBoard.class);
				Unmarshaller m = jc.createUnmarshaller();
				SudokuBoard board = (SudokuBoard) m.unmarshal(chooser.getSelectedFile());

				_sudokuBoard.setzeGUIZellenWerte(board.getZellenWerte());

				JButton[][] button = _sudokuBoard.getButtons();
				for (int x = 0; x < button.length; x++) {
					for (int y = 0; y < button[x].length; y++) {
						if (_sudokuBoard.istZelleGesetzt(x, y))
							button[x][y].removeActionListener(_sudokuBoard.getButtonListener());
						else if (button[x][y].getActionListeners().length == 0)
							button[x][y].addActionListener(_sudokuBoard.getButtonListener());
					}
				}

				_sudoku.getCheck().setEnabled(true);
				_sudoku.getHint().setEnabled(true);
				_sudoku.getSpielStandSpeichern().setEnabled(true);
				_sudoku.getZurueckSetzen().setEnabled(true);
				_sudoku.getLoesen().setEnabled(true);
				_sudokuBoard.getStatistik().loescheStatusBar();
				_sudokuBoard.getStatistik().starteTimer();
			}
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
	}

	private GUISudoku	_sudoku;
	private GUISudokuBoard	_sudokuBoard;
}
