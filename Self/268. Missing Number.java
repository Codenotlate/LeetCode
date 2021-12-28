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


// phase3 self
// M1: math way
class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int expect = (1 + n) * n / 2;
        for (int num: nums) {expect -= num;}
        return expect;
    }
}
// M2: negative label
// given it has element 0, thus we can't simply use -num, instead we use -nums[n] - 1
// and we use 0 to n-1 index for 0 to n-1 value range, if all position is labeled in the end, then the missing value is n.
class Solution {
    public int missingNumber(int[] nums) {
        for (int num: nums){
            if (num < 0) {num = Math.abs(num + 1);}
            if (num < nums.length) {nums[num] = -nums[num] - 1;}
        }
        int res = nums.length;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < 0) {nums[i] = Math.abs(nums[i] + 1);} // revert the change to the array back
            else {res = i;}
        }
        return res;
    }
}







