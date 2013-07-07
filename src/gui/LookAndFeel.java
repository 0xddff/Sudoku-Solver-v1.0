package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Die LookAndFeel Klasse implementiert das Interface ActionListener um bei einer Auswahl 
 * das gewünschte Design setzen zu können.
 */
public class LookAndFeel implements ActionListener {

	/**
	 * @param _sudoku GUISudoku-Objekt um Zugriff auf das "Hauptfenster" zu haben
	 */
	public LookAndFeel(GUISudoku sudoku) {
		_sudoku = sudoku;
	}

	/**
	 * Wird ein LookAndFeel an einem JRadioButton in GUISudoku gesetzt wird diese actionPerformed(ActionEvent)-Methode
	 * aufgerufen. Wir holen uns zunächst alle in einer ButtonGroup zusammengefassten JRadioButton und schreiben
	 * uns in ein LookAndFeelInfo-Array alle im System installierten LookAndFeels.
	 * 
	 * Nun durchlaufen wir zwei Schleifen. Einmal iterieren wir über unsere JRadioButtons und für jeden JRadioButton
	 * über alle LookAndFeels. Wenn unser JRadioButton selektiert ist und das System das im JRadioButton angegeben
	 * LookAndFeel enthält dann übernehmen wir das neue Design.
	 * Tritt dabei ein Fehler auf fangen wir die Exception und geben die Fehlermeldung in der Konsole aus.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ButtonGroup group = _sudoku.getLookAndFeel();
		LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();

		for (AbstractButton tmp : Collections.list(group.getElements())) {
			for (LookAndFeelInfo look : info) {
				if (tmp.isSelected() && look.getName().contains(tmp.getText())) {
					try {
						UIManager.setLookAndFeel(look.getClassName());
						SwingUtilities.updateComponentTreeUI(_sudoku.getFrame());
					} catch (Exception ex) {
						System.out.println("Fehler beim Laden vom Design");
					}
				}
			}
		}
	}

	GUISudoku	_sudoku;
}
