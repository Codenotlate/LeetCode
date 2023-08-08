//phase 3  BFS idea
// detail see goodnote

class Solution {
    public int jump(int[] nums) {
        int i = 0;
        int level = 0;
        int curLevelMax = 0;
        int nextLevelMax = 0;

        while ( i < nums.length) {
        	if (curLevelMax >= nums.length - 1) {return level;}
        	while (i <= curLevelMax) {
        		nextLevelMax = Math.max(nextLevelMax, i + nums[i]);
        		i++;
        	}
        	level++;
        	curLevelMax = nextLevelMax;
        }
        return -1; // should not go to this line
    }
}


// review self O(n) way
class Solution {
    public int jump(int[] nums) {
        int farp = 0;
        int count = 0;
        int i = 0;
        while(i < nums.length) {
            if (farp >= nums.length - 1) {return count;}
            int lastfarp = farp;
            while (i <= lastfarp) {
                farp = Math.max(farp, i + nums[i]);
                i++;
            }
            count++;
            
        }
        return count;
    }
}


// Review
/*Initial thought
dp way: dp[i] = 1 + min(dp[k]) where k in [i+1, i+nums[i]].
[2,1,2,1,0] O(n^2) space O(n)
improve it to O(n) using greedy way:
[2, 3, 1, 1, 4] compare i + nums[i] with j + nums[j], choose the larger one
cur i  j
   cur i     j
[2, 3, 0, 1, 4]
cur
   cur
*/
// M1: DP way O(n^2) time O(n) space
class Solution {
    public int jump(int[] nums) {
        int[] dp = new int[nums.length];
        for (int i = nums.length - 2; i >= 0 ; i--) {
            int minNum = nums.length + 1;
            for (int k = i+1; k < nums.length && k <= i + nums[i]; k++) {
                minNum = Math.min(minNum, dp[k]);
            }
            dp[i] = 1 + minNum;
        }
        return dp[0];
    }
}
// M2: Greedy way O(n) time O(1) space
// similar idea as above BFS way, utilize i + nums[i].
class Solution {
    public int jump(int[] nums) {
        int cur = 0;
        int n = nums.length;
        int steps = 0;
        while(cur < n) {
            if(cur + nums[cur] >= n-1) {break;}
            int next = cur + 1;
            for (int i = cur + 1; i <= cur + nums[cur] && i < n; i++) {
                if (next + nums[next] < i + nums[i]) {next = i;}
            }
            cur = next;
            steps++;
        }
        return n == 1 ? 0 : steps + 1;
    }
}


// Review
/*Thoughts
Use greedy way, for each num in nums, it has the potential to expand the most right position we can get. for cur <= right, and we check if cur + nums[cur] > right, if that's the case, meaning with current step + 1, we can expand the right side to nextright, which is the max pos of all pos <= current right can reach to. If we have cur > right, then step++, right = nextright, and nextright = cur + nums[cur]. And whenever we have right >= n-1,  current step number will be the min result to return.
Time O(n) space O(1)
 */
class Solution {
    public int jump(int[] nums) {
        int right = 0;
        int step = 0;
        int nextright = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i <= right && nextright < i + nums[i]) {
                nextright = i + nums[i];
            } else if (i > right) {
                right = nextright;
                nextright = i + nums[i];
                step++;
            }
            if (right >= nums.length - 1) {return step;}            
        }
        return step; // should not reach this line

    }
}






// Review 23/8/7 - used hint
/*
Way 1 O(n^2): dp[i] = min(dp[j] + 1) where j + nums[j]  > = i
Way 2 O(n) gready way: think about all positions that can reach to with same step amounts as tree nodes at same level. Then we can BFS. Or we can view all positions in the same window having the same step amounts. Then the start of the next window will be current far + 1, and step++. (Basically, whenever we have the left side of the window exceeding the previous right side of the window(farest), we do step++.)
 */

class Solution {
    public int jump(int[] nums) {
        int far = 0;
        int step = 0;
        int i = 0;
        while ( i < nums.length) {
            if (far >= nums.length - 1) {return step;}
            int nextfar = far;
            while (i <= far) {
                nextfar = Math.max(nextfar, i + nums[i]);
                i++;
            }
            far = nextfar;
            step++;
        }
        return step;
    }
}