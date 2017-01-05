package model.Algorithms;

import model.ImageMatrix;

public abstract class Algorithm {
	
	private ImageMatrix image;
	
	public Algorithm(ImageMatrix image) {
		this.image = image;
	}
	
	public abstract void process();
}
