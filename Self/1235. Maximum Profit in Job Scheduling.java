// 2024.10.22 - self come up with optimal answer in 30min
// sort by start then end first, dp + bs -  time O(nlogn)
// remember to adjust profit corresponding to the time sort 
class Solution {
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] gaps = new int[n][3];
        for (int i = 0; i < n; i++) {
            gaps[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        Arrays.sort(gaps, (a,b) -> (a[0]==b[0]?a[1]-b[1]:a[0]-b[0]));
        int[] dp = new int[n];
        dp[n-1] = gaps[n-1][2];
        int res = dp[n-1];
        for (int i = n-2; i >= 0; i--) {
            int nextLte = findNextValid(gaps, gaps[i][1]);
            int dp_nextLte = nextLte == -1? 0: dp[nextLte];
            dp[i] = Math.max(dp[i+1], gaps[i][2]+dp_nextLte);
            res = Math.max(res, dp[i]);
        }
        return res;    
    }


    private int findNextValid(int[][] gaps, int curEnd) {
        int left = 0;
        int right = gaps.length-1;
        while (left < right) {
            int mid = left + (right-left)/2;
            if (gaps[mid][0] >= curEnd) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return gaps[left][0] < curEnd ? -1:left;

    }
}