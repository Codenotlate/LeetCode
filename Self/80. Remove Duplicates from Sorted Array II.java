class Solution {
    public int removeDuplicates(int[] nums) {
        //special case
        if (nums.length <= 2) {return nums.length;}
        int s = 1;
        int f = 2;
        while (f < nums.length) {
        	if (nums[f] != nums[s - 1]) {
        		nums[++s] = nums[f];
        	}
        	f++;
        }
        return s + 1;
    }
}