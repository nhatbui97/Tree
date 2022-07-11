package tree;

import java.util.ArrayList;

public class Tree {
	Node root;
	ArrayList<Node> listNode = new ArrayList<Node>();
	ArrayList<Node> listNodeBeforeMove = new ArrayList<Node>();
	
	public Tree() {}
	public Node getRoot() {
		return root;
	}
	public ArrayList<Node> getListNodeBeforeMove() {
		return listNodeBeforeMove;
	}
	public ArrayList<Node> getListNode() {
		Traverse("BFS");
		return listNode;
	}
	public void Create() {};
	public void Insert(int parentValue, int insertValue) {};
	public void Insert(int insertValue) {};
	public void Delete(int deleteValue) {};
	public void Update(int currentValue, int newValue) {};
	public void Traverse(String algorithm) {};
	public Node Search(int value) {
		return null;
	};
	
}
