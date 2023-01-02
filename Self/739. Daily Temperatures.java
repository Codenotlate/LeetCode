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



// Review
/*Initial thought
similar idea using monotone stack. Time O(n) space O(n)
improved way with O(1) space: The key is to move backwards and for i, utilize the answer res[i+1] to skip the days in middle and directly jump to i + res[x].
Also keep track of largest till now, so that if current val >= largest, then we can simply skip it and move backwards.
*/
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[temperatures.length];
        for (int i = 0; i < temperatures.length; i++) {
            while(!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                int popIdx = stack.pop();
                res[popIdx] = i - popIdx;
            }
            stack.push(i);
        }
        return res;
    }
}
// Need to review this method
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] res = new int[n];
        int peekIdx = n - 1;
        for (int i = n - 2;i >= 0; i--) {
            while(peekIdx < n && temperatures[peekIdx] <= temperatures[i]) {
                if (res[peekIdx] == 0) {peekIdx = n;}
                else {peekIdx += res[peekIdx];}
                
            }
            if (peekIdx < n) {res[i] = peekIdx - i;}
            peekIdx = i;
        }
        return res;
    }
}




// review 23/1/2
/*Thoughts
Method1: monotonic stack, put in index number, when stack peek num is smaller then cur num, pop the index out, res of the popIdx will be curIdx - popIdx, keep popping out until cur num is smaller or equal to peek num.
time and space O(n)

Method2: try O(1) space
first do similar monotonic stack as M1 but in backwards way. Then view the res array as an stack itself, record down the peekIdx representing the peek num in the stack, and the next num in the stack after peekIdx pop out will be peekIdx + res[peekIdx].
Thus we have time O(n) but space O(1)

 */
// M1: monotonic stack
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] res = new int[n];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while(!stack.isEmpty() && temperatures[i]> temperatures[stack.peek()]) {
                int popIdx = stack.pop();
                res[popIdx] = i - popIdx;
            }
            stack.push(i);
        }
        return res;
    }
}

// M2: backwards + res array as monotonic stack
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] res = new int[n];
        int peekIdx = n;
        for (int i = n - 1; i >= 0; i--) {
            while(peekIdx!=n && temperatures[peekIdx] <= temperatures[i]) {
                peekIdx = res[peekIdx] == 0 ? n : res[peekIdx] + peekIdx;
            }
            res[i] = peekIdx == n ? 0 : peekIdx - i;
            peekIdx = i;
        }
        
        return res;
    }
}


