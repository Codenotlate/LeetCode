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