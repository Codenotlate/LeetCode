/* from solution
notice that if you have duplicate values in nums array, if you earn one of them, you end up earning all of them. This is because you have deleted its neighbors and therefore make its remaining duplicates "undeletable".

Thus the problem can be converted to a set of (num:num*count), and we get the max sum out of it based on the no num-1 and num+1 rule. Using DP.
if forwards: dp[i] = max(arr[i]+dp[i+2], dp[i+1])
if backwards: dp[i] = max(arr[i] + dp[i-2], dp[i-1]).

One way is to use an array with size(max-min+1). count * num stores in arr[num-min].
time O(n+max-min) space O(max-min)
Another space saving way is to replace array with a map.
time same, space O(min(max-min, n))

https://leetcode.com/problems/delete-and-earn/discuss/109895/JavaC%2B%2B-Clean-Code-with-Explanation

https://leetcode.com/problems/delete-and-earn/discuss/109891/Sharing-my-Simple-Straight-Forward-Java-O(n)-Solution-Explanation-Included

*/
//M1
class Solution {
    public int deleteAndEarn(int[] nums) {
        int min = 10001;
        int max = 0;
        for (int n: nums) {
            min = Math.min(min, n);
            max = Math.max(max, n);
        }
        int[] arr = new int[max-min+1];
        for (int n: nums) {
            arr[n-min] +=n;
        }
        
        int dp = arr[max-min];
        int dp_next = 0;
        for (int i = max-min-1; i>= 0; i--) {
            int temp = dp;
            dp = Math.max(dp, arr[i] + dp_next);
            dp_next = temp;
        }
        return dp;
    }
}

//M2
class Solution {
    public int deleteAndEarn(int[] nums) {
        int min = 10001;
        int max = 0;
        for (int n: nums) {
            min = Math.min(min, n);
            max = Math.max(max, n);
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int n: nums) {
            map.put(n, map.getOrDefault(n,0)+n);
        }
        
        int dp = map.get(max);
        int dp_next = 0;
        for (int i = max-1; i>= min; i--) {
            int temp = dp;
            dp = Math.max(dp, map.getOrDefault(i,0) + dp_next);
            dp_next = temp;
        }
        return dp;
    }
}











// Review
/*Thought
M1: use bucket sort, using an array with size l(l= max - min). And put each value to its corresponding pos and its count sum as the element at that pos.  Then do a backwards. dp[l-1] = count[l-1]. for i in range (l-2,0), dp[i] = max(dp[i+1], dp[i+2]+countSum[i]). And we return dp[0].
time O(l+n) space O(l)
The space of DP can be optimized to O(1), but since we need the countSum, O(space) unchanged.

M2: for examples like [1,2,10001]. above way will take a lot of unnecessary time. Thus we can use a nonconsecutive countsum map with size n. But in this case, for dp[i], we need to sort the keys in map and go from largest to smallest. And need to deal with cases where dp[i+1] or dp[i+2] not exist.
time O(nlogn) space O(n)
*/
// M1: same as above M1, space can be optimized as above M2
class Solution {
    public int deleteAndEarn(int[] nums) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int n: nums) {min = Math.min(min,n); max = Math.max(max, n);}
        int[] countSum = new int[max-min+1];
        for (int n: nums) {countSum[n-min] += n;}
        
        int dp_i2 = 0;
        int dp_i1 = countSum[max-min];
        for (int i = max-min-1; i >=0; i--) {
            int dp_i = Math.max(countSum[i] + dp_i2, dp_i1);
            dp_i2 = dp_i1;
            dp_i1 = dp_i;
        }
        return dp_i1;
    }
}
//M2: for cases like [1,2,10000001]
class Solution {
    public int deleteAndEarn(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> keys = new ArrayList<>();
        for (int n: nums) {
            if (!map.containsKey(n)) {keys.add(n);}
            map.put(n, map.getOrDefault(n, 0) + n);
        }
        
        Collections.sort(keys);
        
        for (int i = keys.size() -2; i >=0; i--) {
            int key = keys.get(i);
            int dp_i = 0;
            if (!map.containsKey(key+1)) {
                dp_i = map.get(key)+ (map.containsKey(key+2)? map.get(key+2) : map.get(keys.get(i+1)));
            } else {
                int dp_i2 =  i < keys.size()-2 && keys.get(i+2) >= key+2 ? map.get(keys.get(i+2)) : 0;
                dp_i = Math.max(map.getOrDefault(key+1,0), map.get(key) + dp_i2);
            }
            
            map.put(key, dp_i);
        }
        
        return map.get(keys.get(0));
    }
}