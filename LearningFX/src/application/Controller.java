package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;
import tree.*;

import java.io.InputStream;
import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.*;	

public class Controller{
	
	private ArrayList<StackPane> listStack = new ArrayList<StackPane>();
	private ArrayList<Line> listLine = new ArrayList<Line>();
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
		
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), layout);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
        
        hideAllBoard();
        
        ft.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	layout.getChildren().clear();
            }
        });
        
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
    void Create(ActionEvent event){
    	hideAllBoard();
    	if (tree.getRoot() != null) {
	    	tree.Create();
	    	listStack.removeAll(listStack);
	    	
	        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), layout);
	        ft.setFromValue(1);
	        ft.setToValue(0);
	        ft.play(); 
	        ft.setOnFinished(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	layout.getChildren().clear();
	            }
	        });
	    	
    	}
    }
    //-------------------------------------------------------------------------------------------
    
    //insert
    private double radius = 12;
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
    		hideOtherBoard(insertBoard1);
    		showBoard(insertBoard1, insertButton);   		
    	}else {
    		hideOtherBoard(insertBoard2);
    		showBoard(insertBoard2, insertButton);
    	}
    }
    @FXML
    void goInsert1(ActionEvent event) {
    	
    	int insertValue = Integer.parseInt(insertText1.getText());
    	insertText1.setText(null);
    	tree.Insert(insertValue);
    	goBackInsert1(event);
    	
    	double layoutWidth = layout.getWidth();
    	
    	Circle circle = new Circle();
    	circle.setRadius(radius);
    	circle.setFill(javafx.scene.paint.Color.WHITE);
    	circle.setStrokeWidth(2);
    	circle.setStroke(javafx.scene.paint.Color.BLACK);
        
        Text text = new Text(String.valueOf(insertValue));
        text.setBoundsType(TextBoundsType.VISUAL); 
        text.setStyle("-fx-font-weight: bold");
        StackPane stack = new StackPane();
        stack.getChildren().addAll(circle, text);
        
        stack.setLayoutX(layoutWidth/2 - circle.getRadius());
        stack.setLayoutY(75);
        
        listStack.add(stack);
        
        FadeTransition ft = new FadeTransition(Duration.seconds(1), layout);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        
        layout.getChildren().add(stack);
    }
    @FXML
    void goBackInsert1(ActionEvent event) {	
    	insertText1.setText(null);	
    	hideBoard(insertBoard1, insertButton);
    }
    @FXML
    void goInsert2(ActionEvent event){
    	SequentialTransition seqT = new SequentialTransition();
    	
    	int insertValue = Integer.parseInt(insertText2.getText());
    	int parentValue = Integer.parseInt(parentText2.getText());
    	tree.Insert(parentValue, insertValue);
    	
    	parentText2.setText(null);
    	insertText2.setText(null);   
    	
    	//visitNode()
    	
    	int size = tree.getOrderVisit().size();
    	StackPane parentStack = null;
    	for (StackPane stack : listStack) {
    		for (javafx.scene.Node n : stack.getChildren()) {
    			if (n instanceof Text) {
    				Text n1 = (Text) n;
    				if (n1.getText().equals(String.valueOf(parentValue))) {
    					parentStack = stack;
    				}
    			}
    		}
    	}
    	double X = parentStack.getLayoutX();
    	double Y = parentStack.getLayoutY();
    	double targetX = 200 * Math.pow(0.5, size - 1);
    	double targetY = 75 + 100 * (1 - Math.pow(0.9, size)) / 0.1;
    	double a = targetX / (100 * Math.pow(0.9, size - 1));
    	double errorY = Math.sqrt(radius*radius / (1 + a*a));
    	double errorX = a*errorY;
    	
    	if (tree.getOrderDirection().get(size - 1) == 0) {
    		targetX = X + radius - targetX;
    		
            Line line = new Line(X + radius - errorX , Y + radius + errorY
            		, targetX + errorX , targetY + radius - errorY );
            line.setStrokeWidth(2);
            
            listLine.add(line);
            
            FadeTransition ft = new FadeTransition(Duration.seconds(1), line);
            ft.setFromValue(0);
            ft.setToValue(1);
            seqT.getChildren().add(ft);
            
            layout.getChildren().add(line);
            
            Circle circle = new Circle();
        	circle.setRadius(radius);
        	circle.setFill(javafx.scene.paint.Color.WHITE);
        	circle.setStrokeWidth(2);
        	circle.setStroke(javafx.scene.paint.Color.BLACK);
            
            Text text = new Text(String.valueOf(insertValue));
            text.setBoundsType(TextBoundsType.VISUAL); 
            text.setStyle("-fx-font-weight: bold");
            StackPane stack = new StackPane();
            stack.getChildren().addAll(circle, text);
            
            stack.setLayoutX(targetX - radius);
            stack.setLayoutY(targetY);
            
            listStack.add(stack);
            
            FadeTransition ft1 = new FadeTransition(Duration.seconds(1), stack);
            ft1.setFromValue(0);
            ft1.setToValue(1);
            seqT.getChildren().add(ft1);
            
            layout.getChildren().add(stack);
                     
    	}else {
    		targetX = X + radius + targetX;
    		
            Line line = new Line(X + radius + errorX + 2 , Y + radius + errorY
            		, targetX - errorX , targetY + radius - errorY );
            line.setStrokeWidth(2);
            listLine.add(line);
            
            FadeTransition ft = new FadeTransition(Duration.seconds(1), line);
            ft.setFromValue(0);
            ft.setToValue(1);
            seqT.getChildren().add(ft);
            
            layout.getChildren().add(line);
            
            Circle circle = new Circle();
        	circle.setRadius(radius);
        	circle.setFill(javafx.scene.paint.Color.WHITE);
        	circle.setStrokeWidth(2);
        	circle.setStroke(javafx.scene.paint.Color.BLACK);
            
            Text text = new Text(String.valueOf(insertValue));
            text.setBoundsType(TextBoundsType.VISUAL); 
            text.setStyle("-fx-font-weight: bold");
            StackPane stack = new StackPane();
            stack.getChildren().addAll(circle, text);
            
            stack.setLayoutX(targetX - radius);
            stack.setLayoutY(targetY);
            
            listStack.add(stack);
            
            FadeTransition ft1 = new FadeTransition(Duration.seconds(1), stack);
            ft1.setFromValue(0);
            ft1.setToValue(1);
            seqT.getChildren().add(ft1);
            
            layout.getChildren().add(stack);
    		
    	}
    	if (tree.isRotate() == true) {
    		
    	}
    	if (tree.isHeightLimit() == true) {
    		
    	}
    	seqT.play();

    }
    @FXML
    void goBackInsert2(ActionEvent event) {
    	insertText2.setText(null);
    	parentText2.setText(null);
    	hideBoard(insertBoard2, insertButton);
    }
    //-------------------------------------------------------------------------------------------
    
    //delete
    @FXML
    private Button deleteButton;
    @FXML
    private TextField deleteText;
    @FXML
    private AnchorPane deleteBoard;
    @FXML
    void Delete(ActionEvent event) {
    	hideOtherBoard(deleteBoard);
    	showBoard(deleteBoard, deleteButton);
    }
    @FXML
    void goDelete(ActionEvent event) {
    	int deleteValue = Integer.parseInt(deleteText.getText());
    	deleteText.setText(null);
    	tree.Delete(deleteValue);
    	
    	//visitNode()
    	
    	for (StackPane stack : listStack) {
    		for (javafx.scene.Node n : stack.getChildren()) {
    			if (n instanceof Text) {
    				if(((Text) n).getText().equals(String.valueOf(deleteValue))) {
    					Line lineRemove = listLine.get(listStack.indexOf(stack) - 1);
    					
    					listStack.remove(stack);
    					FadeTransition ft = new FadeTransition(Duration.seconds(1), stack);
	        	        ft.setFromValue(1);
	        	        ft.setToValue(0);
	        	        ft.play();  					
    				}
    			}
    		}
    	}
    }

    @FXML
    void goBackDelete(ActionEvent event) {
    	deleteText.setText(null);
    	hideBoard(deleteBoard, deleteButton);

    }
    //-------------------------------------------------------------------------------------------
    
    //update
    @FXML
    private Button updateButton;
    @FXML
    private TextField currentText;
    @FXML
    private TextField updateText;
    @FXML
    private AnchorPane updateBoard;
    @FXML
    void Update(ActionEvent event) {
    	hideOtherBoard(updateBoard);
    	showBoard(updateBoard, updateButton);
    }
    @FXML
    void goUpdate(ActionEvent event) {

    }

    @FXML
    void goBackUpdate(ActionEvent event) {
    	currentText.setText(null);
    	updateText.setText(null);
    	hideBoard(updateBoard, updateButton);
    }
    //-------------------------------------------------------------------------------------------
    
    //traverse
    @FXML
    private Button traverseButton;
    @FXML
    private TextField traverseText;
    @FXML
    private AnchorPane traverseBoard;
    @FXML
    void Traverse(ActionEvent event) {
    	hideOtherBoard(traverseBoard);
    	showBoard(traverseBoard, traverseButton);
    }
    @FXML
    void goTraverse(ActionEvent event) {

    }

    @FXML
    void goBackTraverse(ActionEvent event) {
    	traverseText.setText(null);
    	hideBoard(traverseBoard, traverseButton);
    }
    //-------------------------------------------------------------------------------------------
    
    //search
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchText;
    @FXML
    private AnchorPane searchBoard;
    @FXML
    void Search(ActionEvent event) {
    	hideOtherBoard(searchBoard);
    	showBoard(searchBoard, searchButton);
    }
    @FXML
    void goSearch(ActionEvent event) {

    }

    @FXML
    void goBackSearch(ActionEvent event) {
    	searchText.setText(null);
    	hideBoard(searchBoard, searchButton);
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
	//_________________________________SupportFunction_____________________________________
	//_____________________________________________________________________________________
	private void visitNode() {
		ArrayList<FadeTransition> listFade = new ArrayList<FadeTransition>();
		
    	for (int i = 0; i < tree.getOrderVisit().size(); i++) {
    		for (StackPane stack : listStack) {
    			for (javafx.scene.Node n : stack.getChildren()) {
    				if (n instanceof Text) {
		    			if (((Text) n).getText().equals(String.valueOf(tree.getOrderVisit().get(i)))) {
		    				stack.setBlendMode(BlendMode.DIFFERENCE);
		        			FadeTransition ft = new FadeTransition(Duration.seconds(1), stack);
		        	        ft.setFromValue(0);
		        	        ft.setToValue(1);
		        	        listFade.add(ft);  
		        			if (tree.getOrderDirection().get(i) == 0) {
		        				//do something with list view
		        			}else {
		        				//do something with list view
		        			}
		    			}
	    			}
    			}
    		}
    	}
    	SequentialTransition seqT = new SequentialTransition();
    	for (FadeTransition ft : listFade) {
    		seqT.getChildren().add(ft);
    	}
    	seqT.play();
	}
	private void showBoard(AnchorPane board, Button button) {
		button.setVisible(false);
        
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), board);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        board.setVisible(true);
	}
	private void hideBoard(AnchorPane board, Button button) {
		board.setVisible(false);
        
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), button);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        button.setVisible(true);
	}
	private void hideAllBoard() {
        if(insertBoard1.isVisible()) {
    		hideBoard(insertBoard1, insertButton);
    	}
        if(insertBoard2.isVisible()) {
    		hideBoard(insertBoard2, insertButton);
    	}
        if(deleteBoard.isVisible()) {
    		hideBoard(deleteBoard, deleteButton);
    	}
        if(updateBoard.isVisible()) {
    		hideBoard(updateBoard, updateButton);
    	}
        if(traverseBoard.isVisible()) {
    		hideBoard(traverseBoard, traverseButton);
    	}
        if(searchBoard.isVisible()) {
    		hideBoard(searchBoard, searchButton);
    	}
	}
	private void hideOtherBoard(AnchorPane board) {
        if(board != insertBoard1 && insertBoard1.isVisible()) {
        	insertBoard1.setVisible(false);
        	insertButton.setVisible(true);
    	}
        if(board != insertBoard2 && insertBoard2.isVisible()) {
        	insertBoard2.setVisible(false);
        	insertButton.setVisible(true);
    	}
        if(board != deleteBoard && deleteBoard.isVisible()) {
        	deleteBoard.setVisible(false);
        	deleteButton.setVisible(true);
    	}
        if(board != updateBoard && updateBoard.isVisible()) {
        	updateBoard.setVisible(false);
        	updateButton.setVisible(true);
    	}
        if(board != traverseBoard && traverseBoard.isVisible()) {
        	traverseBoard.setVisible(false);
        	traverseButton.setVisible(true);
    	}
        if(board != searchBoard && searchBoard.isVisible()) {
        	searchBoard.setVisible(false);
        	searchButton.setVisible(true);
    	}
	}
}

