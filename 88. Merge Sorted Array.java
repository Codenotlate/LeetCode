class Solution {
	// use three pointers, moving backwards
	// Time O(n + m) space O(1)
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // base case
        if (n == 0) return;
        if (m == 0) {
        	for (int i = 0; i < n; i++) {
        		nums1[i] = nums2[i];
        	}
        	return;
        }

        // move backwards
        int p1 = m - 1;
        int p2 = n - 1;
        int cur = m + n - 1;

        while (p1 >= 0 && p2 >= 0) {
        	if (nums1[p1] >= nums2[p2]) {
        		nums1[cur] = nums1[p1--];
        	} else {
        		nums1[cur] = nums2[p2--];
        	}
        	cur--;
        }
        if (p2 < 0) {return;}
        while (cur >= 0) {
        	nums1[cur--] = nums2[p2--];
        }
    }
}