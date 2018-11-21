package s09;

import java.util.Random;
// ------------------------------------------------------------ 
public class StringSearching {
  // ------------------------------------------------------------ 
  static /*final*/ int HASHER = 301; // Maybe also try with 7 and 46237
  static /*final*/ int BASE   = 256; // Please also try with 257
  // ---------------------
  static int firstFootprint(String s, int len) {
    return -1;
    // TODO - A COMPLETER
  }
  // ---------------------
  // must absolutely be O(1)
  // coef is (BASE  power  P.LENGTH-1)  mod  HASHER
  static int nextFootprint(int previousFootprint, char dropChar, char newChar, int coef) {
    return -1;
    // TODO - A COMPLETER
    // h = previousFootprint
    // h = h - ...               // dropChar        (bien réfléchir !)
    // h = h * ...               // shift
    // h = h + ...               // newChar
  }
  // ---------------------
  // Rabin-Karp algorithm
  public static int indexOf_rk(String t, String p) {
    return -1;
    // TODO - A COMPLETER
  }
}
