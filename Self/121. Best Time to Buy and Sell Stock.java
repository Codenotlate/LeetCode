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