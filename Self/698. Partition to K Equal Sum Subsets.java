// Phase 2: Self M1 self opinion: O(k^n)
// not intuitive
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int allSum = 0; int maxNum = 0;
        for (int num: nums) {
        	allSum += num;
            maxNum = Math.max(maxNum, num);
        }
        if (allSum % k != 0 || maxNum > allSum / k) {return false;}
        int target = allSum / k;
        int[] targets = new int[k];
        Arrays.fill(targets, target);

        return dfs(nums, nums.length - 1, targets, k);
    }

    private boolean dfs(int[] nums, int curPos, int[] targets, int k) {
    	if (curPos < 0) {return k == 0;}
    	Set<Integer> visited = new HashSet<>();
        for (int i = 0; i < targets.length; i++) {
            if (visited.contains(targets[i])) {continue;}
    		int val = targets[i] - nums[curPos];
            visited.add(targets[i]);
    		if (val >= 0) {
    			if (val == 0) {k -= 1;}
    			targets[i] = val;
    			if (dfs(nums, curPos - 1, targets, k)) {return true;}
    			targets[i] += nums[curPos];
    		}
    	}
    	return false;
    }
}


// M2: another DFS, self opinion time O(k * target *(2 ^ n))
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int allSum = 0; int maxNum = 0;
        for (int num: nums) {
        	allSum += num;
            maxNum = Math.max(maxNum, num);
        }
        if (allSum % k != 0 || maxNum > allSum / k) {return false;}
        int target = allSum / k;
        int[] visited = new int[nums.length];

        return dfs(nums, 0, 0, target, k, visited);
    }

    private boolean dfs(int[] nums, int curPos, int curSum, int target, int k, int[] visited) {
    	if (k == 1) {return true;}
        if (curSum == target) {return dfs(nums, 0, 0, target, k - 1, visited);}
        for (int i = curPos; i < nums.length; i++) {
            if (visited[i] == 1) {continue;}
    		if (curSum + nums[i] <= target) {
                visited[i] = 1;
    			if (dfs(nums, curPos + 1, curSum + nums[i], target, k, visited)) {return true;}
    			visited[i] = 0;
    		}
    	}
    	return false;
    }
}


// dp way
// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/discuss/335668/DP-with-Bit-Masking-Solution-%3A-Best-for-Interviews



// Phase3 self -- TLE
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int total = 0;
        int maxn = 0;
        for (int n: nums) {total += n; maxn = Math.max(maxn, n);}
        if (total % k != 0 || total / k < maxn) {return false;}
        int target = total / k;
        int[] visited = new int[nums.length];
        int[] memo = new int[k];
        memo[0] = 1;
        return dfs(nums, visited, target, 0, k);
    }
    
    
    private boolean dfs(int[] nums, int[] visited, int target, int curSum, int k, int[] memo) {
        if (k == 1) {return true;}
        if (memo[k-1] != 0) {return memo[k-1] == 1;}
        if (curSum == target) {
            boolean res = dfs(nums, 0, 0, target, k - 1, visited, memo);
            memo[k-1] = res? 1: -1;
            return res;
        }
        for (int i = 0; i < nums.length; i++){
            if (visited[i] == 0 && curSum + nums[i] <= target) {
                visited[i] = 1;
                if (dfs(nums, visited, target, curSum + nums[i], k, memo)) {
                    memo[k-1] = 1;
                    return true;
                }
                visited[i] = 0;
            }
        }
        memo[k-1] = -1;
        return false;
    }
}