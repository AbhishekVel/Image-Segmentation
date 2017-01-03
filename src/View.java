
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
public class View extends Application {

	
	private int sceneWidth = 400, sceneHeight = 300;
	private ImageView imageView;
	@Override
	public void start(Stage primaryStage) throws Exception {
		imageView = new ImageView();
		
		imageView.setFitHeight(sceneHeight);
		imageView.setFitWidth(sceneWidth);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(imageView);
		Scene scene = new Scene(pane, sceneWidth, sceneHeight);
		
		primaryStage.setTitle("Image Segmentation App");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		BufferedImage img = ImageIO.read(new File("./images/dog.jpg"));
		Matrix imageMatrix = new Matrix(img);
		imageView.setImage(SwingFXUtils.toFXImage(img, null));
		
	}


	
	
}
