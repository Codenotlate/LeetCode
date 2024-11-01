// 2024.10.29
// Binary search on range [min, max], for each target, check if it's possible. The check uses greedy way, from left to right, count consecutive day < target case, and add bcount when countdays >= k. This way can work bcs we are using a greedy way, to leave more to the futrue. Compare bcount with m to see if the target can be achieved. Then adjust binary range to right = mid if valid, and left = mid + 1 if not valid.
// time O(nlog(max-min))
class Solution {
    public int minDays(int[] bloomDay, int m, int k) {
        if ((long)m * k > bloomDay.length) {return -1;}
        int left = bloomDay[0];
        int right = bloomDay[0];
        for (int day: bloomDay) {
            left = Math.min(left, day);
            right = Math.max(right, day);
        }
        while (left < right) {
            int mid = left + (right-left) / 2;
            if (isValid(mid, bloomDay, m, k)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return isValid(left, bloomDay, m, k)? left : -1;

    }

    private boolean isValid(int target, int[] days, int m, int k) {
        int curLen = 0;
        int bCount = 0;
        for (int day: days) {
            if (day <= target) {
                curLen++;
            } else {
                curLen = 0;
            }
            if (curLen == k) {
                bCount++;
                curLen = 0;
            }
            if (bCount >= m) {return true;}
        }
        return false;
        
    }
}