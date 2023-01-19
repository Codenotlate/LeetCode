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
// Other ways: https://leetcode.com/problems/least-number-of-unique-integers-after-k-removals/discuss/686335/JavaPython-3-Greedy-Alg.%3A-3-methods-from-O(nlogn)-to-O(n)-w-brief-explanation-and-analysis




// Review 23/1/19 - same as above
/* Thoughts
start removing from low count elements in arr. So first step we need to get a count map.
M1: Sort the count using a heap. Adjust the peek of the heap based on remaining k. if peek.count <= k, then heap.poll(). k -= poll.count. Else the current heap size is the result.
time O(n + nlogn) = O(nlogn) space O(n)

M2: find a way to improve the time to O(n)
Optimize the sort step to counting sort. Since the maxCount is limited to arr.len. Thus after we have the count map, we can use the freqs[len + 1] to label the freq of each count. Then start from left to right, check the freqs, if k >= freq * count, k -= freq * count, totalFreq -= freq; else k / count, subtract this number from totalFreq and return directly.
time o(n) space O(n)


 */
 // M2
class Solution {
    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n: arr) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        int[] freqs = new int[arr.length + 1];
        int totalFreq = map.keySet().size();
        for (int num: map.keySet()) {
            freqs[map.get(num)]++;
        }

        for (int c = 1; c < freqs.length; c++) {
            if (freqs[c]== 0) {continue;}
            if (k >= freqs[c] * c) {
                k -= freqs[c] * c;
                totalFreq -= freqs[c];
            } else {
                totalFreq -= k / c;
                return totalFreq;
            }
        }
        return totalFreq;
    }
}