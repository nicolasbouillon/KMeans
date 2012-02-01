import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Sample {

	private static final int PANEL_WIDTH = 800;
	private static final int PANEL_HEIGHT = 700;

	private LinkedList<Individual> population = new LinkedList<Individual>();
	private LinkedList<Cluster> clusters = new LinkedList<Cluster>();
	private LinkedList<Cluster> initialisedClusters = new LinkedList<Cluster>();
	private Affichage affichage;
	private int iteration;
	private String distance;

	public void setAffichage(Affichage affichage) {
		this.affichage = affichage;
	}

	public LinkedList<Individual> getPopulation() {
		return this.population;
	}

	public LinkedList<Cluster> getClusters() {
		return this.clusters;
	}

	public int getIteration() {
		return iteration;
	}

	public String getDistance() {
		return distance;
	}

	public LinkedList<Cluster> getInitialisedClusters() {
		return initialisedClusters;
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
				// lecture de chaque variable pour une ligne donn�e
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

	public Sample(String file, LinkedList<Cluster> initialisedClusters) {
		this(file);
		this.clusters = new LinkedList<Cluster>(initialisedClusters);
	}

	public void launchClustering(int nbClusters, int nbIterations,
			String similarityFunctionToUse) throws InterruptedException {

		// Initialisation
		if (this.clusters.isEmpty()) {
			for (int i = 0; i < nbClusters; i++) {
				// Couleur des clusters (6 différentes prévues)
				Color c = null;
				if (this.affichage != null) {
					c = this.affichage.getNewColor(this.getClusters()
							.size());
				}
				// Initialisation du cluster aléatoire (choix d'un individu
				// initial)
				int randomIndivIndex = (int) (Math.random() * this.population
						.size());
				Individual initialIndiv = new Individual(
						this.population.get(randomIndivIndex));

				this.clusters.add(new Cluster(c, initialIndiv));
				this.initialisedClusters.add(new Cluster(c, initialIndiv));
			}

		}

		this.iteration = 1;
		this.distance = similarityFunctionToUse;
		// boucle d'affectation de la populations aux différents clusters
		for (int i = 0; i < nbIterations; i++) {
			for (Cluster cluster : this.clusters) {
				cluster.clear();
			}
			this.reallocatePopulation(similarityFunctionToUse);

			// mise à jour de l'affichage dans le cas de 2 variables
			if (this.getPopulation().get(0).getDataList().size() == 2) {
				this.affichage.repaint();
				Thread.sleep(1000);
			}

			for (Cluster cluster : this.clusters) {
				cluster.computeCenter();
			}
			this.iteration = this.iteration + 1;
		}

	}

	private void reallocatePopulation(String similarityFunctionToUse) {
		Method meth = this.getSimilarityMethod(similarityFunctionToUse);
		try {
			for (Individual indiv : this.population) {
				float maxSimilarity = 0;
				Cluster clusterToAddTo = null;
				for (Cluster cluster : this.clusters) {
					Object args[] = new Object[1];
					args[0] = indiv;
					Object similarity = meth.invoke(cluster.getCenter(), args);
					Float similarityFloat = (Float) similarity;

					if (similarityFloat > maxSimilarity) {
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

	// Introspection to call the wanted similarity function
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Method getSimilarityMethod(String s) {
		try {

			Class indivClass = Individual.class;
			Class[] args1 = new Class[1];
			args1[0] = Individual.class;
			Method similarityFunction = indivClass.getMethod(s, args1);
			return similarityFunction;

		} catch (NoSuchMethodException ex) {
			System.out.println("Method doesn't exist " + ex.toString());
		}
		return null;
	}

	public void openDisplayPanel() {
		Affichage affichage = new Affichage(this);
		WindowUtilities.openInJFrame(affichage, PANEL_WIDTH, PANEL_HEIGHT);
	}

}