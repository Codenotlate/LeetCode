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
















