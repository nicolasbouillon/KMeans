import java.awt.Color;
import java.util.LinkedList;

public class Cluster extends LinkedList<Individual> {

	private static final long serialVersionUID = 1L;
	private Individual clusterCenter;
	private Color clusterColor;

	public Cluster(Color color, Individual center) {
		this.clusterColor = color;
		this.clusterCenter = center;
	}

	public Color getColor(){
		return this.clusterColor;
	}
	
	public Individual getCenter(){
		return this.clusterCenter;
	}
	
	
}
