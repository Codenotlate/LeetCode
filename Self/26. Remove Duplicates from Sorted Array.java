class Solution {
    public int removeDuplicates(int[] nums) {
        // similar to 26, again use two pointers
        if (nums.length <= 1) {return nums.length;}
        int s = 0;
        int f = 1;
        while (f < nums.length) {
        	if (nums[f] != nums[s]) {
        		nums[s + 1] = nums[f];
        		s++;
        	}
        	f++;
        }
        return s + 1;
    }
}