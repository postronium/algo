package s03;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Heap<E extends Comparable<E>> {
  private final ArrayList<E> buffer;
  // ------------------------------------------------------------
  public Heap() {
    buffer = new ArrayList<E>();
  }
  // ------------------------------------------------------------
  public void add(E e) {
    buffer.add(e);
    percolateUp(buffer.size()-1);
  }
  // ------------------------------------------------------------
  public E removeMin() {
    E m = min();
    int last = buffer.size()-1;
    buffer.set(0, buffer.get(last));
    buffer.remove(last);
    siftDown(0);
    return m;
  }
  // ------------------------------------------------------------
  public E min() {
    return buffer.get(0);
  }
  // ------------------------------------------------------------
  public boolean isEmpty() {
    return buffer.size()==0;
  }
  // ------------------------------------------------------------
  public String toString() {
    String res="Array: ";
    for(int i=0; i<buffer.size(); i++)
      res += buffer.get(i)+" ";
    return res+"\n Tree: "+toReadableString(0);
  }
  // ------------------------------------------------------------
  /** Moves down the element at index i, by successive swaps, 
   *  until it is correctly positioned */
  private void siftDown(int i) {
	  boolean smallerLeft = false;
	  if (buffer.contains(leftChild(i))) {
		  smallerLeft = buffer.get(leftChild(i)).compareTo(buffer.get(i)) < 0;
	  }
	  boolean smallerRight = false;
	  if (buffer.contains(rightChild(i))) {
		  smallerRight = buffer.get(rightChild(i)).compareTo(buffer.get(i)) < 0;
	  }
    
    if (smallerLeft && smallerRight) {
    	int compVal = buffer.get(leftChild(i)).compareTo(buffer.get(rightChild(i)));
    	if (compVal < 0) {
    		swap(i, leftChild(i));
    		siftDown(leftChild(i));
    	} else {
    		swap(i, rightChild(i));
    		siftDown(rightChild(i));
    	}
    } else if(smallerLeft) {
    	swap(i, leftChild(i));
    	siftDown(leftChild(i));
    } else if(smallerRight) {
    	swap(i, rightChild(i));
    	siftDown(rightChild(i));
    }	//if (smallerLeft == false && smallerRight == false) => is at the right place
  }
  // ------------------------------------------------------------
  /** Moves up the element at index i, by successive swaps, 
   *  until it is correctly positioned */
  private void percolateUp(int i) {
    // feel free to adapt if you prefer recursion instead of a loop...
    while(i>0 && smallerThanItsParent(i)) {
      swap(i, parent(i));
      i=parent(i);
    }
  }
  
  private boolean smallerThanItsParent(int i) {
     return buffer.get(i).compareTo(buffer.get(parent(i))) < 0;
  }
  // ------------------------------------------------------------
  private int parent(int i) {
    return (i-1)/2;
  }
  private int leftChild (int i) {
    return 2*i+1;
  }
  private int rightChild(int i) {
    return 2*i+2;  
  }
  // --------------
  private void swap(int i, int j) {
    E aux=buffer.get(i);
    buffer.set(i, buffer.get(j));
    buffer.set(j, aux);
  }
  // ------------------------------------------------------------
  // ------------------------------------------------------------
  // ------------------------------------------------------------
  public String toReadableString(int r) {
    List<StringBuilder> b=subtreeBlock(r);
    StringBuilder res=new StringBuilder("\n");
    for(StringBuilder s:b) res.append(s).append('\n');
    return res.toString();
  }
  
  /* ------------ Example: --------------------
������������������������9�������������
�������������2�����������```````4�����
����������12��``8������������6���```7�
��3��������`�����``10�����14��`���1��`
5��``0��������������`11���������������
������`13�����������������������������
   ------------------------------------------ */
  
  private static final char SPACE = '\u00A0'; // the rarer non-breaking space
  private static final char LMARK = '�';
  private static final char RMARK = '`';
  private static final String FIRST_SEP = ""+LMARK+SPACE+RMARK;
  private static final String OTHER_SEP = ""+SPACE+SPACE+SPACE;

  private List<StringBuilder> subtreeBlock(int n) {
    List<StringBuilder> ls, rs, res=new ArrayList<>();
    if(n>=buffer.size()) {  // empty subtree
      res.add(new StringBuilder("")); 
      return res;
    } 
    String eltStr=String.valueOf(buffer.get(n));
    //if(eltStr.startsWith(""+SPACE) || eltStr.endsWith(""+SPACE))
    //  System.out.println("possible display problem...");
    ls=subtreeBlock(leftChild(n));
    rs=subtreeBlock(rightChild(n));
    int lw=ls.get(0).length();
    int rw=rs.get(0).length();
    if(lw+rw==0) {  // no child
      res.add(new StringBuilder(eltStr)); 
      return res;
    }
    replaceExtremeSpaces(ls.get(0), SPACE, LMARK);  // "    elt�����"
    replaceExtremeSpaces(rs.get(0), RMARK, SPACE);  // "````elt     "
    String sep = FIRST_SEP;
    for(int i=0;  i<ls.size() || i<rs.size(); i++) {
      StringBuilder l=(i<ls.size()) ? ls.get(i) : blockOf(lw, SPACE);
      StringBuilder r=(i<rs.size()) ? rs.get(i) : blockOf(rw, SPACE);
      res.add(l.append(sep).append(r));
      sep=OTHER_SEP;  // only the first line is joined with � `
    }
    StringBuilder first=blockOf(lw+1, SPACE).append(eltStr);
    int pad=(lw+rw+sep.length()) - first.length();
    if(pad>=0) {
      first.append(blockOf(pad, SPACE));
    } else {  // special case where "root line" is longer because of its elt
      StringBuilder suffix=blockOf(-pad, SPACE);
      for(StringBuilder z:res)
        z.append(suffix);
    }
    res.add(0, first);
    //assert first.length()==res.get(1).length() : 
    //      (first.length()+" "+res.get(1).length()); 
    return res;
  }    
  
  // "   ...   " --> "ccc...ddd"
  private static void replaceExtremeSpaces(StringBuilder sb, char c, char d) {
    int n=sb.length();
    for(int i=0;   i<n  && sb.charAt(i)==SPACE; i++) sb.setCharAt(i, c);
    for(int i=n-1; i>=0 && sb.charAt(i)==SPACE; i--) sb.setCharAt(i, d);
  }

  // (3,'a') --> "aaa"
  private static StringBuilder blockOf(int w, char c) {
    StringBuilder r=new StringBuilder("");
    while(w-- > 0) r.append(c);
    return r;
  }

  // ------------------------------------------------------------
  // ------------------------------------------------------------
  // ------------------------------------------------------------
  public static void main(String [] args) {
    int n=10;
    if (args.length>0) n = Integer.parseInt(args[0]);
    Random r = new Random();
    Heap<Integer> h= new Heap<Integer>();
    Integer e;
    for (int i=0; i<n; i++) {
      String log="";
      if (r.nextInt(10)<4 && !h.isEmpty()) { 
        e = h.removeMin();
        log += "\n--- remove "+e+"\n" + h;
      } else {
        e = new Integer(r.nextInt(n));
        h.add(e);
        log += "\n--- add    "+e+"\n" + h;
      }
      if (i<20) System.out.println(log);
    }
  }
  // ------------------------------------------------------------

}
