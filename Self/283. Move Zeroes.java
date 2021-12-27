class Solution {
    public void moveZeroes(int[] nums) {
        int pre = -1;
        int cur = 0;
        while (cur < nums.length) {
        	if (nums[cur] != 0) {
        		nums[++pre] = nums[cur];
        	}
        	cur++;
        }
        while (pre + 1 < nums.length) {
        	nums[pre + 1] = 0;
        	pre++;
        }
    }
}


// Phase3 self
class Solution {
    public void moveZeroes(int[] nums) {
        int pre = -1;
        int cur = 0;
        while (cur < nums.length) {
        	if (nums[cur] != 0) {
        		nums[++pre] = nums[cur];
        		if (pre != cur) {
        			nums[cur] = 0;
        		}
        	}
        	cur++;
        }
    }
}

// Review self
class Solution {
    public void moveZeroes(int[] nums) {
        int endp = 0;
        int i = 0;
        while ( i < nums.length) {
            if (nums[i] == 0) {i++;}
            else {
                int temp = nums[i];
                nums[i] = nums[endp];
                nums[endp++] = temp;
                if (endp > i) {i = endp;}
            }
        }
    }
}