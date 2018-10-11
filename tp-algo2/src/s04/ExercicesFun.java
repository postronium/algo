package s04;

public class ExercicesFun {
	
	/*
	 * Exercice 14 Serie 12
	 * Target :
	 * 		CPU : O(n)
	 * 		RAM : O(1)
	 */
	public static void propagate(int[][] m, int v) {
		for (int i = 0; i < m.length/3; i++) {
			for (int j = 0; j < m[i].length/3; j++) {
				propagationFor9(m, i*3, j*3, (i+1)*3, (j+1)*3, 8);
				//printMatrix(m);
			}
		}
		
		for (int i = 0; i < m.length/3; i++)
			pullDown(m, i, v);
		
		for (int i = 0; i < m.length/3; i++)
			pullUp(m, i, v);
		
		/* Don't work
		 * If this would work, the algoryth would have a CPU complexity of : O(xn) and RAM : O(1)
		 * The algorythm would't work in some specific cases
		 * For example if a block of 3 by 3 in some of the corners are all 9 equal to v
		
		for (int i = 0; i < m[0].length/3; i++)
			pullLeft(m, i, v);
		
		for (int i = 0; i < m[0].length/3; i++)
			pullRight(m, i, v);
			
		*/
	}
	
	/**
	 * Simple solution for the propagation algoryth
	 * Complexity:
	 * 		CPU : up to n pow(2)+ if all numbers are equal to v
	 * 		MEM : always sqrt(n)
	 * @param m
	 * @param si
	 * @param sj
	 * @param ei
	 * @param ej
	 * @param v
	 */
	public static void propagationFor9(int[][] m, int si, int sj, int ei, int ej, int v) {
		boolean[] vInPrevRow = new boolean[3];
		for (int i = si; i < ei; i++) {
			boolean vInThisRow = false;
			for (int j = sj; j < ej; j++) {
				vInPrevRow[j-sj] = vInPrevRow[j-sj] || (m[i][j] == v);
				if (m[i][j] == v) vInThisRow = true;
			}
			if (vInThisRow)
				for (int j = sj; j < ej; j++)
					m[i][j] = v;
		}
		for (int j = sj; j < ej; j++)
			if (vInPrevRow[j-sj])
				for (int i = si; i < ei; i++)
					m[i][j] = v;
					
	}
	
	public static void pullDown(int[][] m, int from, int v) {
		boolean[] actual = new boolean[3];
		boolean[] lastVs = new boolean[3];
		for (int i = 0; i < m.length; i++) {
			if (i%3 == 0 && i != 0) {
				lastVs = actual;
			}
			
			for (int j = from; j < from+3; j++)									//write
				if (lastVs[j-from])
					m[i][j] = v;
			
			if (!(m[i][from] == v && m[i][from+1] == v && m[i][from+2] == v))	//remember
				for (int j = from; j < from+3; j++)
					actual[j-from] = (m[i][j] == v);
			
		}
			
	}
	
	public static void pullUp(int[][] m, int from, int v) {
		boolean[] actual = new boolean[3];
		boolean[] lastVs = new boolean[3];
		for (int i = m.length-1; i > -1; i--) {
			if (i%3 == 0 && i != m.length-1)
				lastVs = actual;
			
			for (int j = from; j < from+3; j++)									//write
				if (lastVs[j-from])
					m[i][j] = v;
			
			if (!(m[i][from] == v && m[i][from+1] == v && m[i][from+2] == v))	//remember
				for (int j = from; j < from+3; j++)
					actual[j-from] = (m[i][j] == v);
			
		}
			
	}
	
	public static void pullLeft(int[][] m, int from, int v) {
		boolean[] actual = new boolean[3];
		boolean[] lastVs = new boolean[3];
		for (int j = m.length-1; j < -1; j++) {
			if (j%3 == 0 && j != m.length-1)
				lastVs = actual;
			
			for (int i = from; i < from+3; i++)									//write
				if (lastVs[i-from])
					m[i][j] = v;
			
			if (!(m[from][j] == v && m[from+1][j] == v && m[from+2][j] == v))	//remember
				for (int i = from; i < from+3; i++)
					actual[i-from] = (m[i][j] == v);
			
		}
			
	}
	
	
	public static void pullRight(int[][] m, int from, int v) {
		boolean[] actual = new boolean[3];
		boolean[] lastVs = new boolean[3];
		for (int j = 0; j < m.length; j++) {
			if (j%3 == 0 && j != 0)
				lastVs = actual;
			
			for (int i = from; i < from+3; i++)									//write
				if (lastVs[i-from])
					m[i][j] = v;
			
			if (!(m[from][j] == v && m[from+1][j] == v && m[from+2][j] == v))	//remember
				for (int i = from; i < from+3; i++)
					actual[i-from] = (m[i][j] == v);
			
		}
			
	}
	
	public static void printMatrix(int[][] m) {
		for(int[] line : m) {
			for (int item : line) {
				System.out.print(item + " | ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
	
	public static void testPropagate() {
		int[][] matrix = new int[][] {
			{8, 1, 1, 1, 8, 1, 1, 8, 1}, 
			{1, 1, 1, 1, 1, 1, 1, 8, 1}, 
			{1, 1, 1, 1, 1, 1, 1, 1, 1}, 
			{1, 1, 1, 1, 1, 1, 1, 1, 1}, 
			{1, 1, 1, 1, 8, 8, 8, 1, 1}, 
			{1, 1, 1, 1, 8, 1, 1, 1, 1}, 
			{1, 1, 1, 1, 1, 1, 1, 1, 1}, 
			{1, 1, 1, 1, 1, 1, 1, 8, 1}, 
			{1, 1, 1, 1, 1, 1, 1, 1, 1}
		};
		
		propagate(matrix, 8);
		printMatrix(matrix);
	}
	
	public static void main(String[] args) {
		testPropagate();
	}

}
