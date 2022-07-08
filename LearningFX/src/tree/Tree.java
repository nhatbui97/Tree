package tree;

import java.util.ArrayList;

public class Tree {
	Node root;
	ArrayList<Node> listNode = new ArrayList<Node>();
	ArrayList<Node> listNodeBeforeRotate = new ArrayList<Node>();
	ArrayList<Node> listNodeBeforeLimitHeight = new ArrayList<Node>();
	boolean rotate = false;
	boolean heightLimit = false;
	
	public ArrayList<Node> getListNodeBeforeRotate() {
		return listNodeBeforeRotate;
	}
	public ArrayList<Node> getListNodeBeforeLimitHeight() {
		return listNodeBeforeLimitHeight;
	}
	public Tree() {}
	public Node getRoot() {
		return root;
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
