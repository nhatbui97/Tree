package tree;

public class Main {

	public static void main(String[] args) {
		AVLTree avl = new AVLTree(5);
		avl.Insert(10);
		avl.Insert(10, 9);
		avl.Insert(10, 15);
		avl.Insert(15, 13);
		avl.Insert(13, 14);
		
		avl.Traverse("BFS");
		
		
			
	}
}
