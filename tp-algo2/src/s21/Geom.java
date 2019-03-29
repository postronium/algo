package s21;
import java.awt.Point;

public class Geom {

  public static int signedArea(Point p1, Point p2, Point p3) {
    return (p2.x-p1.x)*(p3.y-p1.y) - (p3.x-p1.x)*(p2.y-p1.y);
    // negative if clockwise; twice the area of the triangle p1-p2-p3
  }

  public static int ccw(Point p1, Point p2, Point p3) {
    int a = signedArea(p1, p2, p3);
    if (a < 0) return -1;
    if (a > 0) return +1;
    return 0;
  }

  public static boolean intersect(Segm s0, Segm s1) {
    // TODO - A COMPLETER..
    return ((ccw(s0.from(), s0.to(), s1.from())
            *ccw(s0.from(), s0.to(), s1.to())) <= 0)
        && ((ccw(s1.from(), s1.to(), s0.from())
            *ccw(s1.from(), s1.to(), s0.to())) <= 0);
  }

  public static boolean isInLeftAngle(Point query, Point a, Point b, Point c) {
    return true; // TODO - A COMPLETER..
  }

  public static boolean isInTriangle(Point query, Point a, Point b, Point c) {
    return true; // TODO - A COMPLETER..
  }

  public static boolean isOnSegment(Point p, Segm s) {
    return true; // TODO - A COMPLETER..
  }

  public static boolean isInCcwOrder(Point [] simplePolygon) {
    return true; // TODO - A COMPLETER..
  }

  public static boolean isInPolygon(Point [] polyg, Point p) {
    return true; // TODO - A COMPLETER..
  }

  public static void main(String[] args) {
    s21.CG.main(args);
  }

}
