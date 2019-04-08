package s21;

import java.awt.Point;

public class Geom {

    private static double PRECISION = 0.000001;
    private static Point OUT_OF_WORLD = new Point(99999, 99999);

    public static int signedArea(Point p1, Point p2, Point p3) {
        return (p2.x - p1.x) * (p3.y - p1.y) - (p3.x - p1.x) * (p2.y - p1.y);
        // negative if clockwise; twice the area of the triangle p1-p2-p3
    }

    public static int ccw(Point p1, Point p2, Point p3) {
        int a = signedArea(p1, p2, p3);
        if (a < 0) return -1;
        if (a > 0) return +1;
        return 0;
    }

    public static boolean intersect(Segm s0, Segm s1) {
        if (isOnSegment(s0.from(), s1) || isOnSegment(s0.to(), s1) ||       //one of the point of s0 is on the line s1
                isOnSegment(s1.from(), s0) || isOnSegment(s1.to(), s0)) {   //one of the point of s1 is on the line s0
            return true;
        }

        return ((ccw(s0.from(), s0.to(), s1.from())
                * ccw(s0.from(), s0.to(), s1.to())) < 0)
                && ((ccw(s1.from(), s1.to(), s0.from())
                * ccw(s1.from(), s1.to(), s0.to())) < 0);
    }

    public static boolean isInLeftAngle(Point query, Point a, Point b, Point c) {
        boolean convex = ccw(a, b, c) >= 0;
        if (convex) {
            return (ccw(a, b, query) >= 0 && ccw(c, b, query) < 0);
        } else {
            return (ccw(a, b, query) >= 0 || ccw(c, b, query) < 0);
        }
    }

    //flat triangle with a point on the edges of the triangle returns true
    public static boolean isInTriangle(Point query, Point a, Point b, Point c) {
        boolean isCcw = ccw(a, b, c) >= 0 ;
        if (isCcw) {
            return ccw(a, b, query) >= 0 &&
                    ccw(b, c, query) >= 0 &&
                    ccw(c, a, query) >= 0;
        } else {
            return ccw(a, b, query) <= 0 &&
                    ccw(b, c, query) <= 0 &&
                    ccw(c, a, query) <= 0;
        }
    }

    public static boolean isOnSegment(Point p, Segm s) {
        if (Math.abs(ccw(s.from(), s.to(), p)) > PRECISION)
            return false;

        //check if the point is in the smallest rectangle that can contain the lines
        return ((p.x >= s.from().x && p.x <= s.to().x) || (p.x <= s.from().x && p.x >= s.to().x)) && //check for x axe
                ((p.y >= s.from().y && p.y <= s.to().y) || (p.y <= s.from().y && p.y >= s.to().y));  //check for y axe
    }

    //return false if the polygon has less than 3 points
    //inspired by CRM
    public static boolean isInCcwOrder(Point[] simplePolygon) {
        Point[] pts = simplePolygon;        //make it more readable
        if (simplePolygon.length < 3)
            return false;
        int len = simplePolygon.length;
        double expectedAngleSum = Math.PI + (len-3)*Math.PI;
        double calculatedSum = 0.0;
        for (int i = 0; i < len; i++) {     //for each angle
            Point p1 = pts[i];
            Point p2 = pts[(i+1)%len];
            Point p3 = pts[(i+2)%len];
            calculatedSum += getAngle(p1, p2, p3);
        }

        System.out.println("################################ " + pts.length + " pts");
        System.out.println("  expected angle : " + expectedAngleSum);
        System.out.println("  calculated angle : " + calculatedSum);

        return Math.abs(calculatedSum - expectedAngleSum) < PRECISION;

    }

    /**
     *  Return the angle between the lines p1->p2 and p2->p3
     * @param p1 first point
     * @param p2 middle point
     * @param p3 last point
     * @return the angle p1^p3 in radiant
     */
    private static double getAngle(Point p1, Point p2, Point p3) {
        double area = signedArea(p1, p2, p3);
        if (area < 0) {         // clockwise
            area = -area;
            return 2*Math.PI - Math.asin(area / (Point.distance(p1.x, p1.y, p2.x, p2.y) * Point.distance(p2.x, p2.y, p3.x, p3.y)));
        } else if (area == 0) {
            return Math.PI;
        }

        //counter clockwise
        return Math.asin(area / (Point.distance(p1.x, p1.y, p2.x, p2.y) * Point.distance(p2.x, p2.y, p3.x, p3.y)));
    }

    public static boolean isInPolygon(Point[] polyg, Point p) {
        Segm testLine = new Segm(p, OUT_OF_WORLD);
        int nIntersections = 0;
        for (int i = 0; i < polyg.length; i++) {
            if (intersect(testLine, new Segm(polyg[i], polyg[(i+1)%polyg.length])))
                nIntersections++;
        }
        return nIntersections%2 == 1;
    }

    public static void main(String[] args) {
        s21.Geom.main(args);
    }

}
