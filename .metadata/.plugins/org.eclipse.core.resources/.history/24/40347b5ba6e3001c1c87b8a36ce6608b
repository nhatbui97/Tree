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
	
	public void Traverse(String algorithm) {
		if (algorithm == "DFS") {
			TraverseDFS(root);
		}else if (algorithm == "BFS") {
			Node[] test = new Node[99];
			test[0] = root;
			TraverseBFS(test);
		}
	}
	private void TraverseDFS(Node aNode) {
		if (aNode != null) {
			System.out.println(aNode.key);
			TraverseDFS(aNode.left);
			TraverseDFS(aNode.right);
		}
	}
	private void TraverseBFS(Node[] listParent) {
		Node[] listParentCopy = listParent;
		for (int i = 0; i < listParentCopy.length; i++ ) {
			if (listParent[i] != null) {
				System.out.println(listParent[i].key);
				for (int j = 0; j<)
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
		
}

class Node{
	int key;
	Node left, right;
	Node(int key){
		this.key = key;
	}
}
