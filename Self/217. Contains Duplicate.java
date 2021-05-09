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

// phase 3: sort way
// regular sort time O(nlogn) space could be O（1）

class Solution {
    public boolean containsDuplicate(int[] nums) {
        if (nums.length <= 1) {return false;}
        Arrays.sort(nums);
        int prev = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == prev) {return true;}
            prev = nums[i];
        }
        return false;
    }
}