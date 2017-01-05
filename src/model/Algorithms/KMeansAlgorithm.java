package model.Algorithms;

import java.awt.Point;
import java.util.Random;

import model.Cluster;
import model.ImageMatrix;

public class KMeansAlgorithm extends Algorithm {
	
	private Cluster[] clusters;

	public KMeansAlgorithm(ImageMatrix image) {
		super(image);
		clusters = new Cluster[3];
	}
	
	@Override
	public void process() {
		assignClustersRandomly();
		rebuildClusters();
		generateClusters();
	}
	
	/**
	 * assign all pixels with a random cluster
	 * [O(p) : p for # of pixels] time complexity
	 */
	private void assignClustersRandomly() {
		
		Random random = new Random();
		for (int i = 0; i < this.image.getPixelsHeight(); i++) {
			for (int j = 0; j < this.image.getPixelsWidth(); j++) {
				this.image.getCluster()[i][j] = random.nextInt(clusters.length);
			}
		}
		
		System.out.println("Completed assigning clusters randomly to all pixels (stored in cluster array in ImageMatrix).");	
	}

	/**
	 * Two actions:
	 * 1)adds all the points associated with a certain cluster into that respective cluster
	 * 2)updates the centroids of all of the clusters
	 * [O(p*c + p) : p for # of pixels and c for # of clusters] time complexity
	 */
	private void rebuildClusters() {
		clusters = new Cluster[3];
		
		//[O(p) : p for # of pixels] time complexity, since iterating through all pixels
		for (int i = 0; i < this.image.getPixelsHeight(); i++) {
			for (int j = 0; j < this.image.getPixelsWidth(); j++) {
				int clusterIndex = this.image.getCluster()[i][j];
				
				if (clusters[clusterIndex] == null) {
					clusters[clusterIndex] = new Cluster(image);
				}
				// the order is switched because the x coordinate is i (width iteration) 
				//and the y coordinate is j (height iteration)
				clusters[clusterIndex].addPoint(new Point(j, i));
			}
		}
		
		//[O(p*c) p for # of pixels and c for # of clusters] time complexity
		for (int i = 0; i < clusters.length; i++) {
			clusters[i].updateCentroid();
		}
		
		System.out.println("Completed building clusters.");
	}
	
	/**
	 * uses the euclidean distance to find the nearest centroid to a point (for all points)
	 * if a change occurred, then the algorithm must be repeated again until no change occurs (indication of the clusters stabilizing)
	 */
	private void generateClusters() {
		boolean changeOccurred = false;
		
		// do while loop is used to allow one iteration of the program before checking for a change
		do {
			changeOccurred = false;
			
			for (int i = 0; i < this.image.getPixelsHeight(); i++) {
				for (int j = 0; j < this.image.getPixelsWidth(); j++) {
					double minDistance = Double.MAX_VALUE;
					int bestClusterIndex = -1;
					
					for (int clusterIndex = 0; clusterIndex < clusters.length; clusterIndex++) {
						double computedDistance = euclideanDistance(this.image.featuresData()[i][j], clusters[clusterIndex].getCentroid());
						
						if (computedDistance < minDistance) {
							minDistance = computedDistance;
							bestClusterIndex = clusterIndex;	
						}
					}
					
					if (this.image.getCluster()[i][j] != bestClusterIndex) {
						this.image.getCluster()[i][j] = bestClusterIndex;
						changeOccurred = true;
					}
				}
			}
			
			if (changeOccurred)
				rebuildClusters();
			
			
		} while (changeOccurred);
		
		System.out.println("Completed generating the clusters for the current image.");	
	}
	
	/*
	 * Computes the euclidean distance between two points
	 * [O(length of the vectors) : length of the vectors represent the depth of the data (in this case: 3 *RGB*)] time complexity
	 */
	private double euclideanDistance(int[] vector1, int[] vector2) {
		if (vector1.length != vector2.length) {
			System.err.println("Error: The given vectors are not equal in length, therefore cannot compute euclidean distance.");
			System.exit(1);
		}
		
		double distance = 0;
		
		for (int i = 0; i < vector1.length; i ++)  {
			double difference = vector1[i] - vector2[i];
			distance += Math.pow(difference, 2);
		}
		
		return Math.sqrt(distance);
	}
}
