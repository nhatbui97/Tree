package tree;

public class BinarySearchTree {
	Node root;
	
	public void Insert(int insertValue) {
		Node newNode = new Node(insertValue);
		root = newNode;
		return;
	}
	public void Insert(int parentValue, int insertValue) {
		Node newNode = new Node(insertValue);
		Node parentNode = Search(parentValue);
		Node searchDuplicate = Search(insertValue);
		if (searchDuplicate != null) {
			System.out.println("The tree has already had that node");
		}else {
			if (insertValue < parentValue) {
				Node tempNode = parentNode.left;
				parentNode.left = newNode;
				if (tempNode != null) {
					if (newNode.value > tempNode.value) {
						newNode.left = tempNode;
					}else {
						newNode.right = tempNode;
					}
				}
			}else {
				Node tempNode = parentNode.right;
				parentNode.right = newNode;
				if (tempNode != null) {
					if (newNode.value > tempNode.value) {
						newNode.left = tempNode;
					}else {
						newNode.right = tempNode;
					}
				}
			}
		}	
	}
	
	public void Delete(int deleteValue) {
		root = Search(deleteValue);
		Node tempRoot = root;
		if (root == null) {
			System.out.println("The Node does not exist");
		}else {
			if (root.left == null && root.right == null) {
				root = null;
				root = tempRoot;	
			}
			
		}
	}
	private Node Predecessor(Node aNode) {
		if (aNode.right != null) {
			return Predecessor(aNode.right);
		}
		return aNode;
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
			System.out.println(aNode.value);
			TraverseDFS(aNode.left);
			TraverseDFS(aNode.right);
		}
	}
	private void TraverseBFS(Node[] listParent) {
		Node[] listChildren = new Node[99];
		if (listParent[0] != null) {
			for (int i = 0; i < listParent.length; i++ ) {
				if (listParent[i] != null) {
					System.out.println(listParent[i].value);
					for (int j = 0; j < listChildren.length; j++) {
						if (listChildren[j] == null) {
							if( listParent[i].left == null && listParent[i].right != null) {
								listChildren[j] = listParent[i].right;
								break;
							}else {
								listChildren[j] = listParent[i].left;
								listChildren[j+1] = listParent[i].right;
								break;
							}
						}
					}
				}
			}
			TraverseBFS(listChildren);
		}
	}
	
	public Node Search(int value) {
		Node focusNode = root;
		if (focusNode != null) {
			while(true) {
				if (value < focusNode.value) {
					focusNode = focusNode.left;
					if (focusNode == null) {
						break;
					}
				}else if (value > focusNode.value) {
					focusNode = focusNode.right;
					if (focusNode == null) {
						break;
					}
				}else {
					break;
				}
			}
		}
		return focusNode;
	}
		
}
