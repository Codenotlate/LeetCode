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