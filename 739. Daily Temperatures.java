class Solution {
    public int[] dailyTemperatures(int[] T) {
        // use a stack to store the index of the elements haven't find the next closest larger elements
	    Stack<Integer> stack = new Stack<>();
	    int[] res = new int[T.length];
 	    for (int curIdx = 0; curIdx < T.length; curIdx++) {
	    	while (!stack.isEmpty() && T[curIdx] > T[stack.peek()]) {
	    		int idx = stack.pop();
	    		res[idx] = curIdx - idx;
	    	}
	    	stack.add(curIdx);
	    }
	    // for what left in stack, there's no later element larger than it
	    while (!stack.isEmpty()) {
	    	res[stack.pop()] = 0;
	    }
	    return res;
    }
}

class Solution {
	// replace th stack in solution1 with array and head idx
	// way faster than solution1
    public int[] dailyTemperatures(int[] T) {
        // use a stack to store the index of the elements haven't find the next closest larger elements
	    int[] stack = new int[T.length];
	    int head = -1;
	    int[] res = new int[T.length];
 	    for (int curIdx = 0; curIdx < T.length; curIdx++) {
	    	while (head != -1 && T[curIdx] > T[stack[head]]) {
	    		int idx = stack[head];
	    		res[idx] = curIdx - idx;
	    		head -= 1;
	    	}
	    	stack[++head] = curIdx;
	    }
	    // for what left in stack, there's no later element larger than it
	    while (head != -1) {
	    	res[stack[head--]] = 0;
	    }
	    return res;
    }
}


// another way: walking backwards
//https://leetcode.com/problems/daily-temperatures/discuss/384123/100-Speed-and-100-Space-C%2B%2B
// https://leetcode.com/problems/daily-temperatures/discuss/468875/DP-solution-beats-100












