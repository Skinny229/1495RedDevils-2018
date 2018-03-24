package org.usfirst.frc.team1495.robot.pathgen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final double kInchPerPixels = 0.91;
	public static final double kPixelsPerInch = 1.10;
	public static final int kOffsetX = 10;
	public static final int kOffsetY = 10;
	public static final int kDimensionX = 379;
	public static final int kDimensionY = 416;
	
	public static void main(String[] args) {
		Main main = new Main();
		JFrame frame = new JFrame("1495 Path Generator");
		frame.setSize(new Dimension(500,700));
		main.setPreferredSize(new Dimension(500,700));
		//BufferedImage image = null;
		//try {
		//	image = ImageIO.read(new File("PowerUpHalfField.png"));
		//} catch (IOException e) {
		//	System.out.println("WHERE IS IT?");
		//}
		//frame.setContentPane(new ImagePanel(image));
		
		frame.getContentPane().add(main, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);

		Graphics g = frame.getGraphics();
		while (true) {
			g.setColor(Color.BLACK);
			g.drawLine(379, 0, 379, 416);
			g.drawLine(0, 416, 379, 416);
			g.setColor(Color.BLUE);
			Path path1 = new Path(
					new CubicSegment(new Point(0, 0), new Point(1, 1), new Point(1.5, 1.5), new Point(2, 1)),
					new LinearSegment(new Point(2, 1), new Point(2.5, 0.5)));
			for (int i = 0; i < 50; i++) {
				g.drawLine((int) (path1.segments[0].interpolate((double) (i) / 50).x * 100),
						kDimensionY - (int) (path1.segments[0].interpolate((double) (i) / 50).y * 100),
						(int) (path1.segments[0].interpolate((double) (i + 1) / 50).x * 100),
						kDimensionY - (int) (path1.segments[0].interpolate((double) (i + 1) / 50).y * 100));
				g.drawLine((int) (path1.segments[1].interpolate((double) (i) / 50).x * 100),
						kDimensionY - (int) (path1.segments[1].interpolate((double) (i) / 50).y * 100),
						(int) (path1.segments[1].interpolate((double) (i + 1) / 50).x * 100),
						kDimensionY - (int) (path1.segments[1].interpolate((double) (i + 1) / 50).y * 100));
			}
			g.setColor(Color.RED);
			Path path2 = new Path(new QuadraticSegment(new Point(0, 0), new Point(2.5, 4), new Point(3, 1)),
					new LinearSegment(new Point(3, 1), new Point(2.5, 4.3)));
			for (int i = 0; i < 50; i++) {
				g.drawLine((int) (path2.segments[0].interpolate((double) (i) / 50).x * 100),
						kDimensionY - (int) (path2.segments[0].interpolate((double) (i) / 50).y * 100),
						(int) (path2.segments[0].interpolate((double) (i + 1) / 50).x * 100),
						kDimensionY - (int) (path2.segments[0].interpolate((double) (i + 1) / 50).y * 100));
				g.drawLine((int) (path2.segments[1].interpolate((double) (i) / 50).x * 100),
						kDimensionY - (int) (path2.segments[1].interpolate((double) (i) / 50).y * 100),
						(int) (path2.segments[1].interpolate((double) (i + 1) / 50).x * 100),
						kDimensionY - (int) (path2.segments[1].interpolate((double) (i + 1) / 50).y * 100));
			}
			g.setColor(Color.GREEN);
			Path path3 = new Path(new LinearSegment(new Point(0, 0), new Point(1, 1)),
					new LinearSegment(new Point(1, 2), new Point(3, 3)));
			for (int i = 0; i < 50; i++) {
				g.drawLine((int) (path3.segments[0].interpolate((double) (i) / 50).x * 100),
						kDimensionY - (int) (path3.segments[0].interpolate((double) (i) / 50).y * 100),
						(int) (path3.segments[0].interpolate((double) (i + 1) / 50).x * 100),
						kDimensionY - (int) (path3.segments[0].interpolate((double) (i + 1) / 50).y * 100));
				g.drawLine((int) (path3.segments[1].interpolate((double) (i) / 50).x * 100),
						kDimensionY - (int) (path3.segments[1].interpolate((double) (i) / 50).y * 100),
						(int) (path3.segments[1].interpolate((double) (i + 1) / 50).x * 100),
						kDimensionY - (int) (path3.segments[1].interpolate((double) (i + 1) / 50).y * 100));
			}
		}
	}
}

class ImagePanel extends JComponent {
	private static final long serialVersionUID = 1L;
	private Image image;
    public ImagePanel(Image image) {
        this.image = image;
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}

