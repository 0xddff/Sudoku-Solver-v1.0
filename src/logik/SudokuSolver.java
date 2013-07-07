package logik;

import gui.GUISudokuBoard;

/**
 * Die SudokuSolver-Klasse enthält Methoden um ein Sudoku-Board-Array zu lösen, oder die Lösbarkeit
 * zu prüfen.
 * 
 */

public class SudokuSolver {

	/**
	 * 
	 * @param sudokuBoard GUISudokuBoard-Objekt
	 * 
	 * Dieser parametrisierte Konstruktor benötigt ein GUISudokuBoard-Objekt um die
	 * Zellenwerte aus der GUI auszulesen. Hierzu wird eine Referenz vom übergebenen
	 * GUISudokuBoard-Objekt in der Klassenvariablen "_sudoku" gespeichert.
	 * 
	 */
	public SudokuSolver(GUISudokuBoard sudokuBoard) {
		_sudokuBoard = sudokuBoard;
	}

	/**
	 * 
	 * Das Array "feld" wird Zeile für Zeile und Spalte für Spalte mit der Methode "istKandidat" auf
	 * einen Regelbruch überprüft, sobald eine Zelle des Sudokus ungleich Null ist.
	 * 
	 * @param feld zweidimensionales int-Array in dem sich ein Sudoku befindet
         * 
	 * @return true falls das "feld" keinen Regelbruch enthält
	 */
	private boolean istErlaubtesBoard(int[][] feld) {
		for (int zeile = 0; zeile < 9; zeile++) {
			for (int spalte = 0; spalte < 9; spalte++) {
				int num = feld[zeile][spalte];
				if (num != 0) {
					if (!istKandidat(feld, zeile, spalte, num)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Diese Methode übergibt die ihr übergebenen Parameter an die drei Methoden "zeilenPruefer",
	 * "spaltenPruefer" und "boxPruefer".
	 * 
	 * @param feld zweidimensionales int-Array in dem sich ein Sudoku befindet
	 * @param zeile zu überprüfende Zeile
	 * @param spalte zu überprüfende Spalte
	 * @param num zu überprüfende Zahl
         * 
	 * @return true wenn keine der drei Methoden einen Regelbruch feststellt
	 */
	private boolean istKandidat(int[][] feld, int zeile, int spalte, int num) {
		return !zeilenPruefer(feld, zeile, spalte, num) && !spaltenPruefer(feld, zeile, spalte, num) && !boxPruefer(feld, zeile, spalte, num);
	}

	/**
	 * Diese Methode überprüft die ihr übergebene Zeile mit der übergebenen Zahl auf Dopplungen und
	 * somit auf Regelbrüche.
	 * 
	 * @param feld zweidimensionales int-Array in dem sich ein Sudoku befindet
	 * @param zeile zu überprüfende Zeile
	 * @param spalte zu überprüfende Spalte
	 * @param num zu überprüfende Zahl
         * 
	 * @return true falls die übergebene Spalte nicht mit i übereinstimmt, also nicht sie selbst
	 * ist, und die Zahl an einer weiteren Stelle der Zeile bereits vorhanden ist
	 */
	private boolean zeilenPruefer(int[][] feld, int zeile, int spalte, int num) {
		for (int i = 0; i < 9; i++) {
			if (i != spalte && feld[zeile][i] == num) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Diese Methode überprüft die ihr übergebene Spalte mit der übergebenen Zahl auf Dopplungen und
	 * somit auf Regelbrüche.
	 * 
	 * @param feld zweidimensionales int-Array in dem sich ein Sudoku befindet
	 * @param zeilem zu überprüfende Zeile
	 * @param spalte zu überprüfende Spalte
	 * @param num, zu überprüfende Zahl
         * 
	 * @return true falls die übergebene Zeile nicht mit i übereinstimmt, also nicht sie selbst ist,
	 * und die Zahl an einer weiteren Stelle der Spalte bereits vorhanden ist
	 */
	private boolean spaltenPruefer(int[][] feld, int zeile, int spalte, int num) {
		for (int i = 0; i < 9; i++) {
			if (i != zeile && feld[i][spalte] == num) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Diese Methode überprüft ob in einer Box die selbe Zahl bereits vorhanden ist. Hierzu werden
	 * im Falle eines 9x9 Sudokus jeweils 9 mal 3 Zeilen und 3 Spalten des Arrays betrachtet.
	 * 
	 * @param feld,zweidimensionales int-Array in dem sich ein Sudoku befindet
	 * @param zeile zu überprüfende Zeile
	 * @param spalte zu überprüfende Spalte
	 * @param num zu überprüfende Zahl
         * 
	 * @return true falls die übergebene Zeile nicht mit i übereinstimmt, also nicht sie selbst ist,
	 * und die übergebene Spalte nicht mit j übereinstimmt, also ebenfalls nicht sie selbst
	 * ist, und die Zahl an einer weiteren Stelle der Box bereits vorhanden ist
	 */
	private boolean boxPruefer(int[][] feld, int zeile, int spalte, int num) {

		int z = zeile - zeile % 3;
		int s = spalte - spalte % 3;

		for (int i = z; i < 3 + z; i++) {
			for (int j = s; j < 3 + s; j++) {
				if (i != zeile && j != spalte && feld[i][j] == num) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Diese Methode wird außerhalb dieser Klasse benutzt um das Sudoku aus der GUI einzulesen um es
	 * zu überprüfen und die Lösung in das in der SudokuBoard-Klasse befindliche Lösungs-Array zu
	 * schreiben.
	 * 
	 * @param zeile Zeilenstartpunkt
	 * @param spalte Spaltenstartpunkt
         * 
	 * @return true falls versucheZuLoesen true zurückgibt
	 */
	public boolean versucheZuLoesen(int zeile, int spalte) {
		return versucheZuLoesen(_sudokuBoard.getZellenWerte(), zeile, spalte);
	}

	/**
	 * Diese Methode versucht das Sudoku zu lösen. Hierzu wird zu Beginn das übergebene Array in das
	 * in der SudokuBoard-Klasse befindliche Lösungs-Array geschrieben, wodurch das übergebene Array
	 * unverändert bleibt. Daraufhin wird das Lösungs-Array Zeile für Zeile und Spalte für Spalte
	 * eingelesen, wobei mittels der ersten Bedingung bereits beschriebene Zellen übersprungen
	 * werden und sich die Methode selbst mit der nächsten Zeile und Spalte aufruft. Die letzte
	 * for-Schleife prüft jede Zahl mittels der Methoden IstKandidat und istErlaubtesBoard und setzt
	 * die aktuelle Zahl an die aktuelle Zeilen-Spalten-Kombination, falls keine Regelbrüche
	 * festgestellt wurden. In der letzten if-Bedingung ruft sich die Methode mit der nächsten Zeile
	 * und Spalte selbst auf.
	 * 
	 * @param feld zweidimensionales int-Array in dem sich ein Sudoku befindet
	 * @param zeile zu überprüfende Zeile
	 * @param spalte zu überprüfende Spalte
         * 
	 * @return true falls die ArrayIndexOutOfBoundsException ausgelüst wird und somit das gesamt
	 * Array keine Fehler hatte, oder istKandidat und istErlaubtesBoard keine Regelbrüche
	 * feststellen.
	 */
	private boolean versucheZuLoesen(int[][] feld, int zeile, int spalte) {
		for (int i = 0; i < feld.length; i++) {
			for (int x = 0; x < feld[i].length; x++) {
				_sudokuBoard.setzeZelleInLoesung(feld[i][x], i, x);
			}
		}

		int nextspalte = (spalte + 1) % _sudokuBoard.getLoesung().length;
		int nextzeile = (nextspalte == 0) ? zeile + 1 : zeile;

		try {
			if (_sudokuBoard.getLoesung()[zeile][spalte] != 0) {
				return versucheZuLoesen(_sudokuBoard.getLoesung(), nextzeile, nextspalte);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return true;
		}

		for (int i = 1; i <= _sudokuBoard.getLoesung().length; i++) {
			if (istKandidat(_sudokuBoard.getLoesung(), zeile, spalte, i)) {
				if (istErlaubtesBoard(_sudokuBoard.getLoesung())) {
					_sudokuBoard.setzeZelleInLoesung(i, zeile, spalte);
				} else
					return false;

				if (versucheZuLoesen(_sudokuBoard.getLoesung(), nextzeile, nextspalte)) {
					return true;
				}
			}
		}
		_sudokuBoard.setzeZelleInLoesung(0, zeile, spalte);
		return false;
	}

	private GUISudokuBoard	_sudokuBoard;
}
