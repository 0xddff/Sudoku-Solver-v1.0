package logik;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Ist unsere Logik-Klasse in der immer der aktuelle Spielstand, die Lösung und die Vorgabe (das
 * generierte Sudoku) enthalten sind. Die Annotations @XMLRootElement und @XMLElement legen fest,
 * welche Elemente zur Spielstandspeicherung relevant sind.
 */
@XmlRootElement(name = "sudoku")
public class SudokuBoard {
	/**
	 * Nicht parameterisierter Konstruktor der nur zum Spielstand speichern und laden verwendet
	 * wird.
	 */
	public SudokuBoard() {

	}

	/**
	 * @param size int-Wert der die Größe des Sudoku's angibt (9)
	 * 
	 * Parameterisierter Konstruktor der als Übergabewert die Sudokugröße bekommt. In
	 * unserem erweiterten Konstruktor initialiseren wir die benötigten int-Arrays und
	 * Variablen mit der übergebenen Größe.
	 */
	public SudokuBoard(int size) {
		_board = new int[size][size];
		_vorgabe = new int[size][size];
		_loesung = new int[size][size];
		SIZE = size;
		BOX_SIZE = (int) Math.sqrt(size);
	}

	/**
	 * Parameterisierter Konstruktor der als Übergabewert das Sudokuboard bekommt. Wir rufen diesen
	 * Konstruktor nur beim Speichern eines Sudokus auf. Mit dem this(int)-Aufruf initialiseren wir
	 * unsere Klasse neu und übernehmen das übergebene Sudokufeld als das aktuelle Sudoku.
	 * 
	 * @param board int-Array, dass das Spielfeld enthält
	 */
	public SudokuBoard(int[][] board) {
		this(board.length);
		_board = board;
	}

	/**
	 * Mit dieser Methode wird anhand der Übergabeparameter überprüft, ob ein Wert an dieser Stelle
	 * in unserem Sudokufeld steht.
	 * 
	 * @param spalte eindeutige int-Variable zur Zuordnung im 9x9 Sudokufeld
	 * @param zeile eindeutige int-Variable zur Zuordnung im 9x9 Sudokufeld
         * 
	 * @return true, wenn an der Stelle ein Wert ungleich 0 gesetzt ist
	 */
	public boolean istZelleGesetzt(int zeile, int spalte) {
		return _board[zeile][spalte] != 0;
	}

	/**
	 * @param zahl der in der Logik zu setzenden Zahlenwert (0-9)
	 * @param zeile eindeutige int-Variable zur Zuordnung der Zahl im 9x9 Sudokufeld
	 * @param spalte eindeutige int-Variable zur Zuordnung der Zahl im 9x9 Sudokufeld
	 * 
	 * Mit dieser Methode wird eine neue Zahl an der übergebene Stelle gesetzt.
	 */
	public void setzeZellenWert(int zahl, int zeile, int spalte) {
		_board[zeile][spalte] = zahl;
	}

	/**
	 * @param zeile eindeutige int-Variable zur Zuordnung der Zahl im 9x9 Sudokufeld
	 * @param spalte eindeutige int-Variable zur Zuordnung der Zahl im 9x9 Sudokufeld
	 * 
	 * Mit dieser Methode lassen wir uns den Wert an der übergebenen Position
	 * zurückliefern.
	 * 
	 * @return int-Wert an der angegebenen Position
	 */
	public int getZellenWert(int zeile, int spalte) {
		return _board[zeile][spalte];
	}

	/**
	 * @param zeile eindeutige int-Variable zur Zuordnung der Zahl im 9x9 Sudokufeld
	 *            
	 * Mit dieser Methode merken wir uns die Zeile an der ein neuer Wert
	 * in unserem Sudoku gesetzt werden soll.
	 */
	public void setZeileMarkiert(int zeile) {
		_zeileMarkiert = zeile;
	}

	/**
	 * Liefert uns die gemerkte Zeilen-Position zurück um an der richtigen Position
	 * im Sudoku einen neuen Wert zu setzen.
	 * 
	 * @return int-Wert der uns die gemerkte Zeilen-Position liefert
	 */
	public int getZeileMarkiert() {
		return _zeileMarkiert;
	}

	/**
	 * @param spalte eindeutige int-Variable zur Zuordnung der Zahl im 9x9 Sudokufeld
	 *            
	 * Mit dieser Methode merken wir uns die Spalte an der ein neuer Wert
	 * in unserem Sudoku gesetzt werden soll.
	 */
	public void setSpalteMarkiert(int spalte) {
		_spalteMarkiert = spalte;
	}

	/**
	 * Liefert uns die gemerkte Zeilen-Position zurück um an der richtigen Position
	 * im Sudoku einen neuen Wert zu setzen.
	 * 
	 * @return int-Wert der uns die gemerkte Zeilen-Position liefert
	 */
	public int getSpalteMarkiert() {
		return _spalteMarkiert;
	}

	
	/**
	 * Diese Methode überprüft unser komplettes 9x9 Sudokufeld auf Vollständigkeit.
	 * Wenn ein Feld noch nicht ausgefüllt ist wird false zurückgegeben.
	 * Ansonsten liefert die Methode immer true.
	 * 
	 * @return true, wenn ein Feld keinen Wert enthält
	 */
	public boolean istBoardVollstaendig() {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (_board[x][y] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Liefert uns das aktuelle Spielfeld zurück.
	 * 
	 * @return int-Array, dass das aktuelle Spielfeld enthält
	 */
	public int[][] getZellenWerte() {
		return _board;
	}

	/**
	 * Diese Methode setzt das in der Klasse NewSudoku erzeugte Sudoku und
	 * speichert es in dem _vorgabe int-Array ab. Zusätzlich initialiseren
	 * wir unser Lösungsarray mit den Werten aus der Vorgabe.
	 * 
	 * @param vorgabe erzeugtes Sudoku aus NewSudoku-Klasse
	 */
	protected void setzeVorgabe(int[][] vorgabe) {
		_vorgabe = vorgabe;

		for (int i = 0; i < _vorgabe.length; i++) {
			for (int x = 0; x < _vorgabe[i].length; x++) {
				_loesung[i][x] = _vorgabe[i][x];
			}
		}
	}
	
	/**
	 * Liefert uns das erzeugte Spielfeld zurück.
	 * 
	 * @return int-Array, dass das erzeugte Spielfeld enthält
	 */
	public int[][] getVorgabe() {
		return _vorgabe;
	}

	/**
	 * Liefert uns das gelöste Spielfeld zurück.
	 * 
	 * @return int-Array, dass das gelöste Spielfeld enthält
	 */
	public int[][] getLoesung() {
		return _loesung;
	}

	/**
	 * @param zahl der in der Lösung zu setzenden Zahlenwert (0-9)
	 * @param zeile eindeutige int-Variable zur Zuordnung der Zahl im 9x9 Sudokufeld
	 * @param spalte eindeutige int-Variable zur Zuordnung der Zahl im 9x9 Sudokufeld
	 * 
	 * Mit dieser Methode wird eine neue Zahl an der übergebene Stelle gesetzt.
	 */
	public void setzeZelleInLoesung(int zahl, int zeile, int spalte) {
		_loesung[zeile][spalte] = zahl;
	}

	private int		_zeileMarkiert	= 0;
	private int		_spalteMarkiert	= 0;
	protected final int	LEER		= 0;
	public int		SIZE;
	public int		BOX_SIZE;
	@XmlElement(name = "board", required = true)
	protected int[][]	_board;
	private int[][]		_vorgabe;
	private int[][]		_loesung;
}