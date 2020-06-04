package Project_2;

public class PriorityQueue {

	PQNode root;
	int size;
	
	public PriorityQueue() {
		root = null;
		size = 0;
	}
	
	public void insert(Huffman_Node node) {
		PQNode hnode = new PQNode(node);
		insert(hnode);
	}
	
	public void insert(PQNode n) {
			PQNode node = Node((size-1)/2);
			String path = path(size);

			if(size == 0) {
				root = n;
				size++;
			}else if(path.charAt(path.length()-1) == 'L') {
				node.left = n;
				size++;
				minHeapify(node.left);
			} else {
				node.right = n;
				size++;
				minHeapify(node.right);
			}
			
	}

	
	public Huffman_Node removeMin() {
		if(size != 0) {
			Huffman_Node min= root.data;
			root.data = Node(size - 1).data;
			
			String path = path(size-1);
			PQNode plastnode = Node((size - 2)/2);
			if(size>1) {
				if(path.charAt(path.length()-1) == 'L') {
					plastnode.left = null;
				} else {
					plastnode.right = null;
				}
			}
			size--;
			downHeapify(root);
			
			return min;
		}
		
		return null;
		
	}
	
	public void downHeapify(PQNode nroot) {
		
		PQNode lchild = nroot.left;
		PQNode rchild = nroot.right;
		PQNode minChild;
		if(rchild == null) {
			if(lchild == null) {
				return;
			} else {
				minChild = lchild;
			}
		} else if(lchild.data.freq <= rchild.data.freq) {
			minChild = lchild;
		} else {
			minChild = rchild;
		}
		
		if(nroot.data.freq > minChild.data.freq) {
			swap(nroot, minChild);
			downHeapify(nroot);
		}
		
	}
	
	public void minHeapify(PQNode node) {
		int i = size;
		PQNode pnode = Node((i-2)/2);
		
		while(i != 0 && pnode.data.freq > node.data.freq) {
			swap(pnode, node);
			i = (i-2) /2;
			node = pnode;
			pnode = Node((i-2)/2);
		}
	}
	
	private void swap(PQNode a, PQNode b) {
	    Huffman_Node temp = a.data;
	    a.data = b.data;
	    b.data = temp;
	  }
	
	
	public String path(int position) {
	    if (position < 1)
	      return null;
	    position++;
	    String answer = "";
	    while (position > 1) {
	      if (position % 2 == 0) { // left branch
	        answer = "L" + answer; // prepend
	      } else { // right branch
	        answer = "R" + answer;
	      }
	      position /= 2;
	    }
	    return answer;
	  }
	
	public PQNode Node(int position) {
	    if (position < 0)
	      return null;
	    if (position == 0)
	      return root;
	    //position--;
	    String path = path(position);
	    PQNode iterator = root;
	    for (int i = 0; i < path.length(); i++) {
	      if (path.charAt(i) == 'L') {
	        iterator = iterator.left;
	      } else {
	        iterator = iterator.right;
	      }
	    }
	    return iterator;
	  }
	
	public void printHeap() {
	    String[] s = { "Heap(inorder)=" };
	    recursivePrintHeap(root, s);
	    System.out.println(s[0]); // array is passed by reference, string is not
	  }

	  private void recursivePrintHeap(PQNode n, String[] s) {
	    // prints the heap in order
	    if (n == null)
	      return;
	    recursivePrintHeap(n.left, s);
	    s[0] += "(" + n.data.ch + ":" + n.data.freq+ ")";
	    recursivePrintHeap(n.right, s);
	  }
}
