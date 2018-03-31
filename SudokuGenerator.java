package Prog3;
/**
 * UIC CS 342 Project 3 - Sudoku 
 * Project Member 
 * Johnson Ogunyomi   <joguny2@uic.edu>
 * Margi Katwala 	 <mkatwa3@uic.edu>
 * Syed Rahman 	    	 <srahma35@uic.edu>
 * **/ 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {

 public SudokuPuzzle generateRandomSudoku(SudokuPuzzleType puzzleType) {
  SudokuPuzzle puzzle = new SudokuPuzzle(puzzleType.getRows(), puzzleType.getColumns(), puzzleType.getBoxWidth(), puzzleType.getBoxHeight(), puzzleType.getValidValues());
  SudokuPuzzle copy = new SudokuPuzzle(puzzleType.getRows(), puzzleType.getColumns(), puzzleType.getBoxWidth(), puzzleType.getBoxHeight(), puzzleType.getValidValues());
  
  Random randomGenerator = new Random();
  
  
  List<String> notUsedValidValues =  new ArrayList<String>(Arrays.asList(copy.getValidValues()));
  for(int r = 0;r < copy.getNumRows();r++) {
   int randomValue = randomGenerator.nextInt(notUsedValidValues.size());
   copy.makeMove(r, 0, notUsedValidValues.get(randomValue), true);
   notUsedValidValues.remove(randomValue);
  }
  
  
  
  int numberOfValuesToKeep = (int)(0.22222*(copy.getNumRows()*copy.getNumRows()));
  
  for(int i = 0;i < numberOfValuesToKeep; i++) {
	  int randomRow = randomGenerator.nextInt(puzzle.getNumRows());
	  int randomColumn = randomGenerator.nextInt(puzzle.getNumColumns());
   
	  if(puzzle.isSlotAvailable(randomRow, randomColumn)) 
		  puzzle.makeMove(randomRow, randomColumn, copy.getValue(randomRow, randomColumn), false);
  }
  
  puzzle.updateCLists();
  return puzzle;
 }
 
 public SudokuPuzzle generateRandomSudoku(SudokuPuzzleType puzzleType, String[][] cellCoords) {
     SudokuPuzzle puzzle = new SudokuPuzzle(puzzleType.getRows(), puzzleType.getColumns(), puzzleType.getBoxWidth(), 
      puzzleType.getBoxHeight(), puzzleType.getValidValues());
      
     for (int r=0; r<9; r++) {
      for (int c=0; c<9; c++) {
       if (cellCoords[r][c] == null)
        continue;
       if (puzzle.isSlotAvailable(r, c))
        puzzle.makeMove(r, c, cellCoords[r][c], false);
      }
     }

     return puzzle;
 }
}
