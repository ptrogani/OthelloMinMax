import java.awt.Point;
import java.util.concurrent.TimeUnit;

public class MinMaxTree {
	
	ControlMoves cm = new ControlMoves();
	
	private Node root;
	private int depth;
	private int pcId;
	private int currentTurn;
	
	private Point move;
	
	private int board[][];
	
	private Node currentN;
	private int currentDepth;
	
	//Constructor
	public MinMaxTree(int board[][],int depth,int pcId) {
		board(board);			
		this.depth = depth;
		this.pcId = pcId;
		
		this.root = new Node(this.pcId, this.board);
		
		this.currentTurn = pcId;
		this.currentN = this.root;
		this.currentDepth = 0;
		
		treeHandler();
		
	}
	public void board(int[][] board) {
		this.board = new int[8][8];
		
		for(int i = 0;i<8;i++) {
			for(int j=0;j<8;j++) {
				this.board[i][j] = board[i][j];
			}
		}
		
	}
	
	public void treeHandler() {
		while(!(this.currentN.isLeaf() && this.currentN.isRoot())) {
			//go up
			if(this.currentN.isLeaf()) {		
					deleteNode();
			}
			//go down
			else {
				if(this.currentN.isRoot()) {
					insertNode();
				}
				else {
					//AB cut
					if(cutAB()) {
						Node.id++;
						deleteNode();
					}
					else {
						insertNode();
					}
				}
			}
		}
	}
	
	//go down to the tree
	public void insertNode() {
		
		this.currentDepth = this.currentN.getDepth()+1;
		this.currentTurn = this.currentN.whoPlays();
		
		//create a leaf
		if(this.currentDepth==this.depth) {
			
			int[][] tmp = new int[8][8];
			for (int i=0;i<8;i++) {
				for (int j=0;j<8;j++) {
					tmp[i][j] = this.currentN.getBoard()[i][j];
				}
			}
			tmp = cm.changePoints(tmp,this.currentN.nextKid(),this.currentTurn);
			//print(b);
			this.currentN = new Node(this.currentN,tmp,this.pcId,this.currentDepth,true);
		}
		//create a node
		else {
			
			int[][] tmp = new int[8][8];
			for (int i=0;i<8;i++) {
				for (int j=0;j<8;j++) {
					tmp[i][j] = this.currentN.getBoard()[i][j];
				}
			}
			tmp = cm.changePoints(tmp,this.currentN.nextKid(),this.currentTurn);
			//print(b);
			
			this.currentN = new Node(this.currentN,tmp,this.pcId,this.currentDepth);
		}

	}
	
	//dont create the nodes based on AB cut
	public boolean cutAB() {
		//if node is min
		if(!this.currentN.isMax()) {
			if(this.currentN.getParent().getA()>=this.currentN.getB()) {
				//cutAB
				return true;
			}
			
		}
		//if node is max
		else {
			if(this.currentN.getParent().getB()<=this.currentN.getA()) {
				//cutAB
				return true;
			}
			
		}
		return false;
	}
	
	//go up to the tree
	public void deleteNode() {
		Node temp;
		
		this.currentN.getParent().newValue(this.currentN.getValue());
		
		//delete node
		temp = this.currentN.getParent();
		this.currentN.setParent(null);
		this.currentN = temp;
					
		if(this.currentN.hasKids()) {
			this.currentN.deleteKid();
		}
		
	}
	
	//the minMax has ended and now returns the chosen move
	public Point returnMove() {
		return this.currentN.getNextMove();
	}
	
	public void print(int[][] b) {
		System.out.println("Node depth "+this.currentDepth);
		for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	        	
	        		
	        		System.out.print(" "+b[i][j]);
	        	
	        	
	        }
	        System.out.println();
		}
		System.out.println();
	}

}


