/* M1 - O(nlogk)
phase3 learning: we use treeset in this problem to deal with range
https://medium.com/swlh/introduction-to-treeset-and-treemap-5405f21124d
put, contains, remove, lower, floor, higher, ceiling, etc operations all take O(logn) time in treeSet/TreeMap
*/


class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        // Note, since we will check num + t later, using int will cause overflow possibly
        // also have to declare as treeSet instead of just set, otherwise can't use the ceiling method below.
        TreeSet<Long> windowSetK = new TreeSet<>();

        for (int i = 0; i < nums.length; i++) {

        	long cur = nums[i];
        	// long + int will auto converted to a long
        	Long min = windowSetK.ceiling(cur - t);
 			// have to declare min as Long above, otherwise, long can't be compared with null
        	if (min != null && min <= cur + t) {return true;}
        	windowSetK.add(cur);
        	// note it's i >= k here
        	if (i >= k) {windowSetK.remove((long) nums[i - k]);}
        }
        return false;
    }
}


/* M2:  - O (n)
https://leetcode.com/problems/contains-duplicate-iii/discuss/61645/AC-O(N)-solution-in-Java-using-buckets-with-explanation
using bucket to check the range in O(1) 
As a followup question, it naturally also requires maintaining a window of size k. 
Since there is now a constraint on the range of the values of the elements to be considered duplicates, 
it reminds us of doing a range check which is implemented in tree data structure and would take O(LogN) if a balanced tree structure is used, 
or doing a bucket check which is constant time. We shall just discuss the idea using bucket here.
*/
// idea also explained in goodnote

class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        Map<Long, Long> bucketMap = new HashMap<>();
        // reposition to deal with negative values
        for (int i = 0; i < nums.length; i++) {
        	// don't forget to cast int to long
        	Long cur = (long) nums[i] - Integer.MIN_VALUE;
        	Long id = cur / (t + 1); // set bucket size as t + 1
        	if (bucketMap.containsKey(id) ||
        		(bucketMap.containsKey(id + 1) && Math.abs(bucketMap.get(id + 1) - cur) <= t) ||
        		(bucketMap.containsKey(id - 1) && Math.abs(bucketMap.get(id - 1) - cur) <= t)) {
        		return true;
        	}
        	bucketMap.put(id, cur);
        	// remove if size > k
        	if (bucketMap.keySet().size() > k) {
        		long removeID = ((long) nums[i - k] - Integer.MIN_VALUE) / (t + 1);
        		bucketMap.remove(removeID);
        	}
        }
        return false;
    }
}




















