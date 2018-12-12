package s11;

import java.util.Random;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class StackTestJU {
  private IntStack s1, s2;
  private static Random rnd = new Random();

  @Before
  public void setUp() {
    s1 = new IntStack(10);
    s2 = new IntStack();
  }

  @Test
  public void testNewIsEmpty() {
    assertTrue(s1.isEmpty() && s2.isEmpty());
  }

  @Test
  public void testPushThenPop() {
    s1.push(4);
    assertTrue(4==s1.pop());
  }
}
