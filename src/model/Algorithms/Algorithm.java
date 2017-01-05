package model.Algorithms;

import model.ImageMatrix;

public abstract class Algorithm {
	
	protected ImageMatrix image;
	
	public Algorithm(ImageMatrix image) {
		this.image = image;
	}
	
	public abstract void process();
	
	public ImageMatrix getImage() {
		return this.image;
	}
	
	
}
