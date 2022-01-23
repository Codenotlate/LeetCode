// self phase 3: time O(3n) = O(n), space O(1)
// related posts:
// https://leetcode.com/problems/first-missing-positive/discuss/319270/Explanation-of-crucial-observation-needed-to-deduce-algorithm
// https://leetcode.com/problems/first-missing-positive/discuss/17214/Java-simple-solution-with-documentation
class Solution {
    public int firstMissingPositive(int[] nums) {
        // first loop: since we only care about num in range (1, n) and we can use the array to do implace label if all the values are positive
        // thus, we can change the value of those num <= 0 to (n + 1)
        int n = nums.length;
        for (int i = 0; i < n; i++) {
        	if (nums[i] <= 0) {nums[i] = n + 1;}
        }

        // second loop: label the value in correspoding position as negative as visited
        for (int i = 0; i < n; i++) {
        	int target = Math.abs(nums[i]);
        	// skip those outside the range (1, n)
        	if (target > n) {continue;}
        	if (nums[target - 1] > 0) { // avoid label twice by duplicates
        		nums[target - 1] *= -1;
        	}
        }

        // third loop: if there exists a value > 0, then it shows value to this position hasn't been visited
        for (int i = 0; i < n; i++) {
        	if (nums[i] > 0) {return i + 1;}
        }

        return n + 1;
    }
}

/*
The key to above solution is that we reduce the problem to care about only nums in range (1, n). Then we want to use array in-place to label as visited, which means we need the array to have all nums as positive numbers. And based on the valid range (1, n), we dicided to put all nums we don't care to a positive number but outside of the (1, n) range, which is  >= n + 1.
*/



// Additional solution phase 3: swap every node to its correct position
class Solution {
    public int firstMissingPositive(int[] nums) {
        int i = 0;
        int n = nums.length;
        while (i < n) {
        	// we do swap only when this number is in valid range and also the corresponding position value is not the same as it. Otherwise, we will go into infinite loop.
        	if (nums[i] - 1 >= 0 && nums[i] - 1 < n && nums[nums[i] - 1] != nums[i]) {
        		swap(nums, nums[i] - 1, i);
        	} else {
        		i++;
        	}
        }

        i = 0;
        while (i < n) {
        	if (nums[i] != i + 1) {
        		break;
        	}
            i++;
        }
        return i + 1;
    }


    private void swap(int[] nums, int idx1, int idx2) {
    	int temp = nums[idx1];
    	nums[idx1] = nums[idx2];
    	nums[idx2] = temp;
    }
}


// Phase3 self
// similar to above M1: 3 loops
class Solution {
    // since require O(1) space, think about label in place
    // Think about negative label, since we only care about [1,n]. Thus for original negative number in array, we can convert it to nums.len+1, so that it won't cause confusion for original positive numbers labeled as negative. (But in this case array is changed in place and can't revert back)
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] <= 0) {nums[i] = n+1; continue;}
        }
        for (int i = 0; i < n; i++) {
            int num = Math.abs(nums[i]);
            if (num <= n && nums[num-1] > 0) {nums[num-1] *= -1;}
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {return i+1;}
        }
        return n+1;
        
    }
}