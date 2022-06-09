package tree;

public class AVLTree {
	AVLNode root;
	int maxDistance;
	private boolean isLeftChild;
	AVLTree(int maxDistance){
		this.maxDistance = maxDistance;
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
	private void rotate(AVLNode N) {
		if (balance(N) == 2 || balance(N) == -2) {
			AVLNode parent = parentNodeOf(N);
			AVLNode localRoot;
			if (balance(N) == 2) {
				if (balance(N.left) == 1) {
					localRoot = N.left;
					LL(N);
				}else {
					localRoot = N.left.right;
					LR(N);
				}
			}else{
				if (balance(N.right) == -1) {
					localRoot = N.right;
					RR(N);
				}else {
					localRoot = N.right.left;
					RL(N);
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
	private void Insert(AVLNode N , 
			int parentValue, int insertValue) {
		if (parentValue < N.value ) {
			Insert(N.left, parentValue, insertValue);
		}else if (parentValue > N.value) {
			Insert(N.right, parentValue, insertValue);
		}else {
			if (insertValue < parentValue) {
				N.left = new AVLNode(insertValue);
			}else {
				N.right = new AVLNode(insertValue);
			}
		}
		updateHeight(N);
		rotate(N);
		limitDistance(N);
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
	private AVLNode toTheRight(AVLNode N) {
		if (N.right != null) {
			return toTheRight(N.right);
		}
		return N;
	}
	private AVLNode predecessor(AVLNode N) {
		return toTheRight(N.left);
	}
	private void Delete(AVLNode N, int deleteValue) {
		if (deleteValue < N.value) {
			Delete(N.left, deleteValue);
		}else if (deleteValue > N.value) {
			Delete(N.right, deleteValue);
		}else {
			AVLNode parent = parentNodeOf(N);
			if (isLeftChild) {
				if (N.left == null) {
					if (N.right == null) {
						parent.left = null;
					}else {
						parent.left = N.right;
					}
				}else {
					if(N.right == null) {
						parent.left = N.left;
					}else {
						AVLNode preParent = parentNodeOf(predecessor(N));
						parent.left = predecessor(N);
						if (parent.left != N.left) {
							parent.left.left = N.left;
						}
						parent.left.right = N.right;
						preParent.right = null;
					}
				}
			}else {
				if (N.left == null) {
					if (N.right == null) {
						parent.right = null;
					}else {
						parent.right = N.right;
					}
				}else {
					if(N.right == null) {
						parent.right = N.left;
					}else {
						AVLNode preParent = parentNodeOf(predecessor(N));
						parent.right = predecessor(N);
						if (parent.right != N.left) {
							parent.right.left = N.left;
						}
						parent.right.right = N.right;
						preParent.right = null;
					}
				}
				
			}
		}
		updateHeight(N);
		rotate(N);
	}
	private void limitDistance(AVLNode N) {
		if (N.height - 1 > maxDistance) {
			if (balance(N) == 1) {
				for (int i = 0; i < maxDistance - 2; i++) {
					N = N.left;
				}
				if (balance(N.left) == 1) {
					if (balance(N.left.left) == 1) {
						
					}else {
						
					}
				}else {
					if (balance(N.left.right) == 1) {
						
					}else {
						
					}
					
				}
				
			}
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
		Delete(root, deleteValue);
	}
	public void Update(int currentValue, int newValue) {
		AVLNode N = Search(currentValue);
		AVLNode parent = parentNodeOf(N);
		if ((N.left == null || newValue > N.left.value )
				&& (N.right == null || newValue < N.right.value)) {
			if (parent == null) {
				N.value = newValue;
			}else {
				if (isLeftChild) {
					if (newValue < parent.value) {
						N.value = newValue;
					}
				}else {
					if (newValue > parent.value) {
						N.value = newValue;
					}
				}
			}
		}
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
		AVLNode N = root;
		if (N != null) {
			while(true) {
				if (value < N.value) {
					N = N.left;
					if (N == null) {
						break;
					}
				}else if (value > N.value) {
					N = N.right;
					if (N == null) {
						break;
					}
				}else {
					break;
				}
			}
		}
		return N;
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




