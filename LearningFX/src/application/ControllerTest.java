package application;


import javafx.animation.*;
import javafx.animation.PathTransition.OrientationType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;


public class ControllerTest {
	int x = 5;
	
    @FXML
    private AnchorPane layout;
    
    private Circle circle = new Circle(100, 100, 15);
    
    private Circle circle1 = new Circle(300, 300, 15);
    
    private Circle circle2 = new Circle(400, 100, 15);
    @FXML
    void draw(ActionEvent event) {

	        
	    layout.getChildren().add(circle);
	    layout.getChildren().add(circle1);
	    layout.getChildren().add(circle2);
	    
	    
        circle.setTranslateX(300);
 
        
        

    }
    @FXML
    void showLayout(ActionEvent event) {
    	circle.setTranslateX(200);
    }

}
