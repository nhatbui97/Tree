package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.*;	

public class Controller{
	
    @FXML
    private Button searchButton;

    @FXML
    private Button deleteButton;

    @FXML
    private AnchorPane insertBoard;

    @FXML
    private Button insertButton;

    @FXML
    private AnchorPane slideMe;

    @FXML
    private Button createButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button traverseButton;
	
    @FXML
    private Button slideButton;
    
	private int maxDistance;

	@FXML
	void AVLTreeClick(ActionEvent event) {
		getMaxDistance();
		slideMe.setVisible(true);
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
    	FadeTransition ft1 = new FadeTransition(Duration.seconds(0.5), insertButton);
    	ft1.setFromValue(1);
        ft1.setToValue(0);
        ft1.play();
        insertButton.setVisible(false);
        
        FadeTransition ft2 = new FadeTransition(Duration.seconds(0.5), insertBoard);
        ft2.setFromValue(0);
        ft2.setToValue(1);
        ft2.play();
        insertBoard.setVisible(true);
    }
    @FXML
    void goInsert(ActionEvent event) {

    }

    @FXML
    void goBackInsert(ActionEvent event) {
    	FadeTransition ft1 = new FadeTransition(Duration.seconds(0.5), insertBoard);
    	ft1.setFromValue(1);
        ft1.setToValue(0);
        ft1.play();
        insertBoard.setVisible(false);
        
        FadeTransition ft2 = new FadeTransition(Duration.seconds(0.5), insertButton);
        ft2.setFromValue(0);
        ft2.setToValue(1);
        ft2.play();
        insertButton.setVisible(true);
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
    	if (slideButton.getText().equals(">>>")) {
	    	swipeTransition.setToX(slideMe.getWidth() - slideButton.getWidth() );
	    	swipeTransition.play();
	    	slideButton.setText("<<<");
    	}else {
    		swipeTransition.setToX(0);
    		swipeTransition.play();
    		slideButton.setText(">>>");
    	}
    }
}

