import java.awt.Color;
import java.util.LinkedList;

public class Cluster extends LinkedList<Individual> {

	private static final long serialVersionUID = 1L;
	private Individual initialClusterCenter;
	private Individual clusterCenter;
	private Color clusterColor;

	public Cluster(Color color, Individual center) {
		this.clusterColor = color;
		this.initialClusterCenter = new Individual(center);
		this.clusterCenter = new Individual(center);
	}

	public Color getColor() {
		return this.clusterColor;
	}

	public Individual getCenter() {
		return this.clusterCenter;
	}
	
	public Individual getInitialCenter() {
		return this.initialClusterCenter;
	}

	public void computeCenter() {
		int nbParameters = this.clusterCenter.getDataList().size();
		for (int i = 0; i < nbParameters ; i++) {
			float data = 0;
			for (Individual indiv : this) {
				data += indiv.getData(i);
			}
			data = data/(float)this.size() ;
			this.clusterCenter.getDataList().set(i, data);
		}
	}

}
