package s13;

import java.io.FileInputStream;
import java.io.IOException;

// Reads any file one bit at a time (most significant bit first) 
public class BitReader {
	
	private final FileInputStream fis;
	private byte[] buffer;			//could also be done by a FIFO queue
	private int nextBit;
	private static final int BUFFER_SIZE = 1;
	private boolean isDone;

	// private ...
	
	public BitReader(String filename) throws IOException {
		this.fis = new FileInputStream(filename);
		this.buffer = new byte[BUFFER_SIZE];
		this.nextBit = this.buffer.length*8;
		this.isDone = false;
	}

	public boolean isOver() {
		return this.isDone;
	}

	public boolean next() throws IOException {
		if (this.nextBit >= this.buffer.length*8) {
			this.nextBit = 0;
			int res = this.fis.read(this.buffer);
			if (res == -1) {
				this.isDone = true;
				return false;
			}
		}
		
		byte b = this.buffer[this.nextBit/8];
		boolean bit = (b >> (7 - this.nextBit%8))%2 == 1;
		this.nextBit++;
		return bit;
	}

	public void close() throws IOException {
		this.fis.close();
	}
}