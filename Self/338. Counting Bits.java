class Solution {
	//optimal method: time O(n) one pass, space O(n) and no built-in func
    public int[] countBits(int num) {
        int[] res = new int[num + 1];
        int latestPowerOfTwo = 1;
        for (int i = 1; i <= num; i++) {
        	if ((i & (i - 1)) == 0) {
        		res[i] = 1;
        		latestPowerOfTwo = i;
        	} else {
        		res[i] = 1 + res[i - latestPowerOfTwo];      	
        	}
        }
        return res;
    }
}


class Solution {
	// another similar idea using DP
	public int[] countBits(int num) {
	    int[] f = new int[num + 1];
	    for (int i=1; i<=num; i++) f[i] = f[i >> 1] + (i & 1);
	    return f;
	}
}



class Solution {
	//brutal force method: time O(n*32), space O(n)
    public int[] countBits(int num) {
        int[] res = new int[num + 1];
        for (int i = 1; i <= num; i++) {
        	if ((i & (i - 1)) == 0) {
        		res[i] = 1;
        	} else {
        		int temp = i;
        		while (temp != 0) {
        			res[i] += (temp & 1) == 1 ? 1 : 0;
        			temp >>>= 1;
        		}     	
        	}
        }
        return res;
    }
}


// Thinking process in a interveiw
// https://leetcode.com/problems/counting-bits/discuss/79557/How-we-handle-this-question-on-interview-Thinking-process-%2B-DP-solution




