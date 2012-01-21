import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Affichage extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int ZOOM_FACTOR = 6;
	private static final int CROSS_SEMISIZE = 5;

	private Sample sample;

	public Color getNewColor(int index) {
		Color color = null;
		switch (index) {
		case 0:
			color = Color.blue;
			break;
		case 1:
			color = Color.red;
			break;
		case 2:
			color = Color.green;
			break;
		case 3:
			color = Color.magenta;
			break;
		case 4:
			color = Color.cyan;
			break;
		case 5:
			color = Color.orange;
			break;
		default:
			color = this.getNewColor(index - 6);
			break;
		}
		return color;

	}

	public void paintComponent(Graphics g) {
		clear(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawString("x : individus initiaux", 1, 15);
		g2d.drawString("+ : centre de gravité des groupes", 1, 30);

		for (Cluster cluster : this.sample.getClusters()) {

			// draw a cross for moving center of gravity
			float x = cluster.getCenter().getData(0);
			float y = cluster.getCenter().getData(1);
			g2d.setColor(Color.black);
			g2d.drawLine(ZOOM_FACTOR * (int) x - CROSS_SEMISIZE, ZOOM_FACTOR
					* (int) y, ZOOM_FACTOR * (int) x + CROSS_SEMISIZE,
					ZOOM_FACTOR * (int) y);
			g2d.drawLine(ZOOM_FACTOR * (int) x, ZOOM_FACTOR * (int) y
					- CROSS_SEMISIZE, ZOOM_FACTOR * (int) x, ZOOM_FACTOR
					* (int) y + CROSS_SEMISIZE);

			// draw a diagonal cross for initial chosen individuals
			x = cluster.getInitialCenter().getData(0);
			y = cluster.getInitialCenter().getData(1);
			g2d.drawLine(ZOOM_FACTOR * (int) x - CROSS_SEMISIZE, ZOOM_FACTOR
					* (int) y - CROSS_SEMISIZE, ZOOM_FACTOR * (int) x
					+ CROSS_SEMISIZE, ZOOM_FACTOR * (int) y + CROSS_SEMISIZE);
			g2d.drawLine(ZOOM_FACTOR * (int) x + CROSS_SEMISIZE, ZOOM_FACTOR
					* (int) y - CROSS_SEMISIZE, ZOOM_FACTOR * (int) x
					- CROSS_SEMISIZE, ZOOM_FACTOR * (int) y + CROSS_SEMISIZE);

			for (Individual indiv : cluster) {
				x = indiv.getData(0);
				y = indiv.getData(1);
				g2d.setColor(cluster.getColor());
				g2d.drawLine(ZOOM_FACTOR * (int) x, ZOOM_FACTOR * (int) y,
						ZOOM_FACTOR * (int) x + 2, ZOOM_FACTOR * (int) y + 2);

			}
		}
	}

	protected void clear(Graphics g) {
		super.paintComponent(g);
	}

	public Affichage(Sample sample) {
		super();
		sample.setAffichage(this);
		this.sample = sample;
	}

}
