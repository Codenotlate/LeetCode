class Solution {
    public int maxProfit(int[] prices) {
        if (prices.length == 0) {return 0;}
        int min = prices[0];
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
        	profit = Math.max(profit, prices[i] - min);
        	min = Math.min(min, prices[i]);
        }
        return profit;
    }
}




// phase 3 self
class Solution {
    public int maxProfit(int[] prices) {
        int min = Integer.MAX_VALUE;
        int profit = 0;
        for (int p: prices) {
            if (p < min) {
                min = p;
            } else {
                profit = Math.max(profit, p - min);
            }
        }
        return profit;
    }
}



//23/8/4 - above ways view each day as a sell day and try to find min prices of all previous days
// O(n) time and O(1) space
// Basically for each day we want to see the max of all the following day's price. Thus we start from right to left.
class Solution {
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        int maxAtRight = 0;
        for (int i = prices.length - 1; i >= 0; i--) {
            if (prices[i] > maxAtRight) {
                maxAtRight = prices[i];
            } else {
                maxProfit = Math.max(maxProfit, maxAtRight - prices[i]);
            }
        }
        return maxProfit;
    }
}