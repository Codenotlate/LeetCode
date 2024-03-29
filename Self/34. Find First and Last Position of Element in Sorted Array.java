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



// Phase3 self
// pay attention to all the details
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int[] res = new int[]{-1, -1};
        // remeber to check special case
        if (nums.length == 0) {return res;}
        
        int s = 0; 
        int e = nums.length - 1;
        int first = searchFirstLte(nums, s, e, target);
        int second = searchFirstLte(nums, s, e, target + 1);
        // since searchFirstLte return the first pos that has element >= target
        // we can only update res[0] if that element == target
        if (first != -1 && nums[first] == target) {
            res[0] = first;
            // if res[0] != -1 then res[1] must exist too, second could be -1 when the target is the last element and there's no element >= target + 1
            // thus also need to adjust second to update res[1].
            // remember to do second - 1
            res[1] = second == -1 ? nums.length - 1 : second - 1;
        }
        
        return res;
    }
    
    private int searchFirstLte(int[] nums, int s, int e, int target) {
        while (s < e) {
            int m = s + (e - s) / 2;
            if (nums[m] >= target) {
                e = m;
            } else {
                s = m + 1;
            }
        }
        
        return nums[s] >= target? s : -1;
    }
}





// Phase3 self 2.0 (another way to find first and last, remember to use two elements case to check whether a infinite loop)
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[2];
        result[0] = searchFirst(nums, target);
        result[1] = searchLast(nums, target);
        return result;
    }
    
    private int searchFirst(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        if (end < 0) {return -1;}
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                end = mid;
            } else if (nums[mid] > target) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return nums[start] == target ? start : -1;
    }
    
    private int searchLast(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        if (end < 0) {return -1;}
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                if (nums[mid + 1] > target) {return mid;}
                start = mid + 1;
            } else if (nums[mid] > target) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return nums[start] == target ? start : -1;
    }
    
}



// Review self
// much consice code
class Solution {
    /* initial thought:
    log(n) binary search. Can write two binary search functions, one for firstLte, another for lastLte. Or we can use one binary search firstLte. Then we use it for target and target+1.
    check nums[p1] and nums[p2-1](if p2 == -1, then p2-1 = nums.len-1), return [-1,-1] if p1 == -1 or nums[p1] != target.
    */
    public int[] searchRange(int[] nums, int target) {
        if(nums.length == 0) {return new int[]{-1, -1};}
        int p1 = firstLte(nums, target);
        if (p1 == -1 || nums[p1] != target) {return new int[]{-1,-1};}
        int p2 = firstLte(nums, target+1);
        p2 = p2 == -1? nums.length : p2;
        return new int[]{p1, p2-1};
    }
    
    private int firstLte(int[] nums, int target) {
        int start = 0;
        int end = nums.length-1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] >= target) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return nums[start] >= target? start : -1;
    }
}