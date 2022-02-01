// phase 3 self - slow O(n^2)
// This way can be optimized to below M3, by make next i equal to j+1 instead of i+1
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


// M2
// Phase3: O(n) way, requires math to prove
/*https://leetcode.com/problems/gas-station/discuss/42572/Proof-of-%22if-total-gas-is-greater-than-total-cost-there-is-a-solution%22.-C%2B%2B

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








// Phase3 self
// M3
// The idea of M3 is very similar to the solution below: https://leetcode.com/problems/gas-station/discuss/1706142/JavaC%2B%2BPython-An-explanation-that-ever-EXISTS-till-now!!!!
// but if we use the solution link. we should use the math proof in M2 to guarantee total(cost) < total(gas) means must have a solution. The we don't need to do the j loop check in below.
class Solution {
    /* initial thought
    naive way: start with the first station having gas > cost, go to next station.if at one station, rem + gas  < cost,  find the next station with gas > cost and start from that station again.  If start station > end of array, return -1, if the passed station goes back to current start station, return the start station.
    time O(N) space O(1)
    */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int start = 0;
        int rem = 0;
        while(start < gas.length) {
            while (start < gas.length && rem + gas[start] - cost[start] < 0) {start++;}
            if (start >= gas.length) {return -1;}
            int i = start + 1;
            rem = gas[start] - cost[start];
            for (; i < 2 * gas.length; i++) {
                int j = i % gas.length;
                if ( j == start) {return start;}
                rem += gas[j] - cost[j];
                if (rem < 0) {
                    rem = 0;
                    break;
                }
            }
            start = i+1;        
        }        
        return -1;
    }
}





