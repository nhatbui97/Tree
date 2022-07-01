package tree;

public class Main {

	public static void main(String[] args) {
		AVLTree avl = new AVLTree(3);
		avl.Insert(10);
		avl.Insert(10, 5);
		avl.Insert(10, 15);
		avl.Insert(5, 2);
		avl.Insert(5, 8);
		avl.Insert(15, 12);
		avl.Insert(15, 18);
		avl.Insert(12, 11);
		avl.Insert(12, 13);
		avl.Insert(18, 17);
		avl.Insert(18, 19);
		avl.Delete(15);
		avl.Delete(13);
		avl.Delete(10);
		avl.Delete(5);
		avl.Insert(2, 3);
		avl.Insert(3, 4);
		avl.Insert(19, 20);
		avl.Insert(11, 10);
		avl.Insert(4, 5);
		avl.Create();
		
		
		
		
		
		
			
	}
}
