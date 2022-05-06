/*Initial thought
involving sum and continuous subarray, think about presum. Given for each preSum(i), we need to check the presum on [0,i-2]. It's going to be time O(n^2). space O(n)

whether could improve to O(n)?
notice, if we only need them to sum up to k, then we don't need to check every presum in [0,i-2], we can simply have a hashSet and check whether presum(i) - k is in the hashset. But here we need to have multiple of k.
Given this, if we convert presum as the presum mod k, then the problem converts to the similar version as above. for each current presum mod k (x), we only need to check whether x is already in hashMap, if it is and the corresponding subarray len >= 2, return true.

after optimize: time O(n) space O(min(k,n)) for hashMap, since mod k in [0,k-1]. HashMap can also be replaced by an array with size k. And initialized with -2. (since -1 to n-1 will be used to label the position).
DON'T USE ARRAY instead of map, since k can be pretty large.

*/

/*
debug sample1: [1,0] k = 2
Note: here we can not use hashSet only, because we have subarray len >= 2 req, thus we need to know the position of previous presum%k to check the len.
debug sample2: [5,0,0,0] k = 3
*/
// This way will have memory exceed when k is pretty large k = 1000000000
class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0,-1);
        int preSum = 0;
        for (int i = 0; i < nums.length; i++) {
            preSum += nums[i];
            int mod = preSum % k;
            if (map.containsKey(mod) && map.get(mod) <= i-2) {return true;}
            else if(!map.containsKey(mod)){map.put(mod, i);}
        }
        return false;
    }
}

// similar as the way here
// https://leetcode.com/problems/continuous-subarray-sum/discuss/99499/Java-O(n)-time-O(k)-space



// This way will have memory exceed when k is pretty large k = 1000000000
// class Solution {
//     public boolean checkSubarraySum(int[] nums, int k) {
//         int[] pos = new int[k];
//         Arrays.fill(pos,-2);
//         pos[0] = -1;
//         int preSum = 0;
//         for (int i = 0; i < nums.length; i++) {
//             preSum += nums[i];
//             int mod = preSum % k;
//             if (pos[mod] != -2 && pos[mod] <= i-2) {return true;}
//             else if(pos[mod] == -2){pos[mod] = i;}
//         }
//         return false;
//     }
// }





// Review - O(n) time O(k) space same as above
/* Thought
subarray -> think about accumsum
Also multiple of k can be convert as: if A%k == B % k, then (A-B) % k == 0
Thus all value can be categoriesed into k classes [0,k-1].
Then for each element, cumsum += n. rem = cumsum % k, and we check if map contains rem and its corresponding pos is not curpos-1, return true. Otherwise, if map not contains rem. map.put(ren, curpos). return false in the end. Since k might be huge, replace the map with size k array will cause space limit exceed in leetcode.

*/


class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        map.put(0,-1); // since 0 is always valid
        int cumsum = 0;
        for (int i = 0; i < nums.length; i++) {
            cumsum += nums[i];
            int rem = cumsum % k;
            if (map.getOrDefault(rem, i) < i-1) {return true;}
            if (!map.containsKey(rem)) {map.put(rem, i);}
        }
        return false;
    }
}
