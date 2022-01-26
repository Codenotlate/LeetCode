class Solution {
    public void rotate(int[] nums, int k) {
    	// The idea is to seperate two parts and reverse separatedly
    	// Then reverse the whole array
    	int n = nums.length;
    	k = k % n;
    	int first = 0;
    	int second = n - k;
    	reverse(nums, first, second - 1);
    	reverse(nums, second, n - 1);
    	reverse(nums, first, n - 1);
    }


    private void reverse(int[] nums, int start, int end) {
    	while (start < end) {
    		int temp = nums[start];
    		nums[start] = nums[end];
    		nums[end] = temp;

    		start++;
    		end--;
    	}
    }
}

// can also reverse as a whole and then reverse each part separated by k
/* https://leetcode.com/problems/rotate-array/discuss/54250/Easy-to-read-Java-solution
nums = "----->-->"; k =3
result = "-->----->";

reverse "----->-->" we can get "<--<-----"
reverse "<--" we can get "--><-----"
reverse "<-----" we can get "-->----->"
this visualization help me figure it out :)
*/


// Phase 3 self
class Solution {
    /* initial thought:
    for O(n) space solutions: M1 - label the start position of the rotation(n-k).add number from [n-k,k] to temp array and then [0,n-k-1], copy the temp array back to nums. M2 - have a temp array to store result, loop each number, the new pos for this number in temp array should be (i+k) % n. copy the temp array back to nums.
    for O(1) space solutions: M3 - two reverse in place, first reverse the whole array, then reverse [0,k-1] and [k,n] separately.
    // Attention: need to consider special case where k > n
    */
    public void rotate(int[] nums, int k) {
        // M1 O(n) O(n)
        int n = nums.length;
        int[] temp = new int[n];
        k %= n;
        if (k == 0) {return;}
        int j = 0;
        for (int i = n-k; i < n; i++) {
            temp[j++] = nums[i];
        }
        for (int i = 0; i < n-k; i++) {
            temp[j++] = nums[i];
        }
        
        for (int i = 0; i < n; i++) {
            nums[i] = temp[i];
        }
    }
}

class Solution {
    public void rotate(int[] nums, int k) {
        // M2 O(n) O(n)
        int n = nums.length;
        int[] temp = new int[n];
        k %= n;
        if (k == 0) {return;}
        for(int i = 0; i < n; i++) {
            temp[(i+k) %n] = nums[i];
        }
        
        for (int i = 0; i < n; i++) {
            nums[i] = temp[i];
        }
    }
}

class Solution {
    public void rotate(int[] nums, int k) {
        // M3 O(n) space O(1)
        int n = nums.length;
        k %= n;
        if (k == 0) {return;}
        
        reverse(nums, 0, n-1);
        reverse(nums, 0, k-1);
        reverse(nums, k, n-1);
    }
    
    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
}



// add: https://leetcode.com/problems/rotate-array/solution/
// approach 3 in solution is similar to above M2, but optimize into O(1) space solution. Basically each circle represents all the positions having n % k = some constant. And when we reach the original starting position of the circle we will move to the next circle. Until we process all the circle groups.
// try self implement this next time