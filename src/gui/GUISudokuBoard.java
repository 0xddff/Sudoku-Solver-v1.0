package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import logik.Statistik;
import logik.SudokuBoard;

import listener.ButtonListener;
import listener.ButtonMouseListener;

/**
 * Ist die grafische Oberfläche der Buttons im Sudoku-Solver. GUISudokuBoard erweitert SudokuBoard
 * um einen konsistenten Datenbestand zwischen Logik und GUI zu gewährleisten. Der public definierte
 * Konstruktor ruft unseren private definierten Konstruktor über die this.-Zeigerfunktion auf und
 * übergibt die Größe des SudokuBoards (9x9) Desweiteren erzeugt GUISudokuBoard ein Objekt vom Typ
 * Statistik.
 **/

public class GUISudokuBoard extends SudokuBoard {

	/**
	 * @param board das zur Initialisierung übergebene 9x9 int-Array
	 * 
	 * Mit dem this-Zeiger rufen wir unseren privaten, parametrisierten Konstruktor auf
	 * und übergeben die Größe des SudokuBoards (9x9). Anschließend setzen wir die Werte
	 * in der GUI als auch in der Logik.
	 */
	public GUISudokuBoard(int[][] board) {
		this(board.length);
		setzeGUIZellenWerte(board);
	}

	/**
	 * @param size die zur Initialisierung übergebene int-Array Größe (9)
	 * 
	 * Zuerst müssen wir den Konstruktor der erbenden Klasse aufrufen - diesem übergeben
	 * wir zusätzlich auch die Größe unseres int-Array (9). Danach erfolgt die
	 * Initialisierung der, zur Darstellung im Sudoku relevanten, Swing-Komponenten. Wir
	 * erzeugen ein zweidimensionales JButton-Array mit der übergebenen "size". Unser
	 * JPanel bekommt beim Konstruktoraufruf als Layoutmanager ein GridLayout mit der
	 * Zeilen-/ und Spalten- anzahl von 3 übergeben. BOX_SIZE ist hierbei eine int
	 * Variable aus der Klasse SudokuBoard die wir durch den super-Aufruf bereits
	 * initialisiert haben.
	 * 
	 * Nun erfolgt die Initialisierung der einzelnen 3x3 Boxen im 9x9 Sudoku. Wir mussten
	 * leider diesen umständlichen Weg nehmen, da die meisten Sudoku- Lösungsalgorithmen
	 * eine bestimmte Feld-Initialisierung voraussetzten.
	 */

	private GUISudokuBoard(int size) {
		super(size);
		_button = new JButton[size][size];
		_panel = new JPanel(new GridLayout(BOX_SIZE, BOX_SIZE));

		_statusBar = new Statistik();

		for (int box = 0; box < size; box++) {
			JPanel _subPanel = new JPanel(new GridLayout(BOX_SIZE, BOX_SIZE));
			_subPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			if (box == 0) {
				for (int spalte = 0; spalte < 3; spalte++) {
					for (int zeile = 0; zeile < 3; zeile++) {
						_subPanel.add(getButtonForSubPanel(spalte, zeile));
					}
				}
			}
			if (box == 1) {
				for (int spalte = 0; spalte < 3; spalte++) {
					for (int zeile = 3; zeile < 6; zeile++) {
						_subPanel.add(getButtonForSubPanel(spalte, zeile));
					}
				}
			}
			if (box == 2) {
				for (int spalte = 0; spalte < 3; spalte++) {
					for (int zeile = 6; zeile < 9; zeile++) {
						_subPanel.add(getButtonForSubPanel(spalte, zeile));
					}
				}
			}
			if (box == 3) {
				for (int spalte = 3; spalte < 6; spalte++) {
					for (int zeile = 0; zeile < 3; zeile++) {
						_subPanel.add(getButtonForSubPanel(spalte, zeile));
					}
				}
			}
			if (box == 4) {
				for (int spalte = 3; spalte < 6; spalte++) {
					for (int zeile = 3; zeile < 6; zeile++) {
						_subPanel.add(getButtonForSubPanel(spalte, zeile));
					}
				}
			}
			if (box == 5) {
				for (int spalte = 3; spalte < 6; spalte++) {
					for (int zeile = 6; zeile < 9; zeile++) {
						_subPanel.add(getButtonForSubPanel(spalte, zeile));
					}
				}
			}
			if (box == 6) {
				for (int spalte = 6; spalte < 9; spalte++) {
					for (int zeile = 0; zeile < 3; zeile++) {
						_subPanel.add(getButtonForSubPanel(spalte, zeile));
					}
				}
			}
			if (box == 7) {
				for (int spalte = 6; spalte < 9; spalte++) {
					for (int zeile = 3; zeile < 6; zeile++) {
						_subPanel.add(getButtonForSubPanel(spalte, zeile));
					}
				}
			}
			if (box == 8) {
				for (int spalte = 6; spalte < 9; spalte++) {
					for (int zeile = 6; zeile < 9; zeile++) {
						_subPanel.add(getButtonForSubPanel(spalte, zeile));
					}
				}
			}
			_panel.add(_subPanel);
		}
	}

