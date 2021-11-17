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