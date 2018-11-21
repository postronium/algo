package s08;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ExerciceFun {
	
	private static String FILE_PATH = "./numbers";
	//We use byte because my computer don't have enought free disk space
	//but it works the same with 32bit integer
	private static int N_BITS_BYTE = 8;
	private static int MAX_VAL = Byte.MAX_VALUE;
	private static int MIN_VAL = Byte.MIN_VALUE;
	private static int AMOUNT_OF_NUMBERS = MAX_VAL - MIN_VAL + 1; //+1 is for 0
	//to generate Test data
	private static int[] missingNumbers = new int[] {72};
	private static int[] count_zeros = new int[N_BITS_BYTE];	//init to 0
	private static int[] count_ones = new int[N_BITS_BYTE];		//init to 0
	
	public static void main(String[] args) {
		try {
			writeTestFile();					//generate Test file
		} catch (IOException e) {
			e.printStackTrace();
		}
		read(); displayResult();
	}
	
	public static void writeTestFile() throws IOException {
		File testFile = new File(FILE_PATH);
		testFile.delete();
		FileWriter fr = new FileWriter(testFile, true);
		for (int i = MIN_VAL; i < MAX_VAL+1; i++) {
			boolean canBeWriten = true;
			for (int number : missingNumbers)
				if (i == number)
					canBeWriten = false;
			if (canBeWriten)
				fr.write(i + "\n");
		}
		fr.close();
	}
	
	private static void read() {
		try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
	        stream.forEach(ExerciceFun::process8BitNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void process8BitNumber(String number) {
		int n = Integer.parseInt(number);
		for (int i = 0; i < N_BITS_BYTE; i++) {	//foreach bit in the integer
			if (n%2 == 0)
				count_zeros[i]++;
			else
				count_ones[i]++;
			n = n >> 1;
		}
	}
	
	private static void displayResult() {
		boolean noResult = true;
		for (int n : count_zeros)
			if ((AMOUNT_OF_NUMBERS/2 - n) != 0)
				noResult = false;
		if (noResult) {
			System.out.println("No missing number");
			return ;
		}
		int number = 0;
		for (int i = count_ones.length-1; i > -1; i--) {
			number += (AMOUNT_OF_NUMBERS/2 - count_ones[i]);
			number = number << 1;
		}
		number = number >> 1;
		if (number > 127) number -= AMOUNT_OF_NUMBERS;
		System.out.printf("Missing number : %d\n", number);
	}

}
