package tree;

public class AVLTree {
	private boolean isLeftChild;
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
	private int balance(AVLNode N) {
		return (N == null) ? 0 
				: (height(N.left) - height(N.right));
	}
	private void updateHeight(AVLNode N) {
		N.height = 1 + max(height(N.right),
				height(N.left));
	}
	private void LL(AVLNode x) {
		AVLNode y = x.left;
		x.left = y.right;
		y.right = x;
		updateHeight(x);
		updateHeight(y);
	}
	private void RR(AVLNode x) {
		AVLNode y = x.right;
		x.right = y.left;
		y.left = x;
		updateHeight(x);
		updateHeight(y);
	}
	private void LR(AVLNode x) {
		AVLNode y = x.left;
		AVLNode z = y.right;
		if (balance(z) == 1) {
			y.right = z.left;
			x.left = null;
		}else {
			x.left = z.right;
			y.right = null;
		}
		z.left = y;
		z.right = x;
		updateHeight(x);
		updateHeight(y);
		updateHeight(z);
	}
	private void RL(AVLNode x) {
		AVLNode y = x.right;
		AVLNode z = y.left;
		if (balance(z) == 1) {
			x.right = z.left;
			y.left = null;
		}else {
			y.left = z.right;
			x.right = null;
		}
		z.right = y;
		z.left = x;
		updateHeight(x);
		updateHeight(y);
		updateHeight(z);
	}
	private AVLNode parentNodeOf(AVLNode N) {
		AVLNode focusNode = root;
		while(true) {
			if (N.value < focusNode.value) {
				if (focusNode.left == N) {
					isLeftChild = true;
					break;
				}else {
					focusNode = focusNode.left;
				}
			}else if (N.value > focusNode.value) {
				if (focusNode.right == N) {
					isLeftChild = false;
					break;
				}else {
					focusNode = focusNode.right;
				}
			}else {
				focusNode = null;
				break;
			}
		}
		return focusNode;
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
		updateHeight(focusNode);
		if (balance(focusNode) == 2 || balance(focusNode) == -2) {
			AVLNode parent = parentNodeOf(focusNode);
			AVLNode localRoot;
			if (balance(focusNode) == 2) {
				if (balance(focusNode.left) == 1) {
					localRoot = focusNode.left;
					LL(focusNode);
				}else {
					localRoot = focusNode.left.right;
					LR(focusNode);
				}
			}else{
				if (balance(focusNode.right) == -1) {
					localRoot = focusNode.right;
					RR(focusNode);
				}else {
					localRoot = focusNode.right.left;
					RL(focusNode);
				}
			}
			if (parent == null) {
				root = localRoot;
			}else {
				if (isLeftChild) {
					parent.left = localRoot;
				}else {
					parent.right = localRoot;
				}
			}
		}	
	}
	private void TraverseDFS(AVLNode N) {
		if (N != null) {
			System.out.println(N.value);
			TraverseDFS(N.left);
			TraverseDFS(N.right);
		}
	}
	private void TraverseBFS(AVLNode[] listParent) {
		AVLNode[] listChildren = new AVLNode[99];
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

	public void Create() {
		root = null;
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
			AVLNode[] initList = new AVLNode[99];
			initList[0] = root;
			TraverseBFS(initList);
		}
	}
	
	public AVLNode Search(int value) {
		AVLNode focusNode = root;
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




