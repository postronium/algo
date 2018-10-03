package s02;

public class S12Ex15 {
	
	public static void main(String[] args) {
		float[] testData1 = new float[] {
				6, 3, 2, 4, 6, 6, 9, 2, 4, 3, 2
		};
		float[] testData2 = new float[] {
				4, 2, 2, 8, 8, 3, 1, 5, 4, 3, -5
		};
		float[] testData3 = new float[] {4, 5, 6, 7, 1, 0};
		
		
		System.out.println(nbOfStars(testData1));
	}
	
	public static int nbOfStars3(float[] t) {
		int total = 0;						//total amount of start
		int smallest = Integer.MAX_VALUE;	//the smallest amount of start found
		int numStars = 0;					//the amount of start of that will be added
		int nPosToCompensate = 1;			//amount of values to compensate after the down
											//sequence is done
		
		boolean wasGoingUp = false;
		
		for (int i = 1; i < t.length; i++) {
			if (t[i-1] < t[i]) {
				nPosToCompensate++;
				numStars++;
				if (smallest > numStars)
					smallest = numStars;
				total += numStars;
				wasGoingUp = true;
			} else if (t[i-1] > t[i]) {
				if (wasGoingUp) {
					wasGoingUp = false;
					numStars = 0;
					total += nPosToCompensate;	     	//compensate in a way that the smallest
														//value has one stare
					nPosToCompensate = 1;
				}
			} else {		// values are equals
				total += numStars;
				nPosToCompensate++;
			}
		}
		
		total += nPosToCompensate;		            //compensate in a way that the smallest
									     			//value has one stare
		
		return total;
	}
	
	public static int nbOfStars(float[] t) {
		int total = 0;						//total amount of start
		int smallest = Integer.MAX_VALUE;	//the smallest amount of start found
		int numStars = 0;					//the amount of start of that will be added
		int nPosToCompensate = 0;			//amount of values to compensate after the down
											//sequence is done
		
		boolean wasGoingDown = false;
		
		for (int i = 1; i < t.length; i++) {
			if (t[i-1] > t[i]) {			//decrement if next is smaller
				nPosToCompensate++;
				numStars--;
				if (smallest > numStars)
					smallest = numStars;
				total += numStars;
				wasGoingDown = true;
			} else if (t[i-1] < t[i]) {
				numStars = 0;
				if (!wasGoingDown) {
					smallest = -1;
				}
				total -= (nPosToCompensate+1)*(smallest-1);		//compensate in a way that the smallest
																//value has one star
				smallest = -1;
				nPosToCompensate = 0;
			} else {		// values are equals
				total += numStars;
				nPosToCompensate++;
			}
		}
		
		total -= (nPosToCompensate+1)*(smallest-1);		//compensate in a way that the smallest
														//value has one stare
		
		return total;
	}
	
	public static int nbOfStars2(float[] t) {
		int total = 0;						//total amount of start
		int smallest = Integer.MAX_VALUE;	//the smallest amount of start found
		int numStars = 0;					//the amount of start of that will be added
		
		for (int i = 1; i < t.length; i++) {
			if (t[i-1] < t[i]) {			//increment if next is bigger
				numStars++;
			} else if (t[i-1] > t[i]) {		//decrement if next is smaller
				numStars--;
				if (smallest > numStars)
					smallest = numStars;
			}
			total += numStars;
		}
		
		total -= t.length*(smallest-1);		//compensate in a way that the smallest
											//value has one stare
		
		return total;
	}

}
