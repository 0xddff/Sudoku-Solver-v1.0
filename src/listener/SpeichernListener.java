package listener;

import gui.GUISudokuBoard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFileChooser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import logik.SudokuBoard;

/**
 * Die Klasse SpeichernListener implementiert das Interface ActionListener um auf das
 * Drücken des entsprechenden JMenuItems reagieren zu können.
 */
public class SpeichernListener implements ActionListener {

	/**
	 * @param sudokuBoard GUISudokuBoard-Objekt das Manipulationen an GUI und Logik ermöglicht
	 * 
	 * In unserem SpeichernListener Konstruktor erfolgt eine Speicherung des übergebenen
	 * GUISudokuBoard-Objekts sudokuBoard in der Variable _sudokuBoard.
	 */
	
	public SpeichernListener(GUISudokuBoard sudokuBoard) {
		_sudokuBoard = sudokuBoard;
	}

	/**
	 * Wird immer aufgerufen wenn das entsprechende JMenuItem geklickt wird.
	 * Wir erzeugen uns zunächst ein Objekt der Klasse FileChooser und setzen einen SelectionMode.
	 * Das bedeutet, dass über unseren JFileChooser-Dialog nur Ordner auswählbar sind.
	 * 
	 * In einer IF-Anweisung rufen wir nun den JFileChooser Dialog auf und werten
	 * den Rückgabeparameter aus. Nur wenn ein Ordner und der Button "Speichern"
	 * des Dialog gedrückt wird erzeugen wir uns ein Objekt des Typs JAXBContext
	 * dem wir unsere SudokuBoard.class übergeben. Ein Marshaller-Objekt ermöglicht
	 * es nun über einen einfachen marshal(Object, FileOutputStream)-Methodenaufruf die in der SudokuBoard
	 * Klasse gespeicherten Werte auszulesen.
	 * 
	 * Um immer einen eindeutigen Dateinamen für unsere Spielstände zu haben, ermitteln wir uns über die
	 * Klasse Calendar das aktuelle Datum + Zeit und passen die Ausgabe über die Klasse SimpleDateFormat an.
	 * 
	 * Unser generiete Dateiname ist also: Sudoku_JAHR_MONAT_TAG_STUNDE_MINUTE_SEKUNDE.sudoku
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
			chooser.setDialogTitle("Speichern");
			if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				JAXBContext jc = JAXBContext.newInstance(SudokuBoard.class);
				Marshaller m = jc.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

				DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
				Calendar cal = Calendar.getInstance();
				String fileName = chooser.getSelectedFile().getAbsolutePath() + "\\Sudoku_" + dateFormat.format(cal.getTime()) + ".sudoku";
				System.out.println(fileName);
				m.marshal(new SudokuBoard(_sudokuBoard.getZellenWerte()), new FileOutputStream(fileName));
			}
		} catch (JAXBException | FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	private GUISudokuBoard	_sudokuBoard;
}
