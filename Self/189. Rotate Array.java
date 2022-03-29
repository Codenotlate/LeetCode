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




// Review
/*Intial thought
M0 way: brute force. rotate elements by 1 unit at each step and do k steps. time O(n * k) space O(1)
M1 way: If we can use O(n) space, we can just fill it in the order we desired to a new array and copy back. About the order, we can start with len-k and follow with 0 to len-k-1, or start with 0 by converting the index as (i + k) % n.
M2 way: O(1) space way. reverse twice inplace. The first time reverse  [len-k,len-1] and [0, len-k-1], then reverse the whole array.
M3 way: O(1) space way. Use cyclic replacement
[1,2,3,4,5,6,7] k = 3
1 -> 4 ->7 ->3 ->6 ->2 ->5 count == len, stop
Could be another case: where we stay in the same circle but didn't visit all element. In this case, we need to move the starting pointer to the next one until count == n.
[1,2,3,4,5,6] k = 2
1->3->5->1
2->4->6->2 if idx == init idx && count == n return.
[1,2,3,4,5,6，7，8，9] k = 3
*/
class Solution {
    public void rotate(int[] nums, int k) {
        int len = nums.length;
        k %= len;
        reverse(nums, 0, len-k-1);
        reverse(nums, len-k, len - 1);
        reverse(nums, 0, len - 1);
    }
    
    private void reverse(int[] nums, int i, int j) {
        while(i < j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            i++;
            j--;
        }
    }
}
// M3 cyclic way
class Solution {
    public void rotate(int[] nums, int k) {
        int count = 0;
        k %= nums.length;
        for (int i = 0; i < nums.length; i++) {
            int cur = i;
            int prev = nums[cur];
            do {
                int next = (cur + k) % nums.length;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                cur = next;  
                count++;
                if(count == nums.length) {return;}
            } while(cur != i);
        }        
    }    
}