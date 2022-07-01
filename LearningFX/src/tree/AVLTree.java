package tree;

public class AVLTree {
	AVLNode root;
	int maxDistance;
	AVLTree(int maxDistance){
		this.maxDistance = maxDistance;
	}
	//--------------------------------------------------------------
	//tìm max
	private int max(int value1, int value2) {
		return (value1 > value2) ? value1 : value2;
	}
	//--------------------------------------------------------------
	//tìm bố mày là ai
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
	private boolean isLeftChild;
	//--------------------------------------------------------------
	//thông số cơ bản của cây
	private int height(AVLNode N) {
		return (N == null) ? 0 : N.height;
	}
	private int balance(AVLNode N) {
		return (N == null) ? 0 
				: (height(N.left) - height(N.right));
	}
	//--------------------------------------------------------------
	//hàm update height
	private void updateHeight(AVLNode N) {
		N.height = 1 + max(height(N.right),
				height(N.left));
	}
	private void treeUpdateHeight(AVLNode N) {
		if (N != null) {
			treeUpdateHeight(N.left);
			updateHeight(N);
			treeUpdateHeight(N.right);
			updateHeight(N);
		}
	}
	//--------------------------------------------------------------
	//hàm đu quay
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
	//--------------------------------------------------------------
	//nhập/xóa
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
		if (root.height - 1 > maxDistance) {
			System.out.println("________Before________");
			Traverse("BFS");
			limitDistance(N);
			System.out.println("________After________");
			Traverse("BFS");
		}
	}
	private void Delete(AVLNode N, int deleteValue) {
		if (deleteValue < N.value) {
			Delete(N.left, deleteValue);
		}else if (deleteValue > N.value) {
			Delete(N.right, deleteValue);
		}else {
			AVLNode parent = parentNodeOf(N);
			boolean isLeftChildOfParent = isLeftChild;
			if (N.left == null) {
				if (parent == null) {
					root = N.right;
					N = root;
				}else {
					if(isLeftChildOfParent) {
						parent.left = N.right;
					}else {
						parent.right = N.right;
					}
					N = N.right;
				}
			}else {
				AVLNode predN = predecessor(N);
				if (predN != N.left) {
					predN.left = N.left;
				}
				predN.right = N.right;
				AVLNode parentPredN = parentNodeOf(predN);
				if (isLeftChild) {
					parentPredN.left = null;
				}else {
					parentPredN.right = null;
				}
				if (parent == null) {
					root = predN;
					N = root;
				}else {
					if (isLeftChildOfParent) {
						parent.left = predN;
					}else {
						parent.right = predN;
					}
					N = predN;
				}
			}
		}
		treeUpdateHeight(N);
		rotate(N);
	}
	//--------------------------------------------------------------
	//traverse
	private void TraverseDFS(AVLNode N) {
		if (N != null) {
			System.out.println(N.value + "(" + N.height + ")");
			TraverseDFS(N.left);
			TraverseDFS(N.right);
		}
	}
	private void TraverseBFS(AVLNode[] listParent) {
		AVLNode[] listChildren = new AVLNode[99];
		if (listParent[0] != null) {
			for (int i = 0; i < listParent.length; i++ ) {
				if (listParent[i] != null) {
					System.out.print(listParent[i].value 
							+ "(" + listParent[i].height + ")" + " ");
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
			System.out.print("\n");
			TraverseBFS(listChildren);
		}
	}
	//--------------------------------------------------------------
	//predecessor/successor
	private AVLNode toTheLastRight(AVLNode N) {
		if (N.right != null) {
			return toTheLastRight(N.right);
		}
		return N;
	}
	private AVLNode predecessor(AVLNode N) {
		return toTheLastRight(N.left);
	}
	private AVLNode toTheLastLeft(AVLNode N) {
		if (N.left != null) {
			return toTheLastLeft(N.left);
		}
		return N;
	}
	private AVLNode successor(AVLNode N) {
		return toTheLastLeft(N.right);
	}
	//--------------------------------------------------------------
	/*hàm giới hạn chiều cao cây
	  Ý tưởng:
	   + Đi tới Node (bắt đầu từ root)
	   + 2 TH:
	       . balance = 1, Node = pred, succ.left = Node
	       . balance = -1, Node = succ, pred.right = Node
	   + Xoay Node.left, Node.right
	   + Đi tới Node.left, Node.right, làm tương tự
	   (khi height = 4 tiếp tục quay về Node đến khi tmđk)
	 */
	private void limitDistance(AVLNode N) {
		if (balance(N) == 1) {
			AVLNode N_copy = N;
			AVLNode preN = predecessor(N);
			AVLNode preParent = parentNodeOf(preN);
			if (isLeftChild) {
				preParent.left = preN.left;
			}else {
				preParent.right = preN.left;
			}
			successor(N).left = N;
			preN.right = N.right;
			preN.left = N.left;
			N.right = null;
			N.left = null;
			N = preN;
			if (N_copy == root) {
				root = N;
			}else {
				AVLNode parent = parentNodeOf(N_copy);
				if (isLeftChild) {
					parent.left = N;
				}else {
					parent.right = N;
				}
				
			}
			treeUpdateHeight(root);
			rotate(N.left);
			rotate(N.right);
			treeUpdateHeight(root);
			if (root.height - 1 > maxDistance) {
				if (N.height > 4) {
					limitDistance(N.left);
					limitDistance(N.right);
				}else if(N.height == 4) {
					limitDistance(N);
				}
			}
		}else if (balance(N) == -1) {
			AVLNode N_copy = N;
			AVLNode sucN = successor(N);
			AVLNode sucParent = parentNodeOf(sucN);
			if (isLeftChild) {
				sucParent.left = sucN.right;
			}else {
				sucParent.right = sucN.right;
			}
			predecessor(N).right = N;
			sucN.right = N.right;
			sucN.left = N.left;
			N.right = null;
			N.left = null;
			N = sucN;
			if (N_copy == root) {
				root = N;
			}else {
				AVLNode parent = parentNodeOf(N_copy);
				if (isLeftChild) {
					parent.left = N;
				}else {
					parent.right = N;
				}
				
			}
			treeUpdateHeight(root);
			rotate(N.left);
			rotate(N.right);
			treeUpdateHeight(root);
			if (root.height - 1 > maxDistance) {
				if (N.height > 4) {
					limitDistance(N.left);
					limitDistance(N.right);
				}else if(N.height == 4) {
					limitDistance(N);
				}
			}
		}
	}
	//--------------------------------------------------------------
	//--------------------------------------------------------------
	//Operations
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




