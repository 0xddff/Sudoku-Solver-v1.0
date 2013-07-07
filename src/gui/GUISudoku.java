package gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.InputEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import listener.CheckListener;
import listener.DeleteListener;
import listener.HintListener;
import listener.LadenListener;
import listener.LoesungListener;
import listener.NewGameListener;
import listener.SpeichernListener;
import logik.SudokuSolver;

/**
 * Ist die grafische Gesamtoberfläche des Sudoku-Solver. GUISudoku enthält das Anzeige-JFrame
 * dem alle grafischen Bedienelemente hinzugefügt werden. Wir erben von SudokuSolver,
 * um unser erzeugtes GUISudokuBoard-Objekt an den SudokuSolver weiterzureichen. Dadurch
 * kann auch der SudokuSolver Werte in der GUI und Logik setzen.
 */

public class GUISudoku extends SudokuSolver {
	
	/**
	 * @param sudokuBoard GUISudokuBoard, dass uns das initialiserte 9x9 Sudokufeld liefert.
	 * 
	 * Zuerst müssen wir in unserem parameterisiertem Konstruktor den Konstruktor unserer Super-
	 * Klasse aufrufen. Hierbei übergeben wir das GUISudokuBoard-Objekt um im SudokuSolver auch
	 * Zugriff auf die in der Logik enthaltenen Zellenwerte zu haben. 
	 * Danach initialiseren wir unser JFrame, unsere JMenuItems sowie JRadioButtons, setzen Hotkeys,
	 * melden die ActionListener an und fügen die Items unseren JMenu, JMenuBar hinzu.
	 */
	public GUISudoku(GUISudokuBoard sudokuBoard) {
		super(sudokuBoard);
		_frame = new JFrame("SudokuSolver v1.0");
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setLocationRelativeTo(null);

		_newGame = new JMenuItem("Neues Spiel");
		_newGame.addActionListener(new NewGameListener(sudokuBoard, this));
		_newGame.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK));
		
		_loesen = new JMenuItem("Lösen");
		_loesen.addActionListener(new LoesungListener(sudokuBoard,this));
		_loesen.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
		_loesen.setEnabled(false);
		_check = new JMenuItem("Überprüfen");
		_check.addActionListener(new CheckListener(this));
		_check.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		_check.setEnabled(false);
		_hint = new JMenuItem("Tipp");
		_hint.addActionListener(new HintListener(sudokuBoard,this));
		_hint.setAccelerator(KeyStroke.getKeyStroke('T', InputEvent.CTRL_MASK));
		_hint.setEnabled(false);
		_previous = new JMenuItem("Zurückcksetzen");
		_previous.addActionListener(new DeleteListener(sudokuBoard));
		_previous.setAccelerator(KeyStroke.getKeyStroke('Z', InputEvent.CTRL_MASK));
		_previous.setEnabled(false);
		_save = new JMenuItem("Spielstand speichern");
		_save.addActionListener(new SpeichernListener(sudokuBoard));
		_save.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
		_save.setEnabled(false);
		_laden = new JMenuItem("Spielstand laden");
		_laden.addActionListener(new LadenListener(this, sudokuBoard));
		_laden.setAccelerator(KeyStroke.getKeyStroke('L', InputEvent.CTRL_MASK));

		_spiel = new JMenu("Spiel");
		_spiel.add(_newGame);
		_spiel.add(_loesen);
		_spiel.add(_check);
		_spiel.add(_previous);
		_spiel.add(_hint);
		_spiel.add(_laden);
		_spiel.add(_save);

		_leicht = new JRadioButton("Leicht");
		_mittel = new JRadioButton("Mittel");
		_mittel.setSelected(true);
		_schwer = new JRadioButton("Schwer");

		_lookAndFeelGruppe = new ButtonGroup();
		_buttonGroup = new ButtonGroup();
		_buttonGroup.add(_leicht);
		_buttonGroup.add(_mittel);
		_buttonGroup.add(_schwer);

		_einstellungen = new JMenu("Einstellungen");
		_schwierigkeit = new JMenu("Schwierigkeitsgrad");
		_einstellungen.add(_schwierigkeit);
		_schwierigkeit.add(_leicht);
		_schwierigkeit.add(_mittel);
		_schwierigkeit.add(_schwer);
		
		_lookAndFeel = new JMenu("Look And Feel");
		_einstellungen.add(_lookAndFeel);

		initLookAndFeel();
		
		_menuBar = new JMenuBar();
		_menuBar.add(_spiel);
		_menuBar.add(_einstellungen);
		_panel = sudokuBoard.getPanel();

		_frame.setLayout(new GridBagLayout());
		addComponent(_panel, 0, 0, 1, 1);
		addComponent(sudokuBoard.getStatusBar(), 0, 1, 1, 1);

		_frame.setJMenuBar(_menuBar);

		_frame.pack();
		_frame.setResizable(false);
		_frame.setVisible(true);
	}

	/**
	 * @param component die zum JFrame hinzuzufügende Komponente (alle Elemente die von Component erben)
	 * @param gridx X-Koordinate im GridBagLayout
	 * @param gridy Y-Koordinate im GridBagLayout
	 * @param gridwidth Breite der Komponente
	 * @param gridheight Höhe der Komponente
	 */
	private void addComponent(Component component,
			int gridx, int gridy, int gridwidth, int gridheight) {
		Insets insets = new Insets(0, 0, 0, 0);
		GridBagConstraints gbc = new GridBagConstraints(gridx, gridy,
				gridwidth, gridheight, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, insets, 0, 0);
		_frame.add(component, gbc);
	}
	
	/**
	 * Schreibt alle im System angemeldeten Designs in ein LookAndFeelInfo-Array. Über dieses
	 * Array iterieren wir nun und erzeugen dabei, für jedes einzelne Element, ein JRadioButton
	 * das bei der Objekterzeugung den Namen des LookAndFeels als Parameter übergeben bekommt.
	 * An diesem JRadioButton wird außerdem noch der LookAndFeel(GUISudoku) AktionListener angemeldet.
	 * Zusätzlich werden die einzelnen JRadioButtons einen ButtonGroup hinzugefügt, damit immer
	 * nur ein Button zur selben Zeit aktiv ist. Die IF-Anweisung sorgt dafür, dass der 
	 * JRadioButton mit dem im System aktivierten Design selektiert ist.
	 */
	private void initLookAndFeel(){
		LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();

		for(LookAndFeelInfo tmp : info){
			JRadioButton item = new JRadioButton(tmp.getName());
			item.addActionListener(new LookAndFeel(this));
			_lookAndFeelGruppe.add(item);
			_lookAndFeel.add(item);
			
			if(UIManager.getLookAndFeel().getName() == tmp.getName())
				item.setSelected(true);
		}
	}
	
	/**
	 * Liefert uns das JMenuItem zum Spielstand speichern zurück.
	 * 
	 * @return JMenuItem
	 */
	public JMenuItem getSpielStandSpeichern(){
		return _save;
	}
	
	/**
	 * Liefert uns das JMenuItem zum Hint zurück.
	 * 
	 * @return JMenuItem
	 */
	public JMenuItem getHint(){
		return _hint;
	}
	
	/**
	 * Liefert uns das JMenuItem zum Zurücksetzen zurück.
	 * 
	 * @return JMenuItem
	 */
	public JMenuItem getZurueckSetzen(){
		return _previous;
	}
	
	/**
	 * Liefert uns das JMenuItem zum Spiel überprüfen zurück.
	 * 
	 * @return JMenuItem
	 */
	public JMenuItem getCheck(){
		return _check;
	}
	
	/**
	 * Liefert uns das JMenuItem zum Spiel lösen zurück.
	 * 
	 * @return JMenuItem
	 */
	public JMenuItem getLoesen(){
		return _loesen;
	}
	
	/**
	 * Liefert uns die ButtonGroup mit JRadioButtons die die LookAndFeel-Informationen enthalten.
	 * 
	 * @return ButtonGroup von JRadioButtons
	 */
	public ButtonGroup getLookAndFeel(){
		return _lookAndFeelGruppe;
	}
	
	/**
	 * Liefert uns die ButtonGroup mit JRadioButtons die den Schwierigkeitsgrad enthalten.
	 * 
	 * @return ButtonGroup des Schwierigkeitsgrad
	 */
	public ButtonGroup getButtonGroup(){
		return _buttonGroup;
	}

	/**
	 * Liefert das Hauptfenster vom Sudoku-Solver zurück.
	 * 
	 * @return JFrame vom Sudoku-Solver
	 */
	public JFrame getFrame() {
		return _frame;
	}

	private JMenuItem _save;
	private JMenuItem _laden;
	private JMenuItem _newGame;
	private JMenuItem _hint;
	private JMenuItem _previous;
	private JMenuBar _menuBar;
	private JMenu _einstellungen;
	private JMenu _schwierigkeit;
	private JMenu _spiel;
	private JMenu _lookAndFeel;
	private JRadioButton _leicht;
	private JRadioButton _mittel;
	private JRadioButton _schwer;
	private ButtonGroup _buttonGroup;
	private ButtonGroup _lookAndFeelGruppe;
	private JMenuItem _loesen;
	private JMenuItem _check;
	private JFrame _frame;
	private JPanel _panel;
}