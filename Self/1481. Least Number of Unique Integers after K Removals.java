/* Initial thought
First build the count map of numbers in arr. In order to have least number of unique int after k elements removed, we need to remove those number with less count with higher priority.
Can first build a count map, then sort all the count number using counting array int[arr.length] and start from the smaller count to compare with k.
time O(n)
space O(n)
*/
// This is the optimized way
class Solution {
    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int maxCount = 0;
        for (int n: arr) {
            map.put(n, map.getOrDefault(n, 0) + 1);
            maxCount = Math.max(maxCount,map.get(n));
        }
        
        int total = map.keySet().size();
        int[] count = new int[maxCount + 1];
        for (int num: map.keySet()) {
            count[map.get(num)]++;
        }
        
        for (int i = 0; i < count.length; i++) {
            if (count[i] == 0) {continue;}
            while (count[i]-- > 0) {
                k -= i;
                if (k < 0) {return total;}
                total--;       
            }
                 
        }
        return 0; // should not reach this line
    }
}
// Other ways: https://leetcode.com/problems/least-number-of-unique-integers-after-k-removals/discuss/686335/JavaPython-3-Greedy-Alg.%3A-3-methods-from-O(nlogn)-to-O(n)-w-brief-explanation-and-analysis.ø