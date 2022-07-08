/*Thought
M1: get the count of plates from starting point to each candle pos. Build a map like <candle pos, count>. Then for each range, do binary search to find the first candle pos >= left range, and the first candle pos <= right range. Count of pos2 - count of pos1 will be the result.
time O(n + klogn) space O(n)

M2: similar to M1, but for each position, we directly use two arrays to label the count of first candle on the right(used when this pos is the left side of the range) and on the left(used when pos is the right side of range).
time O(n+k) space O(n)

*/
class Solution {
    public int[] platesBetweenCandles(String s, int[][] queries) {
        int n = s.length();
        int[] left = new int[n];
        int[] right = new int[n];
        int curcount = 0;
        int leftCandleCount = 0;
        boolean hasFirstCandle = false;
        // fill the left array
        for (int i = 0; i < n; i++) {
            if (hasFirstCandle && s.charAt(i) == '*') {
                curcount++;
            } else if(s.charAt(i) == '|') {
                hasFirstCandle = true;
                leftCandleCount = curcount;
            }
            left[i] = leftCandleCount;
        }
        // fill the right array
        int rightCandleCount = 0;
        for (int i = n-1; i >= 0; i--) {
            if (s.charAt(i) == '*') {
                right[i] = rightCandleCount;
            } else {
                right[i] = left[i];
                rightCandleCount = right[i];
            }
        }
        
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int leftSide = queries[i][0];
            int rightSide = queries[i][1];
            // don't forget these two special cases
            if (leftSide == rightSide) {res[i] = 0; continue;}
            res[i] = Math.max(0,left[rightSide] - right[leftSide]);
        }
        return res;
        
    }
}
// similar to https://leetcode.com/problems/plates-between-candles/discuss/1549018/JavaC%2B%2BPython-Binary-Search-and-O(Q-%2B-N)-Solution