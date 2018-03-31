package Prog3;
/**
 * UIC CS 342 Project 3 - Sudoku 
 * Project Member 
 * Johnson Ogunyomi   <joguny2@uic.edu>
 * Margi Katwala 	 <mkatwa3@uic.edu>
 * Syed Rahman 	    	 <srahma35@uic.edu>
 * **/ 
import java.util.ArrayList;

public class SudokuPuzzle {

	protected SudokuCell[][] board;
	// Table to determine if a slot is mutable
	protected boolean[][] mutable;
	private final int ROWS;
	private final int COLUMNS;
	private final int BOXWIDTH;
	private final int BOXHEIGHT;
	private final String [] VALIDVALUES;
	
	public SudokuPuzzle(int rows,int columns,int boxWidth,int boxHeight,String [] validValues) {
		this.ROWS = rows;
		this.COLUMNS = columns;
		this.BOXWIDTH = boxWidth;
		this.BOXHEIGHT = boxHeight;
		this.VALIDVALUES = validValues;
		this.board = new SudokuCell[ROWS][COLUMNS];
		this.mutable = new boolean[ROWS][COLUMNS];
		initializeBoard();
		initializeMutableSlots();
		//initializeCLists();
	}
	
	//copy constructor
	public SudokuPuzzle(SudokuPuzzle puzzle) {
		this.ROWS = puzzle.ROWS;
		this.COLUMNS = puzzle.COLUMNS;
		this.BOXWIDTH = puzzle.BOXWIDTH;
		this.BOXHEIGHT = puzzle.BOXHEIGHT;
		this.VALIDVALUES = puzzle.VALIDVALUES;
		this.board = new SudokuCell[ROWS][COLUMNS];
		for(int r = 0;r < ROWS;r++) {
			for(int c = 0;c < COLUMNS;c++) {
				board[r][c].setValue( puzzle.board[r][c].getValue() ); 
				board[r][c].setCoordinate( puzzle.board[r][c].getCoordinate() );
				board[r][c].setCandidateList( puzzle.board[r][c].getCandidateList() );
			}
		}
		this.mutable = new boolean[ROWS][COLUMNS];
		for(int r = 0;r < ROWS;r++) {
			for(int c = 0;c < COLUMNS;c++) {
				this.mutable[r][c] = puzzle.mutable[r][c];
			}
		}
	}
	
	
	public int getNumRows() {
		return this.ROWS;
	}
	
	public int getNumColumns() {
		return this.COLUMNS;
	}
	
	public int getBoxWidth() {
		return this.BOXWIDTH;
	}
	
	public int getBoxHeight() {
		return this.BOXHEIGHT;
	}
	
	public String [] getValidValues() {
		return this.VALIDVALUES;
	}
	
	public void makeMove(int row,int col,String value,boolean isMutable) {
		if(this.isValidValue(value) && this.isValidMove(row,col,value) && this.isSlotMutable(row, col)) {
			this.board[row][col].setValue(value);
			this.mutable[row][col] = isMutable;
		}
	}
	
	public void erase(int row, int col, boolean isMutable) {
		if(this.isSlotMutable(row, col))
			this.board[row][col].setValue("");
		//this.mutable[row][col] = isMutable;
	}
	
