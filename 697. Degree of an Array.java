class Solution {
	// time O(n), space O(n)
    public int findShortestSubArray(int[] nums) {
        Map<Integer, int[]> map = new HashMap<>();
        int maxCount = 1;
        for (int i = 0; i < nums.length; i++) {
        	if (map.keySet().contains(nums[i])) {
        		int[] array = map.get(nums[i]);
        		map.put(nums[i], new int[]{array[0] + 1, array[1], i});
        		maxCount = Math.max(maxCount, array[0] + 1);
        	} else {
        		map.put(nums[i], new int[]{1, i, i});
        	}
        }

        int minLen = nums.length;
        
        for (int[] a: map.values()) {
        	if (a[0] == maxCount) {
        		minLen = Math.min(minLen, a[2] - a[1] + 1);
        	}
        }

        return minLen;
    }
}





class Solution {
	// improve to one pass
    public int findShortestSubArray(int[] nums) {
        Map<Integer, int[]> map = new HashMap<>();
        int maxCount = 1;
        int minLen = 1;
        for (int i = 0; i < nums.length; i++) {
        	if (map.keySet().contains(nums[i])) {
        		int[] array = map.get(nums[i]);
        		map.put(nums[i], new int[]{array[0] + 1, array[1]});
        		if (maxCount < array[0] + 1) {
        			maxCount = array[0] + 1;
        			minLen = i - array[1] + 1;
        		} else if (maxCount == array[0] + 1) {
        			minLen = Math.min(minLen, i - array[1] + 1);
        		}
        	} else {
        		map.put(nums[i], new int[]{1, i});
        	}
        }

        return minLen;
    }
}













