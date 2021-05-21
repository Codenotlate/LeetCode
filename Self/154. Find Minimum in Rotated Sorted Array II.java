// follow up from 153 (now with duplicate)
// similar problem pairs as 33 and 81
// again the binary search will have a case when we can't decrease the search range by half
// thus worst case scenario: O(n), average O(logn)


// phase 3 self: compare with low
class Solution {
    public int findMin(int[] nums) {
        int s = 0;
        int e = nums.length - 1;
        int res = Integer.MAX_VALUE;

        while (s <= e) {
        	if (nums[s] < nums[e]) {return nums[s];}
        	int m = s + (e - s) / 2;
        	if (nums[m] > nums[s]) {
        		s = m + 1;
        	} else if (nums[m] < nums[s]) {
        		e = m;
        	} else { // nums[m] == nums[s]
        		res = Math.min(res, nums[s]);
        		s++;
        	}
        }

        return res;
    }
}



// phase 3 self: compare with high, in this case we don't need to track res, cause we move h--, and even nums[h] is the min, we will have nums[m] == nums[h] which is still in the search range
class Solution {
    public int findMin(int[] nums) {
        int s = 0;
        int e = nums.length - 1;

        while (s < e) {
        	if (nums[s] < nums[e]) {return nums[s];}
        	int m = s + (e - s) / 2;
        	if (nums[m] > nums[e]) {
        		s = m + 1;
        	} else if (nums[m] < nums[e]) {
        		e = m;
        	} else { // nums[m] == nums[s]
        		e--;
        	}
        }

        return nums[s];
    }
}











