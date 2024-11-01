/** 2024/3/13
===M0: Brute force way: Each element can be the highest freq number, check the smallest difference with all other elements, and see how k is gonna cover that. Record the max freq.
    Sort first, so for each element, we just need to check its left side neighbors
    time O(nlogn + n^2)
===M1: sort first, for each element i, we don't need to check [1,i-1] again, we can record the number of operations needed for i-1 element. Then the number of operations needed for current element = numOps(i-1) + (element[i]-element[i-1])*i. 
    notice the maxLen window can start from any position. Thus it's again a sliding window problem. Window [left,right] represents the number of ops needed to add all numbers in the window to element[right]. A valid window should have numOps <= k.
    when we expand the window to the right, we add prev window size * diff. when we shrink the window from the left, we subtract sumDiff from it.
    time O(nlogn + n)
===M1.1: optimized of M1, we don't shrink window, just move with the current size, since we want the maxLen of the window.

------------------------------------------
learning
===space O(logn)
    In Java, Arrays.sort() for primitives is implemented using a variant of the Quick Sort algorithm, which has a space complexity of O(log⁡n)
===M2: Above M1 way is on the other perspective just check whether k+accuSum till max value>= window size * max value. 
    Thus we just need to check this for each window, and based on this to expand or move window. "If" and "while" here is again same as the M1 and M1.1, no big diff since we just want maxLen.
===M3: binary search
    Note: the previous two approaches are the optimal solutions and are sufficient to solve the problem.
    Given an index i, if we treat nums[i] as target, we are concerned with how many elements on the left we can take. In the earlier approaches, we used a sliding window. In this approach, we will directly find the left-most index of these elements using binary search.
    Will need to first build a prefixSum array, which takes O(n) space. Then for the binary search, we compare target *half size -accumulative Sum for that half with k. 
 */
// self M1
class Solution {
    public int maxFrequency(int[] nums, int k) {
        Arrays.sort(nums);
        int maxFreq = 1;
        long numOps = 0;
        int left = 0;
        int right = 1;
        long diffSum = 0;
        while (right < nums.length) {
            int curDiff = nums[right] - nums[right-1];
            diffSum += curDiff;
            // Don't forget the overflow issue [1,....,1,100000]
            numOps += (long)(right-left) * curDiff;
            while (numOps > k) { // M1.1 change this to if, it's the same, as we only care about window size
                numOps -= diffSum;
                diffSum -= nums[left+1]-nums[left];
                left++;
            }
            maxFreq = Math.max(maxFreq, right-left+1);
            right++;
        }
        return maxFreq;

    }
}
// M2
class Solution {
    public int maxFrequency(int[] nums, int k) {
        int left = 0;
        int right = 0;
        long windowSum = 0;
        int maxLen = 1;
        Arrays.sort(nums);
        while (right < nums.length) {
            windowSum += nums[right];
            if (windowSum + k < (long)(right-left+1) * nums[right]) {
                windowSum -= nums[left];
                left++;
            }
            maxLen = Math.max(maxLen, right-left+1);
            right++;
        }
        return maxLen;
    }
}
// M3 - binary search
class Solution {
    public int maxFrequency(int[] nums, int k) {
        Arrays.sort(nums);
        long[] preSum = new long[nums.length];
        int maxFreq = 1;
        for (int i=0; i < nums.length; i++) {
            if (i == 0) {preSum[i] = nums[i]; continue;}
            preSum[i] = preSum[i-1] + nums[i];
            int leftMost = binSearchleft(nums,preSum, 0, i, k);
            maxFreq = Math.max(maxFreq, i-leftMost+1);
        }
        return maxFreq;

    }

    private int binSearchleft(int[] nums, long[] preSum, int left, int right, int k) {
        int rightPos = right;
        while (left < right) {
            int mid = left + (right-left)/2;
            // distinguish the target right passed in with the change right position of the binary search range. The calculation of targetSum and currentSum should be based on initial target right passed in. And don't forget to handle overflow
            long targetSum = (long)nums[rightPos] * (rightPos - mid + 1);
            long currentSum = preSum[rightPos] - preSum[mid]+nums[mid];
            if (targetSum - currentSum > k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
        
    }
}




// 2024.10.28
// solution on range [1, n]. 「 This is WRONG! 』For each freq x, the min operation needed is to change every one smaller than the xth number to the xth number. Thus we need to sort first. And use above check for validation on each freq. The check can take two ways: way1 is O(n) time, starts from the (x-1) and sum the diff till the first element in nums. way2 is O(1) time, but we need to pre-calculate it and store in an diffSum array.
// no matter which validation check way we take, time is O(nlogn) due to the sort operation.
// M1: way2 check
class Solution {
    public int maxFrequency(int[] nums, int k) {
        int n = nums.length;
        long[] diffSum = new long[n];
        Arrays.sort(nums);
        for (int i = 1; i < n ; i++) {
            diffSum[i] = diffSum[i-1] + (nums[i]-nums[i-1])*(long)i;
        }
        int left = 1;
        int right = n;
        int res = 1;
        while (left <= right) {
            int mid = left + (right-left) / 2;
            if (isValid(mid, diffSum, k)) {
                res = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return res;
    }


    private boolean isValid(int n, long[] diffSum, int k) {
        return diffSum[n-1] <= k;
    }

    
}



