package tree;

public class AVLTree {
	AVLNode root;
	int maxHeight;
	AVLTree(int maxHeight){
		this.maxHeight = maxHeight;
	}
	private int height(AVLNode N) {
		return (N == null) ? 0 : N.height;
	}
	private int max(int value1, int value2) {
		return (value1 > value2) ? value1 : value2;
	}
	private int getBalance(AVLNode N) {
		return (N == null) ? 0 
				: (height(N.left) - height(N.right));	
	}
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode z = x.right;
        x.right = y;
        y.left = z;
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;
        return x;
    }
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode z = y.left;
        y.left = x;
        x.right = z;
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;
        return y;
    }
	
	public void Create() {
		root = null;
	}
	private void Insert(AVLNode focusNode , 
			int parentValue, int insertValue) {
		if (parentValue < focusNode.value ) {
			Insert(focusNode.left, parentValue, insertValue);
		}else if (parentValue > focusNode.value) {
			Insert(focusNode.right, parentValue, insertValue);
		}else {
			if (insertValue < parentValue) {
				focusNode.left = new AVLNode(insertValue);
			}else {
				focusNode.right = new AVLNode(insertValue);
			}
		}
		focusNode.height = 1 + max(height(focusNode.right),
				height(focusNode.left));
		int balance = getBalance(focusNode);
		
		
	}
	public void Insert(int parentValue, int insertValue) {
		Insert(root, parentValue, insertValue);
	}
	public void Insert(int insertValue) {
		root = new AVLNode(insertValue);
	}
	public void Delete(int deleteValue) {
		
	}
	public void Update(int currentValue, int newValue) {
		
	}
	public void Traverse(String algorithm) {
		if (algorithm == "DFS") {
			TraverseDFS(root);
		}else if (algorithm == "BFS") {
			Node[] initList = new Node[99];
			initList[0] = root;
			TraverseBFS(initList);
		}
	}
	private void TraverseDFS(Node N) {
		if (N != null) {
			System.out.println(N.value);
			TraverseDFS(N.left);
			TraverseDFS(N.right);
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

class AVLNode extends Node{
	int height;
	AVLNode left, right;
	AVLNode(int value){
		super(value);
		height = 1;
		left = right = null;
	}
}




