package s13;

import java.io.IOException;

public class ReadWriteTest { 
	
	public static void main(String[] args) {
		testRead();
		testWrite();
	}
	
	public static void testWrite() {
		BitWriter writer;
		try {
			writer = new BitWriter("newFile.txt");
			writer.put(false);
			writer.put(true);
			writer.put(false);
			writer.put(false);
			writer.put(false);
			writer.put(false);
			writer.put(true);
			writer.put(false);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void testRead() {
		BitReader reader;
		try {
			reader = new BitReader("file.txt");
			int counter = 0;
			while(!reader.isOver()) {
				counter++;
				System.out.println(reader.next());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
