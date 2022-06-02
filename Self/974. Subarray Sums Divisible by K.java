/*Thought
subarray -> prefix sum with hasmap or int[]
%k -> 0 to (k-1) classes
for each num, calculate the cur accumsum converted by %k, so always in range (0, k-1). If accumsum = 0, res add 1 first. Then check the count in the hashmap(array) for previous prefix == accumsum, add that count to res. Update the count for accumsum and move on till all nums are processed.
time O(n) space O(min(n,k))

note in Java -1 % 5 = -1
*/
class Solution {
    public int subarraysDivByK(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int res = 0;
        int accumsum = 0;
        for (int n:nums) {
            accumsum = (accumsum + n) %k;
            if (accumsum < 0) {accumsum += k;} // for negative module in java
            if (accumsum == 0) {res++;}
            int prevCount = map.getOrDefault(accumsum,0);
            res += prevCount;
            map.put(accumsum, prevCount +1);
        }
        return res;
    }
}