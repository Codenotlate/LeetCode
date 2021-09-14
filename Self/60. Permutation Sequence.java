// Phase3 almost self
// k - 1 is crucial here
// time O(n^2) space O(n)
class Solution {
    public String getPermutation(int n, int k) {
        // build the array for the nums and also the lookup array for factorial
        List<Integer> nums = new LinkedList<>();
        int[] factorials = new int[n];
        nums.add(1);
        factorials[0] = 1;
        for (int i = 1; i < n; i++) {
            nums.add(i + 1);
            factorials[i] = factorials[i - 1] * (i + 1);
        }
        StringBuilder res = new StringBuilder();
        
        recur(nums, factorials, res, n, k);
        return res.toString();
    }
    
    
    private void recur(List<Integer> nums, int[] factorials, StringBuilder res, int n, int k) {
        //base case
        if (k  == 1) {
            for (int i = 0; i < nums.size(); i++) {
                res.append(nums.get(i));
            }
            return;
        }
        
        int factor = factorials[n - 2];
        int i = (k - 1)/factor;
        res.append(nums.get(i));
        nums.remove(i);
        recur(nums, factorials, res, n - 1, k - i * factor);
    }
}

// see also iterative way 
// https://leetcode.com/problems/permutation-sequence/discuss/22507/%22Explain-like-I'm-five%22-Java-Solution-in-O(n)