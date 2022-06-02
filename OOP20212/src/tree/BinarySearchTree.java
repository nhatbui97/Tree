package tree;

public class BinarySearchTree {
	Node root;
	public void Insert(int insertKey) {
		Node newNode = new Node(insertKey);
		root = newNode;
		return;
	}
	public void Insert(int parentKey, int insertKey) {
		Node newNode = new Node(insertKey);
		Node parentNode = Search(parentKey);
		Node searchDuplicate = Search(insertKey);
		if (searchDuplicate != null) {
			System.out.println("The tree has already had that node");
		}else {
			if (insertKey < parentKey) {
				Node tempNode = parentNode.left;
				parentNode.left = newNode;
				if (tempNode != null) {
					if (newNode.key > tempNode.key) {
						newNode.left = tempNode;
					}else {
						newNode.right = tempNode;
					}
				}
			}else {
				Node tempNode = parentNode.right;
				parentNode.right = newNode;
				if (tempNode != null) {
					if (newNode.key > tempNode.key) {
						newNode.left = tempNode;
					}else {
						newNode.right = tempNode;
					}
				}
			}
		}	
	}
	public Node Search(int key) {
		Node focusNode = root;
		while(true) {
			if (key < focusNode.key) {
				focusNode = focusNode.left;
				if (focusNode == null) {
					break;
				}
			}else if (key > focusNode.key) {
				focusNode = focusNode.right;
				if (focusNode == null) {
					break;
				}
			}else {
				break;
			}
		}
		return focusNode;
	}
	public void Traverse(String algorithm) {
		if (algorithm == "DFS") {
			
			
		}else if (algorithm == "BFS") {
			
		}
		
	}
}

class Node{
	int key;
	Node left, right;
	Node(int key){
		this.key = key;
	}
}
