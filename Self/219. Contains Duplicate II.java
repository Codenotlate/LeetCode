// self phase 3
class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        // Use a hashmap to store the integer and its latest occured position
        Map<Integer, Integer> posMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
        	if (posMap.keySet().contains(nums[i]) && i - posMap.get(nums[i]) <= k) {
        		return true;
        	}
        	posMap.put(nums[i], i);
        }
        return false;
    }
}

// another way: can also simply use a set
// remove those elements that is larger than k pos from cur processed pos

class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
        	if (i > k) {set.remove(nums[i - k - 1]);}
        	// if (set.contains(nums[i])) {return true;}
        	// set.add(nums[i]);
        	// above two lines can be replaced by below
        	if (!set.add(nums[i])) {return true;}
        }
        return false;
    }
}


/*
The add() method of Set in Java is used to add a specific element into a Set collection. The function adds the element only if the specified element is not already present in the set else the function return False if the element is already present in the Set.
*/
