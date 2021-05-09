
// Self phase3: regular sort way
// Time O (nlogn) space O(1)
class Solution {
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        for (int i = 0; i < citations.length; i++) {
        	if (citations[i] >= citations.length - i) {
        		return citations.length - i;
        	}
        }
        return 0;
    }
}


// M2: counting sort
// since the answer will be in range 0 to n, then we can use counting sort to separate them into (n + 1) buckets
// time O(n), space O(n)
class Solution {
    public int hIndex(int[] citations) {
        int n = citations.length;
        int[] count = new int[n + 1];
        for (int c: citations) {
        	// if number is large than n, then we put it in the bucket n
        	c = Math.min(c, n);
        	count[c]++;
        }

        int num = 0;
        for (int i = n; i >= 0; i--) {
        	num += count[i];
        	if (num >= i) {
        		return i;
        	}
        }
        return 0;
    }
}

/*
Counting sort: each bucket only contains the number of elements in this bucket
Bucket sort: separate elements into each bucket first, then within each bucket, do quick sort
Radix sort: Do bucket sort for each digit.

*/





