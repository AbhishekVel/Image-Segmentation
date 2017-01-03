import java.awt.Image;
import java.awt.image.PixelGrabber;

public class Matrix {
	
	private int pixelsWidth, pixelsHeight;
	private int[][] pixels;
	
	public Matrix(Image image) {
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
	}
	
	/**
	 * Takes in a one dimensional array of pixels and converts it into a 2D array
	 * @param oneDimPixels
	 * returns two dimensional array with pixels
	 */
	private int[][] convertDimensions(int[] oneDimPixels) {
		int[][] tempArray = new int[pixelsWidth][pixelsHeight];
		
		for (int i = 0; i < pixelsHeight; i++) {
			for (int j = 0; j < pixelsWidth; j++) {
				tempArray[i][j] = oneDimPixels[i* pixelsWidth + j]; 
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
	
	// returns a cloned copy of the pixels instead of the actual pixels to avoid changing of data
	public int[][] getPixels() {
		return pixels.clone();
	}
	
	
	
	
}
