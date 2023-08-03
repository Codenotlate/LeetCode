class Solution {
    public int removeDuplicates(int[] nums) {
        // similar to 27, again use two pointers
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



// 23/8/1 review
// since now we need to keep relative order, we can no longer use the way in 27 to swap with the right side
class Solution {
    public int removeDuplicates(int[] nums) {
        int resEnd = 0;
        int cur = 0;;
        while (cur < nums.length) {
            if (cur == 0 || nums[cur] != nums[cur-1]){
                nums[resEnd++] = nums[cur];
            } 
            cur++;
        }
        return resEnd;
    }
}