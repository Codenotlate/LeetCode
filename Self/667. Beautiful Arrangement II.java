class Solution {
    public int[] constructArray(int n, int k) {
        int[] res = new int[n];
        res[0] = 1;
        for (int i = 1, diff = k; i <= k; i++, diff--) {
        	if (i % 2 != 0) {
        		res[i] = res[i - 1] + diff;
        	} else {
        		res[i] = res[i - 1] - diff;
        	}
        }

        for (int i = k + 1; i < n; i++) {
        	res[i] = i + 1;
        }
        return res;
    }
}