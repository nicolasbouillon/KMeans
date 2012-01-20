import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Sample {

	private LinkedList<Individual> population = new LinkedList<Individual>();
	private LinkedList<Cluster> clusters = new LinkedList<Cluster>();

	public LinkedList<Individual> getPopulation() {
		return this.population;
	}

	public LinkedList<Cluster> getClusters() {
		return this.clusters;
	}

	public Sample(String file) {
		try {
			// lecture du fichier texte
			InputStream ips = new FileInputStream(file);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;

			// lecture ligne par ligne
			while ((ligne = br.readLine()) != null) {
				LinkedList<Float> dataList = new LinkedList<Float>();
				// lecture de chaque variable pour une ligne donnée
				StringTokenizer st = new StringTokenizer(ligne, "\t");
				while (st.hasMoreTokens()) {
					dataList.add(new Float(st.nextToken()));
				}

				this.population.add(new Individual(dataList));
			}
			br.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private void launchClustering(int nbClusters, String similarityFunctionToUse) {
		for (int i = 0; i < nbClusters - 1; i++) {
			// Couleur de chaque cluster déterminée aléatoirement
			Color c = new Color((int) (Math.random() * 0xFFFFFF));
			// Initialisation du cluster aléatoire (choix d'un individu initial)
			int randomIndivIndex = (int) Math.random() * this.population.size();
			Individual initialIndiv = new Individual(
					this.population.get(randomIndivIndex));

			this.clusters.add(new Cluster(c, initialIndiv));
		}
		
		Method meth = this.getSimilarityMethod(similarityFunctionToUse);
		try {
			for (Individual indiv : this.population) {
				float maxSimilarity = 0;
				Cluster clusterToAddTo = null;
				for (Cluster cluster : this.clusters) {
					Object args[] = new Object[1];
					args[0] = indiv;
					Object similarity = meth.invoke(cluster.getCenter(), args);
					Float similarityFloat = (Float)similarity ;
					
					
					if (similarityFloat > maxSimilarity){
						clusterToAddTo = cluster;
						maxSimilarity = similarityFloat;
					}
				}
				clusterToAddTo.add(indiv);
			}			
			
		} catch (Throwable e) {
			System.err.println(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Method getSimilarityMethod(String s) {
		try {

			Class indivClass = Individual.class;
			Class[] args1 = new Class[1];
			args1[0] = Individual.class;
			Method similarityFunction = indivClass.getMethod(s, args1);
			return similarityFunction;

		} catch (NoSuchMethodException ex) {
			System.out.println("Method either doesn't exist "
					+ "or is not public: " + ex.toString());
		}
		return null;
	}

	private void openDisplayPanel() {
		WindowUtilities.openInJFrame(new Affichage(this), 520, 450);

	}

	public static void main(String[] args) {
		Sample sample = new Sample("exemple2.txt");

		sample.launchClustering(2, "Euclidian");
		if (sample.population.get(0).getDataList().size() == 2) {
			sample.openDisplayPanel();
		}
	}
}