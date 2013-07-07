package logik;

import gui.JStatusBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Die Klasse Statistik erzeugt die für uns in GUISudokuBoard relevante JStatusBar. Wir implementieren außerdem das Interface
 * ActionListener um sekündlich unseren Timer in der GUI zu aktualiseren.
 */

public class Statistik implements ActionListener {
	/**
	 * In unserem Konstruktor erzeugen wir uns eine temporäre ArrayList die Objekte vom Typ JComponent enthält.
	 * Dieser Liste fügen wir unsere in der JStatusBar, von Rechts nach Links aneinandergereiht, darzustellenden
	 * Elemente hinzu. Timer erwartet im Konstruktor eine Zeitangabe in Millisekunden, wie oft sich der Timer aktualisieren
	 * soll und den aufzurufenden ActionListener.
	 * JStatusBar erwartet im Konstruktor eine weiteres, einzelnes Objekt vom Typ JComponent das linksbündig
	 * in der JStatusBar dargestellt wird.
	 */
	public Statistik() {
		List<JComponent> list = new ArrayList<JComponent>();

		list.add(new JLabel("Moves: " + moveCount));
		list.add(new JLabel("Hints used: " + hintCount));
		timer = new Timer(1000, this);
		list.add(new JLabel("Timer: " + timerCount + " Sekunden"));
		_statusBar = new JStatusBar(new JLabel("Statistik: "), list);
	}

	/**
	 * Liefert die initialiserte JStatusBar zurück um diese einem JFrame hinzuzufügen.
	 * 
	 * @return JStatusBar die wir initialisert haben
	 */
	
	public JStatusBar getStatusBar() {
		return _statusBar;
	}

	/**
	 * Setzt alle in der JStatusBar angezeigten Counter auf 0.
	 */
	
	public void loescheStatusBar() {
		moveCount = -1;
		hintCount = -1;
		timerCount = -1;
		
		erhoeheHintCount();
		erhoeheMoveCount();
	}

	/**
	 * Erhöht den Timer immer um 1 und aktualisert den darzustellenden Wert in der JStatusBar.
	 */
	public void erhoeheTimerCount() {
		timerCount++;
		JLabel tmp = (JLabel) _statusBar.getSecondaryComponent(2);
		tmp.setText("Timer: " + timerCount + " Sekunden");
	}

	/**
	 * Erhöht den Züge-Zähler immer um 1 und aktualisert den darzustellenden Wert in der JStatusBar.
	 */
	public void erhoeheMoveCount() {
		moveCount++;
		JLabel tmp = (JLabel) _statusBar.getSecondaryComponent(0);
		tmp.setText("Moves: " + moveCount);
	}

	/**
	 * Erhöht den Hinweis-Zähler immer um 1 und aktualisert den darzustellenden Wert in der JStatusBar.
	 */
	public void erhoeheHintCount() {
		hintCount++;
		JLabel tmp = (JLabel) _statusBar.getSecondaryComponent(1);
		tmp.setText("Hints used: " + hintCount);
	}

	/**
	 * startet den Timer
	 */
	public void starteTimer() {
		timer.start();
	}

	/**
	 * stoppt den Timer
	 */
	public void stoppeTimer() {
		timer.stop();
	}
	
	/**
	 * Wird jede Sekunde durch das Timer-Objekt aufgerufen
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		erhoeheTimerCount();
	}

	private Timer		timer;
	private int		hintCount	= 0;
	private int		moveCount	= 0;
	private int		timerCount	= 0;
	private JStatusBar	_statusBar;
}
