// note problem setting changes, now S can be negative, so ignore this method, and go to below.
class Solution {
	// M1: dfs + backtracking with memo
	// time O(n * target) n = nums.length, target = (S + sum(nums)) / 2
	// space O(n * target)
	// memory Limit exceeded
    // this solution is no longer valid, cause now S could be negative in the problem. 
    public int findTargetSumWays(int[] nums, int S) {
        int target = 0;
        for (int n: nums) {
        	target += n;
        }
        // this step is crucial, avoid unnecessary space and runtime cost
        // since now S can be negative, we should have abs(S) > target
        if ((target + S) % 2 != 0 || S > target) {return 0;}
        target = (target + S) / 2;
        int[][] memo = new int[nums.length][target + 1];
        for (int[] m: memo) {
        	Arrays.fill(m, -1);
        }

        return dfs(nums, target, 0, memo);
    }

    private int dfs(int[] nums, int target, int curPos, int[][] memo) {
    	if (curPos == nums.length || target < 0) {
    		return target == 0 ? 1 : 0;
    	}
    	if (memo[curPos][target] != -1) {return memo[curPos][target];}
    	int res = dfs(nums, target - nums[curPos], curPos + 1, memo) + dfs(nums, target, curPos + 1, memo);
    	memo[curPos][target] = res;
    	return res;
    }
}


// can also do direct dfs, without converting to target


// still old problem setting, ignore
class Solution {
	// M2: use knapsack pattern with optimized space 1d dp
	// dp[i][w] represents # of subset with sum = target using nums[0:i]
	// time O(n * target) space O(target)
    public int findTargetSumWays(int[] nums, int S) {
        int target = 0;
        for (int n: nums) {
        	target += n;
        }
        // this step is crucial, avoid unnecessary space and runtime cost
        if ((target + S) % 2 != 0 || target < S) {return 0;}
        target = (target + S) / 2;


        int[] dp = new int[target + 1];
        dp[0] = 1; 
        for (int i = 0; i < nums.length; i++) {
        	for (int w = target; w >= nums[i]; w--) {
        		dp[w] = dp[w - nums[i]] + dp[w];
        	}
        }
        return dp[target];
    }
}

//https://leetcode.com/problems/target-sum/discuss/97334/Java-(15-ms)-C%2B%2B-(3-ms)-O(ns)-iterative-DP-solution-using-subset-sum-with-explanation
// why converting the target


// Phase3 (diff from above methods, problem setting changes, now the target can be a negative number)
// the key step is to convert it to subset sum problem
class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int n: nums) {
            sum += n;
        }
        if ((target + sum) % 2 != 0 || Math.abs(target) > sum) {
            return 0;
        }
        int convertT = (target + sum) / 2;
        int[][] memo = new int[nums.length][convertT + 1];
        for (int[] m: memo) {Arrays.fill(m, -1);}
        return dfs(nums, convertT, 0, memo);
    }
    
    
    private int dfs(int[] nums, int t, int i, int[][] memo) {
        if (i >= nums.length) {return t == 0 ? 1 : 0;}
        if (memo[i][t] != -1) {return memo[i][t];}
        int notchoose = dfs(nums, t, i+1, memo);
        int choose = t-nums[i] >= 0? dfs(nums, t - nums[i], i+1, memo) : 0;
        memo[i][t] = choose + notchoose;
        return choose + notchoose;
    }
}

class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int n: nums) {
            sum += n;
        }
        if ((target + sum) % 2 != 0 || Math.abs(target) > sum) {
            return 0;
        }
        int convertT = (target + sum) / 2;
        // dp[i][w] = dp[i-1][w] + dp[i-1][w-nums[i]], then convert to 1d
        int[] dp = new int[convertT + 1];
        dp[0] = 1;
        for (int i = 0; i < nums.length; i++) {
            for (int w = convertT; w >= 0; w--) {
                if (w >= nums[i]) {
                    dp[w] += dp[w - nums[i]];
                }       
            }
        }
        return dp[convertT];
    }
    
    
   
}



// Review
/*Initial thought
Two ways to interpret the target: 
* one way is to directly use it, then along the way, target range can be [target - sum(nums), target + sum(nums)]. Thus we we have an array with size = 2*sum + 1 to track them. And we need to do t+offset along the way to convert index range as [0,2*sum].
* Another way is to convert the target as: sum(+ group) - sum(- group) = target => 2 * sum(+group) = target + sum(nums). Thus the question becomes find a subset having sum = (target + sum(nums)) / 2.

Below two ways choose to use interpretation 1 of target above, interpretation 2 is implemented in earlier records. 
****************************  
Turns out the way using original target is prone to bug in implementation. Thus recommend the converted target way. 
******************************

M1 way: dfs + bktk, since every n in nums has two choices +n or -n. Also noticed here we have repetitive subproblems. i.e. we can memo down result of pair(target, starting idx till nums end). The target range here is [target - sum(nums), target + sum(nums)].
time O(n * t) space O(n *t) where n is the len of nums, t is the sum of nums.

M2 way: bottom-up dp way. dp[i][t] = dp[i+1][t+nums[i]] + dp[i+1][t-nums[i]]. Thus we loop i backwards, and to optimize space, we only need an array with size = 2*sum + 1. And we need to do current target + offset to convert to array index.
time O(n * t) space O(t)
*/
// M1: dfs + memo + original target
class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        // get the sum
        int sum = 0;
        for (int n: nums) {sum += n;}
        if (sum < Math.abs(target)) {return 0;}
        int[][] memo = new int[nums.length][2 * sum + 1];
        for (int[] r: memo) {Arrays.fill(r, -1);}
        // note the sum-target here is the offset, since later offset will change, thus we record sum - original target as offset to pass to later dfs. This offset is used to convert range [target - sum, target + sum] to array index range [0, 2 * sum].
        return find(nums,sum-target, 0, target, memo);
    }
    
    private int find(int[] nums, int offset, int i, int target, int[][] memo) {
        if (i >= nums.length) {return target == 0 ? 1 : 0;}
        if (memo[i][target + offset] != -1) {return memo[i][target +offset];}
        int res = 0;
        res += find(nums, offset,i + 1, target - nums[i], memo) + find(nums, offset, i+1,target + nums[i], memo);
        memo[i][target + offset] = res;
        return res;
    }
}
// M2 dp - original target
class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        // get the sum
        int sum = 0;
        for (int n: nums) {sum += n;}
        if (sum < Math.abs(target)) {return 0;}
        int[] dp = new int[2*sum + 1];
        int offset = sum - target;
        // init dp[len][t]: dp[len][0] = 1
        dp[offset] = 1;
        
        for (int i = nums.length - 1; i >= 0; i--) {
            int[] temp = new int[dp.length];
            for (int t = nums[i]; t + nums[i] <= 2 * sum; t++) {
                temp[t] = dp[t+nums[i]] + dp[t-nums[i]];
            }
            dp = temp;
        }
        return dp[target + offset];
    }
}




















