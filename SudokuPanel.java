package Prog3;
/**
 * UIC CS 342 Project 3 - Sudoku 
 * Project Member 
 * Johnson Ogunyomi   <joguny2@uic.edu>
 * Margi Katwala 	 <mkatwa3@uic.edu>
 * Syed Rahman 	    	 <srahma35@uic.edu>
 * **/ 
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;

import javax.swing.JToggleButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;



@SuppressWarnings("serial")
public class SudokuPanel extends JPanel {

	private SudokuPuzzle puzzle;
	private int currentCol;
	private int currentRow;
	private int usedWidth;
	private int usedHeight;
	private int fontSize;
	String buttonVal;
	
	public SudokuPanel() {
		this.setPreferredSize(new Dimension(540,450));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.puzzle = new SudokuGenerator().generateRandomSudoku(SudokuPuzzleType.NINEBYNINE);
		currentCol = -1;
		currentRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;
	}
	
	public SudokuPanel(SudokuPuzzle puzzle) {
		this.setPreferredSize(new Dimension(540,450));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.puzzle = puzzle;
		currentCol = -1;
		currentRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;
	}
	
	public void newSudokuPuzzle(SudokuPuzzle puzzle) {
		this.puzzle = puzzle;
	}
	
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(1.0f,1.0f,1.0f));
		
		int slotWidth = this.getWidth()/puzzle.getNumColumns();
		int slotHeight = this.getHeight()/puzzle.getNumRows();
		
		usedWidth = (this.getWidth()/puzzle.getNumColumns())*puzzle.getNumColumns();
		usedHeight = (this.getHeight()/puzzle.getNumRows())*puzzle.getNumRows();
		
		g2d.fillRect(0, 0,usedWidth,usedHeight);
		
		g2d.setColor(new Color(0.0f,0.0f,0.0f));
		for(int x = 0;x <= usedWidth;x+=slotWidth) {
			if((x/slotWidth) % puzzle.getBoxWidth() == 0) {
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(x, 0, x, usedHeight);
			}
			else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(x, 0, x, usedHeight);
			}
		}
	
		for(int y = 0;y <= usedHeight;y+=slotHeight) {
			if((y/slotHeight) % puzzle.getBoxHeight() == 0) {
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(0, y, usedWidth, y);
			}
			else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(0, y, usedWidth, y);
			}
		}
		
		
		Font f = new Font("Times New Roman", Font.PLAIN, fontSize);
		g2d.setFont(f);
		FontRenderContext fContext = g2d.getFontRenderContext();
		for(int row=0;row < puzzle.getNumRows();row++) {
			for(int col=0;col < puzzle.getNumColumns();col++) {
				if(!puzzle.isSlotAvailable(row, col)) {
					int textWidth = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getWidth();
					int textHeight = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getHeight();
					g2d.drawString(puzzle.getValue(row, col), (col*slotWidth)+((slotWidth/2)-(textWidth/2)), (row*slotHeight)+((slotHeight/2)+(textHeight/2)));
				}
			}
		}
		if(currentCol != -1 && currentRow != -1) {
			g2d.setColor(new Color(0.0f,0.0f,1.0f,0.3f));
			g2d.fillRect(currentCol * slotWidth,currentRow * slotHeight,slotWidth,slotHeight);
		}
	}
	
	public void messageFromNumActionListener(String buttonValue) {
		if(currentCol != -1 && currentRow != -1) {
			puzzle.makeMove(currentRow, currentCol, buttonValue, true);
			repaint();
		}
	}
	
	// Listener for the side buttons
	public class NumActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JToggleButton buttonClicked = (JToggleButton) e.getSource();
			if (buttonClicked.getText().equals("e"))
				buttonVal = "";
			else
				buttonVal = buttonClicked.getText();
		}
		
	
	}
	private class SudokuPanelMouseAdapter extends MouseInputAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				int slotWidth = usedWidth/puzzle.getNumColumns();
				int slotHeight = usedHeight/puzzle.getNumRows();
				currentRow = e.getY() / slotHeight;
				currentCol = e.getX() / slotWidth;
				e.getComponent().repaint();
				//JOptionPane.showMessageDialog(null, "cell you clicks is" + e.getButton());
				if (buttonVal.equals(""))  {
					puzzle.erase(currentRow, currentCol, puzzle.isSlotMutable(currentRow, currentCol));
					//System.out.println("starting naked");
					//puzzle.NakedAlgo();
				//	System.out.println(puzzle.candidateList(currentRow, currentCol));
					//puzzle.SingleAlgo();
				}
				else 
					puzzle.makeMove(currentRow, currentCol, buttonVal, true);
					//System.out.println(puzzle.candidateList(currentRow, currentCol));
				puzzle.makeCandidateList(currentRow, currentCol);
				puzzle.board[currentRow][currentCol].setCandidateList(puzzle.makeCandidateList(currentRow, currentCol));
				puzzle.updateCLists();
				//System.out.println(puzzle.candidateList(currentRow, currentCol));
				//puzzle.SingleAlgo();
				//puzzle.HiddenAlgo();
			//	puzzle.LockedAlgo();
				//puzzle.NakedAlgo();
				//puzzle.LockedCandidate()
				//System.out.println(puzzle.getBoard()[5][8].getCandidateList());
				
				
					
			}
		}
	}
}