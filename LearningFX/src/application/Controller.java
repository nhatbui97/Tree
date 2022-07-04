package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;
import tree.*;

import java.io.InputStream;
import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.*;	

public class Controller{
	
	private ArrayList<StackPane> listStack = new ArrayList<StackPane>();
    @FXML
    private AnchorPane layout;
	Tree tree = new Tree();
	//AVL Tree
	private int maxDistance;
	@FXML
	void AVLTreeClick(ActionEvent event) {
		getMaxDistance();
		tree = new AVLTree(maxDistance);
		buttonBar.setVisible(true);
		listViewBar.setVisible(true);
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
    private TranslateTransition slideButtonBar = new TranslateTransition();
    @FXML
    void slideButtonBar(ActionEvent event) {
    	Class<?> clazz = this.getClass();
    	double slideToX = buttonBar.getWidth() - slideButtonLeft.getWidth();
    	slideButtonBar.setNode(buttonBar);
    	slideButtonBar.setDuration(Duration.seconds(1));
    	if (slideButtonBar.getToX() != 0 && 
    			slideButtonBar.getToX() != slideToX ) {
    		slideButtonBar.setToX(0);
    	}
    	if (slideButtonBar.getToX() == 0) {
    		slideButtonBar.setToX(slideToX);
    		slideButtonBar.play();
    		slideButtonLeft.setGraphic(null);
    		InputStream input = clazz.getResourceAsStream("/image/slideLeft.png");
    		slideButtonLeft.setGraphic(new ImageView(new Image(input, 22, 22, false, true)));
    	}else {
    		slideButtonBar.setToX(0);
    		slideButtonBar.play();
    		slideButtonLeft.setGraphic(null);
    		InputStream input = clazz.getResourceAsStream("/image/slideRight.png");
    		slideButtonLeft.setGraphic(new ImageView(new Image(input, 22, 22, false, true)));
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
    private AnchorPane insertBoard1;
    @FXML
    private AnchorPane insertBoard2;
    @FXML
    private TextField insertText1;
    @FXML
    private TextField parentText2;
    @FXML
    private TextField insertText2;
    @FXML
    void Insert(ActionEvent event) {
    	if (tree.getRoot() == null){
    		FadeTransition ft1 = new FadeTransition(Duration.seconds(0.5), insertButton);
	    	ft1.setFromValue(1);
	        ft1.setToValue(0);
	        ft1.play();
	        insertButton.setVisible(false);
	        
	        FadeTransition ft2 = new FadeTransition(Duration.seconds(0.5), insertBoard1);
	        ft2.setFromValue(0);
	        ft2.setToValue(1);
	        ft2.play();
	        insertBoard1.setVisible(true);
    		
    	}else {
	    	FadeTransition ft1 = new FadeTransition(Duration.seconds(0.5), insertButton);
	    	ft1.setFromValue(1);
	        ft1.setToValue(0);
	        ft1.play();
	        insertButton.setVisible(false);
	        
	        FadeTransition ft2 = new FadeTransition(Duration.seconds(0.5), insertBoard2);
	        ft2.setFromValue(0);
	        ft2.setToValue(1);
	        ft2.play();
	        insertBoard2.setVisible(true);
    	}
    }
    @FXML
    void goInsert1(ActionEvent event) {
    	int insertValue = Integer.parseInt(insertText1.getText());
    	tree.Insert(insertValue);
    	goBackInsert1(event);
    	
    	double layoutWidth = layout.getWidth();
    	
    	Circle circle = new Circle();
    	circle.setRadius(16);
    	circle.setFill(javafx.scene.paint.Color.WHITE);
    	circle.setStrokeWidth(3);
    	circle.setStroke(javafx.scene.paint.Color.BLACK);
        
        Text text = new Text(String.valueOf(insertValue));
        text.setBoundsType(TextBoundsType.VISUAL); 
        StackPane stack = new StackPane();
        stack.getChildren().addAll(circle, text);
        
        stack.setLayoutX(layoutWidth/2 - circle.getRadius());
        stack.setLayoutY(75);
        
        listStack.add(stack);
        
        FadeTransition ft2 = new FadeTransition(Duration.seconds(0.5), stack);
        ft2.setFromValue(0);
        ft2.setToValue(1);
        ft2.play();
        
        layout.getChildren().add(stack);
    }
    @FXML
    void goBackInsert1(ActionEvent event) {
    	FadeTransition ft1 = new FadeTransition(Duration.seconds(0.5), insertBoard1);
    	ft1.setFromValue(1);
        ft1.setToValue(0);
        ft1.play();
        insertBoard1.setVisible(false);
        
        FadeTransition ft2 = new FadeTransition(Duration.seconds(0.5), insertButton);
        ft2.setFromValue(0);
        ft2.setToValue(1);
        ft2.play();
        insertButton.setVisible(true);
    }
    @FXML
    void goInsert2(ActionEvent event) {
    	tree.Insert(Integer.parseInt(parentText2.getText()),
    			Integer.parseInt(insertText2.getText()));
    	parentText2.setText(null);
    	insertText2.setText(null);
    }
    @FXML
    void goBackInsert2(ActionEvent event) {
    	FadeTransition ft1 = new FadeTransition(Duration.seconds(0.5), insertBoard2);
    	ft1.setFromValue(1);
        ft1.setToValue(0);
        ft1.play();
        insertBoard2.setVisible(false);
        
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
    private TranslateTransition slideListView = new TranslateTransition();
    @FXML
    void slideListView(ActionEvent event) {
    	Class<?> clazz = this.getClass();
    	double slideToX = slideButtonRight.getWidth() - listViewBar.getWidth();
    	slideListView.setNode(listViewBar);
    	slideListView.setDuration(Duration.seconds(1));
    	if (slideListView.getToX() != 0
    			&& slideListView.getToX() != slideToX) {
    		slideListView.setToX(0);
    	}
    	if (slideListView.getToX() == 0) {
    		slideListView.setToX (slideToX);
    		slideListView.play();
    		slideButtonRight.setGraphic(null);
    		InputStream input = clazz.getResourceAsStream("/image/slideRight.png");
	    	slideButtonRight.setGraphic(new ImageView(new Image(input, 22, 22, false, true)));
    	}else{
    		slideListView.setToX(0);
    		slideListView.play();
	    	slideButtonRight.setGraphic(null);
	    	InputStream input = clazz.getResourceAsStream("/image/slideLeft.png");
	    	slideButtonRight.setGraphic(new ImageView(new Image(input, 22, 22, false, true)));
    	}

    }
    //______________________________________________________________________________________
    @FXML
    private ProgressBar progressBar;	
	@FXML
	void quitProgram(ActionEvent event) {
		System.exit(0);
	}
}

