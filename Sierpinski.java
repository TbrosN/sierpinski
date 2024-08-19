import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
public class Sierpinski extends JFrame implements ActionListener, MouseListener
{
    private ArrayList<Point> points; //the points in the original polygon
    private int t;
	  private Timer tmr;
    private double angle;
    private int x; // of the center
    private int y; // "           "
    private int r; // of the circumcircle
    private boolean stopped;
    private boolean finished;

    static final double pi = Math.PI;
    static final int DELAY = 500;
    static final int width = 750;
    static final int height = 750;

    public Sierpinski(int n, int x, int y, int r) {

      setBackground(Color.MAGENTA);

	   t=0;
      angle = 2*pi / n;
      this.x = x;
      this.y = y;
      this.r = r;
      points = new ArrayList<Point>();
      getPoints(Math.PI/2-angle/2);

      stopped = false;
      finished = false;
  		tmr = new Timer(DELAY, this);
		tmr.start();
      addMouseListener(this);
  }

  // Generates the coordinates of a regular n-gon and adds them to points
  public void getPoints(double a)
  {
      if (a >= 2*pi + Math.PI/2-angle/2) return;
      points.add( new Point( x + (int)(r*Math.cos(a)), y + (int)(r*Math.sin(a)) ) );
      getPoints(a + angle);
  }

 /**
   *  Processes timer events
   */

  	public void actionPerformed(ActionEvent e)
	{
		if (!stopped) t++;
      if (t > 8) {
         t = 0;
         finished = true;
      }
      repaint();
    }
    
    public boolean getFinished() {
      return finished;
    }

/**
 * Midpoint: returns the midpoint of p0 and p1
 */
     public Point midpoint (Point p0, Point p1)
       {
		   return new Point((p0.x+p1.x)/2,(p0.y+p1.y)/2);
       }

    public void drawPoly (ArrayList<Point> p, Graphics g)
    {
      for (int i = 0; i < p.size() - 1; i++)
        g.drawLine(p.get(i).x, p.get(i).y, p.get(i+1).x, p.get(i+1).y);
      g.drawLine(p.get(p.size() - 1).x, p.get(p.size() - 1).y, p.get(0).x, p.get(0).y);
    }

   public void paint(Graphics g)
   {
      if (t == 0) g.clearRect(0,0,width,height);
   	sierp(points,t,g);
   }

    public void sierp (ArrayList<Point> p, int numTimes, Graphics g)
    {
		// check base case, if numTimes reaches zero return
		if (numTimes == 0) return;

		// draw this triangle
		drawPoly(p, g);

		// recursively call this method three times for the other three triangles
		// formed by each vertex with the midpoints of its adjacent sides
      ArrayList<Point> temp;
	   for (int i = 0; i < p.size(); i++)
      {
         temp = new ArrayList<Point>();
         for (int j = 0; j < p.size(); j++)
            temp.add( midpoint(p.get(i), p.get(j)) );
         sierp(temp, numTimes - 1, g);
      }
    }

  	public static void main(String[] args)
  	{
  		// Sierpinski prog = new Sierpinski(5, width/2, height/2, 2*width/5);
      for (int i = 3; i <= 7; i++) {
         Sierpinski prog = new Sierpinski(i, width/2, height/2, 2*width/5);
         prog.addWindowListener(new WindowAdapter()
                                 {
                                     public void windowClosing(WindowEvent e)
                                     {
                                         System.exit(0);
                                     }
                                 }
                                );
        prog.setSize(width, height);
        prog.setVisible(true);
        while (!prog.getFinished());
        prog.setVisible(false);
      }
  	}

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {
      if (stopped) stopped = false;
      else stopped = true;
      //System.out.println(stopped);
    }
}
