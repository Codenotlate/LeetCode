class Solution {
	// M1: dp way time O(n^2) space O(n)
	// dp[i] represents the LIS length of nums[0:i] i included
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        // initilize as 1
        Arrays.fill(dp, 1);
        for (int i = 1; i < n; i++) {
        	for (int j = 0; j < i; j++) {
        		if (nums[j] < nums[i]) {
        			dp[i] = Math.max(dp[i], dp[j] + 1);
        		}
        	}
        }

        int res = 0;
        for (int l: dp) {
        	res = Math.max(res, l);
        }
        return res;
    }
}


//https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247484498&idx=1&sn=df58ef249c457dd50ea632f7c2e6e761&chksm=9bd7fa5aaca0734c29bcf7979146359f63f521e3060c2acbf57a4992c887aeebe2a9e4bd8a89&scene=21#wechat_redirect

class Solution {
	// M2: O(nlogn) way using binary search
	// patience sorting algo: math prove omit
	// separate into piles, each time find the leftmost pile with top >= cur, if can't find, create a new pile
	// the result is the number of piles after iterating all n in nums
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] top = new int[n + 1];
        int piles = 0;
        for (int num: nums) {
        	// binary search first element > num in top
        	int left = 0, right = piles;
        	while (left < right) {
        		int mid = (right - left) / 2 + left;
        		if (top[mid] < num) {
        			left = mid + 1;
        		} else {
        			right = mid;
        		}
        	}

        	// if can't find one, then build a new pile
        	if (left == piles) {
        		piles += 1;
        	}
        	top[left] = num;
        }
        return piles;
    }
}
















