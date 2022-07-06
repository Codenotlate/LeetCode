/*Thought
Basically get the max of the inventory array for orders times, and adjust the max along the way.
M1 self: Use a treemap to sort the inventory numbers first, the key will be the number and value will be the count in the array. Loop for #orders times, each time, add the maxkey to the result, adjust the count of the maxkey (count--, if count == 0, remove that key). Also adjust the count of maxkey-1(count++, if count == 1, adding that pair).
Since the constraints as: 1 <= orders <= min(sum(inventory[i]), 10^9), no need to worry about the edge case where orders > sum(inventory[i]).
time O(nlogn+klogn) where k=orders, n = inventory.len
space O(n)
Above M1 is correct, but too slow when k is way larger than n, e.g. [1000000000], 1000000000

M2: from discussion. Instead of add one at a time, need to use math to speed up.
Can generate the math formula from below example
1  3  5  5  9  9  9    original sorted inventory
1  3  5  5  5  5  5    profit gain after selling all 9-value balls: (9 + 8 + 7 + 6) * 3 -> (9 + 6) * (9 - 6 + 1) / 2 * 3
1  3  3  3  3  3  3    (5 + 4) * (5 - 4 + 1) / 2 * 5  -> (curValue + nextValue + 1) * (curValue - nextValue) / 2 * numSameColor
*/
// M1: TLE
class Solution {
    public int maxProfit(int[] inventory, int orders) {
        TreeMap<Integer,Integer> map = new TreeMap<>();
        for (int n: inventory) {
            map.put(n, map.getOrDefault(n,0) + 1);
        }
        int res = 0;
        while(orders-- > 0) {
            int max = map.lastKey();
            res = (res + max) % 1000000007;
            int oriCount = map.get(max);
            if (oriCount == 1) {map.remove(max);}
            else {map.put(max, oriCount-1);}
            map.put(max-1, map.getOrDefault(max-1,0) + 1);
        }
        return res;
    }
}


// M2: the data overflow issue is very tricky
// time will be O(nlogn) for sort and then min(k,n)logn for the loop
class Solution {
    public int maxProfit(int[] inventory, int orders) {
        TreeMap<Integer,Integer> map = new TreeMap<>();
        for (int n: inventory) {
            map.put(n, map.getOrDefault(n,0) + 1);
        }
        long res = 0;
        while(orders> 0) {
            int max = map.lastKey();
            int count = map.get(max);
            map.remove(max);
            int nextmax = map.isEmpty()? 0 : map.lastKey();
            long orderCount = (long)(max-nextmax) * count;
            if (orders >= orderCount) {
                res = (res + (long)(max+nextmax+1) * (max-nextmax) / 2 * count) % 1000000007;
            } else {
                int countFull = orders / count;
                int rem = orders % count;
                res = (res + (long)(max + max - countFull+1) * countFull / 2 * count) % 1000000007;
                res = (res + (long)(max-countFull) * rem) % 1000000007;
            }
            
            if(nextmax != 0) {map.put(nextmax,map.get(nextmax) + count);}
            orders = orders <= orderCount? 0 : orders - (int)orderCount;
        }
        return (int) res;
    }
}

// similar idea as post: https://leetcode.com/problems/sell-diminishing-valued-colored-balls/discuss/1116418/Java-sorting-solution-or-easy-to-understand-or-O(nlogn)
// But above solution also fails [10^9,10^9,10^9] 10^9 example. So still need to handle data overflow properly.
// another post: https://leetcode.com/problems/sell-diminishing-valued-colored-balls/discuss/927720/Java-PriorityQueue-%2B-Math
