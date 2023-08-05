class Solution {
    public int maxProfit(int[] prices) {
        if (prices.length <= 1) {return 0;}
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
        	int diff = prices[i] - prices[i - 1];
        	if (diff > 0) {profit += diff;}
        }
        return profit;
    }
}




// phase3 self
class Solution {
    public int maxProfit(int[] prices) {
        if (prices.length <= 1) {return 0;}
        
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if(prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        return profit;
    }
}


// 23/8/5
class Solution {
    public int maxProfit(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            profit += Math.max(prices[i] - prices[i-1], 0);
        }
        return profit;
    }
}