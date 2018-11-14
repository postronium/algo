package s07;

import java.util.Arrays;

// ------------------------------------------------------------
public class DisjSets {
	
	private static final String EMPTY_STRING = "";
	private static final int NULL_INDEX = -1;
	
	private int[] data;

	public DisjSets(int nbOfElements) {
		data = new int[nbOfElements];
		Arrays.fill(data, NULL_INDEX);
	}

	public boolean isInSame(int i, int j) {
		int rootI = getRoot(i);
		int rootJ = getRoot(j);
		return rootI == rootJ;
	}

	public void union(int i, int j) {
		int rootI = getRoot(i);
		int rootJ = getRoot(j);
		if (i == j || rootI == rootJ) 
			return;
		assert(data[rootI] < 0 && data[rootJ] < 0);
		int sum = (data[rootI] + data[rootJ]);
		System.out.println(sum);
		/*
		if (-sum > data.length) {
			System.out.println("Probleme " + data.length + " < " + -sum);
			System.out.println(Arrays.toString(data));
		}
		*/
		if (data[rootI] < data[rootJ]) {	//if rootI is bigger
			assert(data[rootJ] < 0);
			assert(data[rootI] < 0);
			data[rootI] += data[rootJ];
			data[j] = rootI;
		} else {
			assert(data[rootJ] < 0);
			assert(data[rootI] < 0);
			data[rootJ] += data[rootI];
			data[i] = rootJ;
		}
	}
	
	private boolean testValidity() {
		for (int i = 0; i < data.length; i++)
			if (data[i] == i) return false;
		return true;
	}

	public int nbOfElements() { // as given in the constructor
		return data.length;
	}

	public int minInSame(int i) {
		int rootI = getRoot(i);
		int smallest = i;
		for (int j = 0; j < i; j++)
			if (rootI == getRoot(j) && j < smallest)
				smallest = j;
		return smallest;
	}

	public boolean isUnique() {
		if (data[0] < 0) {
			return (data[0] + data.length) == 0;
		}
		return (data[getRoot(data[0])] + data.length) == 0;
	}

	@Override
	public String toString() {
		String[] strGrp = new String[data.length];
		Arrays.fill(strGrp, EMPTY_STRING);
		for (int i = 0; i < data.length; i++) {
			int root = getRoot(i);
			if (strGrp[root].equals(EMPTY_STRING))
				strGrp[root] = "{";
			else
				strGrp[root] += ", ";
			strGrp[root] += i;
		}
		
		String completString = "";
		for (int i = 0; i < strGrp.length-1; i++)
			if (!strGrp[i].equals(EMPTY_STRING))
				completString += strGrp[i] + "}, ";	
		if (!strGrp[strGrp.length-1].equals(EMPTY_STRING))
			completString += strGrp[strGrp.length-1] + "}";	
		
		return completString;
	}
	
	private int getRoot(int position) {
		if (data[position] < 0)
			return position;
		int root = getRoot(data[position]);
		//data[position] = root;
		return root;
	}

	@Override
	public boolean equals(Object otherDisjSets) {
		if (!(otherDisjSets instanceof DisjSets))
			return false;
		DisjSets otherSet = (DisjSets) otherDisjSets;
		if (otherSet.data.length != this.data.length)
			return false;
		for (int i = 0; i < otherSet.data.length; i++)
			if (otherSet.data[i] != this.data[i])
				return false;
		return true;
	}
}
