import java.awt.Point;
import java.util.ArrayList;

public class ControlMoves {
	
	//Constructor
	public ControlMoves() {
		
	}
    
	//this method will return a list of the available moves for player
	public ArrayList<Point> MovesToPlay(int[][] board, int player) {
		
		//list of available moves
		ArrayList<Point> moves = new ArrayList<>();
		
		//check every cell of the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            	
	            //if the cell is not empty, break loop and go to the next cell
	            if(board[i][j] != 0)
	            	continue;
	            	
	           //now that the cell is empty, look around
	            	
	           //first look up
	           if(lookUp(player,board,i,j)) {
	        	   //this cell is a possible move, add it to moves and go to the next cell
	        	   moves.add(new Point(i,j));
	        	   continue;
	           }
	           //second look down
	           if(lookDown(player,board,i,j)) {
	        	   //this cell is a possible move, add it to moves and go to the next cell
	        	   moves.add(new Point(i,j));
	        	   continue;
	           }
	           //third look right
	           if(lookRight(player,board,i,j)) {
	        	   //this cell is a possible move, add it to moves and go to the next cell
	        	   moves.add(new Point(i,j));
	        	   continue;
	           }
	           //fourth look left
	           if(lookLeft(player,board,i,j)) {
	        	   //this cell is a possible move, add it to moves and go to the next cell
	        	   moves.add(new Point(i,j));
	        	   continue;
	           }
	           //fifth look up right
	           if(lookUpRight(player,board,i,j)) {
	        	   //this cell is a possible move, add it to moves and go to the next cell
	        	   moves.add(new Point(i,j));
	        	   continue;
	           }
	           //sixth look down right
	           if(lookDownRight(player,board,i,j)) {
	        	   //this cell is a possible move, add it to moves and go to the next cell
	        	   moves.add(new Point(i,j));
	        	   continue;
	           }
	           //seventh look down left
	           if(lookDownLeft(player,board,i,j)) {
	        	   //this cell is a possible move, add it to moves and go to the next cell
	        	   moves.add(new Point(i,j));
	        	   continue;
	           }
	           //eighth look up left
	           if(lookUpLeft(player,board,i,j)) {
	        	   //this cell is a possible move, add it to moves and go to the next cell
	        	   moves.add(new Point(i,j));
	        	   continue;
	           }
	           
	         //if you made it up to here, there is no possible moves so go check another cell
            }
        }    
		
		return moves;	
	}
	
	//this method changes disks between players
	public int[][] changePoints(int[][] board, Point point, int player){
		
		//put the player's disk on the board
		board[point.x][point.y] = player;
		
		int i;
		int j;
		
		//check every cell of the neighborhood to change what you have to

		//first look up
        if(lookUp(player,board,point.x,point.y)) {
        	i = point.x - 1;
        	while(board[i][point.y] != player) {
				board[i][point.y] = player;
				i--;
			}   
        }
        //second look down
        if(lookDown(player,board,point.x,point.y)) {
        	i = point.x + 1;
        	while(board[i][point.y] != player) {
				board[i][point.y] = player;
				i++;
			}  	   
        }
        //third look right
        if(lookRight(player,board,point.x,point.y)) {
        	j = point.y + 1;
        	while(board[point.x][j] != player) {
				board[point.x][j] = player;
				j++;
			}
        }
        //fourth look left
        if(lookLeft(player,board,point.x,point.y)) {
        	j = point.y - 1;
        	while(board[point.x][j] != player) {
				board[point.x][j] = player;
				j--;
			}	   
        }
        //fifth look up right
        if(lookUpRight(player,board,point.x,point.y)) {
        	i = point.x - 1;
        	j = point.y + 1;
        	while(board[i][j] != player) {
				board[i][j] = player;
				i--;
				j++;
			}  	   
        }
        //sixth look down right
        if(lookDownRight(player,board,point.x,point.y)) {
        	i = point.x + 1;
        	j = point.y + 1;
        	while(board[i][j] != player) {
				board[i][j] = player;
				i++;
				j++;
			}
     	  
        }
        //seventh look down left
        if(lookDownLeft(player,board,point.x,point.y)) {
        	i = point.x + 1;
        	j = point.y - 1;
        	while(board[i][j] != player) {
				board[i][j] = player;
				i++;
				j--;
			}
        }
        //eighth look up left
        if(lookUpLeft(player,board,point.x,point.y)) {
        	i = point.x - 1;
        	j = point.y - 1;
        	while(board[i][j] != player) {
				board[i][j] = player;
				i--;
				j--;
			}
        }
		
		return board;				
	}
	
	//this method based on, who is the current player, finds his opponent
	public int findOpponent(int player) {
		
		if(player==1) {
			return 2;
		}
		else {
			return 1;
		}
	
	}
	
	public boolean lookUp(int player,int board[][],int i, int j) {
		int opponent = findOpponent(player);
		int up = i - 1;
    	if(up > 0 && board[up][j] == opponent) {
    		
    		while(up >= 0 && board[up][j] == opponent){
                up--;                     
            }
    		
    		if(up >= 0 && board[up][j] == player) {
    			//this cell is a possible move
    			return true;
    		}
    	}
    	return false;
	}
	
	public boolean lookDown(int player,int board[][],int i, int j) {
		int opponent = findOpponent(player);
    	int down = i + 1;
    	if(down < 7 && board[down][j] == opponent) {
    		
    		while(down <= 7 && board[down][j] == opponent){
                down++;                     
            }
    		
    		if(down <= 7 && board[down][j] == player) {
    			//this cell is a possible move
    			return true;
    		}
    	}
		return false;
	}
	
	public boolean lookRight(int player,int board[][],int i, int j) {
		int opponent = findOpponent(player);
		int right = j + 1;
    	if(right < 7 && board[i][right] == opponent) {
    		
    		while(right <= 7 && board[i][right] == opponent){
                right++;                     
            }
    		
    		if(right <= 7 && board[i][right] == player) {
    			//this cell is a possible move
    			return true;
    		}          		
    	}
		return false;			
	}
	
	public boolean lookLeft(int player,int board[][],int i, int j) {
		int opponent = findOpponent(player);
		int left = j - 1;
    	if(left > 0 && board[i][left] == opponent) {
    		
    		while(left >= 0 && board[i][left] == opponent){
                left--;                     
            }
    		
    		if(left >= 0 && board[i][left] == player) {
    			//this cell is a possible move
    			return true;
    		}           		
    	}
		return false;			
	}
	
	public boolean lookUpRight(int player,int board[][],int i, int j) {
		int opponent = findOpponent(player);
		int up = i - 1;
    	int right = j + 1;
    	if(right < 7 && up > 0 && board[up][right] == opponent) {
    		
    		while(right <= 7 && up >= 0 && board[up][right] == opponent){
                up--;
                right++;
            }
    		
    		if(right <= 7 && up >= 0 && board[up][right] == player) {
    			//this cell is a possible move
    			return true;
    		}         		
    	}
    	return false;
	}
	
	public boolean lookDownRight(int player,int board[][],int i, int j) {
		int opponent = findOpponent(player);
		int down = i + 1;
    	int right = j + 1;
    	if(right < 7 && down < 7 && board[down][right] == opponent) {
    		
    		while(right <= 7 && down <= 7 && board[down][right] == opponent){
                down++;
                right++;
            }
    		
    		if(right <= 7 && down <= 7 && board[down][right] == player) {
    			//this cell is a possible move
    			return true;
    		}           		
    	}
    	return false;
	}
	
	public boolean lookDownLeft(int player,int board[][],int i, int j) {
		int opponent = findOpponent(player);
		int down = i + 1;
    	int left = j - 1;
    	if(left > 0 && down < 7 && board[down][left] == opponent) {
    		
    		while(left >= 0 && down <= 7 && board[down][left] == opponent){
                down++;
                left--;
            }
    		
    		if(left >= 0 && down <= 7 && board[down][left] == player) {
    			//this cell is a possible move
    			return true;
    		}          		
    	}
    	return false;
	}
	
	public boolean lookUpLeft(int player,int board[][],int i, int j) {
		int opponent = findOpponent(player);
		int up = i - 1;
    	int left = j - 1;
    	if(left > 0 && up > 0 && board[up][left] == opponent) {
    		
    		while(left >= 0 && up >= 0 && board[up][left] == opponent){
                up--;
                left--;
            }
    		
    		if(left >= 0 && up >= 0 && board[up][left] == player) {
    			//this cell is a possible move
    			return true;
    		}       		
    	}
    	return false;
	}

}
