class Solution {
	// method1: negate the array to label visited and convert back
	// time O(n) space O(1)
    public int findDuplicate(int[] nums) {
    	int res = 0;
        for (int i = 0; i < nums.length; i++) {
        	int n = Math.abs(nums[i]);
        	if (nums[n - 1] < 0) {
        		res = n;
        		break;
        	} else {
        		nums[n - 1] *= -1;
        	}
        }
        // convert the nums back to positive before return the res
        for (int n: nums) {
        	if (n < 0) {
        		n *= -1;
        	}
        }
        return res;
    }
}



class Solution {
	// method2: reduce to cycle detects in linkedlist
	// sequence built by x, nums[x], nums[nums[x]], nums[[nums[nums[x]]]]...
	// fast and slow pointers, two round
	// time O(n) space O(1)
    public int findDuplicate(int[] nums) {
    	int fast = nums[0];
    	int slow = nums[0];

    	do {
    		fast = nums[nums[fast]];
    		slow = nums[slow];
    	} while (fast != slow);

    	fast = nums[0];
    	while (fast != slow) {
    		fast = nums[fast];
    		slow = nums[slow];
    	} 

    	return fast;
    }
}


class Solution {
	// method3: using binary search with range
	// time O(nlogn) space O(1)
    public int findDuplicate(int[] nums) {
    	int low = 1;
    	int high = nums.length - 1;

    	while (low < high) {
    		int mid = (high - low) / 2 + low;
    		int count = 0;
    		for (int n: nums) {
    			if (n <= mid) {count++;}
    		}

    		if (count > mid) {
    			high = mid;
    		} else {
    			low = mid + 1;
    		}
    	}
    	return low;
    }
}




