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



// Phase3 self: moving backwards: more intuitive than first two methods
// time O(n) space O(n)
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        Stack<Integer> stack = new Stack<>();
        stack.push(n-1);
        for (int i = n - 2; i >= 0; i--) {
            while (!stack.isEmpty() && temperatures[stack.peek()] <= temperatures[i]) {
                stack.pop();
            }
            if(!stack.isEmpty()) {answer[i] = stack.peek() - i;}
            stack.push(i);        
        }
        return answer;
    }
}


// M2: use answer it self and a peekIdx to mimic the function of a stack, optimize space to O(1)
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        int peekIdx = n - 1;
        for (int i = n - 2; i >= 0; i--) {
            while (peekIdx < n && temperatures[peekIdx] <= temperatures[i]) {
            	// pay attention to when answer[peekIdx] is 0, meaning, the stack is empty
                if(answer[peekIdx] == 0) {peekIdx = n;}
                else {peekIdx += answer[peekIdx];}
            }
            if(peekIdx < n) {answer[i] = peekIdx - i;}
            peekIdx = i;       
        }
        return answer;
    }
}








