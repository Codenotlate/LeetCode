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

// Phase3 self
// loop twice since it's circular.
// time O(n) space O(n)
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[nums.length];
        Arrays.fill(res, -1);
        for (int i = 0; i < 2 * nums.length; i++) {
            int idx = i % nums.length;
            while(!stack.isEmpty() && nums[idx] > nums[stack.peek()]) {
                res[stack.pop()] = nums[idx];
            }
            // only need to add to stack when it's in first round
            if (i < nums.length) {stack.push(idx);}           
        }
        return res;
    }
}

// Review
/*initial thought
for circular array, always make the index range double array size and do (i % size) to get the element.
Using a stack, push in the index, and only push in the index if nums[curIdx] <= nums[stack.peek()]. Otherwise, pop out and res[popIdx] = nums[curIdx] till <= is not the case. Then push curIdx in.
Init int[] res with -1. i range in (0, 2*len-1). if res[i%len] != -1, don't push i into the stack.
time O(2n) = O(n) space O(n)
e.g. [3,2,4,1,4,2,1]
stack = [2(4), 4(4),2(4),4(4) 
res = [4,4,-1,4,-1,3,3]
*/
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int n = nums.length;
        int[] res = new int[n];
        Arrays.fill(res, -1);
        for(int i = 0; i < 2 * n; i++) {
            int idx = i % n;
            while (!stack.isEmpty() && nums[stack.peek()] < nums[idx]) {
                int popIdx = stack.pop();
                res[popIdx] = nums[idx];
            }
            if(res[idx] == -1) {stack.push(idx);}
        }
        return res;
    }
}




/** 2024.4.10
Basically same idea as above, adding a stop early condition
Time O(n) Space O(n) 

*/
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int n = nums.length;
        int[] res = new int[n];
        Arrays.fill(res, -1);
        int cur = 0;
        while (cur < nums.length * 2) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[cur%n]) {
                res[stack.pop()] = nums[cur%n];
            }
            if (!stack.isEmpty() && stack.peek() == cur%n) {break;}
            if (res[cur%n] == -1) {stack.push(cur%n);}
            cur++;
        }
        return res;
    }
}