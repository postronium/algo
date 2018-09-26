package s02;

import java.util.Arrays;

public class IntQueueChained {
	//======================================================================
	/* TODO: adapt using pseudo-pointers instead of queue node objects
	 * "Memory management" code:
	 * - define "memory" arrays, the NIL constant, and firstFreeCell
	 * - define allocate/deallocate, with automatic array expansion
	 * "User" code:
	 * - modify enqueue/dequeue/..., keeping the same logic/algorithm
	 * - test
	 */
	//======================================================================
	
	private static final int DEFAULT_MEME_SIZE = 1024;	//number of data elements at the initialisation of the memory
	private static final int NIL = -1;					//null pointer of (used for the last element)								
	
	private static int[] data = new int[DEFAULT_MEME_SIZE];		//memory data
	private static int[] ptr = new int[DEFAULT_MEME_SIZE];		//memory pointer to next data element
	
	private static int firstFreeCell = 0;
	private static int actualMemSize = DEFAULT_MEME_SIZE;
  
	/*
	 * Initialise empty memory pointers
	 */
	static {
		for (int i = 0; i < DEFAULT_MEME_SIZE-1; i++) {
			data[i] = 0;
			ptr[i] = i+1;
		}
		data[DEFAULT_MEME_SIZE-1] = 0;
		ptr[DEFAULT_MEME_SIZE-1] = NIL;
	}
	
	private static int allocate() {
		int allocated = firstFreeCell;			//pointer to newly allocated memory
	  
		firstFreeCell = ptr[firstFreeCell];
		if (firstFreeCell == NIL)
			doubleMem();			//double memory if needed
	  
		return allocated;
	}

  
	/*
	 * Unallocate the memory at the given pointer and the num-1 following elements
	 * If num = NIL the memory is unallocated until NIL is reached
	 * PRE: the pointer doesn't point to a free memory cell
	 * memPtr: Pointer to the memory to unallocate
	 */
	private static void unallocate(int memPtr, int num) {
		if (num == 0) return;
		if (ptr[memPtr] != NIL) {
			unallocate(ptr[memPtr], num-1);
		}
		ptr[memPtr] = firstFreeCell;
		ptr[firstFreeCell] = memPtr;
	}
  
  	private static void doubleMem() {
  		int newMemSize = 2 * actualMemSize;
	  	Arrays.copyOf(ptr, newMemSize);
	  	Arrays.copyOf(data, newMemSize);
	  
	  	for (int i = actualMemSize; i < newMemSize-1; i++) {
	  		ptr[i] = i+1;
	  	}
	  	
	  	ptr[newMemSize-1] = firstFreeCell;
	  	firstFreeCell = actualMemSize;
	  	
	  	actualMemSize = newMemSize;
	}
  
  
  
  
  
  	//======================================================================
  	private int frontPtr = NIL;
  	private int backPtr = NIL;
  	// ------------------------------
  	public IntQueueChained() {}
  	// --------------------------
  
  	public void enqueue (int elt) {
  		if (frontPtr == NIL && backPtr == NIL) {
  			backPtr = allocate();
  			frontPtr = backPtr;
  			ptr[backPtr] = NIL;
  		} else {
  			ptr[backPtr] = allocate();
  			backPtr = ptr[backPtr];
  		}
  		data[backPtr] = elt;
  	}
  	// --------------------------
  	public boolean isEmpty() {
  		return backPtr == NIL;
  	}
  	// --------------------------
  	public int consult() {
  		return data[frontPtr];
  	}
  	// --------------------------
  	public int dequeue() {
	  int unallocPtr = frontPtr;
    	int removedData = data[frontPtr];
    	frontPtr = ptr[frontPtr];
    
    	unallocate(unallocPtr, 1);
    
    	return removedData;
  	}
  
  	@Override
  	public void finalize() {
	  	if (frontPtr != NIL) {
		  	unallocate(frontPtr, NIL);
	  	}
  	}
}
