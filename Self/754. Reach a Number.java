/*Thought
Math way to find the rule. First notice negative and positive targets are symmetric, thus we can consider positive target only.
first get the first k with cursum = 1 +2+...+k >= target. If cursum == target, return k. Else get diff = cursum - target. If diff % 2 == 0, meaning we can have diff/2 in 1 to k with negative sign, then 1+2+3+..(-diff/2)+..+k == target. Else diff % 2 != 0, then k is impossible, we consider k+1 steps. If (k+1) is odd, then new delta = diff + k + 1, will be even, then we can return k+1. Else if (k+1) is even, then new delta is till odd, and we need to consider k+2, whch must have even delta, thus we return k+2.

time O(sqrt(target)) space O(1)
*/
class Solution {
    public int reachNumber(int target) {
        if (target < 0) {target *= -1;}
        int k = 0;
        int cursum = 0;
        while (cursum < target) {
            k++;
            cursum += k;
        }
        if (cursum == target) {return k;}
        int diff = cursum - target;
        if (diff % 2 == 0) {return k;}
        return k + 1 + k % 2;
    }
}