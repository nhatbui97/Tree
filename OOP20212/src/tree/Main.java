package tree;

public class Main {

	public static void main(String[] args) {
		BinarySearchTree bst = new BinarySearchTree();
		bst.Insert(10);
		bst.Insert(10, 12);
		bst.Insert(12, 11);
		bst.Insert(12, 13);
		bst.Insert(10, 14);
		bst.Insert(10, 6);
		bst.Insert(6, 8);
		bst.Insert(6, 5);
		bst.Insert(8, 7);
		bst.Insert(8, 9);
		Node[] test = new Node[20];
		System.out.println(test.length);
		
	}
}
