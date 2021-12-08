import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;

public class Cells extends JLabel implements MouseListener{

    int i;
    int j;

    JPanel parent;
    OtheloPanel op;

    public int highlight = 0;

    public String text = "";

    public Cells(OtheloPanel op,JPanel parent,int i,int j){
    	
    	this.op = op;
        this.parent = parent;
        this.i = i;
        this.j = j;
        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {

        int margin_left = this.getWidth() / 10;
        int margin_top = this.getHeight() / 10;

        //draw highlight
        if(highlight == 1) {
            g.setColor(new Color(138, 177, 62));
            g.fillRect(0,0,this.getWidth(),this.getHeight());
            g.setColor(parent.getBackground());
            g.fillRect(4,4,this.getWidth()-8,this.getHeight()-8);
        }else if(highlight == 2){
            g.setColor(new Color(177, 158, 70));
            g.fillRect(0,0,this.getWidth(),this.getHeight());
            g.setColor(parent.getBackground());
            g.fillRect(4,4,this.getWidth()-8,this.getHeight()-8);
        }else if(highlight == 10){
            g.setColor(new Color(177, 43, 71));
            g.fillRect(0,0,this.getWidth(),this.getHeight());
        }

        //draw border
        g.setColor(Color.BLACK);
        g.drawRect(0,0,this.getWidth(),this.getHeight());

        //draw piece
        int value = op.getBoard(i,j);
        if(value == 1){
            g.setColor(new Color(44, 62, 80));
            g.fillOval(margin_left,margin_top,this.getWidth()-2*margin_left,this.getHeight()-2*margin_top);
        }
        else if(value == 2) {
            g.setColor(new Color(236, 240, 241));
            g.fillOval(margin_left,margin_top,this.getWidth()-2*margin_left,this.getHeight()-2*margin_top);
        }

        if(!text.isEmpty()){
            g.setColor(new Color(255, 255, 0));
            Font font = g.getFont();
            Font nfont = new Font(font.getName(),Font.PLAIN,30);
            g.setFont(nfont);

            drawStringInCenterOfRectangle(g,0,0,this.getWidth(),this.getHeight(),text);
        }

        super.paint(g);
    }


    //Extension function to ease drawing text
    public void drawStringInCenterOfRectangle(Graphics g,int x,int y,int w,int h,String text){
        Graphics2D g2 = (Graphics2D) g;
        Font bfont = g2.getFont();
        FontRenderContext context = g2.getFontRenderContext();
        g2.setFont(bfont);
        int textWidth = (int) bfont.getStringBounds(text, context).getWidth();
        LineMetrics ln = bfont.getLineMetrics(text, context);
        int textHeight = (int) (ln.getAscent() + ln.getDescent());
        int tx = x+(w - textWidth)/2;
        int ty = (int)((y + h + textHeight)/2 - ln.getDescent());
        g2.drawString(text, (int)tx, (int)ty);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
   
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	
        op.handleClick(i,j);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
