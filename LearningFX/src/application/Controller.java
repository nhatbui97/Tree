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
import javafx.scene.paint.Color;
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
	
	private int speed = 1;
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
    private SequentialTransition seqT = new SequentialTransition();
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
    		insertText1.setText(null);
    		hideOtherBoard(insertBoard1);
    		showBoard(insertBoard1, insertButton);   		
    	}else {
    		insertText2.setText(null);
    		parentText2.setText(null);
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
    	circle.setFill(Color.WHITE);
    	circle.setStrokeWidth(2);
    	circle.setStroke(Color.BLACK);
        
        Text text = new Text(String.valueOf(insertValue));
        text.setBoundsType(TextBoundsType.VISUAL); 
        text.setStyle("-fx-font-weight: bold");
        StackPane stack = new StackPane();
        stack.getChildren().addAll(circle, text);
        
        stack.setLayoutX(layoutWidth/2 - circle.getRadius());
        stack.setLayoutY(75);
        
        listStack.add(stack);
        
        FadeTransition ft = new FadeTransition(Duration.seconds(speed), layout);
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
    	int insertValue = Integer.parseInt(insertText2.getText());
    	int parentValue = Integer.parseInt(parentText2.getText());
    	tree.Insert(parentValue, insertValue);
    	
    	parentText2.setText(null);
    	insertText2.setText(null);   
    	
    	//visitNode()
    	
    	draw(layout, parentValue, insertValue, Color.WHITE, Color.BLACK, 2);
    	
    	if (tree.isRotate() == true) {
    		
    	}
    	if (tree.isHeightLimit() == true) {
    		
    	}
    	seqT.play();
    	seqT.getChildren().clear();
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
    	deleteText.setText(null);
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
    					
    					FadeTransition ft = new FadeTransition(Duration.seconds(speed), stack);
	        	        ft.setFromValue(1);
	        	        ft.setToValue(0);
	        	        ft.play();
	        	        
    					FadeTransition ft1 = new FadeTransition(Duration.seconds(speed), lineRemove);
	        	        ft1.setFromValue(1);
	        	        ft1.setToValue(0);
	        	        ft1.play();
	        	        
	        	        ft1.setOnFinished(new EventHandler<ActionEvent>() {
	        	            @Override
	        	            public void handle(ActionEvent event) {
	        	            	listStack.remove(stack);
	        	            	listLine.remove(lineRemove);
	        	            	layout.getChildren().remove(stack);
	        	            	layout.getChildren().remove(lineRemove);
	        	            }
	        	        });

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
    	updateText.setText(null);
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
    	traverseText.setText(null);
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
    	searchText.setText(null);
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
    @FXML
    private AnchorPane layoutVisit;
	private void visitNode() {
		ArrayList<Integer> orderVisit = tree.getOrderVisit();
		ArrayList<Integer> orderDirection = tree.getOrderDirection();
		int size = orderVisit.size();
	    for (int i = 0; i < size; i++) {
	    	StackPane parentStack = null;
	    	for (StackPane stack : listStack) {
	    		for (javafx.scene.Node n : stack.getChildren()) {
	    			if (n instanceof Text) {
	    				Text n1 = (Text) n;
	    				if (n1.getText().equals(String.valueOf(orderVisit.get(i)))) {
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
		}
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
	private void draw(AnchorPane lay, int parentValue, int insertValue,
			Color fillColor, Color strokeColor, int strokeWidth) {
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
            line.setStrokeWidth(strokeWidth);
            line.setStroke(strokeColor);
            
            FadeTransition ft = new FadeTransition(Duration.seconds(speed), line);
            ft.setFromValue(0);
            ft.setToValue(1);
            seqT.getChildren().add(ft);
     
            Circle circle = new Circle();
        	circle.setRadius(radius);
        	circle.setFill(fillColor);
        	circle.setStrokeWidth(strokeWidth);
        	circle.setStroke(strokeColor);
            
            Text text = new Text(String.valueOf(insertValue));
            text.setBoundsType(TextBoundsType.VISUAL); 
            text.setStyle("-fx-font-weight: bold");
            StackPane stack = new StackPane();
            stack.getChildren().addAll(circle, text);
            
            stack.setLayoutX(targetX - radius);
            stack.setLayoutY(targetY);
            
            FadeTransition ft1 = new FadeTransition(Duration.seconds(speed), stack);
            ft1.setFromValue(0);
            ft1.setToValue(1);
            seqT.getChildren().add(ft1);
            
            if (lay == layout) {
            	listLine.add(line);
            	listStack.add(stack);
            }
            
            lay.getChildren().add(line);
            lay.getChildren().add(stack);
                     
    	}else {
    		targetX = X + radius + targetX;
    		
            Line line = new Line(X + radius + errorX + 2 , Y + radius + errorY
            		, targetX - errorX , targetY + radius - errorY );
            line.setStrokeWidth(strokeWidth);
            line.setStroke(strokeColor);
            
            FadeTransition ft = new FadeTransition(Duration.seconds(speed), line);
            ft.setFromValue(0);
            ft.setToValue(1);
            seqT.getChildren().add(ft);
            
            Circle circle = new Circle();
        	circle.setRadius(radius);
        	circle.setFill(fillColor);
        	circle.setStrokeWidth(strokeWidth);
        	circle.setStroke(strokeColor);
            
            Text text = new Text(String.valueOf(insertValue));
            text.setBoundsType(TextBoundsType.VISUAL); 
            text.setStyle("-fx-font-weight: bold");
            StackPane stack = new StackPane();
            stack.getChildren().addAll(circle, text);
            
            stack.setLayoutX(targetX - radius);
            stack.setLayoutY(targetY);
            
            FadeTransition ft1 = new FadeTransition(Duration.seconds(speed), stack);
            ft1.setFromValue(0);
            ft1.setToValue(1);
            seqT.getChildren().add(ft1);
            
            if (lay == layout) {
            	listLine.add(line);
            	listStack.add(stack);
            }
            
            lay.getChildren().add(line);
            lay.getChildren().add(stack);
    		
    	}
    	
	}
}

