import java.util.LinkedList;


public class Individual {
	
	private LinkedList <Float> dataList = new LinkedList<Float>();
	

	public Individual(LinkedList<Float> dataList) {
		this.dataList = dataList;
	}

	public Individual(Individual individual) {
		this.dataList = individual.getDataList();
	}

	public LinkedList<Float> getDataList(){
		return this.dataList;		
	}
	
	public Float getData(int i){
		return this.dataList.get(i);		
	}
	
	
	
	
}
