import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Affichage extends JPanel {

	private static final long serialVersionUID = 1L;

	private Sample sample;

	public void paintComponent(Graphics g) {
		clear(g);
		Graphics2D g2d = (Graphics2D) g;
		for (Cluster cluster : this.sample.getClusters()) {
			for (Individual indiv : cluster) {
				float x = indiv.getData(0);
				float y = indiv.getData(1);
				g2d.drawLine(4 * (int) x, 4 * (int) y, 4 * (int) x + 2,
						4 * (int) y + 2);
				g2d.setColor(cluster.getColor());
			}
		}
	}

	protected void clear(Graphics g) {
		super.paintComponent(g);
	}

	public Affichage(Sample sample) {
		super();
		this.sample = sample;
	}

}
