import java.awt.Point;
import java.util.ArrayList;

public class Node {
	
	static int id = 0;
	private int idNode;
	
	ControlMoves cm = new ControlMoves();
	
	private Point nextMove;
	
	private Node parent;
	
	private boolean leaf = false;
	private boolean root = false;
	
	//this list is about possible moves
	private ArrayList<Point> kids;
	
	private int value;
	private int a = -1000;
	private int b = 1000;
	
	private int depth;
	
	private int board[][];
	
	private int pcId;
	
	private int first = 0;
	
	//Constructor for root node
	public Node(int pcId,int board[][]) {
		this.parent = null;
		board(board);
		this.pcId = pcId;
		this.depth = 0;
		this.root = true;
			
		findKids();	
	}
	
	//Constructor for nodes
	public Node(Node parent,int board[][],int pcId,int depth) {
		this.parent = parent;
		board(board);
		this.pcId = pcId;
		this.depth = depth;
		
		findKids();
	}
	
	//Constructor for leafs
	public Node(Node parent,int board[][],int pcId,int depth,boolean leaf) {
		this.parent = parent;
		board(board);
		this.pcId = pcId;
		this.depth = depth;
		this.leaf = leaf;
		
		findValue();		
	}
	
	public void board(int[][] board) {
		this.board = new int[8][8];
	
		for(int i = 0;i<8;i++) {
			for(int j=0;j<8;j++) {
				this.board[i][j] = board[i][j];
			}
		}
		
	}
	
	//if it is a leaf
	public boolean isLeaf() {
		return leaf;	
	}
	
	//if it is the root
	public boolean isRoot() {
		return root;
	}
	
	//finds if there are more kids - possible moves to explore
	public boolean hasKids() {
		
		if(!this.kids.isEmpty())
			return true;
		
		return false;	
	}
	
	//finds the available moves in this state - so the possible kids of the node
	public void findKids(){
		this.kids=cm.MovesToPlay(this.board, whoPlays());
		
		//if this node has no possible moves then is a leaf
		if(this.kids.isEmpty()) {
			this.leaf=true;
			findValue();
		}
		
	}
	
	public Point nextKid() {	
		return this.kids.get(this.first);
	}
	
	public void deleteKid() {
		this.kids.remove(this.first);
		
		if(this.kids.isEmpty())
			this.leaf = true;
	}
	
	//returns true if is a Max node or false if is a Min node
	public boolean isMax() {
		if(this.depth%2 == 0)
			return true;
		
		return false;
	}
	
	//finds whose turn is and which is his color (black=1, white=2)
	public int whoPlays() {
		if(isMax()) {
			return pcId;
		}
		else {
			if(pcId == 1) {
				return 2;
			}
			else {
				return 1;
			}
		}
	}
	
	//changes value -depended on next move- and if it is about root keep track of the move
	public void newValue(int value) {
		if(isMax()) {
			if(value > this.a) {
				this.a = value;
				this.value = value;
				if(this.root) {
					this.nextMove = this.kids.get(this.first);
				}
			}
		}
		else {
			if(value < this.b) {
				this.b = value;
				this.value = value;
			}
		}
	}
	
	//calculates the value of leaf -based on the number of disks
		public void findValue() {
			this.value = 0;
			ValueOfState va = new ValueOfState(this.board,this.pcId);
			
			this.value = va.returnValue();
		}
	
	//setters and getters
	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public Point getNextMove() {
		return nextMove;
	}

	public void setNextMove(Point nextMove) {
		this.nextMove = nextMove;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}
	
}
