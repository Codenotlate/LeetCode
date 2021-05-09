// phase 3 self - slow O(n^2)
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int i = 0;
        int n = gas.length;
        while (i < n) {
        	if (gas[i] >= cost[i]) {
        		int j = (i + 1) % n;
        		int left = gas[i] - cost[i];
        		while (j != i) {
        			left += gas[j] - cost[j];
        			if (left < 0) {break;}
        			j = (j + 1) % n;
        		}
        		if (j == i) {return i;}
        	}
        	i++;
        }
        return -1;
    }
}


// Phase3: O(n) way, requires math to prove
/*https:// leetcode.com/problems/gas-station/discuss/42572/Proof-of-%22if-total-gas-is-greater-than-total-cost-there-is-a-solution%22.-C%2B%2B

if sum(gas) >= sum(cost), then there must exist a solution, and if diff(0)+... diff(i) is the smallest partial sum
then (i + 1) must be the valid start position.

*/

class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int diffSum = 0;
        int resPos = -1;
        int minPartialSum = Integer.MAX_VALUE;
        for (int i = 0; i < gas.length; i++) {
        	diffSum += gas[i] - cost[i];
        	if (diffSum < minPartialSum) {
        		minPartialSum = diffSum;
        		resPos = i + 1;
        	}
        }
        return diffSum < 0 ? -1 : resPos % gas.length;
    }
}