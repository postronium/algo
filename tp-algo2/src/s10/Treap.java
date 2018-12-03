package s10;
import java.util.Random;
//--------------------------------------------------
public class Treap<E extends Comparable<E>>  {
  //============================================================
  static class TreapElt<E extends Comparable<E>> implements Comparable<TreapElt<E>> {
    static Random rnd=new Random(); 
    // -----------------------
    private final E elt;
    private int     pty;
    // -----------------------
    public TreapElt(E e) {
      elt=e; 
      pty=rnd.nextInt();
    }

    public int pty() {
      return pty;
    }

    public E elt() {
      return elt;
    }

    public int compareTo(TreapElt<E> o) {
      return elt.compareTo(o.elt);
    }

    @Override public boolean equals(Object o) {
      if(o==null) return false;
      if (this.getClass() != o.getClass()) return false;
      if (elt==null) return false;
      return elt.equals(((TreapElt<?>)o).elt);
    }

    @Override public String toString() {
      return ""+elt+"#"+pty;
    }

    @Override public int hashCode() {
      return elt.hashCode();
    }
  }
  //============================================================
  private final BST<TreapElt<E>> bst;
  // --------------------------
  public Treap() {
    bst=null;
    // TODO - A COMPLETER
  }

  public void add(E e) {
    // TODO - A COMPLETER
  }

  public void remove(E e) {
    // TODO - A COMPLETER
  }

  public boolean contains(E e) {
    return false; // TODO - A COMPLETER
  }

  public int size() {
    return 0; // TODO - A COMPLETER
  }

  public E minElt() {
    return null; // TODO - A COMPLETER
  }

  public E maxElt() {
    return null; // TODO - A COMPLETER
  }
  
  public String toString() {
    return bst.toString();
  }

  // --------------------------------------------------
  // --- Non-public methods
  // --------------------------------------------------
  private void siftDownAndCut(BTreeItr<TreapElt<E>> ti) {
    // TODO - A COMPLETER
  }

  private void percolateUp(BTreeItr<TreapElt<E>> ti) {
    while((!ti.isRoot()) && isLess(ti, ti.up())) {
      if (ti.isLeftArc()) {ti=ti.up(); ti.rotateRight();}
      else                {ti=ti.up(); ti.rotateLeft(); }
    }
  }

  private boolean isLess(BTreeItr<TreapElt<E>> a, BTreeItr<TreapElt<E>> b) {
    TreapElt<E> ca= a.consult();
    TreapElt<E> cb= b.consult();
    return ca.pty()<cb.pty();
  }
}
