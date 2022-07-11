package tree;

import java.util.ArrayList;
public class Main {

	public static void main(String[] args) {
		Tree tree = new AVLTree(2);
		tree.Insert(10);
		tree.Insert(8);
		tree.Insert(12);
		tree.Insert(7);
		tree.Insert(9);
		tree.Insert(11);
		tree.Insert(6);
		
		for (Node n : tree.getListNode()) {
			System.out.println(n);
		}
		System.out.println("_________________");
		for (Node n : tree.getListNodeBeforeMove()) {
			System.out.println(n);
		}
	}
}
