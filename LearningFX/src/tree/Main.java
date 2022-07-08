package tree;

public class Main {

	public static void main(String[] args) {
		Tree tree = new AVLTree(4);
		tree.Insert(10);
		tree.Insert(10, 15);
		tree.Insert(15, 18);
		for (Node n : tree.getListNodeBeforeRotate()) {
			System.out.println(n);
		}
		System.out.println("_____________________________");
		for (Node n : tree.getListNode()) {
			System.out.println(n);
		}
		System.out.println((int) ((0 - 1)/2));
		
	}
}
