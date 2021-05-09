class Solution {
	// use one implementation of binary search twice: 
	// first to find the first element >= target
	// then to find the first element >= target + 1
	// time O(logn) space O(1)
    public int[] searchRange(int[] nums, int target) {
        int[] res = new int[]{-1, -1};
        // special case
        if (nums.length == 0) {return res;}
        int first = findFirst(nums, target);
        int second = findFirst(nums, target + 1);
        if (first == -1 || nums[first] != target) { // meaning target not find
        	return res;
        }
        res[0] = first;
        if (second == -1) {
        	res[1] = nums.length - 1;
        } else {
        	res[1] = second - 1;
        }
        return res;
    }

    // findFirst using binary search return the index of the first element >= target
    // if no such element, return -1
    private int findFirst(int[] nums, int target) {
    	int start = 0;
    	int end = nums.length - 1;
    	while (start < end) {
    		int mid = start + (end - start) / 2;
    		if (nums[mid] < target) {
    			start = mid + 1;
    		} else {
    			end = mid;
    		}
    	}
    	if (nums[start] >= target) {return start;}
    	return -1;
    }
}



// method2: use two types of binary search
// one for find first and one for find last

public class Solution {
	public int[] searchRange(int[] nums, int target) {
	    int[] result = new int[2];
	    result[0] = findFirst(nums, target);
	    result[1] = findLast(nums, target);
	    return result;
	}

	private int findFirst(int[] nums, int target){
	    int idx = -1;
	    int start = 0;
	    int end = nums.length - 1;
	    while(start <= end){
	        int mid = (start + end) / 2;
	        if(nums[mid] >= target){
	            end = mid - 1;
	        }else{
	            start = mid + 1;
	        }
	        if(nums[mid] == target) idx = mid;
	    }
	    return idx;
	}

	private int findLast(int[] nums, int target){
	    int idx = -1;
	    int start = 0;
	    int end = nums.length - 1;
	    while(start <= end){
	        int mid = (start + end) / 2;
	        if(nums[mid] <= target){
	            start = mid + 1;
	        }else{
	            end = mid - 1;
	        }
	        if(nums[mid] == target) idx = mid;
	    }
	    return idx;
	}
}






// or anothe iterative version for find the first and the last
//https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/discuss/14699/Clean-iterative-solution-with-two-binary-searches-(with-explanation)