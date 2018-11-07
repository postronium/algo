package s07;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

// ======================================================================
public class DisjSetsTestJU {
  private Random rnd = new Random();
  // ------------------------------------------------------------
  @Test
  public void testDisjSets() {
    int n= 200;
    for (int j=1; j<n;j+=rnd.nextInt(10)+1) {
      System.out.print(".");
      testDisjSets(j);
    }
    System.out.println("Test 'union/find/isUnique' passed sucessfully...");
  }
  // ------------------------------------------------------------
  @Test
  public void testEquals() {
    int n= 100;
    for (int j=1; j<n;j+=rnd.nextInt(10)+1) {
      System.out.print(".");
      testEquals(j);
    }
    System.out.println("Test 'equals' passed sucessfully...");
  }
  //------------------------------------------------------------
  static class DisjSetsNaive {
    private final List<Set<Integer>> sets;
    // --------------------------
    public DisjSetsNaive(int n) {
      sets=new ArrayList<Set<Integer>>();
      for(int i=0; i<n; i++) {
        Set<Integer> s=new HashSet<Integer>();
        s.add(i);
        sets.add(s);
      }
    }
    // --------------------------
    public boolean isInSame(int i, int j) {
      for(Set<Integer> s:sets) {
        if (s.contains(i)) return s.contains(j);
      }
      return false;
    }
    // --------------------------
    public void union(int i, int j) {
      if (isInSame(i,j)) return;
      Set<Integer> si=null, sj=null;
      for(Set<Integer> s:sets) {
        if (s.contains(i)) si=s;
        if (s.contains(j)) sj=s;
      }
      si.addAll(sj);
      sets.remove(sj);
    }
    // --------------------------
    public boolean isUnique() { 
      return sets.size()==1;
    }
    // --------------------------
    public boolean equals(Object o) {
      if (!(o instanceof DisjSetsNaive)) return false;
      DisjSetsNaive other=(DisjSetsNaive)o;
      if (other.sets.size() != sets.size()) return false;
      for(Set<Integer> s:sets) {
        boolean found=false;
        for(Set<Integer> os:other.sets)
          if (s.equals(os)) found=true;
        if (!found) return false;
      }
      return true;
    }
    // --------------------------
    public String toString() {
      return ""+sets;
    }
  }
  // ------------------------------------------------------------
  private void testDisjSets(int n) {
    DisjSets d = new DisjSets(n);
    DisjSetsNaive b = new DisjSetsNaive(n);
    int i, j;
    while(! b.isUnique()) {
      assertTrue(!d.isUnique());
      i = rnd.nextInt(n);
      j = rnd.nextInt(n);
      if (rnd.nextBoolean()) {
        b.union(i,j);
        d.union(i,j);
      }
      assertTrue(b.isInSame(i,j) == d.isInSame(i,j));
    }
    assertTrue(d.isUnique());
  }
  // ------------------------------------------------------------
  private void testEquals(int n) {
    DisjSets d1 = new DisjSets(n);
    DisjSetsNaive b1 = new DisjSetsNaive(n);
    while(! b1.isUnique()) {
      DisjSets d2 = new DisjSets(n);
      int i = rnd.nextInt(n);
      int j = rnd.nextInt(n);
      b1.union(i,j); d1.union(i,j);
      if (rnd.nextInt(10)<3)
        compareEquality(b1, d1, n);
      makeEqual(b1, d2);
      assertTrue(d1.equals(d2));
    }
  }
  // ------------------------------------------------------------
  private void compareEquality(DisjSetsNaive b1, DisjSets d1, int n) {
    DisjSets d2 = new DisjSets(n);
    DisjSetsNaive b2 = new DisjSetsNaive(n);
    while(! b2.isUnique()) {
      int i = rnd.nextInt(n);
      int j = rnd.nextInt(n);
      b2.union(i,j); d2.union(i,j);
      assertTrue(b1.equals(b2)==d1.equals(d2));
    }
  }
  // ------------------------------------------------------------
  private void makeEqual(DisjSetsNaive b1, DisjSets d2) {
    for(Set<Integer> s:b1.sets){
      List<Integer> l=new ArrayList<Integer>(s);
      Collections.shuffle(l);
      for(int i=0; i<l.size()-1; i++)
        d2.union(l.get(i), l.get(i+1));
    }
    
  }
}
