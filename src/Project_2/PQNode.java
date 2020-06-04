package Project_2;

public class PQNode {

	Huffman_Node data;
	PQNode left;
	PQNode right;
	PQNode parent;
	
	public PQNode(Huffman_Node node) {
		this.data = node;
	}
	
	public PQNode(PQNode l, PQNode r) {
		left = l;
		right = r;
	}
	
	public boolean isLeaf() {
		return (left == null && right == null);
	}
	
	
}
