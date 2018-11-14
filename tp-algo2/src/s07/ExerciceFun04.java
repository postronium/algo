package s07;

public class ExerciceFun04 {
	
	public static int comps = 0;
	
	public static void main(String[] args) {
		int[] testData = {1, 2, 5, 3, 84, 534, 654, 615, 615, 615, 146 ,614, 165, 64, 13 ,1, 46 ,15, 15 ,163 ,15, 564, 468, 15, -1465, -648, -56, 48, -68, 4, 64, -68, 46, 46};
		int[] answer = getBigAndSmallBetter(testData);
		System.out.println("smalles : " + answer[0]);
		System.out.println("biggest : " + answer[1]);
	}
	
	public static void split(int[] data, int[] bigs, int[] smalls) {
		for (int i = 0; i < data.length/2; i++) {
			if (isBigger(data[i], data[data.length-1-i])) {
				bigs[i] = data[i];
				smalls[i] = data[data.length-1-i];
			} else {
				bigs[i] = data[data.length-1-i];
				smalls[i] = data[i];
			}
		}
	}
	
	public static int[] getBigAndSmallBetter(int[] data) {
		comps = 0;
		
		int[] smalls = new int[data.length/2];
		int[] bigs = new int[data.length/2];
		
		split(data, bigs, smalls);
		
		//compare smalls
		int smallest = Integer.MAX_VALUE;
		for (int i = 0; i < smalls.length; i++)
			smallest = isBigger(smallest, smalls[i]) ? smalls[i] : smallest;
			
		//compare bigs
		int biggest = Integer.MIN_VALUE;
		for (int i = 0; i < bigs.length; i++)
			biggest = isBigger(bigs[i], biggest) ? bigs[i] : biggest;
		
		//special case for odd arrays
		if (data.length%2 == 1) {
			if (isBigger(data[data.length/2], biggest))
				biggest = data[data.length/2];
			else if (isBigger(smallest, data[data.length/2]))
				smallest = data[data.length/2];
		}
		
		System.out.println("ratios : " + comps*100/(2*(data.length-1)) + "%");
			
		return new int[] {smallest, biggest};
	}
	
	//is a bigger than b
	public static boolean isBigger(int a, int b) {
		System.out.println(a + " > " + b + " ? " + (a > b));
		comps++;
		return a > b;
	}
}
