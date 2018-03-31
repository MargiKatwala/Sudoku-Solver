package Prog3;
/**
 * UIC CS 342 Project 3 - Sudoku 
 * Project Member 
 * Johnson Ogunyomi   <joguny2@uic.edu>
 * Margi Katwala 	 <mkatwa3@uic.edu>
 * Syed Rahman 	    	 <srahma35@uic.edu>
 * **/ 
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

//import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class SudokuFrame extends JFrame {

	private JPanel buttonSelectionPanel;
	private SudokuPanel sPanel;
	public SudokuPuzzle puzzle;

	public SudokuFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku");
		this.setMinimumSize(new Dimension(800, 600));

		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("Menu");
		JMenu file2 = new JMenu("Help ");
		JMenu file3 = new JMenu("Hints");
		JMenuItem open = new JMenuItem("Load the puzzle");
		JMenuItem save = new JMenuItem("Store your Puzzle ");
		JMenuItem quit = new JMenuItem("Quit");
		JMenuItem howtoPlay = new JMenuItem("How to play");
		JMenuItem howToUse = new JMenuItem("How to Use");
		JMenuItem about = new JMenuItem("About");
		JMenuItem SingleAlgo = new JMenuItem("Run Single Algorithm");
		JMenuItem HiddenAlgo = new JMenuItem("Run Hidden Single Algorithm ");
		JMenuItem LockedAlgo = new JMenuItem("Run Locked Candidate Algorithm");
		JMenuItem NakedAlgo = new JMenuItem("Run Naked Pairs Algorithm");
		JMenuItem nineByNineGame = new JMenuItem("9 By 9 Game");
		JMenuItem fillMany = new JMenuItem("Fill Many Cells");

		nineByNineGame.addActionListener(new NewGameListener(SudokuPuzzleType.NINEBYNINE, 26));

		file.add(open);
		file.add(save);
		file.add(quit);
		file2.add(howtoPlay);
		file2.add(howToUse);
		file2.add(about);
		file3.add(SingleAlgo);
		file3.add(HiddenAlgo);
		file3.add(LockedAlgo);
		file3.add(NakedAlgo);
		file3.add(fillMany);

		menuBar.add(file);
		menuBar.add(file3);
		menuBar.add(file2);
		this.setJMenuBar(menuBar);

		// this.setJMenuBar(menuBar2);

		JPanel windowPanel = new JPanel();
		windowPanel.setLayout(new FlowLayout());
		windowPanel.setPreferredSize(new Dimension(800, 600));

		buttonSelectionPanel = new JPanel();
		buttonSelectionPanel.setPreferredSize(new Dimension(90, 500));

		sPanel = new SudokuPanel();

		windowPanel.add(sPanel);
		windowPanel.add(buttonSelectionPanel);
		this.add(windowPanel);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.exit(0);
			}
		});
		// SAVE
		// SAVE
		// SAVE
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser saveFile = new JFileChooser(new File("c:\\"));
				saveFile.setDialogTitle("Save a File");
				saveFile.setFileFilter(new FileTypeFilter(".txt", "Text File"));
				int result = saveFile.showSaveDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {
					File file = saveFile.getSelectedFile();
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(file.getPath()));
						SudokuCell[][] board = puzzle.getBoard();
						for (int row=0; row<9; row++) {
							for (int col=0; col<9; col++) {
								if (board[row][col].getValue().equals(""))
									continue;
								String content = String.valueOf(row+1) + " " + String.valueOf(col+1) + " " +  board[row][col].getValue();
								bw.write(content);
								bw.newLine();
							}
						}
						// fw.write(content);
						bw.flush();
						bw.close();

					} catch (Exception e2) {

						JOptionPane.showMessageDialog(null, e2.getMessage());

					}

				}
			}
		});
		// END OF SAVE FILE
		// open the file
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// coordsFromFile();
				rebuildInterface(SudokuPuzzleType.NINEBYNINE, 26, coordsFromFile());
			}
		});

		howtoPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JOptionPane.showMessageDialog(howtoPlay,
						"How to Play Sudoku Guide : \n 1. Load the Puzzle with your txt file  \n 2. Click on the number which will selecte put it in your desire cell (BUT remember one number per row and row combine) \n 3.You can put one number per whole cell \n 4. Problems with using the game? - Check out our How to Play Guide \n");
			}
		});

		howToUse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) 	  	{
				JOptionPane.showMessageDialog(howToUse,
						"How to Use Sudoku Interface Guide  : \n 1. We have several things in this game to help your User \n 2. We have Load the puzzle - load in so you can start your game \n 3. Save it anytime with Save Puzzle in Menu \n 4. Quit anytime you want with Quit opion in the Menu \n 5. Hints - Different ways to solve this given grid \n 6. There is a side 'e' button to undo your move \n 7. The ' ? ' button helps to get the possible numbers in that cell can hold   \n");
			}
		});

		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) 	{
				JOptionPane.showMessageDialog(howToUse,
						" Class CS - 342 Project 3 \n Project Manged by \n  1. Johnson Ogunyomi \n  2. Margi Katwala \n  3.Syed Rahman \n  ");
			}
		});

		SingleAlgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) 	{
				//System.out.println("starting single algo");
				puzzle.SingleAlgo();
				revalidate();
				repaint();
			}
		});

		HiddenAlgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) 	{
				//System.out.println("starting single algo");
				puzzle.HiddenAlgo();
				revalidate();
				repaint();
			}
		});

		LockedAlgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) 	{
				//System.out.println("starting single algo");
				puzzle.LockedAlgo();
				puzzle.SingleAlgo();
				revalidate();
				repaint();
			}
		});

		NakedAlgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) 	{
				//System.out.println("starting single algo");
				puzzle.NakedAlgo();
				puzzle.SingleAlgo();
				revalidate();
				repaint();
			}
		});

		/*fillMany.addActionListener(new ActionListener() {
	  public void actionPerformed(ActionEvent ev) 	{

		  while(puzzle.SingleAlgo() || puzzle.HiddenAlgo() || puzzle.NakedAlgo() /*|| puzzle.LockedAlgo()) {
			  puzzle.SingleAlgo();
			  revalidate();
			  repaint();
		  }
	  }
  });*/

		String empty[][] = new String[9][9];
		rebuildInterface(SudokuPuzzleType.NINEBYNINE, 26, empty);
	}

	// where side buttons are created
	public void rebuildInterface(SudokuPuzzleType puzzleType, int fontSize, String[][] coords) {
		this.puzzle = new SudokuGenerator().generateRandomSudoku(puzzleType, coords);
		this.puzzle.updateCLists();
		sPanel.newSudokuPuzzle(this.puzzle);
		sPanel.setFontSize(fontSize);
		buttonSelectionPanel.removeAll();
		ButtonGroup group = new ButtonGroup();
		for (String value : this.puzzle.getValidValues()) {
			JToggleButton b = new JToggleButton(value, false);
			b.setPreferredSize(new Dimension(42, 42));
			b.addActionListener(sPanel.new NumActionListener());
			buttonSelectionPanel.add(b);
			group.add(b);
			// co][]
		}
		JToggleButton eraser = new JToggleButton("e", false);
		JToggleButton hint = new JToggleButton("?", false);
		eraser.setPreferredSize(new Dimension(42, 42));
		eraser.addActionListener(sPanel.new NumActionListener());
		hint.setPreferredSize(new Dimension(35, 35));
		hint.addActionListener(sPanel.new NumActionListener());

		buttonSelectionPanel.add(eraser);
		buttonSelectionPanel.add(hint);
		group.add(eraser);
		group.add(hint);
		sPanel.repaint();

		buttonSelectionPanel.revalidate();
		buttonSelectionPanel.repaint();
	}

	private class NewGameListener implements ActionListener {

		private SudokuPuzzleType puzzleType;
		private int fontSize;

		public NewGameListener(SudokuPuzzleType puzzleType, int fontSize) {
			this.puzzleType = puzzleType;
			this.fontSize = fontSize;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String empty[][] = new String[9][9];
			rebuildInterface(puzzleType, fontSize, empty);
		}
	}

	public static String[][] coordsFromFile() {
		int int_Row = 0, int_Col = 0, int_val = 0;
		Scanner s = selectFile();
		String[][] cellCoordinates = new String[9][9];

		while (s.hasNext()) {

			int_Row = s.nextInt(); // get row from the file
			int_Col = s.nextInt(); // get column from the file
			int_val = s.nextInt(); // get value from the file

			// checking the values...
			// System.out.println(" parsing "+int_Row+ " "+ int_Col+" "+ int_val);

			cellCoordinates[int_Row - 1][int_Col - 1] = String.valueOf(int_val);
			s.hasNext();

			// Store these parsing information into a 2D array
			// So we can put this value into the cell

		}

		return cellCoordinates;
	}

	

	// Use a dialog box to select a text file (.txt)
	@SuppressWarnings("static-access")
	public static Scanner selectFile() {
		do {
			JFileChooser openFile = new JFileChooser();
			// openFile.showOpenDialog(null);
			int returnVal = openFile.showOpenDialog(null);
			// try {
			if (returnVal == openFile.APPROVE_OPTION) {
				try {
					return new Scanner(openFile.getSelectedFile());
				} catch (FileNotFoundException e) {
		
					JOptionPane.showMessageDialog(null, "Invalid file!", "error", JOptionPane.ERROR_MESSAGE);

				}
			} else
				return null;
		} while (true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SudokuFrame frame = new SudokuFrame();
				frame.setVisible(true);
			}
		});
	}
}