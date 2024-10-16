class Solution {
    /*Initial Thought
    sort the array based on distance, and find the first k number
    time O(nlogn) space O(1) if don't count result space and sort is inplace
    Another way is to use max PQ with size k
    time O(nlogk)  space O(k)
    */
    public int[][] kClosest(int[][] points, int k) {
        Arrays.sort(points, (p1, p2) ->(p1[0]*p1[0] + p1[1]*p1[1] - p2[0]*p2[0] - p2[1] *p2[1]));
        int[][] res = new int[k][2];
        for (int i = 0; i< k; i++) {
            res[i] = points[i];
        }
        return res;
    }
}

class Solution {
    // M2: maxheap way
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> maxheap = new PriorityQueue<>((p1,p2) -> (p2[0]*p2[0] + p2[1]*p2[1]-p1[0]*p1[0] - p1[1]*p1[1]));
        int size = 0;
        for (int[] p: points) {
            maxheap.add(p);
            size++;
            if (size > k) {maxheap.poll();}
        }
        int[][] res = new int[k][2];
        while(!maxheap.isEmpty()) {res[k-1] = maxheap.poll(); k--;}
        return res;
    }
}

// another two O(N) way in solution 3 and 4
// https://leetcode.com/problems/k-closest-points-to-origin/solution/
// learn M4 later

// M3 self: binary search way
class Solution {
    /* improved thought: optimize time to average O(n)
    naive thought: first go through all pointes to get their distance and distance range. Store the count of each distance number into an array as counting sort. Then get accumulated k positions. The drawback is when distance range is pretty sparse and large, this way requires a huge amount of space.
    Improved way: similar idea as searching the range and decide how the count of that target distance. But we use binary search here, the difference with plain BS is that in each step we need to compare the target dist with all positions in the range to find out how many cells in this range is smaller than the target. And based on the comparison between count and k to decide which half of the dist range is going to be discarded.
    Note worst case time is O(n^2) for this bs way. Since it's possible that each time the half we discard only contains one cell. But on average, we can assume we discard half of the cells. Thus average time O(n).
    space O(n) for recording the dist of each cell and the list for valid range for each round of search
    */
    // M3: binary search way
    public int[][] kClosest(int[][] points, int k) {
        // use index in points to identify each point, first calculate distance for each cell and record, which can be later used to decide the count in each round of binary search. Also record the min and max dist range along the way.
        // also store all index into a list to reprensent the search range later in each round. Since original array can't change the size.
        double[] distance = new double[points.length];
        List<Integer> searchRange = new LinkedList<>();
        double minDist = Double.MAX_VALUE;
        double maxDist = 0;
        for (int i = 0; i < points.length; i++) {
            int[] p = points[i];
            distance[i] = p[0] * p[0] + p[1] * p[1];
            searchRange.add(i);
            minDist =  Math.min(minDist, distance[i]);
            maxDist = Math.max(maxDist, distance[i]);
        }
        
        List<Integer> idxRes = new LinkedList<>();
        
        // start the BS search
        while (k > 0) {
            double midDist = minDist + (maxDist - minDist) / 2;
            // This method splic the search range into two part, <= midDist and > midDist, then the size can be used to determine which part is the next valid search range
            Pair<List<Integer>, List<Integer>> splitedLists = split(searchRange, distance, midDist);
            List<Integer> smaller = splitedLists.getKey();
            List<Integer> larger = splitedLists.getValue();
            if (k >= smaller.size()) {
                // meaning all corresponding cells inside smaller list is within top k. Thus add all of them in the final result. And search the larger half next round to find the remaining k-count cells. also increase midDist in next round.
                idxRes.addAll(smaller);
                searchRange = larger;
                k -= smaller.size();
                minDist = midDist;
            } else {
                // meaning smaller list already has more than k cells, the midDist is too large, need to decrease midDist in next round, and update the search range for next round to be smaller.
                searchRange = smaller;
                maxDist = midDist - 1;
            }            
        }
        
        
        // add the result to an array, get the actual position using the index in idxRes
        int[][] res = new int[idxRes.size()][2];
        int j = 0;
        for (int i: idxRes){
            res[j++] = points[i];
        }
        return res;
    }
    
    
    private Pair<List<Integer>, List<Integer>> split(List<Integer> range, double[] distance, double target) {
        List<Integer> smaller = new LinkedList<>();
        List<Integer> larger = new LinkedList<>();
        for (int i: range) {
            if (distance[i] <= target) {
                smaller.add(i);
            } else {
                larger.add(i);
            }
        }
        return new Pair(smaller, larger);
    }
}




