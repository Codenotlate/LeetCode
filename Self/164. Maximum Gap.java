// Phase3 solution
/* Bucket sort
The key is to determine the bucket size(Bsize) as ceiling((max - min) / (n - 1)),
And the max gap will be >= Bsize
The interval for kth bucket will include left side and exclude right side
[min + k * Bsize, min + (k + 1) * Bsize)
This guarantees there are n buckets, and if two num in the same bucket, they won't product max Gap
thus can be neglected, thus only track the min and max nums in each bucket will be enough
Later when determine the max Gap, we compare min of (k)th bucket to the max of its closest non-empty previous bucket
*/

class Solution {
    public int maximumGap(int[] nums) {
        // special case
        if (nums.length < 2) {return 0;}
        // first pass to find min and max
        int min = Integer.MAX_VALUE;
        int max = 0;
        int n = nums.length;
        for (int num: nums) {
        	min = Math.min(min, num);
        	max = Math.max(max, num);
        }
        // note: special case
        if (min == max) {return 0;}

        // set bucket size using min and max
        int bucketSize = (int) Math.ceil((double) (max - min) / (n - 1));
        // second pass: put num into buckets and update the min and max of each bucket
        int[][] buckets = new int[n][2]; // [[min, max]]
        // initialize
        for (int[] b: buckets) {
        	b[0] = Integer.MAX_VALUE;
        	b[1] = Integer.MIN_VALUE;
        }
        for (int num: nums) {
        	int bucketID = (num - min) / bucketSize;
        	// update min or max of that bucket
        	buckets[bucketID][0] = Math.min(num, buckets[bucketID][0]);
        	buckets[bucketID][1] = Math.max(num, buckets[bucketID][1]);
        }

        // third pass of buckets to find the max gap 
        // [Note!!! ->]by comparing min of (k)th bucket to the max of its closest non-empty previous bucket
        int maxGap = 0;
        int prev = 0; // since the first bucket must have min as the num.
        for (int i = 1; i < n; i++) {
        	// remember to skip those empty buckets
        	if (buckets[i][0] == Integer.MAX_VALUE) {continue;}
        	maxGap = Math.max(maxGap, buckets[i][0] - buckets[prev][1]);
        	prev = i;
        }
        return maxGap;
    }
}













