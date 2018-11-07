package s05;

public class ExercicesFun {
	
	//Ex 2 Serie 12
	public static void rotateArrayLeft(int[] data, int shift) {
		shift = shift%data.length;
		shift = data.length-shift;
		rotateArrayRight(data, shift);
	}
	
	public static void swap(int[] data, int a, int b) {
		data[a] = data[a] + data[b];
		data[b] = data[a] - data[b];
		data[a] = data[a] - data[b];
	}
	
	//PRE: shift <= data.length
	public static void rotateArrayRight(int[] data, int shift) {
		shift = shift%data.length;
		if (shift == 0) return;							   //no shift, nothing to do
		
		for (int i = 0; i < (data.length/shift)-1; i++) {
			for (int j = 0; j < shift; j++) {
				int a = i*shift + j;
				int b = (a + shift)%data.length;
				swap(data, j, b);
			}
		}
		
		if (data.length%shift == 0) return;
		
		for (int j = 0; j < data.length%shift; j++) {
			int a = ((data.length/shift)-1)*shift + j;
			int b = (a + shift)%data.length;
			swap(data, j, b);
		}
		for (int j = 0; j < shift-1; j++) {
			swap(data, j, shift-1);
		}
		
	}
	
	//test script
	public static void main(String[] args) {
		int[] testData1 = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		
		rotateArrayRight(testData1, 7);
		
		for (int nb : testData1)
			System.out.print(nb + ", ");
	}
}
