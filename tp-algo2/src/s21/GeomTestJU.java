
package s21;

import static org.junit.Assert.*;

import org.junit.Test;

import java.awt.Point;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import static s21.Geom.*;

public class GeomTestJU {

  @Test public void isInLeftAngleTest() {
    Point a=new Point(0,4), b=new Point(0,0), c=new Point(4,0), d=new Point(4,4);
    assertTrue (isInLeftAngle(d, a,b,c));
    assertFalse(isInLeftAngle(d, c,b,a));
    assertTrue (isInLeftAngle(d, a,c,b));
    assertFalse(isInLeftAngle(d, b,c,a));
    assertTrue (isInLeftAngle(d, b,a,c));
    assertFalse(isInLeftAngle(d, c,a,b));
  }

  @Test public void isInCcwOrderTest() {
    int [] px={0, 0, 1, 3, 8, 18, 38, 50};
    int [] py={0,50,38,18, 8,  3,  1,  0};
    Point[] polyg=new Point[px.length];
    for(int i=0; i<px.length; i++) 
      polyg[i]=new Point(px[i], py[i]);

    assertFalse(isInCcwOrder(polyg));
    List<Point> l=Arrays.asList(polyg);
    Collections.reverse(l);
    polyg=l.toArray(polyg);
    assertTrue(isInCcwOrder(polyg));

    polyg=new Point[]{new Point(0,4), new Point(0,0), new Point(4,0)};
    assertTrue(isInCcwOrder(polyg));
    polyg=new Point[]{new Point(4,0), new Point(0,0), new Point(0,4)};
    assertFalse(isInCcwOrder(polyg));
  }

  @Test public void intersectTest() {
    Segm a1=new Segm(new Point(0,0), new Point(5,5));
    Segm a2=new Segm(new Point(1,1), new Point(3,3));
    Segm a3=new Segm(new Point(1,1), new Point(8,8));
    Segm a4=new Segm(new Point(8,8), new Point(9,9));
    assertTrue(intersect(a1,a2));
    assertTrue(intersect(a2,a1));
    assertTrue(intersect(a1,a3));
    assertTrue(intersect(a3,a1));
    assertFalse(intersect(a1,a4));
    assertFalse(intersect(a4,a1));
    Segm b1=new Segm(new Point(2,2), new Point(0,4));
    Segm b2=new Segm(new Point(5,5), new Point(0,10));
    Segm b3=new Segm(new Point(8,8), new Point(0,16));
    assertTrue(intersect(a1,b1));
    assertTrue(intersect(b1,a1));
    assertTrue(intersect(a1,b2));
    assertTrue(intersect(b2,a1));
    assertFalse(intersect(a1,b3));
    assertFalse(intersect(b3,a1));
  }

  @Test public void isOnSegmentTest() {
    Segm s=new Segm(new Point(0,0), new Point(5,5));
    assertTrue(isOnSegment(new Point(3,3), s));
    assertTrue(isOnSegment(new Point(5,5), s));
    assertFalse(isOnSegment(new Point(6,6), s));
    assertFalse(isOnSegment(new Point(-6,-6), s));
    assertFalse(isOnSegment(new Point(-6, 6), s));
  }

  @Test public void isInPolygonTest() {
    Point[] polyg=new Point[]{new Point(0,4), new Point(0,0), new Point(4,0), new Point(5,4)};
    assertTrue(isInPolygon(polyg, new Point(4,2)));
    assertFalse(isInPolygon(polyg, new Point(0,8)));
  }

}
