class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if (m > n) {return findMedianSortedArrays(nums2, nums1);} // guarantee nums1 is the shorter one

        int low = 0;
        int high = 2 * m;

        while (low <= high) {
        	int cut1 = low + (high - low) / 2;
        	int cut2 = m + n - cut1;

        	int L1 = cut1 == 0 ? Integer.MIN_VALUE : nums1[(cut1 - 1) / 2];
        	int R1 = cut1 == 2 * m ? Integer.MAX_VALUE : nums1[cut1 / 2];
        	int L2 = cut2 == 0 ? Integer.MIN_VALUE : nums2[(cut2 - 1) / 2];
        	int R2 = cut2 == 2 * n ? Integer.MAX_VALUE : nums2[cut2 / 2];

        	if (L1 > R2) { // need to move cut1 left
        		high = cut1 - 1;
        	} else if (L2 > R1) { // need to move cut2 left, ie cut1 right
        		low = cut1 + 1;
        	} else { // L1 <= R2 && L2 <= R1
        		return (double) (Math.max(L1, L2) + Math.min(R1, R2)) / 2;
        	}
        }

        return -1;
    }
}



/*
check idea on
https://leetcode.com/problems/median-of-two-sorted-arrays/discuss/2471/Very-concise-O(log(min(MN)))-iterative-solution-with-detailed-explanation
Goodnotes

Key points:

1) We want to make two cuts, separating nums1 into [. . . . L1 | R1 . . . ] and nums2 into [. . . . L2 | R2 . . . ] respectively, so that [. . . . L1] + [. . . . L2] has equal number of elements as [R1 . . . ] + [R2 . . . ]. Our goal is to find such cutting positions that give us the median values.
2) For an array of length N, there are 2*N + 1 different cutting positions.
Cutting on a gap is simple. Cutting on a number means both left half and right half get the number.
3) We can get the left num and right num in the original array based on the cutPos in converted array:
	L = (c - 1) / 2 and R = c / 2
	edge case is when c = 0 or c = 2 * m, then we set L to -inf and R to +inf
4) With two arrays, a valid cutting position that gives the median can be ANY cutting position of the shorter array. This is not true for the longer array. Therefore, we always cut the shorter array, and then calculate the cutting position of longer array directly(by using the fact that each half has the same number of cutting positions). We want to make nums1 always point to the shorter array for convenience.
5) Using binary search, If L1 > R2, we know current cutting position is incorrect. A valid cutting position for median should be on the left half of nums1.
6) If L2 > R1, we know current cutting position is incorrect. A valid cutting position for median should be on the right half of nums1.
7) if L1 < R2 and L2 < R1, we are good. median = (max(L1, L2) + min(R1, R2)) / 2

*/