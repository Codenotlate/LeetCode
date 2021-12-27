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



// Phase3 self, one pass
// actually no need to keep end position from last time, start position is enough for calculating length with current index i
class Solution {
    public int findShortestSubArray(int[] nums) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int minLen = nums.length + 1;
        int maxCount = 0;
        
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                int count = map.get(nums[i]).get(0);
                map.get(nums[i]).set(0,count + 1);
                if (count + 1 > maxCount) {
                    maxCount = count + 1;
                    minLen = i - map.get(nums[i]).get(1) + 1;
                } else if (count + 1 == maxCount) {
                    minLen = Math.min(minLen, i - map.get(nums[i]).get(1) + 1);
                }
                map.get(nums[i]).set(2,i);
            } else {
                map.put(nums[i], new LinkedList<Integer>());
                map.get(nums[i]).add(1);
                map.get(nums[i]).add(i);
                map.get(nums[i]).add(i);
            }
        }
        return minLen == nums.length + 1 ? 1 : minLen;
        
        
    }
}











