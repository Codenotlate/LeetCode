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








// Review
/*Initial thought
since it's a circular path. We view the array as double size original one. Iterate one by one (i % n). have extra init as 0. If(extra + gas[i] - cost[i] < 0), meaning we can't move on from current position. So we go to next position and reset extra as 0. Otherwise we keep on moving to next pos and if we get back to the current position(i % n), meaning we can succeed, then we return cur pos.
time O(n) space O(1)
*/
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int cur = 0;
        int n = gas.length;
        int extra = 0;
        while (cur < 2 * n) {
            int k = cur;
            while(k < 2 * n && extra + gas[k%n] - cost[k%n] >= 0) {
                extra = extra + gas[k%n] - cost[k%n];
                k++;
                if(k%n == cur%n) {return cur%n;}
            }
            extra = 0;
            cur = k + 1;
        }
        return -1;
    }
}
/*Improved from 2n to n by using math: When we are able to reach the end of the array, we don't need to move further for the circular part as long as we compare the sum(gas) - sum(cost) with 0. If it's >= 0, then its guaranteed we can make a successful circle.*/
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int cur = 0;
        int n = gas.length;
        int extra = 0;
        int totalDiff = 0;
        while (cur < n) {
            int k = cur;
            totalDiff += gas[k] - cost[k];
            while(k < n && extra + gas[k] - cost[k] >= 0) {
                extra = extra + gas[k] - cost[k];
                k++;
                if(k == n) {return totalDiff >= 0 ? cur : -1;}
                totalDiff += gas[k] - cost[k];
            }
            extra = 0;
            cur = k + 1;
        }
        return -1;
    }
}
// above code not elegant enough - below with same idea
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int totalDiff = 0;
        int curDiff = 0;
        int startIdx = 0;
        for (int i = 0; i < gas.length; i++) {
            totalDiff += gas[i] - cost[i];
            curDiff += gas[i] - cost[i];
            if (curDiff < 0) {
                curDiff = 0;
                startIdx = i + 1;
            }
        }
        return totalDiff < 0 ? -1 : startIdx;
    }
}






// Review - 23/3/8
// self come up with above M1, but can be combined with math idea and improve to above M2&M3
/*Thoughts
Given it's circular route, we expand both arrays to twice its size and get the diff as gas-cost for each pos. If we want to succeed, we need to start at a position with accum diff >= 0 and along the way whenever we have accum diff < 0, we get start over with the next pos and accum diff = 0. If we are able to reach the full length, meaning we are able to return with current start index. Otherwise, return -1.

time O(2n) = O(n) space O(1)

e.g.
[1,2,3,4,5][1,2,3,4,5]
[3,4,5,1,2][3,4,5,1,2]
-2,-2,-2,3,3,-2,-2,-2,3,3
 */
// M0
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int curStart = 0;
        int diffSum = 0;
        int curLen = 0;
 
        for (int i = 0; i < 2*gas.length; i++) {
            // one optimization: if curStart >= len, meaning every pos has been tested as the start, we can directly break the loop
            if (diffSum < 0) {
                diffSum = 0;
                curStart = i;
                if (curStart >= gas.length) {break;}
                curLen = 0;
            }
            diffSum += gas[i % gas.length] - cost[i % gas.length];
            curLen++;
            if (curLen == gas.length && diffSum >= 0) {
                return curStart% gas.length;
            }            
        }
        return -1;
    }
}
//M1: optimized with math idea from M0
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int curStart = 0;
        int diffSum = 0;
        int totalSum = 0;
        for (int i = 0; i < gas.length; i++) {
            if (diffSum < 0) {
                diffSum = 0;
                curStart = i;
            }
            diffSum += gas[i] - cost[i];
            totalSum += gas[i] - cost[i];           
        }
        return totalSum >= 0 ? curStart : -1;
    }
}