// solution summary: sort way + heap way + quickselect way
// https://leetcode.com/problems/k-closest-points-to-origin/discuss/220235/Java-Three-solutions-to-this-classical-K-th-problem.
/*
Also explanation in comment about time of quickselect comparing to quickSort:
"Because in the quicksort, you have to take care of two sides of the pivot. But in quickselect, you only focus on the side the target object should be. So, in optimal case, the running time of quicksort is (n + 2 * (n/2) + 4 * (n/4)...), it has logn iterations. Instead, the running time of quickselect would be (n + n/2 + n/4 +...) it has logn iterations as well. Therefore, the running time of quicksort is O(nlogn), instead, the running time of quickselect of quickselect is O(n)"
Hope this can help you:) 
*/







// Review
/*Thought
M1: maxheap size as k. time O(nlogk+klogk) = O(nlogk) space O(k)
M2: counting sort. An array of size maxdist. time O(maxdist) space O(maxdist)
M3: sort the array and return first k nodes. time O(nlogn) space O(logn)
----------------------

M4: based on M2, instead of using a count array, we can do binary search on dist range (min, max), for each middle dist, we check count of points having smaller dist than middist in the range(start, end), and comparing the count with k. If count <= k, start = mid, k-=count, second range on list with element > count. If count > k, end = middist - 1. And do second round on list with element <= count.
Worst case, each time we only eliminate one element from the list, then time O(1+2+...+n-1) = O(n^2) space O(n^2)
average case, each time we eliminated half of the elements from the list, then time O(1 + 2+4 + ... + n/4 + n/2 + n) = O(n) space O(n)
-----------
M5: similar as M4, eliminate the search range every time using quick select. we select a pivot(choose value in mid position) each time, and quick sort the current range to have [smaller than pivot | pivot| larger than pivot] by having small and large pointer starts at both ends and swap along the way. The small and large pointers will meet at pivot position. Then we compare the pivot pos with k, if pivot pos <= k, range start = pivot pos, k-= pivot pos; else if pivot pos > k, range end = pivot pos - 1.
same as M4, the elimination efficiency determines the time, worst case O(n^2). average case O(n). Both have space O(1)
note we choose midpos value as pivot is to trying best to avoid worst case since if the array is nearly sorted then choose start or end pos as pivot will more likely lead to worst case.

*/
// M4: BS way
class Solution {
    public int[][] kClosest(int[][] points, int k) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        List<int[]> distList = new LinkedList<>();
        List<int[]> reslist = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            int[] point = points[i];
            int dist = point[0] * point[0] + point[1] * point[1];
            distList.add(new int[]{dist, i}); // since later we need index to get int[] point to add to result
            min = Math.min(min, dist);
            max = Math.max(max, dist);
        }
        
        int start = min;
        int end = max;
        
        while (k > 0) {
            int mid = start + (end- start) / 2;
            List<List<int[]>> splited = split(distList, mid);
            List<int[]> smaller = splited.get(0);
            List<int[]> larger = splited.get(1);
            if (smaller.size() <= k) {
                k -= smaller.size();
                start = mid;
                distList = larger;
                reslist.addAll(smaller);
            } else {
                end = mid - 1;
                distList = smaller;
            }
        }
        
        
        int[][] res = new int[reslist.size()][2];
        for (int i = 0; i < reslist.size(); i++) {
            res[i] = points[reslist.get(i)[1]];
        }
        return res;        
    }
    
    
    private List<List<int[]>> split(List<int[]> distList, int target) {
        List<int[]> small = new ArrayList<>();
        List<int[]> large = new ArrayList<>();
        for (int[] cur: distList) {
            if (cur[0] <= target) {small.add(cur);}
            else {large.add(cur);}
        }
        List<List<int[]>> res = new ArrayList<>();
        res.add(small);
        res.add(large);
        return res;
    }
}

// implement quickselect way next time





// 2024.10.1
// sort by dist to origin
// can use a size k max heap
// time O(nlogk) space O(k)
class Solution {
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> b[0]*b[0]+b[1]*b[1]-a[0]*a[0]-a[1]*a[1]);
        int[][] res = new int[k][2];
        for (int[] p: points) {
            pq.add(p);
            k--;
            if (k < 0) {
                pq.poll();
                k++;
            }
        }
        
        for (int i = 0; i < res.length; i++) {
            res[i] = pq.poll().clone();
        }
        return res;
    }
}


// tried quick select but failed, practice quick sort and quick select together as a topic























