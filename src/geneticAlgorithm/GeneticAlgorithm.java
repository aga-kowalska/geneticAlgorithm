package geneticAlgorithm;

import java.util.Arrays;

public class GeneticAlgorithm {

	public static final int GENE_SIZE = 20;
	public static final int POPULATION_SIZE = 20;
	public static final int BEST_GENES = 10;

	public static int[][] population = new int[POPULATION_SIZE][GENE_SIZE];
	public static int[][] selectedGenes = new int[BEST_GENES][GENE_SIZE];
	public static int[][][] crossoveredPairs = new int[BEST_GENES][2][GENE_SIZE];

	public static void main(String[] args) {
		
		randomPopulation(population);
		printPopulation(population);
		System.out.println();
		for (int[] gen : population) {
			System.out.println(evaluateGen(gen));
		}
		System.out.println();
		
		for (int i = 0; i < BEST_GENES; i++) {
		selectedGenes[i] = bestGene(population);
		}
		
//		System.out.println("Selected genes sums: ");
//		for (int[] gen : selectedGenes) {
//			System.out.print(evaluateGen(gen) + " ");
//		}
		

		
		
		//breeding
		int numberOfGenerations = 80;
		for (int generation = 0; generation < numberOfGenerations; generation++) {
		for (int gene = 0, i = 0; gene < population.length; gene += 2, i++) {
			int parent1;
			int parent2;
			int[][] chosenParents = new int[10][2];
			int[] currentPair = new int[2];
			
			//choose a pair and check if it is unique
			do {
				parent1 = (int)(Math.random()*(selectedGenes.length));
				parent2 = (int)(Math.random()*(selectedGenes.length)); 
				currentPair[0] = parent1;
				currentPair[1] = parent2;
			} while (contains(chosenParents, currentPair));
			chosenParents[i][0] = parent1;
			chosenParents[i][1] = parent2;
			
			int[][] children = crossOver(selectedGenes[parent1], selectedGenes[parent2]);
			population[gene] = children[0];
			population[gene+1] = children[1];
			
		}
		

		System.out.println("Generation " + generation + " Sums:");
		for (int[] gen : population) {
			System.out.println(evaluateGen(gen));
		}
		System.out.println();
		
		for (int i = 0; i < BEST_GENES; i++) {
			selectedGenes[i] = bestGene(population);
			}
		}
		
		
		//CROSS-OVER TEST
//		int[] a = new int[20];
//		for (int i = 0; i <20; i++) {
//			a[i] = i;
//		}
//		for (int i : a) {
//			System.out.print(i);
//		}
//		System.out.println();
//		int[] b = new int[20];
//		
//		for (int i = 0; i <20; i++) {
//			b[i] = 19-i;
//		}
//		for (int i : b) {
//			System.out.print(i);
//		}
//		System.out.println();
//		
//		int[][] example = crossOver(a, b);
//		for (int[] array : example) {
//		for (int i : array) {
//			System.out.println(i);
//		}
//		System.out.println();
//		}
		

		
	}

	public static void randomPopulation(int[][] population) {
		for (int row = 0; row < population.length; row++) {
			for (int column = 0; column < population[row].length; column++) {
				population[row][column] = (int) (Math.random() * 10);
			}
		}
	}

	public static void printPopulation(int[][] population) {
		for (int row = 0; row < population.length; row++) {
			for (int column = 0; column < population[row].length; column++) {
				System.out.print(population[row][column] + " ");
			}
			System.out.println();
		}
	}

	public static int evaluateGen(int[] gen) {
		int sum = 0;
		for (int i : gen) {
			sum += i;
		}
		return sum;
	}

	public static int[][] crossOver(int[] parent1, int[] parent2) {
		int[][] children = new int[2][GENE_SIZE];
		int[] child1 = new int[GENE_SIZE];
		int[] child2 = new int[GENE_SIZE];
		int randomIndex = (int) (Math.random() * 18 + 1);
		// take one part from one parent
		for (int i = 0; i < randomIndex; i++) {
			child1[i] = parent1[i];
			child2[GENE_SIZE - randomIndex + i] = parent2[i];
		}

		// take one part from another parent
		for (int i = randomIndex; i < GENE_SIZE; i++) {
			child1[i] = parent2[i];
			child2[i - randomIndex] = parent1[i];
		}
		
		children[0] = child1;
		children[1] = child2;

		return children;

	}

	public static boolean contains(int[][] twoDArray, int[] oneDArray) {

		boolean contains = false;

		for (int arrayIndex = 0; arrayIndex < twoDArray.length; arrayIndex++) {
			if (Arrays.equals(twoDArray[arrayIndex], oneDArray)) {
				contains = true;
				break;
			}
		}
		return contains;
	}

	public static boolean contains3D(int[][][] threeDArray, int[][] twoDArray) {

		boolean contains = true;

		for (int arrayIndex = 0; arrayIndex < threeDArray.length; arrayIndex++) {
			for (int arrayTwoDIndex = 0; arrayTwoDIndex < threeDArray[arrayIndex].length; arrayTwoDIndex++) {
				if (!Arrays.equals(threeDArray[arrayIndex][arrayTwoDIndex], twoDArray[arrayTwoDIndex])) {
					contains = false;
					break;
				}
			}
			if (contains) {
				break;
			}
		}
		return contains;
	}

	public static int[] bestGene(int[][] population) {
		int max = 0;
		int maxIndex = 0;
		for (int geneIndex = 0; geneIndex < population.length; geneIndex++) {
			if (evaluateGen(population[geneIndex]) > max && !contains(selectedGenes, population[geneIndex])) {
				max = evaluateGen(population[geneIndex]);
				maxIndex = geneIndex;
			}
		}

		return population[maxIndex];

	}

	public static void createNewGeneration(int[][] population) {

	}

}
