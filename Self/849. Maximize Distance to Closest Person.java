/*Thought
Just find the max gap between each interval within seats occupied. Special case to handle is the [0,0,0,1] and [1,0,0,0] like cases.
time O(n) space O(1)
*/
class Solution {
    public int maxDistToClosest(int[] seats) {
        int maxDist = 0;
        int maxleft = -1;
        int maxright = -1;
        int lastPos = -1;
        int curPos = 0;
        while (curPos <= seats.length) {
            if (curPos == seats.length) {
                maxright = curPos - lastPos - 1;
                break;
            }
            if (seats[curPos] == 1) {
                maxDist = Math.max(maxDist, curPos - lastPos);
                if (lastPos == -1) {maxleft = maxDist - 1;}
                lastPos = curPos;
            }
            curPos++;            
        }
        return Math.max(maxDist / 2, Math.max(maxleft, maxright));
    }
}