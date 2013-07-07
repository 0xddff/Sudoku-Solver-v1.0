package logik;

import gui.GUISudokuBoard;

/**
 * Ist für das erzeugen eines neuen Sudokus zuständig. Zeile, Spalte und Zahl werden dabei
 * zufällig generiert. Wir erben von SudokuSolver um unser erzeugtes Sudoku auf Lösbarkeit prüfen
 * zu können.
 */

public class NewSudoku extends SudokuSolver {

	/**
	 * @param sudokuBoard GUISudokuBoard, dass uns das initialiserte 9x9 Sudokufeld liefert.
	 * 
	 * Zuerst müssen wir in unserem parameterisiertem Konstruktor den Konstruktor
	 * unserer Super- Klasse aufrufen. Hierbei übergeben wir das GUISudokuBoard-Objekt,
	 * um in NewSudoku die Zellenwerte in der Logik setzen zu können.
	 */

	public NewSudoku(GUISudokuBoard sudoku) {
		super(sudoku);
		_sudoku = sudoku;
	}

	/**
	 * Initialisiert die Booleschen Arrays "box", "zeile" und "spalte", welche genutzt werden um
	 * bereits ermittelte Zufallszahlen auszuschließen, mit false. Weiterhin wird das Integer Array
	 * "teilloesung", welches alle gelosten Zahlen enthält mit 0 initialisiert.
	 */

	protected void initialisieren() {
		for (int boxnummer = 0; boxnummer < 9; boxnummer++) {
			for (int zahl = 1; zahl < 10; zahl++) {
				this.box[boxnummer][zahl] = false;
			}
		}
		for (int zeile = 0; zeile < 9; zeile++) {
			for (int spalte = 0; spalte < 9; spalte++) {
				this.zeile[zeile][spalte + 1] = false;
				this.spalte[zeile][spalte + 1] = false;
			}
		}

		for (int zeile = 0; zeile < 9; zeile++) {
			for (int spalte = 0; spalte < 9; spalte++) {
				this.teilloesung[zeile][spalte] = 0;
			}
		}

		count = 0;
	}

	/**
	 * @param zahl übergibt eine Zahl, welche zufällig aus dem "teilloesung" Array ermittelt wurde.
	 * 
	 * Ermittelt ob an der ermittelten Position bereits eine Zahl in dem "teilloesung"
	 * Array vorhanden ist.
	 * 
	 * @return true wenn noch keine Zahl gesetzt wurde bzw. false wenn bereits eine Zahl für diese
	 * Stelle generiert wurde.
	 */

	protected boolean istLeeresFeld(int zahl) {
		return zahl == 0;
	}

	/**
	 * @param zeile übergibt die Zeile die zufällig generiert wurde.
	 * @param spalte übergibt die Spalte die zufällig generiert wurde.
	 * 
	 * Ermittelt anhand von Zeile und Spalte die Nummer Sudoku-Box.
	 * 
	 * @return int-Wert über die Nummer der Box.
	 */

	protected int getBoxNummer(int zeile, int spalte) {
		return 3 * (zeile / 3) + (spalte / 3);
	}

	/**
	 * @param zeile übergibt die zufällig ermittelte Zeile.
	 * @param spalte übergibt die zufällig ermittelte Spalte.
	 * @param zahl übergibt die zufällig ermittelte Zahl.
	 * 
	 * Ermittelt ob die Zufällig ermittelte Zahl sowohl in der Box, Zeile als auch
	 * Spalte nicht bereits vorhanden ist.
	 * 
	 * @return true wenn die Zahl noch nicht vorhanden ist bzw. false wenn die Zahl bereits
	 * vorhanden ist.
	 */

	protected boolean istGueltigerSchritt(int zeile, int spalte, int zahl) {
		return (istLeeresFeld(teilloesung[zeile][spalte])) && !this.zeile[zeile][zahl] && !this.spalte[spalte][zahl]
				&& !this.box[getBoxNummer(zeile, spalte)][zahl];
	}

	/**
	 * @param schwierigkeitsgrad übergibt die Anzahl der Vorgaben und somit den Schwierigkeitsgrad.
	 * 
	 * Ermittelt zufällig Zeile, Spalte und Zahl und überprüft mit der Methode
	 * "istGueltigerSchritt" ob die Zahl nicht bereits in Zeile, Spalte oder Box vorkommt
	 * oder an dieser Postion bereits eine andere Zahl gesetzt wurde. Falls nicht werden
	 * mithilfe der Booleschen Arrays die Zeile, Spalte und Box mit der ermittelten Zahl
	 * auf true gesetzt. Die zufällig ermittelte Zahl wird außerdem in die
	 * "teilloesung geschrieben" und sämtlichen Vorgaben werden die Werte in die GUI
         * geschrieben und das ermittelte Sudoku wird auf Lösbarkeit geprüft.
	 */

	public void erzeugeSudoku(int schwierigkeitsgrad) {
		do {
			initialisieren();
			do {
				for (int i = 0; i < schwierigkeitsgrad; i++) {
					int zeile = (int) (Math.random() * 9);
					int spalte = (int) (Math.random() * 9);
					int zahl = 1 + (int) (Math.random() * 9);
					if (istGueltigerSchritt(zeile, spalte, zahl)) {
						teilloesung[zeile][spalte] = zahl;
						this.box[getBoxNummer(zeile, spalte)][zahl] = true;
						this.zeile[zeile][zahl] = true;
						this.spalte[spalte][zahl] = true;
						count++;
					} else {
						i--;
					}
				}
			} while (count < schwierigkeitsgrad);
			_sudoku.setzeGUIZellenWerte(teilloesung);
			_sudoku.setzeVorgabe(teilloesung);
		} while (!versucheZuLoesen(0, 0));
	}

	private GUISudokuBoard	_sudoku;
	private int		count		= 0;
	protected boolean[][]	zeile		= new boolean[9][10];
	protected boolean[][]	spalte		= new boolean[9][10];
	protected boolean[][]	box			= new boolean[9][10];
	protected int[][]	teilloesung	= new int[9][9];
}
