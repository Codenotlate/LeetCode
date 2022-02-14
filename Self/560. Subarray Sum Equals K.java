class Solution {
    /*Initial thought: need to consider negative num
    naive way: get all the sum of subarrays and find how many equal to k. time O(n^2)
    improved way, storing all prefix sum. Then check how many prefixsum diff == k. prfixsum can be stored in a dict:(prefixSum, endidx). We track endidx because it's possible that we have negative prefixsum, in that case only check prefixsum - k exists is not enough, we also need that corresponding prefixsum produced by the subarray of ealier prefixsum array.
    What if we have same prefixSum for different end index?
    solution: we can instead store a dict :(prefixSum, count). We achieve above endidx goal by checking the dict along the way. Thus a prefixSum only has the prefixSum of its subarray and we don't need to worry about reverse order.
    time O(n) space O(n)
    */
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> sumIdxMap = new HashMap<>();
        int prefixSum = 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            prefixSum += nums[i];
            if (prefixSum == k) {count++;}
            // check along the way
            int otherSum = prefixSum - k;
            if (sumIdxMap.containsKey(otherSum)) {count += sumIdxMap.get(otherSum);}           
            sumIdxMap.put(prefixSum, sumIdxMap.getOrDefault(prefixSum, 0) + 1); 
        }
        return count;
    }
}















// Method no longer working: NOTE: since the numer in nums can be negative now, we can't use sliding window any more
// class Solution {
//     /*Initial thought
//     use sliding window. expand the current window until curSum >= k, then move left side until curSum < k, and add the steps left side moves to the final result. Repeat the window expand and left side move until right side reaches the end of the array.
//     time O(n) space O(1)
//     */
//     public int subarraySum(int[] nums, int k) {
//         int left = 0;
//         int right = 0;
//         int curSum = 0;
//         int count = 0;
//         while (left < nums.length || right < nums.length) {
//             if (curSum < k && right < nums.length) {
//                 curSum += nums[right];
//                 right++;
//             } else {
//                 if (curSum == k && left < right) {count++;}
//                 curSum -= nums[left];
//                 left++;
//             }            
//         }
//         return count;
//     }
// }