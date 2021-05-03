class Solution {
	// using hashset: time O(n), space: O(n)
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n: nums) {
        	if (set.contains(n)) {
        		return true;
        	}
        	set.add(n);
        }
        return false;
    }
}

// sort related way left for later