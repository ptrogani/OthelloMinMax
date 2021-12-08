
public class ValueOfState {
	
	ControlMoves cm = new ControlMoves();
	
	private int[][] board;
	private int pcId;
	private int value;
	private int player;
	private int total;
	private int pcPieces;
	private int playerPieces;
	
	public ValueOfState(int[][] board,int pcId) {
		this.board = board;
		this.pcId = pcId;
		this.value = 0;
		
		if(this.pcId == 1) {
			this.player = 2;
		}
		else {
			this.player = 1;
		}
		
		this.total = 4;
	}
	
	public void valueByPosition() {
		
		int[][] b = {
	            {100, -5, 10 , 5 ,5 , 10, -5, 100},
	            {-5 ,-5 , -1, -1, -1, -1, -5, -5},
	            {10 , -1,20 , 5 , 5 ,20 , -1, 10},
	            { 5 , -1, 5 ,10 ,10 , 5 , -1, 5 },
	            { 5 , -1, 5 ,10 ,10 , 5 , -1, 5 },
	            {10 , -1,20 , 5 , 5 ,20 , -1, 10},
	            {-5 , -5, -1, -1, -1, -1, -5, -5},
	            {100, -5, 10 , 5 , 5 ,10 , -5, 100}
	            };
		
		for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	        	if(this.board[i][j] == this.pcId) {
	        		this.value = this.value + b[i][j];
	        		this.value++;
	        		
	        		this.pcPieces++;
	        		this.total++;
	        	}
	        	else if(this.board[i][j] == this.player) {
	        		this.value = this.value - b[i][j];
	        		this.value--;
	        		
	        		this.playerPieces++;
	        		this.total++;
	        	}
	        	
	        }
		}
	}
	
	public void valueByVictory() {
		
		if(this.total == 64) {
			if(this.pcPieces > this.playerPieces) {
				this.value = this.value + 300;
			}
			else if(this.pcPieces < this.playerPieces) {
				this.value = this.value - 300;
			}
		}
		
		if(this.playerPieces == 0) {
			this.value = this.value + 300;
		}
		
		if(this.pcPieces == 0) {
			this.value = this.value - 300;
		}
		
	}
	

	public int returnValue() {
		
		valueByPosition();
		valueByVictory();
		
		return this.value;
	}
}
