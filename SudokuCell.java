package Prog3;
/**
 * UIC CS 342 Project 3 - Sudoku 
 * Project Member 
 * Johnson Ogunyomi   <joguny2@uic.edu>
 * Margi Katwala 	 <mkatwa3@uic.edu>
 * Syed Rahman 	    	 <srahma35@uic.edu>
 * **/ 
import java.util.ArrayList;
public class SudokuCell {
	private String Value;
	private Tuple<Integer, Integer> coordinate;
	private ArrayList<String> candidateList;

	public SudokuCell(int row, int col, String value) {
		Value = value;
		coordinate = new Tuple<Integer, Integer>(row, col);

		candidateList = new ArrayList<String>();

	}

	public String getValue() {
		return Value;
	}

	public void setValue(String v) {
		Value = v;
	}

	public Tuple<Integer, Integer> getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Tuple<Integer, Integer> c) {
		coordinate = c;
	}

	public ArrayList<String> getCandidateList() {
		return candidateList;
	}

	public void setCandidateList(ArrayList<String> cList) {
		candidateList = cList;
	}


}