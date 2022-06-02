package tree;

public class Main {

	public static void main(String[] args) {
		BinarySearchTree bst = new BinarySearchTree();
		bst.Insert(10);
		bst.Insert(10, 12);
		bst.Insert(12, 11);
		bst.Insert(12, 13);
		bst.Insert(10, 14);
		System.out.println(bst.Search(14).left.key);
	}
}
