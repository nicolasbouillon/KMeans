import java.util.LinkedList;

public class Individual {

	private LinkedList<Float> dataList = new LinkedList<Float>();

	public Individual(LinkedList<Float> dataList) {
		this.dataList = dataList;
	}

	public Individual(Individual individual) {
		LinkedList<Float> list = new LinkedList<Float>(individual.getDataList());
		this.dataList = list;
	}

	public LinkedList<Float> getDataList() {
		return this.dataList;
	}

	public Float getData(int i) {
		return this.dataList.get(i);
	}

	public float EuclidianDistance(Individual individual) {
		float distance = 0;
		for (int i = 0; i < this.getDataList().size(); i++) {
			float projectedDistance = (this.getData(i) - individual.getData(i));
			distance += projectedDistance * projectedDistance;
		}
		return 1 / (float) Math.sqrt(distance);
	}

	// p controls the progressive weight that is placed on differences on
	// individual dimensions
	private static final float p = 20;
	// r controls the progressive weight that is placed on larger differences
	// between objects
	private static final float r = 2;

	public float PowerDistance(Individual individual) {
		float sum = 0;
		for (int i = 0; i < this.getDataList().size(); i++) {
			float projectedDistance = Math.abs(this.getData(i)
					- individual.getData(i));
			sum += (float) Math.pow(projectedDistance, p);
		}
		return 1 / (float) Math.pow(sum, (float) 1 / r);

	}
}
