import java.util.LinkedList;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Sample sample = new Sample("exemple1.txt");
		launchClusteringForOneSample(sample, "EuclidianDistance");
		LinkedList<Cluster> initialisedClusters = sample.getInitialisedClusters();
		
		Sample sample2 = new Sample("exemple1.txt",initialisedClusters);
		launchClusteringForOneSample(sample2, "PowerDistance");
	}

	private static void launchClusteringForOneSample(Sample sample,
			String distanceToUse) throws InterruptedException {

		// crée un panel pour afficher les résultats dans le cas de 2 variables
		if (sample.getPopulation().get(0).getDataList().size() == 2) {
			sample.openDisplayPanel();
		}
		Thread.sleep(1000);
		sample.launchClustering(3,6, distanceToUse);

	}

}