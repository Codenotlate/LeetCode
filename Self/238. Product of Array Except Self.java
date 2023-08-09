// Phase3 self 
// Time O(2N) = O(N) space  O(1) excluding space for res

class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] ans = new int[nums.length];
        ans[0] = nums[0];
        // first pass, get the preProd 
        for (int i = 1; i < nums.length; i++) {
        	ans[i] = ans[i - 1] * nums[i];
        }
        // second pass, update the answer from right to left with a var sufProd
        int sufProd = 1;
        for (int i = nums.length - 1; i >= 1; i--) {
        	ans[i] = sufProd * ans[i - 1];
        	sufProd *= nums[i];
        }
        ans[0] = sufProd;
        return ans;
    }
}


// Review self: similar as above
class Solution {
    // two loops, first one to get preproduct for each position and stored in the result array, second one to go from end to start to get sufproduct, and multiplies with the preproduct for this position.
    // time O(n) space O(1)
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        for (int i=0; i< n; i++) {
            if(i == 0) {res[i] = 1; continue;}
            res[i] = res[i-1] * nums[i-1];
        }
        int sufproduct = 1;
        for (int i = n-1; i >= 0; i--) {
            res[i] *= sufproduct;
            sufproduct *= nums[i];
        }
        
        return res;
    }
}





// Review 23/8/9 - same method as above
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] result = new int[nums.length];
        int cumsum = 1;
        for (int i = 0; i < nums.length; i++) {
            cumsum *= nums[i];
            result[i] = cumsum;
        }
        cumsum = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            int leftSide = i == 0 ? 1: result[i-1];
            result[i] = leftSide * cumsum;
            cumsum *= nums[i];
        }
        return result;
    }
}


/*
Check the post: [3 Minute Read] Mimicking an Interview
How to go from brute force to the final stage.

*/