	public boolean isValidMove(int row,int col,String value) {
		if(this.inRange(row,col)) {
			if(!this.numInCol(col,value) && !this.numInRow(row,value) && !this.numInBox(row,col,value)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean numInCol(int col,String value) {
		if(col <= this.COLUMNS) {
			for(int row=0;row < this.ROWS;row++) {
				if(this.board[row][col].getValue().equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean numInRow(int row,String value) {
		if(row <= this.ROWS) {
			for(int col=0;col < this.COLUMNS;col++) {
				if(this.board[row][col].getValue().equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean numInBox(int row,int col,String value) {
		if(this.inRange(row, col)) {
			int boxRow = row / this.BOXHEIGHT;
			int boxCol = col / this.BOXWIDTH;
			
			int startingRow = (boxRow*this.BOXHEIGHT);
			int startingCol = (boxCol*this.BOXWIDTH);
			
			for(int r = startingRow;r <= (startingRow+this.BOXHEIGHT)-1;r++) {
				for(int c = startingCol;c <= (startingCol+this.BOXWIDTH)-1;c++) {
					if(this.board[r][c].getValue().equals(value)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isSlotAvailable(int row,int col) {
		 return (this.inRange(row,col) && this.board[row][col].getValue().equals("") && this.isSlotMutable(row, col));
	}
	
	public boolean isSlotMutable(int row,int col) {
		return this.mutable[row][col];
	}
	
	public String getValue(int row,int col) {
		if(this.inRange(row,col)) {
			return this.board[row][col].getValue();
		}
		return "";
	}
	
	public SudokuCell[][] getBoard() {
		return this.board;
	}
	
	private boolean isValidValue(String value) {
		for(String str : this.VALIDVALUES) {
			if(str.equals(value)) return true;
		}
		return false;
	}
	
	public boolean inRange(int row,int col) {
		return row <= this.ROWS && col <= this.COLUMNS && row >= 0 && col >= 0;
	}
	
	public boolean boardFull() {
		for(int r = 0;r < this.ROWS;r++) {
			for(int c = 0;c < this.COLUMNS;c++) {
				if(this.board[r][c].getValue().equals("")) return false;
			}
		}
		return true;
	}
	
	public void makeSlotEmpty(int row,int col) {
		this.board[row][col].setValue("");
	}
	
	@Override
	public String toString() {
		String str = "Game Board:\n";
		for(int row=0;row < this.ROWS;row++) {
			for(int col=0;col < this.COLUMNS;col++) {
				str += this.board[row][col].getValue() + " ";
			}
			str += "\n";
		}
		return str+"\n";
	}
	
	public void initializeBoard() {
		for(int row = 0;row < this.ROWS;row++) {
			for(int col = 0;col < this.COLUMNS;col++) {
				this.board[row][col] = new SudokuCell(row, col, "");
			}
		}
	}
	
	public void updateCLists() {
		for(int row=0; row<this.ROWS; row++)
			for(int col=0; col<this.COLUMNS; col++) {
				/*ArrayList<String> cList = makeCandidateList(row, col);
				System.out.println(cList);*/
				this.board[row][col].setCandidateList(makeCandidateList(row, col));
			}
	}
	
	private void initializeMutableSlots() {
		for(int row = 0;row < this.ROWS;row++) {
			for(int col = 0;col < this.COLUMNS;col++) {
				if ( this.board[row][col].getValue().equals("") )
					this.mutable[row][col] = true;
				else 
					this.mutable[row][col] = false;
			}
		}
	}
	
	
	private int switchCol(int col) {
		//int c = -1;
		switch(col) 
		{
			case 1:
			case 2:
			case 3:
				return 2;
			case 4:
			case 5:
			case 6: 
				return 5;
			case 7:
			case 8:
			case 9:
				return 8;
			default:
				return -1;
		}
		
	}
	
	private Tuple<Integer, Integer> getSubGrid(int row, int col) {
		int r = -1;
		switch(row)
		{
			case 1:
			case 2:
			case 3:
				r = 2;
			break;
			case 4:
			case 5:
			case 6:
				r = 5;
				break;
			case 7:
			case 8:
			case 9:
				r = 8;
				break;
		}
		
		return new Tuple<Integer, Integer>(r, switchCol(col));
		
	}
	
	// returns a list of values
	// that are in the cell's corresponding
	// row, column, and subgrid
	public ArrayList<String> takenList(int row, int col) {
		ArrayList<String> tList = new ArrayList<String>();
			
		//check each value in corresponding col
		for (int r=0; r<9; r++) {
			// if value in corr col is not blank
			// add it to the Taken List
			if ( !board[r][col].getValue().equals("") )
				tList.add(board[r][col].getValue());
		}
			
		//check each value in corresponding row
		for (int c=0; c<9; c++) {
			// if value in corr row is not blank
			// add it to the Taken List
			if ( !board[row][c].getValue().equals("") )
				tList.add(board[row][c].getValue());
		}
			
		//get the coordinates of the corresponding subgrid
		Tuple<Integer, Integer> sgCoordinates = getSubGrid(row+1, col+1);
		// for each value in the subgrid
		for (int r=sgCoordinates.x-2; r<=sgCoordinates.x; r++) {
			for (int c=sgCoordinates.y-2; c<=sgCoordinates.y; c++) {
				// if value in corr subgrid is not blank
				// add it to the Taken List
				if ( !board[r][c].getValue().equals("") )
					tList.add(board[r][c].getValue());
			}
			
		}
			
			return tList;
		}
	
	public ArrayList<String> makeCandidateList(int row, int col) {
		ArrayList<String> takenList = takenList(row, col);
		ArrayList<String> cList = new ArrayList<String>();
		if (!this.board[row][col].getValue().equals(""))
			return cList;
		for (String v : VALIDVALUES) {
			if(!takenList.contains(v))
				cList.add(v);
		}
		return cList;
	}
	
	public ArrayList<String> candidateList(int row, int col) {
		return this.board[row][col].getCandidateList();
	}
	
	public boolean SingleAlgo() {
		for(int row=0; row<this.ROWS; row++) {
			for(int col=0; col<this.COLUMNS; col++) {
				ArrayList<String> cList = this.board[row][col].getCandidateList();
				String val = this.board[row][col].getValue();
				if (cList.size() == 1 && val.equals("")) {
					this.board[row][col].setValue(cList.get(0));
					this.updateCLists();
					//System.out.println("single return true");
					return true;
				}
			}
		}
		return false;
	}
	
	// takes an array of counters
	// initlaizes each one to zero
	private void initializeCounters(int count[]) {
		for (int i=0; i<count.length; i++)
			count[i] = 0;
	}
	
	public boolean HiddenAlgo() {
		// for each cell..
		for(int row=0; row<this.ROWS; row++) {
			for(int col=0; col<this.COLUMNS; col++) {
				if (!this.board[row][col].getValue().equals(""))
					continue;
				int count[] = new int[9]; // # of times value appears in CList for each value
				initializeCounters(count);
				// go through each CList in its "group" (subgrid)
				Tuple<Integer, Integer> sgCoordinates = this.getSubGrid(row+1, col+1);
				for (int r=sgCoordinates.x-2; r<=sgCoordinates.x; r++) {
					for (int c=sgCoordinates.y-2; c<=sgCoordinates.y; c++) {
						if (!this.board[r][c].getValue().equals(""))
							continue;
						ArrayList<String> CList = this.board[r][c].getCandidateList();
						int i=0;
						// for each value 1..9
						for (String v : VALIDVALUES) {
							if (CList.contains(v))
								//System.out.println("Value at row " + r + " col " + c + " has " + v + " in its cList!");
								count[i]+=1;
							i++;
						}
						
						
					}
				} // end of subgrid
				
				//for(int i=0; i<count.length; i++)
				//	System.out.print(count[i] + ", ");
				for (int i=0; i<count.length; i++) {
					if (count[i] == 1 && this.candidateList(row, col).contains(String.valueOf(i+1))) {
						Integer val = new Integer(i + 1);
						this.board[row][col].setValue(String.valueOf(val));	
						this.updateCLists();
						return true;
					}
				}
				
			}
		}// end of all cells
		return false;
	}
	
	
	private boolean belongsToSubGrid(int row, int col, Tuple <Integer, Integer> subgrid) {
		if ( (row >= subgrid.x-2 && row <=subgrid.x) && (col >= subgrid.y-2 && col <= subgrid.y) )
			return true;
		else 
			return false;
	}
	
	
	private boolean candidateOfSubRow(int row, Tuple <Integer, Integer> subgrid, String val) {
		//boolean b = false;
		for (int subCol=subgrid.y-2; subCol<=subgrid.y; subCol++) {
			//System.out.println(this.board[row][subCol].getCandidateList() + "form " + row + " " + subCol);
			if (!this.board[row][subCol].getCandidateList().contains(val))
				return false;
		}
		return true;
	}
	
	
	// removes a value v in all cells in a row
	private void removeFromRowCList(int row, int colMax, String v) {
		for (int col = colMax-2; col<=colMax; col++)
			this.board[row][col].getCandidateList().remove(v);
	}
	
	
	// removes a value v in a single cell
	private void removeFromCList(int row, int col, String v) {
		this.board[row][col].getCandidateList().remove(v);
	}
	
	public boolean LockedAlgo() {
		ArrayList< Tuple<Integer, Integer> > subGrids = new ArrayList<Tuple<Integer, Integer>>();
		// creates list of all 9 subgrids
		subGrids.add(this.getSubGrid(1, 1));
		subGrids.add(this.getSubGrid(1, 4));
		subGrids.add(this.getSubGrid(1, 7));
		subGrids.add(this.getSubGrid(4, 1));
		subGrids.add(this.getSubGrid(4, 4));
		subGrids.add(this.getSubGrid(4, 7));
		subGrids.add(this.getSubGrid(7, 1));
		subGrids.add(this.getSubGrid(7, 4));
		subGrids.add(this.getSubGrid(7, 7));
		
		
		// for each subgrid..
		int i =0;
		for (Tuple<Integer, Integer> sg : subGrids) 
		{
			i++;
			// for each row in this subgrid...
			for(int r=sg.x-2; r<=sg.x; r++) 				
			{
				for (String v : VALIDVALUES)
				{
					boolean locked = true;
					// then go through the "rest" of the row 
					// (each cell in overall row not belonging to subgrid)		
					for (int restCol=0; restCol<COLUMNS; restCol++ ) 
					{
						// if these coords belong to the subrow, continue
						// we only want to check the rest of the row
						if (belongsToSubGrid(r, restCol, sg)) 
							continue;
						
						// if v is a candidate for any cell in the rest of the row
						// and if v is not a candidate for the subrow itself
						// v cannot be and candidate for the current subrow
						if ( this.board[r][restCol].getCandidateList().contains(v) || !candidateOfSubRow(r, sg, v))
							locked = false;
						else
						{
							//System.out.println(v + " is a locked candidate at subsec " + r + " " + restCol + " CList: " + this.board[r][restCol].getCandidateList());
							//return;
						}
						//return;

					}	
					if (locked) {
						ArrayList<Integer> rowsInSubGrid = new ArrayList<Integer>();
						rowsInSubGrid.add(sg.x-2);
						rowsInSubGrid.add(sg.x-1);
						rowsInSubGrid.add(sg.x);
						for (int row : rowsInSubGrid) {
							if (row == r)
								continue;
							//System.out.println("deleting " + v + " from row " + row + " at subgrid " + i);
							this.removeFromRowCList(row, sg.y, v);
						}
					//	System.out.println("locked return true");
						return true;
					}
				}
			}
		}
		return false;
	}
		
	public boolean NakedAlgo() {
			// for each cell...
			for(int row=0; row<this.ROWS; row++) 
			{
				for(int col=0; col<this.COLUMNS; col++) 
				{
					SudokuCell c = this.board[row][col];
					ArrayList<String> cList = c.getCandidateList();
					
					
					if (cList.size() != 2)
						continue;
					for(int col2=0; col2<this.COLUMNS; col2++) 
					{
						SudokuCell c2 = this.board[row][col2];
						ArrayList<String> cList2 = c2.getCandidateList();
						if (cList2.size() == 2 && c2.getCoordinate() != c.getCoordinate() && cList2 != cList) {
							String value1 = cList.get(0);
							String value2 = cList.get(1);
							for (int cl=0; cl<this.COLUMNS; cl++) {
								if (cl == col || cl == col2)
									continue;
								removeFromCList(row, cl, value1);
								removeFromCList(row, cl, value2);
							}
				
							return true;
						}
					}
				}
			}
			return false;
		}
	
	
}