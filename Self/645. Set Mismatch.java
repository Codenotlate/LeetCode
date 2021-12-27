class Solution {
	// O(n) and O(n)
    public int[] findErrorNums(int[] nums) {
        int[] res = new int[2];
        if (nums.length == 0) {return res;}
        Set<Integer> visited = new HashSet<>();
        int maxVal = 0;
        int sum = 0;
        for (int n: nums) {
        	if (visited.contains(n)) {
        		res[0] = n;
        	} else {
        		visited.add(n);
        		sum += n;
        		maxVal = Math.max(maxVal, n);
        	}
        }
        int miss = (1 + maxVal) * maxVal / 2 - sum;
        res[1] = (miss == 0) ? maxVal + 1 : miss;
        return res;
    }
}



class Solution {
	// use negative nums to label visited, time O(n), space O(1)
    public int[] findErrorNums(int[] nums) {
        int[] res = new int[2];
        if (nums.length == 0) {return res;}
        for (int i = 0; i < nums.length; i++) {
        	int n = Math.abs(nums[i]);
        	if (nums[n - 1] < 0) { // meaning it's visited
        		res[0] = n;
        	} else {
        		nums[n - 1] *= -1;
        	}
        }
        for (int i = 0; i < nums.length; i++) {
        	if (nums[i] > 0) {
        		res[1] = i + 1;
        	}
        }
        return res;
    }
}


// Phase3 M1
class Solution {
    public int[] findErrorNums(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int dup = 0;
        int sum = 0;
        for (int n: nums) {
            sum += n;
            if (set.contains(n)) {
                dup = n;
            }
            set.add(n);
        }
        int diff = (1 + nums.length) * nums.length / 2 - sum;
        int miss = dup + diff;
        return new int[]{dup, miss};       
    }
}
// M2
class Solution {
    // inplace way: use negative label instead of an extra set
    public int[] findErrorNums(int[] nums) {
        int dup = 0;
        int sum = 0;
        for (int n: nums) {
            n = Math.abs(n);
            sum += n;
            if (nums[n - 1] < 0) {
                dup = n;
            } else {
                nums[n - 1] *= (-1);
            }
        }
        int diff = (1 + nums.length) * nums.length / 2 - sum;
        int miss = dup + diff;
        return new int[]{dup, miss};       
    }
}