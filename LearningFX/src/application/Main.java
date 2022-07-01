package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MyScene.fxml"));
			primaryStage.setTitle("Aplication");
			primaryStage.setScene(new Scene(root, 1280, 720));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
