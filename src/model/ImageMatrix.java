package model;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class ImageMatrix {
	
	private int pixelsWidth, pixelsHeight;
	private int[][] pixels;
	private int[][][] featuresData;
	private int[][] cluster;
	
	public ImageMatrix(int pixelsHeight, int pixelsWidth) {
		this.pixelsWidth = pixelsWidth;
		this.pixelsHeight = pixelsHeight;
		this.pixels = new int[pixelsHeight][pixelsWidth];
	}
	public ImageMatrix(Image image) {
		this.pixelsWidth = image.getWidth(null);
		this.pixelsHeight = image.getHeight(null);
		this.pixels = new int[pixelsHeight][pixelsWidth];
		
		int[] oneDimPixels = new int[pixelsWidth*pixelsHeight];
		
		// convert pixels into integers representing RGB and inserts all values into a 1D array
		PixelGrabber grabber = new PixelGrabber(image, 0, 0, this.pixelsWidth, 
				this.pixelsHeight, oneDimPixels, 0, this.pixelsWidth);	
		
		try {
			grabber.grabPixels();
		} catch (InterruptedException ie) {
			System.out.println("Error while grabbing pixels from image.");
		}
		
		this.pixels = convertDimensions(oneDimPixels);
		featuresData = new int[this.pixelsHeight][this.pixelsWidth][3]; // 3 is for the three features (RGB)
		
		// extracts RGB data from the pixels, and use this data as the features
		for (int i = 0; i < this.pixelsHeight; i++) {
			for (int j = 0; j < this.pixelsWidth; j++) {
				Color c = new Color(pixels[i][j]);
				featuresData[i][j][0] = c.getRed();
				featuresData[i][j][1] = c.getGreen();
				featuresData[i][j][2] = c.getBlue();
			}
		}
		
		//initialize cluster for future purpose of deciding which pixel is in which cluster
		this.cluster = new int[this.pixelsHeight][this.pixelsWidth];
	}
	
	/**
	 * Takes in a one dimensional array of pixels and converts it into a 2D array
	 * @param oneDimPixels
	 * returns two dimensional array with pixels
	 */
	private int[][] convertDimensions(int[] oneDimPixels) {
		int[][] tempArray = new int[pixelsHeight][pixelsWidth];
		for (int i = 0; i < pixelsHeight; i++) {
			for (int j = 0; j < pixelsWidth; j++) {
				tempArray[i][j] = oneDimPixels[i * pixelsWidth + j]; 
			}
		}
		
		return tempArray;
	}
	
	
	public int getPixelsHeight() {
		return this.pixelsHeight;
	}
	
	public int getPixelsWidth() {
		return this.pixelsWidth;
	}
	
	public int[][] pixels() {
		return this.pixels;
	}

	public int[][][] featuresData() {
		return this.featuresData;
	}
	
	public int[][] getCluster() {
		return this.cluster;
	}
	
	/**
	 * Intended to be called after a segmentation algorithm has been used on this matrix
	 * @return the new segmented image
	 */
	public BufferedImage getSegmentedImage(){
		ImageMatrix alteredImageMatrix = new ImageMatrix(this.pixelsHeight, this.pixelsWidth);
		int clusterCount = clusterCount();
		
		for (int i = 0; i < this.pixelsHeight; i++) {
			for (int j = 0; j < this.pixelsWidth; j++) {
				Color c = Color.getHSBColor(this.cluster[i][j] / (float) clusterCount, 1.0f, 1.0f);
				alteredImageMatrix.pixels()[i][j] = c.getRGB();
			}
		}
		
		BufferedImage alteredImage = new BufferedImage(this.pixelsWidth, this.pixelsHeight, BufferedImage.TYPE_INT_RGB);
		
		for (int i = 0; i < this.pixelsHeight; i++) {
			for (int j = 0; j < this.pixelsWidth; j++) {
				alteredImage.setRGB(j, i, alteredImageMatrix.pixels[i][j]);
			}
		}
		
		return alteredImage;
	}
	
	
	/**
	 * TODO make this cluster count better
	 * @return the count of the number of clusters
	 */
	private int clusterCount() {
		Set<Integer> set = new TreeSet<Integer>();
		for (int i = 0; i < this.pixelsHeight; i++) {
			for (int j = 0; j < this.pixelsWidth; j++) {
				set.add(new Integer(cluster[i][j]));
			}
		}
		return set.size();
	}

	
	/**
	 * prints out the data in a text file to test the data
	 */
	public void printFeaturesData() {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("test.txt"));
			for (int i = 0; i < featuresData.length; i++) {
				for (int j = 0; j < featuresData[i].length; j++) {
					for (int k = 0; k < featuresData[i][j].length; k++) {
						writer.write(featuresData[i][j][k] + ", ");
					}
					writer.newLine();
				}
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
}
