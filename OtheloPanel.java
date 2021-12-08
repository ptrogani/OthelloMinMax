import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class OtheloPanel extends JPanel implements MouseListener{
	
	JLabel score1;
    JLabel score2;

    
    private int[][] board;
	Cells[][] cells;
    
    private boolean awaitForClick = false;
    
    private int player;
    private String name;
    private int counterPlayer;
    private int pc;
    private int counterPc;
    private int depth;
    private int turn;
    
    Timer PC_handlerTimer;
    
    private int totalPieces;
	private int black;
	private int white;
	
	JPanel sidebar = new JPanel();
	
	//list of available moves
	ArrayList<Point> moves = new ArrayList<>();
	
    ControlMoves cm = new ControlMoves();
 
    //Constructor
	public OtheloPanel(){
		
		//these methods use DialogPanels to take user's choices
		name();
		turns();
		level();
			
        this.setBackground(Color.yellow);
        this.setLayout(new BorderLayout());

        JPanel otheloBoard = new JPanel();
        otheloBoard.setLayout(new GridLayout(8,8));
        otheloBoard.setPreferredSize(new Dimension(700,700));
        otheloBoard.setBackground(new Color(30, 132, 73));
       
        resetBoard();
    
        cells = new Cells[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new Cells(this,otheloBoard,i,j);
                otheloBoard.add(cells[i][j]);
            }
        }
        
        
        
        sidebar.setLayout(new BoxLayout(sidebar,BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(189, 195, 199));
        sidebar.setPreferredSize(new Dimension(0,90));

        score1 = new JLabel("  ALGORITHM");
        score2 = new JLabel("  "+this.name);
        
        sidebar.add(score1);
        sidebar.add(new JLabel(" "));
        sidebar.add(new JLabel("        VS"));
        sidebar.add(new JLabel(" "));
        sidebar.add(score2);
        
        this.add(sidebar,BorderLayout.SOUTH);
        this.add(otheloBoard);
        
      //AI Handler Timer (to unfreeze gui)
        PC_handlerTimer = new Timer(700,(ActionEvent e) -> {
            pcPlays();
            PC_handlerTimer.stop();
            changePlayers();
        });
       
        changePlayers();
        
	}
	
	//this method initialize othelo game
	public void resetBoard(){
		
        this.board = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                setBoard(i,j,0);
            }
        }       
       setBoard(3,3,2);
       setBoard(3,4,1);
       setBoard(4,3,1);
       setBoard(4,4,2); 
       
       //first player
       setTurn(1);
       
       setTotalPieces(4);
       setBlack(2);
       setWhite(2);
       
       this.counterPlayer = 2;
       this.counterPc = 2;
    }
	
	//Control Players -change between them
	public void changePlayers() {	
		
		if(this.totalPieces<64) { 
			//check moves for this player
			moves = cm.MovesToPlay(this.board, this.turn);
			if(!moves.isEmpty()) {
				
				System.out.println("player : "+this.turn);
				highlights(moves,true);
				if(this.turn==pc) {
					
					PC_handlerTimer.start();
					
				}else {
					this.awaitForClick = true;
				}				
			}
			//if he has no moves check for the other player
			else {		
				changeTurns();
				moves = cm.MovesToPlay(this.board, this.turn);			
				if(!moves.isEmpty()) {
					System.out.println("next turn!");
					highlights(moves,true);
					if(this.turn==pc) {
						PC_handlerTimer.start();
					}else {
						this.awaitForClick = true;
					}
					
				}
				else {
					//if you are here the game is over
					findWinner();
					endGame();
				}
			}	
		}else {
			findWinner();
			endGame();
		}
	}
	
	
	//change turns
	public void changeTurns() {
		
		if(this.turn==1) {
			setTurn(2);
		}
		else if(this.turn==2){
			setTurn(1);
		}
	}
	
	public void pcPlays() {
		Point choose;
		
		MinMaxTree tree = new MinMaxTree(this.board,this.depth,this.pc);
		choose = tree.returnMove();
			
		this.totalPieces++;
    	this.board=cm.changePoints(this.board,choose,this.turn);
   
        highlights(this.moves,false);
        repaint();
        changeTurns();
        //changePlayers();
	}
	
	 public void handleClick(int i,int j){
	
		 Point click = new Point(i,j);

	    if(this.awaitForClick && validMove(click)) {
	    	this.awaitForClick = false;

	        this.totalPieces++;
	    	this.board=cm.changePoints(this.board,click,this.turn);
	   
	        highlights(this.moves,false);
	        repaint();
	        changeTurns();
	        changePlayers();
	     }
	}
	 
	 //check if the users click is a valid move
	 public boolean validMove(Point click) {
		 
		 int i = 0;
		 while(i < this.moves.size()) {			
			 if(this.moves.get(i).equals(click))
				 return true;
			 i++;
		 }	 
		 return false;
	 }

    //update highlights on possible moves
    public void highlights(ArrayList<Point> moves,boolean on){
    	if(on) {
	    	int i = 0;
	    	while(i < moves.size()) {
	    		cells[moves.get(i).x][moves.get(i).y].highlight = 1;
	    		i++;
	    	}
    	}
    	else {
    		for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                	cells[i][j].highlight = 0;
                }
    		}
    	}
    	repaint();
    }
    
    public void printMoves() {
    	 int i = 0;
		 while(i < this.moves.size()) {	
			 i++;
		 }	
    }
    
    public void findWinner() {
    	this.counterPc=0;
    	this.counterPlayer=0;
    	for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            	if(this.board[i][j]==player) {
            		this.counterPlayer++;
            	}
            	else if(this.board[i][j]==pc) {
            		this.counterPc++;
            	}
            }
    	}
    }
    
    public void name() {
    	
    	this.name = (String)JOptionPane.showInputDialog(
                null,
                "Type your name",
                "Introduce yourself",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "Player");
    	
    }
    
    public void level() {
    	
    	Object[] possibilities = {"1","2","3","4","5","6","7","8"};
		this.depth = Integer.parseInt((String) JOptionPane.showInputDialog(
		                    null,
		                    "Choose level",
		                    "How much good are you?",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    possibilities,
		                    "7"));
    	
    }
    
    public void turns() {
    	
    	Object[] options = {"First?!",
        "Second?!"};
		this.player = JOptionPane.showOptionDialog(null,
		"Would you like to play ...",
		"Black or White",
		JOptionPane.YES_NO_OPTION,
		JOptionPane.QUESTION_MESSAGE,
		null,     //do not use a custom Icon
		options,  //the titles of buttons
		options[0]); //default button title
		
		if(this.player == 0) {
			this.player = 1;
			this.pc = 2;
		}else {
			this.player = 2;
			this.pc = 1;
		}
    	
    }
    
 public void endGame() {
	 
	 JOptionPane.showMessageDialog(null,
		"Algorithm : "+this.counterPc+" vs "+this.name+" : "+this.counterPlayer,
		"Game over!",
		JOptionPane.WARNING_MESSAGE);
		
    }
    
    @Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		handleClick(getX(),getY());
		
	}
    
    //setters and getters
    
    public int getBoard(int i,int j){
        return this.board[i][j];
    }

    public void setBoard(int i,int j,int value){
        this.board[i][j]=value;
    }

	public int getTotalPieces() {
		return totalPieces;
	}

	public void setTotalPieces(int totalPieces) {
		this.totalPieces = totalPieces;
	}

	public int getBlack() {
		return black;
	}

	public void setBlack(int black) {
		this.black = black;
	}

	public int getWhite() {
		return white;
	}

	public void setWhite(int white) {
		this.white = white;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
