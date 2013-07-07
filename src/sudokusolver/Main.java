package sudokusolver;

import gui.GUISudoku;
import gui.GUISudokuBoard;

/**
 * Starklasse des Sudoku-Solvers. Initialisiert ein 9x9 großes int-Array mit dem Wert 0 
 * und übergibt dieses Array an den parametrisierten GUISudokuBoard-Konstruktor.
 **/

public class Main {
    
    public static void main(String[] args) {
        int[][] teilloesung = {
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0}
        };
        
        new GUISudoku(new GUISudokuBoard(teilloesung));
    }   
}
