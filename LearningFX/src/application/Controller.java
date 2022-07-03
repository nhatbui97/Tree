package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.*;	

public class Controller{
	

    @FXML
    private ProgressBar progressBar;
	
	private int maxDistance;

	@FXML
	void AVLTreeClick(ActionEvent event) {
		getMaxDistance();
		buttonBar.setVisible(true);
		listViewBar.setVisible(true);
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

    //___________________________________ButtonBar(Left)______________________________________
    //________________________________________________________________________________________

	
    //slide
    @FXML
    private AnchorPane buttonBar;
    @FXML
    private Button slideButtonLeft;
    @FXML
    void slideButtonBar(ActionEvent event) {
    	TranslateTransition slideTransition = new TranslateTransition();
    	slideTransition.setNode(buttonBar);
    	slideTransition.setDuration(Duration.seconds(1));
    	if (slideButtonLeft.getText().equals(">>>")) {
	    	slideTransition.setToX(buttonBar.getWidth() - slideButtonLeft.getWidth() );
	    	slideTransition.play();
	    	slideButtonLeft.setText("<<<");
    	}else {
    		slideTransition.setToX(0);
    		slideTransition.play();
    		slideButtonLeft.setText(">>>");
    	}
    }
    //-------------------------------------------------------------------------------------------
    
    //create
    @FXML
    private Button createButton;
    @FXML
    void Create(ActionEvent event) {
    	
    }
    //-------------------------------------------------------------------------------------------
    
    //insert
    @FXML
    private Button insertButton;
    @FXML
    private AnchorPane insertBoard;
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
    //-------------------------------------------------------------------------------------------
    
    //delete
    @FXML
    private Button deleteButton;
    @FXML
    void Delete(ActionEvent event) {

    }
    //-------------------------------------------------------------------------------------------
    
    //update
    @FXML
    private Button updateButton;
    @FXML
    void Update(ActionEvent event) {

    }
    //-------------------------------------------------------------------------------------------
    
    //traverse
    @FXML
    private Button traverseButton;
    @FXML
    void Traverse(ActionEvent event) {

    }
    //-------------------------------------------------------------------------------------------
    
    //search
    @FXML
    private Button searchButton;
    @FXML
    void Search(ActionEvent event) {

    }
    //-------------------------------------------------------------------------------------------
    
    //_________________________________ListViewBar(Right)_____________________________________
    //________________________________________________________________________________________
    
    //slide
    @FXML
    private Button slideButtonRight;
    @FXML
    private AnchorPane listViewBar;
    @FXML
    void slideListView(ActionEvent event) {
    	TranslateTransition slideTransition = new TranslateTransition();
    	slideTransition.setNode(listViewBar);
    	slideTransition.setDuration(Duration.seconds(1));
    	if (slideButtonRight.getText().equals("<<<")) {
	    	slideTransition.setToX (slideButtonRight.getWidth() - listViewBar.getWidth());
    		slideTransition.play();
    		slideButtonRight.setText(">>>");
    	}else {
    		slideTransition.setToX(0);
	    	slideTransition.play();
	    	slideButtonRight.setText("<<<");
    	}

    }
    
    
}

