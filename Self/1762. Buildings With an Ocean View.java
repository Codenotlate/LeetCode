/* Initial thought
Using monotonic stack. Traverse array backwards with a stack of its index. If the heights[curi] > heights[stack.peek()], stack.push(curi).Since we need the res in increasing order, we can pop out the num in stack to res in the end.
e.g.[4,2,1,3,1] ocean
[4,3,0 -> res:[0,3,4]
time O(n) space O(n)
*/
class Solution {
    public int[] findBuildings(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        for (int i = heights.length - 1; i >= 0; i--) {
            if(stack.isEmpty() || heights[i] > heights[stack.peek()]) {stack.push(i);}
        }
        int[] res = new int[stack.size()];
        for (int i = 0; i < res.length; i++) {res[i] = stack.pop();}
        return res;
    }
}

/* FB Interview req:
just got a phone interview from Facebook. They asked this question, first part was to count how many buildings have an ocean view. The second part was iterate from the beginning and return the list of indexes. I used stack for the second part.
self answer: the first part can using similar idea as above, and no need for the stack, only need to keep track of the maxHeight from right till now, then update count each time we have a new maxHeight. time O(n) space O(1).
For the second part
*/

class Solution {
    public int[] findBuildings(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < heights.length; i++) {
            while(!stack.isEmpty() && heights[i] >= heights[stack.peek()]) {stack.pop();}
            stack.push(i);
        }
        int[] res = new int[stack.size()];
        for (int i = res.length - 1; i >= 0; i--) {res[i] = stack.pop();}               
        return res;
    }
}




/** 2024/4/2
Basically what we want is a strictly decreasing monoStack.
Time O(n) space O(n) for the stack if not view stack as output.
-----------------
We can of course also do right to left and only keep the maxRight, if the list can be viewed as output, then there's O(1) space used.
 */

class Solution {
    public int[] findBuildings(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < heights.length; i++) {
            while(!stack.isEmpty() && heights[stack.peek()] <= heights[i]) {
                stack.pop();
            }
            stack.push(i);
        }
        int[] res = new int[stack.size()];
        for (int i = res.length-1; i>= 0; i--) {
            res[i] = stack.pop();
        }
        return res;
    }
}