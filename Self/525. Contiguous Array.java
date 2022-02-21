class Solution {
    /* Initial thought
    Use prefix Sum, when encounter 0, sum-1, when encounter1, sum+1.
    Since we want the longest contiguous subarray. Thus if we have preSum1 - preSum2 == 0, then the subarray between [pos1, pos2] should be a valid array with same number of 1 and 0.
    Thus use a hashmap: prefix sum: first position. If we have prefix sum already in hashmap, update result as max(result, i - map.get(sum) + 1).
    time O(n)
    time O(n)
    */
    public int findMaxLength(int[] nums) {
        int maxLen = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0,-1); // don't forget this line
        int preSum = 0;
        for (int i = 0; i < nums.length; i++){
            preSum += nums[i] == 0? -1 : 1;
            if (map.containsKey(preSum)) {maxLen = Math.max(maxLen, i - map.get(preSum));}
            else {map.put(preSum,i);}
        }
        return maxLen;
    }
}