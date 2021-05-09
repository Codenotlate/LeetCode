class Solution {
	// one pass, time o(n) space O(1)
	// idea is to for each reverse order pair, 
	// we also need to check nums[i+1] and nums[i-1]
    public boolean checkPossibility(int[] nums) {
        // special case
        if (nums.length <= 2) {return true;}
        int count = 0;
        int i = 1;
        while (i < nums.length) {
        	if (nums[i] < nums[i - 1]) {
        		count++;
        		if (count > 1) {return false;}
        		// determine adjust nums[i-1] or nums[i] based on nums[i-2] and nums[i]
        		if (i >= 2 && nums[i - 2] > nums[i]) {
        			nums[i] = nums[i - 1];
        		} else {
        			nums[i - 1] = nums[i];
        		}
        	}
        	i++;
        }
        return count <= 1;
    }
}

/**
The problem requires that every number has to be equal or greater than previous number.
If we encounter a failing condition where the number is not greater or equal to previous (smaller than previous) we need to make a correction. Correction can be made in either of two ways:

Make the previous number smaller or equal to current number
Make the current number equal to previous number
We can do (1) as long as the number at position i-2 is equal or lower than the current element. (if i-2 is valid)
In case 1 below we can do this at (3) (i = 2) as the element 1 (i = 0) fulfills 1 <= 3. We can replace 7 with 3.
However, this cannot be done in case 2 as 4 <= 3 does not satisfy.

Correction with technique (1) takes priority as there is no risk in lowering the value but there is a risk associated if the value is increased. (Consider scenario in case 1 if we replace 3 with 7, it will fail to satisfy the condition for the last element)

We have to make correction with (2) if we cannot achieve it by (1). In which case we increase the value of current element by matching previous element. In case 2, we replace 3 with 7.

Also we only compare condition with the previous element only because as we move forward we know the previous numbers are already validated .

https://leetcode.com/problems/non-decreasing-array/discuss/106826/JavaC%2B%2B-Simple-greedy-like-solution-with-explanation
*/