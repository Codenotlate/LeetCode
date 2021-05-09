class Solution {
	// method1 : math way
	// time O(n) space O(1)
    public int missingNumber(int[] nums) {
        int cumsum = 0;
        int maxNum = -1;
        boolean hasZero = false;

        for (int n: nums) {
        	maxNum = Math.max(maxNum, n);
        	cumsum += n;
        	if (n == 0) {hasZero = true;}
        }

        int sum = (1 + maxNum) * maxNum / 2;
        if (sum == cumsum) {
        	if (hasZero) {return maxNum + 1;}
        	return 0;
        }
        return sum - cumsum;
    }
}


class Solution {
	// method 2: negative label
	// time O(n) space O(1)
    public int missingNumber(int[] nums) {
        int N = nums.length;
        for (int n: nums) {
        	//convert back from label if it's labeled
        	if (n < 0) {n = Math.abs(n + 1);}        	
        	if (n < N) {
        		// need to deal with nums[n] == 0
        		nums[n] = - nums[n] - 1;
        	}
        }
        // after the 1st pass, all numbers x in nums, should have nums[x] < 0
        for (int i = 0; i < N; i++) {
        	if (nums[i] >= 0) {return i;}
        }
        return N;
    }
}



class Solution {
	// method 3: bit manipulation, XOR with index(0 to N-1) + N
	// time O(n) space O(1)
    public int missingNumber(int[] nums) {
        int res = nums.length;
        for (int i = 0; i < nums.length; i++) {
        	res ^= i ^ nums[i];
        }
        return res;
    }
}







