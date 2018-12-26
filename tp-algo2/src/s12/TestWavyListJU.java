package s12;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class TestWavyListJU {
	private static final int MAX_PTS = 100;
	private static final int N_TESTS = 100;
	private static final int MAX_PT_DIFF = 50;
	private static Random r = new Random();
	
	private static class Point implements Comparable<Point> {
		public int val;
		public Point(int val) { this.val = val; }
		@Override
		public int compareTo(Point pt) { return this.val - pt.val; }
	}

	@Test
	public void testRandomValues() {
		for (int i = 0; i < N_TESTS; i++) {
			List<Point> pts = generateDataWithPeek(MAX_PTS);
			int result = WavyListSearching.minLocation(pts);
			checkResult(pts, result);
		}
	}
	
	private static void checkResult(List<Point> pts, int result) {
		if (result >= pts.size())
			System.out.println("the index returned by the tested function is bigger than the biggest index");
		assert(result < pts.size());
		
		if (pts.get(0).compareTo(pts.get(pts.size()-1)) < 0) {
			if (result != 0)
				System.out.printf("The returned result is wrong. Expected %d returned %d\n", 0, result);
			assert(result == 0);
		} else {
			if (result != pts.size()-1)
				System.out.printf("The returned result is wrong. Expected %d returned %d\n", pts.size()-1, result);
			assert(result == pts.size()-1);
		}
	}
	
	private static Point getLowerPoint(Point prev) {
		int ptDiff = r.nextInt(MAX_PT_DIFF-1)+1;
		Point newPt = new Point(prev.val-ptDiff);
		return newPt;
	}
	
	private static Point getHigherPoint(Point prev) {
		int ptDiff = r.nextInt(MAX_PT_DIFF-1)+1;
		Point newPt = new Point(prev.val+ptDiff);
		return newPt;
	}

	// generate only y position of each point
	private static List<Point> generateDataWithPeek(int maxPt) {
		List<Point> p = new ArrayList<Point>();
		
		int nPts = r.nextInt(maxPt - 1)/2 + 1;
		p.add(new Point(nPts));
		
		for (int i = 1; i < nPts; i++)
			p.add(getHigherPoint(p.get(p.size()-1)));
		
		for (int i = 0; i < nPts; i++)
			p.add(getLowerPoint(p.get(p.size()-1)));
		
		//prevent to return a list with 2 min values
		if (p.get(0).compareTo(p.get(p.size()-1)) == 0 && p.size() > 1)
			p.remove(p.size()-1);

		return p;
	}

}
