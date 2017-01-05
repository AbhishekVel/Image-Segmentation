package model;

import java.awt.Point;
import java.util.ArrayList;

public class Cluster {

	private ArrayList<Point> points;
	private int[] centroid;
	
	public Cluster(ArrayList<Point> points) {
		this.points = points;
	}
	
	public ArrayList<Point> getPoints() {
		return this.points;
	}
	
	public int[] getCentroid() {
		return this.centroid;
	}
	
	
}
