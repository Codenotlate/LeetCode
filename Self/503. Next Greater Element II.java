class Solution {
	// simialr as 739
    public int[] nextGreaterElements(int[] nums) {
        // use a stack to store the index of the elements haven't find the next closest larger elements
	    int[] stack = new int[nums.length];
	    int head = -1;
	    int[] res = new int[nums.length];
 	    for (int i = 0; i < 2 * nums.length; i++) {
	    	int curIdx = i % nums.length;
	    	while (head != -1 && nums[curIdx] > nums[stack[head]]) {	    		
	    		res[stack[head]] = nums[curIdx];
	    		head -= 1;
	    	}
	    	// only add to stack when it's in the first round of loop
	    	if (i < nums.length) {
	    		stack[++head] = curIdx;
	    	}	    	
	    }
	    // for what left in stack, there's no later element larger than it
	    while (head != -1) {
	    	res[stack[head--]] = -1;
	    }
	    return res;
    }
}