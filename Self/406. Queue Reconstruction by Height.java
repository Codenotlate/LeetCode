class Solution {
	// sort from tallest person to lowest and for same height, k ascending
	// Time O(n^2) space O(n)
    public int[][] reconstructQueue(int[][] people) {
        //sort by descending h and ascending k
        Arrays.sort(people, new Comparator<int[]>() {
        	@Override
        	public int compare(int[] p1, int[] p2) {
        		if (p1[0] < p2[0]) {return 1;}
        		else if (p1[0] > p2[0]) {return -1;}
        		else {
        			if (p1[1] > p2[1]) {return 1;}
        			else if (p1[1] < p2[1]) {return -1;}
        			else {return 0;}
        		}
        	}
        });

        List<int[]> queue = new ArrayList<>();
        for (int[] p : people) {
        	queue.add(p[1], p);
        }
        return queue.toArray(new int[people.length][]);
    }
}

/**
k is only determined by people with equal or larger height, so makes sense to insert in non-increasing order of height. Because when we insert some person with height h and count k, we know that we have found its correct position relative to people with equal and larger height. And when we later insert other people with equal or smaller height, we know that it will not affect this relative position. So the answer is right after we insert all people.
*/

/**
How to get the idea of inserting from the tallest one?
https://leetcode.com/problems/queue-reconstruction-by-height/discuss/89359/Explanation-of-the-neat-Sort%2BInsert-solution
*/