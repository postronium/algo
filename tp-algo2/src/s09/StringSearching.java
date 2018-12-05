package s09;

import java.util.Random;

// ------------------------------------------------------------ 
public class StringSearching {
	// ------------------------------------------------------------
	static /* final */ int HASHER = 301; // Maybe also try with 7 and 46237
	static /* final */ int BASE = 256; // Please also try with 257
	// ---------------------

	static int firstFootprint(String s, int len) {
		int hash = 0;
		for (int i = 0; i < len; i++) {
			hash = (hash*BASE + s.charAt(i))%HASHER;
		}
		return hash;
	}

	// ---------------------
	// must absolutely be O(1)
	// coef is (BASE power P.LENGTH-1) mod HASHER
	static int nextFootprint(int previousFootprint, char dropChar, char newChar, int coef) {
		int hash = previousFootprint;
		
		hash = hash - (coef*dropChar)%HASHER;
		if (hash < 0) hash += HASHER;
		
		hash = (hash*BASE + newChar)%HASHER;
		return hash;
	}

	// ---------------------
	// Rabin-Karp algorithm
	public static int indexOf_rk(String t, String p) {
		int tHash = firstFootprint(t, p.length());
		int pHash = firstFootprint(p, p.length());
		int coef = 1;
		for (int i = 0; i < p.length()-1; i++)
			coef = (coef*BASE)%HASHER;
		
		if (tHash == pHash && checkEquality(t.substring(0, p.length()), p))
			return 0;
		
		for (int i = 0; i < t.length() - p.length(); i++) {
			tHash = nextFootprint(tHash, t.charAt(i), t.charAt(i+p.length()), coef);
			if (tHash == pHash && checkEquality(t.substring(i+1, p.length()+i+1), p))
				return i+1;
		}
		
		return -1;
	}
	
	private static boolean checkEquality(String t, String p) {
		for (int i = 0; i < t.length(); i++)
			if (t.charAt(i) != p.charAt(i))
				return false;
		return true;
	}
}
