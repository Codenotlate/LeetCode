class Solution {
    public int[] getModifiedArray(int length, int[][] updates) {
        // Phase 3 self (from solution)
        // basic idea
        // for each element in updates, only add val to idx = start and subtract val to idx = end + 1
        // loop the arr once in the end, recal the accumulated prefix sum
        int[] res = new int[length];
        for (int[] range: updates) {
            res[range[0]] += range[2];
            if (range[1] < length - 1) {
                res[range[1] + 1] -= range[2];
            }
        }
        
        for (int i = 1; i < length; i++) {
            res[i] += res[i-1]; 
        }
        return res;
    }
}


// further thoughts
// https://leetcode.com/problems/range-addition/solution/


// Review self
class Solution {
    /* Initial thought
    naive idea time O(len * n). Try to reduce len part: Instead of apply inc to each position in the range(i), we label the start of the range with +inc, and [end+1] with -inc. This only takes O(1) time for each range. In the end, we accumulate the sum of the inc in each position. Because we did -inc for range starting [end+1], when we do accum sum, the effect is actually no inc for positions on range starting [end+1].
    time O(n) space O(1)    
    */
    public int[] getModifiedArray(int length, int[][] updates) {
        int[] arr = new int[length];
        for (int[] range: updates) {
            int start = range[0];
            int end = range[1];
            int inc = range[2];
            arr[start] += inc;
            if(end+1 < length) {arr[end+1] -= inc;}            
        }
        
        // accumulate sum
        for (int i = 1; i < length; i++) {
            arr[i] += arr[i-1]; 
        }
        return arr;
    }
}





// Review
class Solution {
    public int[] getModifiedArray(int length, int[][] updates) {
        int[] res = new int[length];
        for (int[] update: updates) {
            int val = update[2];
            int start = update[0];
            int end = update[1];
            res[start] += val;
            if (end + 1 < length) {res[end+1] += -val;}
        }
        for (int i = 1; i < length; i++) {
            res[i] +=res[i-1];
        }
        return res;
    }
}