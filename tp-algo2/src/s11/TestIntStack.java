package s11;

import java.util.Random;

import org.junit.Test;

public class TestIntStack {
	
	private static final int N_TESTS = 150;
	
	@Test
	public void simpleTest() {
		IntStack stack = new IntStack();
		Random r = new Random();
		int[] values = new int[N_TESTS];
		
		assert(stack.isEmpty());			//empty at the beginning
		for (int i = 0; i < N_TESTS; i++) {
			values[i]= r.nextInt();
			stack.push(values[i]);
			assert(!stack.isEmpty());		//never empty
		}
		
		for (int i = N_TESTS-1; i > -1; i--) {
			int top = stack.top();
			int val = stack.pop();
			assert(top == val);
			assert(val == values[i]);		//pop in the right order
		}
		assert(stack.isEmpty());			//empty after removing all the elements
	}

}
