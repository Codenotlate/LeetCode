class Solution {
	// time O(n) solution
    public int longestConsecutive(int[] nums) {
        // build a set first
        Set<Integer> set = new HashSet<>();
        for (int n: nums) {
        	set.add(n);
        }
        // iterate the set
        int maxLen = 0;
        for (int curNum: set) {
        	// reduce repetitive work 
        	// curNum - 1 not in set assures starting 
        	// with the smallest num in a potential consecutve sequence
        	if (!set.contains(curNum - 1)) {
        		int curLen = 1;
        		while (set.contains(curNum + 1)) {
        			curLen += 1;
        			curNum += 1;
        		}
        		maxLen = Math.max(maxLen, curLen);
        	}
        }
        return maxLen;
    }
}

// sort solution takes O(nlogn), later