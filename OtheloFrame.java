import java.awt.Color;
import javax.swing.*;

public class OtheloFrame extends JFrame {
	
	public OtheloFrame() {
		
		OtheloPanel othelop = new OtheloPanel();
		this.add(othelop);
        this.setTitle("Othelo");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(700,700);
        getContentPane().setBackground(Color.YELLOW);
	}

	public static void main(String[] args) {
		new OtheloFrame();
	}

}
