package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class ControllerTest {
	//0: go left, 1: go right, other: action
	int x = 5;
	
    @FXML
    private AnchorPane layout;
    
    @FXML
    void draw(ActionEvent event) {
    	double y = layout.getWidth();
    	
    	Circle inner = new Circle();
    	inner.setRadius(16);
        inner.setFill(javafx.scene.paint.Color.WHITE);
        inner.setStrokeWidth(3);
        inner.setStroke(javafx.scene.paint.Color.BLACK);
        
        Text text = new Text(String.valueOf(x));
        text.setBoundsType(TextBoundsType.VISUAL); 
        StackPane stack = new StackPane();
        stack.getChildren().addAll(inner, text);
        
        stack.setLayoutX(y/2 - inner.getRadius());
        stack.setLayoutY(5);
        
        y = y/2;
        Line line = new Line(stack.getLayoutX() , stack.getLayoutY() + inner.getRadius()
        					, y/2, stack.getLayoutY() + 40);
        line.setStrokeWidth(3);
        
    	layout.getChildren().add(stack);
    	layout.getChildren().add(line);

    	
    	

    }

}
