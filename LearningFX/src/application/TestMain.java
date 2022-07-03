package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;
public class TestMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Test.fxml"));
			primaryStage.setTitle("Aplication");
			primaryStage.setScene(new Scene(root, 600, 400));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
