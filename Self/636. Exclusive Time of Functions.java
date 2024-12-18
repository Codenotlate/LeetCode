/* Initial thought
since this is the log of function callstack, I believe there won't have a log like function 0:[1,3] function1: [2:4], we will have something like "({[([[()]])]})".
Thus thinking about using stack. And for exclusive time calculation, the key part is to delete the time period occupied by another function, i.e.for [1(2)3{4}5], we need to delete time period in part2 and part4.
the stack is push with start timestamp, then when encounter end time, pop out and add(end-start+1) to corresponding id position. then update res[stack.peek()] -= end-start+1. This way when we do the res[i] += end-start+1 for the next peek timestamp, that inside part from another function call is excluded.

time O(n)
space O(n) for the stack
*/

class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] res = new int[n];
        Stack<int[]> stack = new Stack<>();
        for (String log: logs) {
            String[] parsed = log.split(":");
            int id = Integer.valueOf(parsed[0]);
            String status = parsed[1];
            int time = Integer.valueOf(parsed[2]);
            if(status.equals("start")) {
                stack.push(new int[]{id, time});
            } else {
                int period = time - stack.pop()[1] + 1;
                res[id] += period;
                if(!stack.isEmpty()){res[stack.peek()[0]] -= period;}
            }
        }
        return res;
    }
}

// https://leetcode.com/problems/exclusive-time-of-functions/discuss/153497/Java-solution-using-stack-wrapper-class-and-calculation-when-pop-element-from-the-stack.






// Review

/*Thought
Pattern like a stack problem. And since its function call, if we have an end, it must matches the peek start call. The key is how to exclude the time in between occupied by other function calls. 
whenever we pop out and calculate the time, we need to adjust the time of peek function call. But we should not change the time in the stack element itself, instead we can change res[i]. Because if we change the stack element we won't have original time for its prev element to adjust.
time O(n) space O(n)
*/
class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] res = new int[n];
        Stack<int[]> stack = new Stack<>();
        for (String log: logs) {
            String[] splits= log.split(":");
            int id = Integer.valueOf(splits[0]);
            int time =  Integer.valueOf(splits[2]);
            if (splits[1].equals("start")) {
                stack.push(new int[]{id,time});
            } else {
                int[] popped = stack.pop();
                int gap = time - popped[1] + 1;
                res[id] += gap;
                if (!stack.isEmpty()) {res[stack.peek()[0]] -= gap; }               
            }
        }
        return res;
    }
}