class Solution {
    /* Initial thought
    Since the instructions are repeated forever, if we can't get back to (0,0) within 4 times of instructions, then it's impossible to get back even with more times repeat.
    If it gets back with 1/2/3 times of instructions, return true.
    dirIdx init as 0, face north. 'L' makes (dirIdx - 1 + 4)%4. 'R' makes (dirIdx + 1)%4. 'G' make position change to[cur[0] + dir[0], cur[1] + dir[1]].
    time O(n) space O(1)
    */
    public boolean isRobotBounded(String instructions) {
        int[][] dirs = new int[][]{{0,1},{1, 0},{0, -1},{-1, 0}};
        int len = instructions.length();
        int dirIdx = 0;
        int[] cur = new int[]{0,0};
        for (int i = 0; i < 4; i++) {
            for (char c: instructions.toCharArray()) {
                int[] curdir = dirs[dirIdx];
                if (c == 'G') {cur[0] += curdir[0]; cur[1] += curdir[1];}
                else if (c == 'L') {dirIdx = (dirIdx + 3) % 4;}
                else {dirIdx = (dirIdx + 1) % 4;}
            }
            if (cur[0] == 0 && cur[1] == 0) {return true;}
        }
        return false;
    }
}


// solution share similar idea as above method, but only with one pass.
/* This solution is based on two facts about the limit cycle trajectory.
1) After at most 4 cycles, the limit cycle trajectory returns to the initial point x = 0, y = 0. That is related to the fact that 4 directions (north, east, south, west) define the repeated cycles' plane symmetry [proof].
2) We do not need to run 4 cycles to identify the limit cycle trajectory. One cycle is enough. There could be two situations here.
First, if the robot returns to the initial point after one cycle, that's the limit cycle trajectory.
Second, if the robot doesn't face north at the end of the first cycle, that's the limit cycle trajectory. Once again, that's the consequence of the plane symmetry for the repeated cycles [proof].



*/
// https://leetcode.com/problems/robot-bounded-in-circle/solution/
// also proof included