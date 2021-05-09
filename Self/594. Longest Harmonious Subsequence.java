class Solution {
	// build a value: count map
	// time O(n) space O(n)
    public int findLHS(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n: nums) {
        	map.put(n, map.getOrDefault(n, 0) + 1);
        }
        // iterate the map and update maxCount
        int maxCount = 0;
        for (int value: map.keySet()) {
        	if (map.containsKey(value + 1)) {
        		maxCount = Math.max(maxCount, map.get(value) + map.get(value + 1));
        	}
        }
        return maxCount;
    }
}

// sort related later