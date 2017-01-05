package model.Algorithms;

import java.awt.Point;
import java.util.ArrayList;

import model.ImageMatrix;

public class Cluster {

	private ArrayList<Point> points; // all the pixels
	private int[] centroid; // represented by RGB (3 values)
	private ImageMatrix image;
	
	public Cluster(ImageMatrix image) {
		this.points = new ArrayList<Point>();
		this.image = image;
		this.centroid = new int[3];
	}
	
	public void updateCentroid() {
		if (points.size() > 0) {
			
			int red = 0, green = 0, blue = 0;
			
			for (Point point: points) {
				int[] colorValues = this.image.featuresData()[point.y][point.x];
				red += colorValues[0];
				green += colorValues[1];
				blue += colorValues[2];
			}
		
			// updating the centroid based on the average of all the values in RGB
			centroid[0] = red/points.size();
			centroid[1] = green/points.size();
			centroid[2] = blue/points.size();
		}
	}
	
	public ArrayList<Point> getPoints() {
		return this.points;
	}
	
	public int[] getCentroid() {
		return this.centroid;
	}
	
	
}
