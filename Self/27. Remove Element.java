class Solution {
    public int removeElement(int[] nums, int val) {
        // set two pointers, one slow to label the end of the valid part
        // another fast to label the current element being scanned

        int s = -1;
        int f = 0;
        while (f < nums.length) {
        	if (nums[f] != val) {
        		nums[s + 1] = nums[f];
        		s++;
        	}
        	f++;
        }
        return s + 1;
    }
}


//20230801 Review
// idea: use the right side of the array to store duplicate vals
class Solution {
    public int removeElement(int[] nums, int val) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            if (nums[left] == val) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                right--;
            } else {
                left++;
            }
        }
        return left;
    }
}