/* Initial Thought
Need to use a hashmap for (t -> p) pair reference, and use a latestTime var to track latest time till now. Then since we need to find the max and min of all p, and will also remove some p in the process, thinking about using a treemap to make getMax, getMin, delete, update operations all with log(n) time.
time: current() -O(1); update/maximum/minimum - O(log(#of unique prices))
space O(n)
*/
class StockPrice {
    Map<Integer, Integer> tmToP;
    int latestTime;
    TreeMap<Integer, Integer> pMap; // note here needs to be defined as treeMap

    public StockPrice() {
        tmToP = new HashMap<>();
        pMap = new TreeMap<>();
        latestTime = 0;
    }
    
    public void update(int timestamp, int price) {
        // get original price, and update tmToP map
        int oriPrice = tmToP.getOrDefault(timestamp, 0);
        tmToP.put(timestamp, price);
        // update latestTime
        latestTime = Math.max(latestTime, timestamp);
        // if has original price (oriPrice != 0), update old price in pMap
        if(oriPrice != 0) {
            int oriCount = pMap.get(oriPrice);
            if(oriCount == 1) {
                pMap.remove(oriPrice);
            } else {
                pMap.replace(oriPrice, oriCount - 1);
            }
        }
        // update new price in pMap
        pMap.put(price, pMap.getOrDefault(price, 0) + 1);       
    }
    
    public int current() {
        return tmToP.get(latestTime);
    }
    
    public int maximum() {
        return pMap.lastKey();
    }
    
    public int minimum() {
        return pMap.firstKey();
    }
}




// good solution in first comment
// https://leetcode.com/problems/stock-price-fluctuation/discuss/1513413/JavaC%2B%2BPython-Strightforward-Solutions

// another way using two heaps, one minheap and one maxheap.
// https://leetcode.com/problems/stock-price-fluctuation/discuss/1513293/Python-Clean-2-Heaps-Commented-Code






















