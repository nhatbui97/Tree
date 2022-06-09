package tree;

public class Main {

	public static void main(String[] args) {
		AVLTree avl = new AVLTree(5);
		avl.Insert(10);
		avl.Insert(10, 9);
		avl.Insert(10, 15);
		avl.Insert(15, 13);
		avl.Insert(13, 14);
		avl.Delete(15);
		avl.Delete(9);
		avl.Insert(14,15);
		avl.Insert(15,18);
		avl.Delete(15);
		avl.Update(14, 15);
		avl.Update(18, 19);
		avl.Update(13, 14);
		
		avl.Traverse("BFS");
		
		
			
	}
}
