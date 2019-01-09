//Author: Gyeongwon Lee
//Date Created: July 14/2017
//This program lets the user play a generic Bullet Dodger game aka 죽림고수
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;
public class SimpleShape extends JFrame
{
    static Random rand = new Random();
    public static void main(String args[])
    {
        new SimpleShape();
        System.out.println("Hello, and welcome to Gyeongwon Lee's Bullet Dodger game!");
        System.out.println("Avoid touching the bullets that come flying at you from all over the place.");
        System.out.println("You can use your keyboard to avoid the bullets.");
        System.out.println("Good luck. May god ever be in your favor.");
    }
    public SimpleShape()
    {
        this.setSize(700,700);
        this.setTitle("Bullet Dodger Game created by Gyeongwon Lee");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new PaintSurface(), BorderLayout.CENTER);
        this.setVisible(true);
    }
    private class PaintSurface extends JComponent
    {
        public void paint(Graphics g)
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
                
                
            Shape s = new Ellipse2D.Float(20,50,250,150);
            g2.setPaint(Color.BLACK);
            g2.draw(s);
            
        }
    }
}
