package model.Algorithms;

import model.ImageMatrix;

public abstract class Algorithm {
	
	protected ImageMatrix image;
	
	public Algorithm(ImageMatrix image) {
		this.image = image;
	}
	
	/**
	 * the main method of this class which will start the process for the algorithm, 
	 * the output is the changed ImageMatrix object
	 */
	public abstract void process();
	
}
