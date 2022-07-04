package tree;

import java.util.ArrayList;

public class Tree {
	Node root;
	ArrayList<Integer> orderVisit = new ArrayList<Integer>();
	//direction: 0 - left, 1 - right
	ArrayList<Integer> orderDirection= new ArrayList<Integer>();
	public Tree() {}
	public Node getRoot() {
		return root;
	}
	public ArrayList<Integer> getOrderVisit() {
		return orderVisit;
	}
	public ArrayList<Integer> getOrderDirection() {
		return orderDirection;
	};
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
