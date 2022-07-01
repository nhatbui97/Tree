package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.event.*;	

public class Controller{
	
	@FXML
    private AnchorPane slideMe;
	
    @FXML
    private Button slideButton;
    
	private int maxDistance;

	@FXML
	void AVLTreeClick(ActionEvent event) {
		getMaxDistance();	
	}
	
	@FXML
	void quitProgram(ActionEvent event) {
		System.exit(0);
	}
	
	void getMaxDistance() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText("Enter max difference: ");
        dialog.showAndWait();
       	maxDistance = Integer.parseInt(dialog.getEditor().getText());
	}
    @FXML
    void Create(ActionEvent event) {

    }

    @FXML
    void Insert(ActionEvent event) {

    }

    @FXML
    void Delete(ActionEvent event) {

    }

    @FXML
    void Update(ActionEvent event) {

    }

    @FXML
    void Traverse(ActionEvent event) {

    }

    @FXML
    void Search(ActionEvent event) {

    }
    @FXML
    void slide(ActionEvent event) {
    	TranslateTransition swipeTransition = new TranslateTransition();
    	swipeTransition.setNode(slideMe);
    	swipeTransition.setDuration(Duration.seconds(1));
    	if (slideButton.getText().equals(">")) {
	    	swipeTransition.setToX(slideMe.getWidth() - slideButton.getWidth() );
	    	swipeTransition.play();
	    	slideButton.setText("<");
    	}else {
    		swipeTransition.setToX(0);
    		swipeTransition.play();
    		slideButton.setText(">");
    	}
    }
}

