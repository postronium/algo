package s13;

import java.io.*;

// Reads any file one bit at a time (most significant bit first)
public class BitReader {
  private final FileInputStream fis;
  // TODO - A COMPLETER
  // private...
  //--------------------
  public BitReader(String filename) throws IOException {
    fis= new FileInputStream(filename);
    // TODO - A COMPLETER
  }
  //--------------------
  public void close() throws IOException {
    // TODO - A COMPLETER
  }
  //--------------------
  public boolean next() throws IOException { 
    return false; // TODO - A COMPLETER
  }
  //--------------------
  public boolean isOver() {
    return false; // TODO - A COMPLETER
  }
  //--------------------
  // Tiny demo...
  public static void main(String [] args) {
    String filename="a.txt";
    try {
      BitReader b = new BitReader(filename);
      int i=0;
      while(!b.isOver()) {
        System.out.print(b.next()?"1":"0");
        i++;
        if (i%8  == 0) System.out.print(" ");
        if (i%80 == 0) System.out.println("");
      }
      b.close();
    } catch (IOException e) {
      throw new RuntimeException(""+e);
    }
  }
}
