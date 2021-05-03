class Solution {
	// greedy algo: time O(nlogn) space O(1) depends on how sorting is implemented
    public int findLongestChain(int[][] pairs) {
        if (pairs.length == 0) {return 0;}
        Arrays.sort(pairs, new Comparator<int[]>() {
        	public int compare(int[] a, int[] b) {
        		return a[1] - b[1];
        	}
        });

        int res = 1;
        int chainEnd = pairs[0][1];
        for (int i = 1; i < pairs.length; i++) {
        	if (pairs[i][0] > chainEnd) {
        		res += 1;
        		chainEnd = pairs[i][1];
        	}
        }
        return res;
    }
}



class Solution {
	// sort first either by first element or second element, and reduce to LIS problem
	// can use dp: time O(n^2) space O(n)
    public int findLongestChain(int[][] pairs) {
        int n = pairs.length;
        if (n == 0) {return 0;}
        Arrays.sort(pairs, new Comparator<int[]>() {
        	public int compare(int[] a, int[] b) {
        		return a[1] - b[1];
        	}
        });

        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 1; i < n; i++) {
        	for (int j = 0; j < i; j++) {
        		if (pairs[j][1] < pairs[i][0]) {
        			dp[i] = Math.max(dp[i], dp[j] + 1);
        		}
        	}
        }

        int res = 0;
        for (int c: dp) {
        	res = Math.max(res, c);
        }
        return res;
    }
}