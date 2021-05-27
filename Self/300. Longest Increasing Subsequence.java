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



// Phase 3
class Solution {
    public int lengthOfLIS(int[] nums) {
        //M1: DP way time O(n^2)
        // dp[i] represents the LIS of nums[1:i] when i is included in the result sequence
        int n = nums.length;
        int[] dp = new int[nums.length];
        // note: need to initialize all to 1 first
        Arrays.fill(dp, 1);
        int max = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }                              
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}



// M2: patience sort - card game algo
// https://leetcode.com/problems/longest-increasing-subsequence/discuss/74824/JavaPython-Binary-search-O(nlogn)-time-with-explanation
// https://leetcode.com/problems/longest-increasing-subsequence/discuss/74880/JAVA-Easy-Version-To-Understand!!!!!!!!
class Solution {
    public int lengthOfLIS(int[] nums) {
        if (nums.length <= 1) {return nums.length;}

        int[] tileTop= new int[nums.length]; // tileTop is an increasing array
        int tileCount = 0; // count the existing tile numbers
        tileTop[tileCount++] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > tileTop[tileCount - 1]) {
                tileTop[tileCount++] = nums[i];
            } else {
                int updatePos = findUpdatePos(tileTop, 0, tileCount - 1, nums[i]);
                tileTop[updatePos] = nums[i];
            }
        }
        return tileCount;
    }


    // want to find the first one larger than or equal to target
    private int findUpdatePos(int[] arr, int start, int end, int target) {
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (arr[mid] < target) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start;
        


    }
}


// another card sort implementation
class Solution {
    public int lengthOfLIS(int[] nums) {
        List<Integer> topList = new ArrayList<>();
        
        for(int n: nums) {
            int pos = findFirstLte(topList, n);
            if (pos == -1) {
                topList.add(n);
            } else {
                topList.set(pos, n);
            }
        }
        
        return topList.size();
    }
    
    private int findFirstLte(List<Integer> list, int k) {
        if (list.isEmpty()) {return -1;}
        int start = 0;
        int end = list.size() - 1;
        
        
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (list.get(mid) < k) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        
        return list.get(start) >= k ? start : -1;
    }
}