	/**
	 * @param spalte eindeutige int-Variable zur Zuordnung eines JButtons im 9x9 Sudokufeld
	 * @param zeile eindeutige int-Variable zur Zuordnung eines JButtons im 9x9 Sudokufeld
         * 
	 * @return gibt den für die Spalte und Zeile initalisierten JButton zurück
	 */

	private JButton getButtonForSubPanel(int spalte, int zeile) {
		_button[spalte][zeile] = new JButton();
		_button[spalte][zeile].setPreferredSize(BUTTON_SIZE);
		_button[spalte][zeile].setFont(new Font("Dialog", Font.PLAIN, 24));
		_button[spalte][zeile].addActionListener(_buttonListener);
		_button[spalte][zeile].addMouseMotionListener(new ButtonMouseListener(this));
		return _button[spalte][zeile];
	}

	/**
	 * @param zahl der in der GUI und Logik zu setzenden Zahlenwert (0-9)
	 * @param zeile eindeutige int-Variable zur Zuordnung der Zahl im 9x9 Sudokufeld
	 * @param spalte eindeutige int-Variable zur Zuordnung der Zahl im 9x9 Sudokufeld
	 * 
	 * Der super-Aufruf sorgt für die konsistente Datenhaltung in Logik und GUI. Sind die
	 * Werte in der Logik gesetzt, erzeugen wir uns eine temporäre String-Variable und
	 * weisen ihr einen Leerstring, wenn zahl = 0, oder den Wert der zahl zu.
	 */

	public void setzeGUIZellenWert(int zahl, int zeile, int spalte) {
		super.setzeZellenWert(zahl, zeile, spalte);
		String text = (zahl == LEER) ? "" : String.valueOf(zahl);
		_button[zeile][spalte].setText(text);
	}

	/**
	 * @param feld das in der GUI und Logik zu setzende 9x9 int-Array
	 * 
	 * Wir iterieren über alle Felder des int-Arrays und rufen für jeden Wert unsere
	 * andere setzeGUIZellenWert-Methode um einen konsistenten Datenstand in GUI und
	 * Logik zu haben.
	 */

	public void setzeGUIZellenWerte(int[][] feld) {
		for (int zeile = 0; zeile < 9; zeile++) {
			for (int spalte = 0; spalte < 9; spalte++) {
				setzeGUIZellenWert(feld[zeile][spalte], zeile, spalte);
			}
		}
	}

	/**
	 * Gibt die im Statistik-Objekt erzeugte JStatusBar zurück
	 * 
	 * @return im Statistik-Objekt erzeugte StatusBar
	 */

	public JStatusBar getStatusBar() {
		return _statusBar.getStatusBar();
	}

	/**
	 * @param x eindeutige Zuordnung einer Zeile im 9x9 Sudokufeld
	 * @param y eindeutige Zuordnung einer Spalte im 9x9 Sudokufeld
	 * 
	 * Gibt den, für in der Zeile und Spalte, ermittelten Wert zurück. Dieser Wert wird
	 * nicht aus der GUI ermittelt, sondern greift auf die Methode aus der Super-Klasse
	 * SudokuBoard zu.
	 * 
	 * @return int-Wert der an der Position (x,y) steht
	 */

	public int getZellenWert(int x, int y) {
		return super.getZellenWert(x, y);
	}

	/**
	 * Liefert uns alle JButtons aus der GUI
	 * 
	 * @return JButton-Array mit allen in der GUI initialisierten JButtons (9x9 Sudoku)
	 */

	public JButton[][] getButtons() {
		return _button;
	}

	/**
	 * Liefert uns das initialiserte 9x9 Sudokufeld zurück um es dem JFrame hinzuzufügen.
	 * 
	 * @return JPanel des initialiserten 9x9 Sudokufeldes
	 */

	public JPanel getPanel() {
		return _panel;
	}

	/**
	 * Liefert uns das im Konstruktor erzeugte Statistik-Objekt zurück.
	 * 
	 * @return Statistik-Objekt zur Manipulation der JStatusBar
	 */

	public Statistik getStatistik() {
		return _statusBar;
	}
	
	public ButtonListener getButtonListener(){
		return _buttonListener;
	}

	private ButtonListener		_buttonListener	= new ButtonListener(this);
	public final Color              YELLOW		= new Color(255, 255, 0);
	public final Color		BLUE			= new Color(0, 153, 204);
	protected JButton[][]		_button;
	private JPanel			_panel;
	private Statistik		_statusBar;
	protected final Dimension	BUTTON_SIZE		= new Dimension(60, 60);
}