class Solution {
    public void rotate(int[] nums, int k) {
    	// The idea is to seperate two parts and reverse separatedly
    	// Then reverse the whole array
    	int n = nums.length;
    	k = k % n;
    	int first = 0;
    	int second = n - k;
    	reverse(nums, first, second - 1);
    	reverse(nums, second, n - 1);
    	reverse(nums, first, n - 1);
    }


    private void reverse(int[] nums, int start, int end) {
    	while (start < end) {
    		int temp = nums[start];
    		nums[start] = nums[end];
    		nums[end] = temp;

    		start++;
    		end--;
    	}
    }
}

// can also reverse as a whole and then reverse each part separated by k
/* https://leetcode.com/problems/rotate-array/discuss/54250/Easy-to-read-Java-solution
nums = "----->-->"; k =3
result = "-->----->";

reverse "----->-->" we can get "<--<-----"
reverse "<--" we can get "--><-----"
reverse "<-----" we can get "-->----->"
this visualization help me figure it out :)
*/