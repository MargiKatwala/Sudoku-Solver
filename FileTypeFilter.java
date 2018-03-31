package Prog3;
/**
 * UIC CS 342 Project 3 - Sudoku 
 * Project Member 
 * Johnson Ogunyomi   <joguny2@uic.edu>
 * Margi Katwala 	 <mkatwa3@uic.edu>
 * Syed Rahman 	    	 <srahma35@uic.edu>
 * **/ 
import java.io.File;
import javax.swing.filechooser.*;

public class FileTypeFilter extends FileFilter {
	private final String extension;
	private final String description;

	public FileTypeFilter(String extension,String description) {
		this.extension= extension;
		this.description = description;


	}
	@Override
	public boolean accept(File file) {
		if(file.isDirectory()) {
			return true;

		}
		return file.getName().endsWith(extension);
	}

	@Override
	public String getDescription() {

		return description + String.format(" (*%s) ",extension);
	}

}