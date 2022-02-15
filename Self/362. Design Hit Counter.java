/* Initial thought
Use a dict to record time -> accumulated sum. e.g. t1:accumcount1: t2: accumcount2
when hit: keep track of maxtime in dict, if t > maxtime, dict.put(t, dict.get(maxtime) + 1) and update maxtime to t. If t <= maxtime, add the count of maxtime. (time O(1))
for getHits(), do binary search on dict.keys() for t and t-300. Find the first key(endt) <= t and find the first key(startt) <= t-300. return dict.get(endt) - dict.get(startt). (time O(logn) where n is the distinct time we get hit)
*/

class HitCounter {
    
    Map<Integer, Integer> countMap;
    List<Integer> orderedKeys;
    int maxtime;

    public HitCounter() {
        countMap = new HashMap<>();
        countMap.put(0, 0);
        orderedKeys = new LinkedList<>();
        orderedKeys.add(0);
        maxtime = 0;
    }
    
    public void hit(int timestamp) {
        if (timestamp > maxtime) {
            countMap.put(timestamp, countMap.get(maxtime) + 1);
            orderedKeys.add(timestamp);
            maxtime = timestamp;
        } else {
            countMap.put(maxtime, countMap.get(maxtime) + 1);
        }
    }
    
    public int getHits(int timestamp) {
        int endtime = binarySearch(timestamp);
        int starttime = binarySearch(timestamp-300);
        return countMap.get(endtime) - countMap.get(starttime);
    }
    
    // return the first t in orderedKeys list that is <= to target
    private int binarySearch(int target) {
        int start = 0;
        int end = orderedKeys.size() - 1;
        while (start < end - 1) {
            int mid = start + (end - start) / 2;
            if (orderedKeys.get(mid) > target) {
                end = mid - 1;
            } else {
                start = mid;
            }
        }
        return orderedKeys.get(end) <= target ? orderedKeys.get(end):orderedKeys.get(start);
    }
}


//above method works for calls like: getHits(310),getHits(3), get(Hits(650), getHits(320)

// the methods in solution assumes we will always call getHits(t) in ascending order of t. Thus we don't need to keep track of counts before t-300. We can simply discard those records in a queue/deque. And the average time of getHits is O(1). space also O(1) since only need to maintain at most 300 each time.
// In interview, better ask for the clearification before hands.
// also in this assumption, then we can simply use a size = 300 array.
// https://leetcode.com/problems/design-hit-counter/discuss/83483/Super-easy-design-O(1)-hit()-O(s)-getHits()-no-fancy-data-structure-is-needed!


























