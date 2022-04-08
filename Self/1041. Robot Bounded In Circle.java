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







// Review
/* Thought
To never leave the circle, one case is after 1 pass ended at the starting position. Another case is after 4 passes ended at the starting position.
M1 way: repeat thee instructions 4 time see if it ends at pos (0,0). This way time is O(4n)  = O(n), space O(1). we will need four directions, and a relationship to find the next direction based on current direction and the L/R instruction.

M2 way; optimize to one pass. Lack of proof here. But seems like as long as we don't end up facing north at positions other than (0,0), we can always get back to starting position after at most 4 times. 

*/

// M1: 4 time way 0 similar as above M1. But above way more elegant in finding the next dir. Based on current dir, add diff gap for L or R to find the next dir.
class Solution {
    public boolean isRobotBounded(String instructions) {
        int[][] dirs = new int[][]{{-1,0},{0,-1},{1,0},{0,1}};
        int[][] nextdir = new int[][]{{1,3},{2,0},{3,1},{0,2}}; // combine left and right next dir together, first one is the index of the dir in dirs for 'L' and second one for 'R'.
        int x = 0;
        int y = 0;
        int dx = 0;
        int dy = 1;
        int n = instructions.length();
        int i = 0;
        while ( i < 4 * n) {
            char c = instructions.charAt( i%n);
            if ( c == 'G') {x += dx; y +=dy;}
            else {
                int  lrIdx = c == 'L'? 0 : 1;
                if ( dx == -1 && dy == 0) {dx = dirs[nextdir[0][lrIdx]][0]; dy = dirs[nextdir[0][lrIdx]][1];}
                else if ( dx == 0 && dy == -1) {dx = dirs[nextdir[1][lrIdx]][0]; dy = dirs[nextdir[1][lrIdx]][1];}
                else if ( dx == 1 && dy == 0) {dx = dirs[nextdir[2][lrIdx]][0]; dy = dirs[nextdir[2][lrIdx]][1];}
                else {dx = dirs[nextdir[3][lrIdx]][0]; dy = dirs[nextdir[3][lrIdx]][1];}
            }
            i++;
        }
        return x==0 && y==0;
        
    }
}
// M2: one pass way
// Note: still need to process G. Can't only count on whether LR count % 4 != 0. Because it's still true even when LR count % 4 == 0 if it returned to (0,0) in the end. Thus update based on 4 times way.
class Solution {
    public boolean isRobotBounded(String instructions) {
        int[][] dirs = new int[][]{{0,1},{-1,0},{0,-1},{1,0}};
        int x = 0;
        int y = 0;
        int dirIdx = 0;
        int n = instructions.length();
        int i = 0;
        while ( i < n) {
            char c = instructions.charAt(i);
            if ( c == 'G') {x += dirs[dirIdx][0]; y +=dirs[dirIdx][1];}
            else if (c == 'L') { dirIdx = (dirIdx + 1) % 4;}
            else {dirIdx = (dirIdx + 3) % 4;}    
            i++;
        }
        if (x == 0 && y == 0 || dirIdx != 0) {return true;}
        return false;

    }
}


/* Math proof for M2: https://leetcode.com/problems/robot-bounded-in-circle/discuss/290856/JavaC%2B%2BPython-Let-Chopper-Help-Explain
Try to prove with math representation:
Let's say the robot starts with facing up. It moves [dx, dy] by executing the instructions once.
If the robot starts with facing
right, it moves [dy, -dx];
left, it moves [-dy, dx];
down, it moves [-dx, -dy]

If the robot faces right (clockwise 90 degree) after executing the instructions once,
the direction sequence of executing the instructions repeatedly will be up -> right -> down -> left -> up
The resulting move is [dx, dy] + [dy, -dx] + [-dx, -dy] + [-dy, dx] = [0, 0] (back to the original starting point)

All other possible direction sequences:
up -> left -> down -> right -> up. The resulting move is [dx, dy] + [-dy, dx] + [-dx, -dy] + [dy, -dx] = [0, 0]
up -> down -> up. The resulting move is [dx, dy] + [-dx, -dy] = [0, 0]
up -> up. The resulting move is [dx, dy]
*/