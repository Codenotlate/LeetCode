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



// 20240326
/**
Two basic ideas:
=== M1: using a map to record the latest index of an element. Time O(n) space O(n)
=== M2: using a hashset with size k, when the index falls out of the window, remove that index element. Time O(n) space O(k)

----------------------------------
learning notes:
=== Non-optimized way: using self-balancing Binary Search Tree (BST).
A BST supports search, delete and insert operations all in O(log⁡k) time, where k is the number of elements in the BST. 
In most interviews you are not required to implement a self-balancing BST, so you may think of it as a black box. In Java, you may use a TreeSet or a TreeMap
Time O(n * log(min(k,n)))
 */
// try M2
class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> kSet = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (kSet.contains(nums[i])) {return true;}
            kSet.add(nums[i]);
            if (kSet.size() > k) {kSet.remove(nums[i-k]);}
        }
        return false;
    }
}
