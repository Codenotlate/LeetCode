/* From solution:  time O(n) space O(1)
https://leetcode.com/problems/minimum-moves-to-equal-array-elements/discuss/93817/It-is-a-math-question
math way: prove idea: original sum + m * (n - 1) = x * n. And since each time we add 1 to the n-1 elements other than the curmax, the original min value of the array will always get add 1 till it reaches final equal number x. Thus we also have x = min + m. Combine these two equation, we can solve it in math way:  m = sum - min * n.
*/
class Solution {
    public int minMoves(int[] nums) {
        int sum = 0;
        int min = Integer.MAX_VALUE;
        for (int n: nums) {
            sum += n;
            min = Math.min(n, min);
        }
        return sum - min * nums.length;
    }
}


// There's also other ways in solution.











// Naive way: TLE
/*Intial thought
each move increment (n-1) elements and we want min move numbers, thus at each step the best way is to leave the biggest one unchanged and increments the rest n-1 elements. Because if we leave another element unchanged the largest element will get even larger and we need extra step later to make the unchanged element equal to the largest.
Each round loop all num in nums,init nextmax = curmax; if num == curmax, maxCount++ and if maxCount== 1, num unchange; other num++, nextmax = Math.max(updated num, nextmax); also tracking round number.
when maxCount == nums.length for that round, return round number.
*/
class Solution {
    public int minMoves(int[] nums) {
        int roundNum = 0;
        int curmax = Integer.MIN_VALUE;
        for (int num: nums) {curmax = Math.max(curmax, num);}
        int maxCount = 0;
        while (maxCount != nums.length) {
            int nextmax = curmax;
            maxCount = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == curmax) {
                    maxCount++;
                    if (maxCount == nums.length) {return roundNum;}
                    if (maxCount == 1) {continue;}
                } 
                nums[i]++;
                nextmax = Math.max(nextmax, nums[i]);
            }
            curmax = nextmax;
            roundNum++;
        }
        return roundNum;
    }
}

