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


	public float Euclidian(Individual individual) {
		float distance = 0;
		for (int i = 0; i < this.getDataList().size(); i++) {
			float projectedDistance = (this.getData(i) - individual.getData(i));
			distance += projectedDistance * projectedDistance;
		}
		return 1/(float) Math.sqrt(distance);
	}

}
