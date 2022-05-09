/* Thought
According to math, if a > 0, two sides will be larger then middle ones, thus we can fill the res from backwards. And if a < 0, then middle ones will be larger, then we can fill the res forwards. If a == 0, we are good with either way. we will have two pointers pointing to start and end of the array and decide which side to move based on res.

Time O(n) space O(1)
*/
class Solution {
    public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
        int[] res = new int[nums.length];
        int left = 0;
        int right = nums.length - 1;
        int idx = a > 0 ? nums.length-1 : 0;
        while (left <= right) {
            int leftres = cal(nums[left],a,b,c);
            int rightres = cal(nums[right],a,b,c);
            if (a > 0) {                
                if (leftres > rightres) {
                    res[idx--] = leftres;
                    left++;
                } else {
                    res[idx--] = rightres;
                    right--;
                }
            } else {
                if (leftres < rightres) {
                    res[idx++] = leftres;
                    left++;
                } else {
                    res[idx++] = rightres;
                    right--;
                }
            }
        }
        return res;
    }
    
    
    private int cal(int x, int a, int b, int c) {
        return a * x * x + b * x + c;
    }
}


// more elegant way in comment: https://leetcode.com/problems/sort-transformed-array/discuss/83322/Java-O(n)-incredibly-short-yet-easy-to-understand-AC-solution