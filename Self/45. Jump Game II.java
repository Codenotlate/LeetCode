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


// review self
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
            }
            count++;
            
        }
        return count;
    }
}