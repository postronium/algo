package s12;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WavyListSearching {
  //-------------------------------------------------
  // A "wavy" list is a *circular* list of size n >= 3, where:
  // - for all i, t[i] != t["i+1"]
  // - there is a single i such that t["i-1"] > t[i] < t["i+1"]
  // - there is a single j such that t["j-1"] < t[j] > t["j+1"]
  //-------------------------------------------------
  // Algo: jump forward or backward depending on the "trend" at
  //       the current position, or stay here if it's better
  //   OR
  // Algo: jump forward or backward, whichever is best
  //-------------------------------------------------

  /** PRE: t is a "wavy" list
      @returns: the index containing the minimum element
  */
  public static<E extends Comparable<E>> int minLocation(List<E> t) {
    return -1;  // TODO
  }

}