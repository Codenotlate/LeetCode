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



// Below used above M1 binary search way. Above M2 way is better
// 2024.10.31
// basically for each query range, find the first candle included and last candle included, then sum all the plates in between. Can have a prefixSum for each candle first, which then makes the "sum all plates in between" step a O(1) operation.
// for each query we use binary search to find the first candle >= left, and find the first candle <= right. Thus we can maintain an array for candle indexes. 
// time: O(s.length)(for candle position and prefixSum) + O(query_len * log(candle_len)); space O(candle_len)
// trickier than I thought, a lot of edge cases. took a lot of time to debug.
class Solution {
    public int[] platesBetweenCandles(String s, int[][] queries) {
        List<Integer[]> candles = new ArrayList<>();
        int countPlates = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '*') {
                countPlates++;
            } else {
                candles.add(new Integer[]{i, countPlates});
            }
        }
        int[] res = new int[queries.length];
        // handle special case where candles is empty
        if (candles.size() == 0) {return res;}
        for (int i = 0;i<queries.length; i++) {
            int[] query = queries[i];
            int firstCandle = findFirst(candles, query[0]);
            int lastCandle = findLast(candles, query[1]);
            if (firstCandle != -1 && lastCandle != -1 && candles.get(firstCandle)[0] <= query[1] && candles.get(lastCandle)[0] >= query[0]) {
                res[i] = candles.get(lastCandle)[1]-candles.get(firstCandle)[1];
            }
            
        }
        return res;
    }

    private int findFirst(List<Integer[]> candles, int target) {
        int left = 0;
        int right = candles.size()-1;
        while (left < right) {
            int mid = left + (right-left) /2;
            if (candles.get(mid)[0] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return candles.get(left)[0] >= target ? left : -1;
    }

    private int findLast(List<Integer[]> candles, int target) {
        int left = 0;
        int right = candles.size()-1;
        int res = -1;
        while (left < right) {
            int mid = left + (right-left) /2;
            if (candles.get(mid)[0] <= target) {
                res = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return candles.get(left)[0] <= target ? left : res;
    }

}