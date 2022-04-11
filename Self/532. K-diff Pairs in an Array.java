/*Initial thought
{3,1,4,5} +1+1
use a set to record the processed num, for each curnum, first check if curnum is already in set, if already in, then can skip. This is to avoid dup pairs. If it's not in the set, check if curnum+2 and curnum-2 in the set, if in, count++.
note: need to consider edge case when k == 0. Change above mentioned set to a count map
time O(n) space O(n)
*/
class Solution {
    public int findPairs(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        int count = 0;
        
        for (int n: nums) {
            if (map.containsKey(n)) {
                if(k == 0 && map.get(n) > 0) {count++; map.put(n,0);}
                continue;
            }
            if (map.containsKey(n+k)) {count++;}
            if (map.containsKey(n-k)) {count++;}
            map.put(n, map.getOrDefault(n,0) + 1);
        }
        return count;
    }
}

// idea same as the first comment in this post
// https://leetcode.com/problems/k-diff-pairs-in-an-array/discuss/100098/Java-O(n)-solution-one-Hashmap-easy-to-understand

// there are naive way -> sort + two pointers way -> two pass map way in the solution
// https://leetcode.com/problems/k-diff-pairs-in-an-array/solution/


// Review
/*Thought
Similar to the idea in two sum. build the count map while traversing the array. Unless k ==0, we will count the first pair, otherwise we will skip same number.
time O(n) 
space O(n)

*/
class Solution {
    public int findPairs(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (int n: nums) {
            if(k != 0) {
                if(map.containsKey(n)) {continue;}
                if(map.containsKey(n + k)) {count++;}
                if(map.containsKey(n-k)) {count++;}
            }
            map.put(n, map.getOrDefault(n, 0)+1);
            if(k == 0 && map.get(n) == 2) {count++;}
        }
        return count;
    }
}