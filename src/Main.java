
public class Main {

	public static void main(String[] args) throws InterruptedException {
		Sample sample = new Sample("exemple1.txt");
		
		// crée un panel pour afficher les résultats dans le cas de 2 variables
		if (sample.getPopulation().get(0).getDataList().size() == 2) {
			sample.openDisplayPanel();
		}
		Thread.sleep(1000);
		sample.launchClustering(4,10, "Euclidian");
		

	}

	
}
