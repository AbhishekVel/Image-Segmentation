package access;

import java.awt.image.BufferedImage;
import model.ImageMatrix;
import model.Algorithms.Algorithm;
import model.Algorithms.KMeansAlgorithm;

public class Segment {
	
	public BufferedImage processImage(BufferedImage originalImg) {
		int numOfClusters = 2;
		
		ImageMatrix imageMatrix = new ImageMatrix(originalImg, numOfClusters);
		Algorithm algorithm = new KMeansAlgorithm(imageMatrix);
		
		algorithm.process();
		
		return imageMatrix.getSegmentedImage();
	}

}
