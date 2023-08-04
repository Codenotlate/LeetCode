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





// 23/8/4 Review
/*
Three cases: cur == 0 || nums[cur] != nums[cur-1], append, count = 1, move both
            nums[cur] == nums[cur-1] && count < 2, append, count =2 , move both
            nums[cur] == nums[cur-1] && count == 2, move cur

This way uses a counter. Above way directly compare to the second last element in valid array. Even better.
 */
class Solution {
    public int removeDuplicates(int[] nums) {
        int resEnd = 0;
        int cur = 0;
        int count = 0;
        while (cur < nums.length) {
            if (cur == 0 || nums[cur] != nums[resEnd-1]) {
                nums[resEnd++] = nums[cur];
                count = 1;
            } else if (count < 2) {
                nums[resEnd++] = nums[cur];
                count = 2;
            } 
            cur++;
        }
        return resEnd;
    }
}