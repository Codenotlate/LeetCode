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




// Review 23/2/8 - Same as above, but not precise enough, also rememver resList.toArray(new int[resList.size()][]) syntax.
/*Thoughts
sort the array by the start and adjust one by one, add only when it's non-overlap with the next one.
time O(nlogn) space O(n) or O(logn) if not counting in result space List

 */
class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (i1, i2) -> (i1[0] - i2[0]));
        List<int[]> resList = new ArrayList<>();
        int start = -1;
        int end = -1;
        for (int[] cur: intervals) {
            if (start == -1) {
                start = cur[0];
                end = cur[1];
                continue;
            }
            if (cur[0] > end) {
                resList.add(new int[]{start, end});
                start = cur[0];
                end = cur[1];
            } else {
                end = Math.max(end, cur[1]);
            }
        }
        resList.add(new int[]{start, end});
        int[][] res = new int[resList.size()][2];
        for (int i = 0; i < res.length; i++) {res[i] = resList.get(i);}
        return res;
    }
}


// next time only check the followup questions - How do you add intervals and merge them for a large stream of intervals? (Facebook Follow-up Question)
// followup questions in solution comment -- need review
/*
https://leetcode.com/problems/merge-intervals/discuss/355318/Fully-Explained-and-Clean-Interval-Tree-for-Facebook-Follow-Up-No-Sorting
Question: How do you add intervals and merge them for a large stream of intervals? (Facebook Follow-up Question)
*/

/*
some ideas from discussion for the followup
1. Cant we just do this with a priority queue ? As we have an incoming stream of intervals, we just need to keep them sorted and pop the smallest two intervals and check if we can merge them.

2.want to discuss about the FB follow up question, "How do you add intervals and merge them for a large stream of intervals? (Facebook Follow-up Question)" from here, https://leetcode.com/problems/merge-intervals/discuss/355318/Fully-Explained-and-Clean-Interval-Tree-for-Facebook-Follow-Up-No-Sorting
If this is an real world abstrcted problem, i'd prefer to use divide and conquer technique depends on the requirements. For example, partitioning input intervals based on interval start/end value, i.e we have 1000 buckets, inside each bucket we maintain a sorted intervals, each bucket can be in one machine or multiples buckets in one machine. then we use merge those bucket 2 by 2 until to the point each bucket contains maximum intervals. And make sure no intervals are across two buckets
For either endless incoming stream mentioned here or memory is too small to load all intervals, this approach should work.
this BST approach is impresive but not realistic for me to write in the interview
https://leetcode.com/problems/merge-intervals/discuss/21451/Share-my-BST-interval-tree-solution-C%2B%2B-No-sorting!


*/


// 20240810
// sort way O(nlogn)
class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals,(i1, i2)->(i1[0]-i2[0]));
        int[] cur = intervals[0];
        List<int[]> res = new LinkedList<>();
        for (int i = 1; i < intervals.length; i++) {
            if (cur[1] < intervals[i][0]) {
                res.add(cur);
                cur = intervals[i];
            } else {
                cur[1] = Math.max(cur[1], intervals[i][1]);
            }
        }
        res.add(cur); // don't forget this line
        int[][] reslist = new int[res.size()][2];
        for (int i = 0; i < reslist.length; i++) {
            reslist[i] = res.get(i);
        }
        return reslist;
    }
}