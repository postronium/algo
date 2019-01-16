package s13;


import java.io.*;
import java.util.Scanner;

public class LempelZiv {
	// ------------------------------------ Usage: LempelZiv code infile outfile
	// ------------------------------------ or: LempelZiv decode infile outfile
	public static void code(String infile, String outfile) throws IOException  { 
    FileInputStream  bis  = new FileInputStream(infile); 
    FileOutputStream bos = new FileOutputStream(outfile); 
    PrintWriter      tos = new PrintWriter(new FileWriter(outfile+".txt")); 
    LzvCodeTable     t = new LzvCodeTable(); 
    byte[]  prefix = new byte[0]; 
    int        crt = bis.read();  
    while(crt != -1) { 
      byte crtByte=(byte)crt; 
      	//A COMPLETER...
        //printItem(bos, tos, item); 
      		//A COMPLETER...
    } 
    bis.close(); 
    bos.close(); 
    tos.close(); 
    //System.out.println("Code table: size = "+t.size()+" ; Content:"); 
    System.out.println(t); 
  }

	// ------------------------------------------------------------
	public static void decode(String infile, String outfile) throws IOException {
		
	}

	// ------------------------------------------------------------
	private static byte[] addToByteArray(byte[] t, byte b) {
		return t;
		
	}

	// ------------------------------------------------------------
	private static void printItem(FileOutputStream bos, PrintWriter tos, LzvItem item) throws IOException {
		printItemAsBinary(bos, item); // really compressed!
		//printItemAsText(tos, item); // useful for debugging
	}

	// ------------------------------------------------------------
	static void printItemAsBinary(FileOutputStream bos, LzvItem item) throws IOException { 
			//A COMPLETER...
  }

	// ------------------------------------------------------------
	private static LzvItem readItem(FileInputStream bis, Scanner tis) throws IOException {
		LzvItem res = readItemFromBinary(bis);
		//LzvItem res1 = readItemFromText(tis);
		return res;
	}

	// ------------------------------------------------------------
	private static LzvItem readItemFromBinary(FileInputStream bis) throws IOException {
		return null; 
			//A COMPLETER...
	}
}