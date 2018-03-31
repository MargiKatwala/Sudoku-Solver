package Prog3;
/**
 * UIC CS 342 Project 3 - Sudoku 
 * Project Member 
 * Johnson Ogunyomi   <joguny2@uic.edu>
 * Margi Katwala 	 <mkatwa3@uic.edu>
 * Syed Rahman 	    	 <srahma35@uic.edu>
 * **/ 
//Tuple Class
//Implements of tuple of 2 objects
public class Tuple<X, Y> { 
public X x; 
public Y y; 
public Tuple(X x, Y y) { 
  this.x = x; 
  this.y = y; 
} 

public X getX() {
  return x;
}

public Y getY() {
  return y;
}

} 