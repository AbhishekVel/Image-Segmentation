package model;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ImageMatrix {
	
	private int pixelsWidth, pixelsHeight;
	private int[][] pixels;
	private int[][][] featuresData;
	
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
