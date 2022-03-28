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

/*
some ideas from discussion for the followup
1. Cant we just do this with a priority queue ? As we have an incoming stream of intervals, we just need to keep them sorted and pop the smallest two intervals and check if we can merge them.


*/




// Phase3 self
// two syntax to remember: sort with custom comparator; convert a list directly to an array.
class Solution {
    // M1: sort based on start position first, then merge one by one
    // time O(nlogn) space O(1) if the sort algo is inplace. 
    // regarding the space of the linkedlist: (from discussion)" I usually tell interviewers its technically O(N) but if we count the LinkedList as the returned object it is O(1)"
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (i1, i2) -> i1[0] - i2[0]);
        int start = intervals[0][0];
        int end = intervals[0][1];
        List<int[]> reslist = new LinkedList<>();
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] > end) {
                reslist.add(new int[]{start, end});
                start = intervals[i][0];
                end = intervals[i][1];
            } else {
                end = Math.max(end, intervals[i][1]);
            }
        }
        reslist.add(new int[]{start, end});
        // int[][] res = new int[reslist.size()][2];
        // for (int i = 0; i < reslist.size(); i++) {
        //     res[i] = reslist.get(i);
        // }
        return reslist.toArray(new int[reslist.size()][]);
    }
}


// Review
/*Initial thought
sort the intervals based on start. Then process one by one, by comparing their end value. Add prev start and end to res when cur start > prev end.
time O(nlogn) space O(n) for the intermediate list.

*/
class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (i1, i2) -> (i1[0] - i2[0]));
        int prevstart = -1;
        int prevend = -1;
        List<int[]> resList = new LinkedList<>();
        for (int[] cur: intervals) {
            if(prevstart == -1) {
                prevstart = cur[0];
                prevend = cur[1];
                continue;   
            }
            if(cur[0] > prevend) {
                resList.add(new int[]{prevstart, prevend});
                prevstart = cur[0];
                prevend = cur[1];
            } else {
                prevend = Math.max(prevend, cur[1]);
            }
        }
        resList.add(new int[]{prevstart, prevend});
        // int[][] res = new int[resList.size()][2];
        // for (int i = 0; i < res.length; i++) {res[i] = resList.get(i);}
        // return res;
        return resList.toArray(new int[resList.size()][]); // much faster than above way
    }
}

// check above followup question idea