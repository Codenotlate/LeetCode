class Solution {
	// time O(n), space O(n)
    public int arrayNesting(int[] nums) {
        int maxLen = 0;
        Set<Integer> visited = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
        	int size = 0;
        	while (!visited.contains(nums[i])) {
        		visited.add(nums[i]);// label as visited
        		size++;
        		i = nums[i];	        	
        	}
        	maxLen = Math.max(maxLen, size);
        	
        }
        return maxLen;
    }
}

class Solution {
	// time O(n), space O(1)
    public int arrayNesting(int[] nums) {
        int maxLen = 0;
        for (int i = 0; i < nums.length; i++) {
        	int size = 0;
        	while (nums[i] != Integer.MAX_VALUE) {
        		int temp = nums[i];
        		nums[i] = Integer.MAX_VALUE;// label as visited
        		size++;	   
        		i = temp;
        		     	
        	}
        	maxLen = Math.max(maxLen, size);
        	
        }
        return maxLen;
    }
}

/**
Just some supplement.

From the statement "The array of size N contains number [0, N-1]", 
we can know that in-degree of all nodes are exactly one 
(n different edges for n nodes).

Therefore the graph should consist of several cycles 
and the cycles have no "tails". 
That's why we can skip the visited nodes, 
where to begin in a visited cycle doesn't matter in this circumstance.
*/






