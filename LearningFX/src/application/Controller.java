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
import java.util.Collections;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.*;	

public class Controller{
	
	private double speed = 0.3;
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
		
		hideAllBoard();
		
		FadeTransition ft = new FadeTransition(Duration.seconds(0.5), layout);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
        
        ft.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	layout.getChildren().clear();
        		drawSkeleton();
        		FadeTransition ft = new FadeTransition(Duration.seconds(0.01), layout);
	            ft.setFromValue(0);
	            ft.setToValue(1);
	            ft.play();
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
    		InputStream input = clazz.getResourceAsStream("/image/slideLeft.png");
    		slideButtonLeft.setGraphic(new ImageView(new Image(input, 22, 22, false, true)));
    	}else {
    		slideButtonBar.setToX(0);
    		slideButtonBar.play();
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
	    	
	        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), layout);
	        ft.setFromValue(1);
	        ft.setToValue(0);
	        ft.play(); 
	        ft.setOnFinished(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	layout.getChildren().clear();
	            	drawSkeleton();
	                FadeTransition ft = new FadeTransition(Duration.seconds(0.01), layout);
	                ft.setFromValue(0);
	                ft.setToValue(1);
	                ft.play();
	            }
	        });
	    	
    	}
    }
    //-------------------------------------------------------------------------------------------
    
    //insert
    private SequentialTransition seq = new SequentialTransition();
    private SequentialTransition seqMove = new SequentialTransition();
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
	void goInsert1(ActionEvent event) {
		
		int insertValue = Integer.parseInt(insertText1.getText());
		insertText1.setText(null);
		tree.Insert(insertValue);
		
		//visit node
		
		//draw
		if (tree.getListNodeBeforeMove().isEmpty()) {
			draw(tree.getListNode(), insertValue);
		}else {
			draw(tree.getListNodeBeforeMove(), insertValue);
			moveTree(tree.getListNodeBeforeMove(), tree.getListNode());
		}
	    seq = new SequentialTransition();
	    seqMove = new SequentialTransition();
	    
	}
	@FXML
    void Insert(ActionEvent event) {
    	if (tree instanceof GenericTree ){
    		insertText2.setText(null);
    		parentText2.setText(null);
    		hideOtherBoard(insertBoard2);
    		showBoard(insertBoard2, insertButton);
    	}else {
    		insertText1.setText(null);
    		hideOtherBoard(insertBoard1);
    		showBoard(insertBoard1, insertButton);   
    	}
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
    	
    	//visit node
    	//draw
    	
    	/*seqT.play();
    	seqT.getChildren().clear();*/
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
    	//deleteNode()
    
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
    		InputStream input = clazz.getResourceAsStream("/image/slideRight.png");
	    	slideButtonRight.setGraphic(new ImageView(new Image(input, 22, 22, false, true)));
    	}else{
    		slideListView.setToX(0);
    		slideListView.play();
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
    private void visitNode(ArrayList<Node> listNode, int value) {
    	
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
	private void draw(ArrayList<Node> listNode, int insertValue) {
		for (int i = 0; i < listNode.size(); i++) {
			if (listNode.get(i) != null) {
				if (listNode.get(i).value == insertValue) {
					if (i != 0) {
						Line insertLine = listLine.get(i - 1);
						insertLine.setVisible(true);
						FadeTransition ft = new FadeTransition(Duration.seconds(speed), insertLine);
				        ft.setFromValue(0);
				        ft.setToValue(1);
				        seq.getChildren().add(ft);
					}
			        Text text = new Text(String.valueOf(insertValue));
			        text.setBoundsType(TextBoundsType.VISUAL); 
			        text.setStyle("-fx-font-weight: bold");
			        
			        StackPane insertStack = listStack.get(i);
			        insertStack.getChildren().add(text);
			        insertStack.setVisible(true);
			        
			        FadeTransition ft1 = new FadeTransition(Duration.seconds(speed), insertStack);
			        ft1.setFromValue(0);
			        ft1.setToValue(1);
			        seq.getChildren().add(ft1);   
				}
			}
		}
		seq.play();
	}
	
	/*Color fillColor;
	Color strokeColor;
	int strokeWidth;
	private void setVisual(Color fillColor, Color strokeColor, int strokeWidth) {
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
		this.strokeWidth = strokeWidth;
	}*/
	
	private void drawSkeleton() {
		listStack.clear();
		listLine.clear();
		double Y = 75;
		
		for (int i = 0; i < 6; i++) {
			double startX = layout.getWidth() / Math.pow(2, i + 1);
			double X = startX;
			double FromX = 2 * startX;
			double FromY = Y;
			double a = 0;
			double errorX = 0;
			double errorY = 0;
			if (i != 0) {
				Y += 100 * Math.pow(0.9, i - 1);
				a = 200 * Math.pow(0.5, i - 1) / (100 * Math.pow(0.9, i - 1));
				errorY = Math.sqrt(radius*radius / (1 + a*a));
				errorX = a * errorY;
			}
			for (int j = 0; j < Math.pow(2, i); j++) {
				 
				StackPane stack = new StackPane();
				
				Circle circle = new Circle();
		     	circle.setRadius(radius);
		     	circle.setFill(Color.WHITE);
		     	circle.setStrokeWidth(2);
		     	circle.setStroke(Color.BLACK);
		     	
		     	stack.getChildren().add(circle);   	
		        stack.setLayoutX(X - radius);
		        stack.setLayoutY(Y);
		        stack.setVisible(false);
		        
		        listStack.add(stack);
		        layout.getChildren().add(stack);	
				
				if (i != 0) {
					Line line;
					if (j % 2 == 0) {
						line = new Line(FromX - errorX , FromY + radius + errorY + 1,
								X + errorX, Y + radius - errorY);
					}else {
						line = new Line(FromX + errorX + 2, FromY + radius + errorY + 1,
								X - errorX, Y + radius - errorY);
					}
			        line.setStrokeWidth(2);
			        line.setStroke(Color.BLACK);
			        line.setVisible(false);
			        
			        listLine.add(line);
			        layout.getChildren().add(line);
				}
				X += 2 * startX;
				if (j % 2 == 1) {
					FromX += 4 * startX;
				}
			}
		}
	}
	
	private void moveTree(ArrayList<Node> fromListNode, ArrayList<Node> toListNode) {
		seq.setOnFinished(new EventHandler<ActionEvent>() {
			
            @Override
            public void handle(ActionEvent event) {
            	//remove all line
            	for (Line line : listLine) {
        			if (line.isVisible()) {
        				FadeTransition ft = new FadeTransition(Duration.seconds(speed), line);
        				ft.setFromValue(1);
        				ft.setToValue(0);
        				ft.play();
        				ft.setOnFinished(new EventHandler<ActionEvent>() {      					
        		            @Override
        		            public void handle(ActionEvent event) {
        		            	line.setVisible(false);
        		            }
        				});
        			}	
        		}
            	/*while loop (fromListNode != toListNode)
    			(fromListNode null, toListNode not null) -> move node to null node and swap fromListNode*/
            	while ((fromListNode.subList(0, toListNode.size())).equals(toListNode) == false) {
            		for (int i = 0; i < toListNode.size(); i++) {
            			if (toListNode.get(i) != null && fromListNode.get(i) == null) {
            				moveToNull(toListNode.get(i), i);
            				Collections.swap(fromListNode, fromListNode.indexOf(toListNode.get(i)), i);
            			}
            		}
            	}
            	seqMove.play();
            	seqMove.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                		//add all line
                		for (int i = 1; i < toListNode.size(); i++) {
                			if (toListNode.get(i) != null) {
                				Line line = listLine.get(i - 1);
                				FadeTransition ft = new FadeTransition(Duration.millis(1), line);
            					ft.setFromValue(1);
            					ft.setToValue(0);
                				ft.play();
                				ft.setOnFinished(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                    	line.setVisible(true);
                        				FadeTransition ft = new FadeTransition(Duration.seconds(speed), line);
                        				ft.setFromValue(0);
                        				ft.setToValue(1);
                        				ft.play();
                                    }
                				});		
                			}	
                		}
                    }
            	});
            }            	
		});	
	}
	
	private void moveToNull(Node n1, int nullIndex) {
		//take index
		int index = 0;
		for (StackPane stack : listStack) {
			for (javafx.scene.Node n : stack.getChildren()) {
				if (n instanceof Text) {
					if(((Text) n).getText().equals(String.valueOf(n1.value))) {
						index = listStack.indexOf(stack);
					}
				}
			}
		}		
		//swap stack on layout
		StackPane moveNode = listStack.get(index);
		StackPane nullNode = listStack.get(nullIndex);	
				
		double moveX = (nullNode.getLayoutX() + nullNode.getTranslateX())
				- (moveNode.getLayoutX() + moveNode.getTranslateX());
		double moveY = (nullNode.getLayoutY() + nullNode.getTranslateY())
				- (moveNode.getLayoutY() + moveNode.getTranslateY());
		TranslateTransition tt = new TranslateTransition();
		tt.setNode(moveNode);
		tt.setDuration(Duration.seconds(speed));
		tt.setToX(moveNode.getTranslateX() + moveX);
		tt.setToY(moveNode.getTranslateY() + moveY);
		seqMove.getChildren().add(tt);
				
		nullNode.setTranslateX(nullNode.getTranslateX() - moveX);
		nullNode.setTranslateY(nullNode.getTranslateY() - moveY);
		
		//swap listStack
		Collections.swap(listStack, index, nullIndex);

	}
	
}

