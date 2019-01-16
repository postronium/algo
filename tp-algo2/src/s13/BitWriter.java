package s13;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

// Produces a file one bit at a time (MSB first),  
// filling the last byte with zeros if necessary 
public class BitWriter {
	
	private final FileOutputStream fos;
	private byte[] buffer;
	private int nextBit;
	private static final int BUFFER_SIZE = 1;
	
	public BitWriter(String filename) throws IOException {
		this.fos = new FileOutputStream(filename);
		this.nextBit = 0;
		this.buffer = new byte[BUFFER_SIZE];
	}

	public void put(boolean b) throws IOException {
		int newData = (b ? 1 : 0);
		int shiftedData = (newData << this.nextBit%8);
		this.buffer[this.nextBit/8] |= shiftedData;
		
		this.nextBit++;
		
		if (this.nextBit >= BUFFER_SIZE*8) {
			this.fos.write(this.buffer);
			this.nextBit = 0;
			this.buffer = new byte[BUFFER_SIZE];
		}
	}

	public void close() throws IOException {
		int nRemaining = this.nextBit/8;
		if (this.nextBit%8 != 0) nRemaining++;
		byte[] remainingData = Arrays.copyOf(this.buffer, nRemaining);
		this.fos.write(remainingData);
		this.fos.close();
	}
}