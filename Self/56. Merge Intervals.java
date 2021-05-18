class Solution {
    public int[][] merge(int[][] intervals) {
        // sort by priority queue
        // see solution - can use arrays.sort directly instead of pq
        PriorityQueue<int[]> heap = new PriorityQueue<>((i1, i2) -> (i1[0] - i2[0]));
        for (int[] intv: intervals) {
        	heap.add(intv);
        }

        List<int[]> resList = new ArrayList<>();
        int lastPos = -1;
        while (!heap.isEmpty()) {
        	int[] cur = heap.poll();
        	if (lastPos == -1 || resList.get(lastPos)[1] < cur[0]) {
        		resList.add(cur);
        		lastPos++;
        	} else {
        		resList.get(lastPos)[1] = Math.max(resList.get(lastPos)[1], cur[1]);
        	}        	
        }

        int[][] res = new int[resList.size()][2];
        for (int i = 0; i < resList.size(); i++) {
        	res[i] = resList.get(i);
        }
        return res;

    }
}

// followup questions in solution comment -- need review
/*
https://leetcode.com/problems/merge-intervals/discuss/355318/Fully-Explained-and-Clean-Interval-Tree-for-Facebook-Follow-Up-No-Sorting
Question: How do you add intervals and merge them for a large stream of intervals? (Facebook Follow-up Question)
*/