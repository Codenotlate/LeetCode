// phase3 self
class Solution {
    public void nextPermutation(int[] nums) {
        // M1: naive way, time O(n^2), space (1)
        if (nums.length <= 1) {return;}
        int n = nums.length;
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] < nums[n - 1]) { // meaning we can find teh next larger number for nums[i]
                int j;
                for (j = i + 1; j < n; j++) {
                    if (nums[i] < nums[j]) {break;}
                }
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                return;
                
            }
            
            int temp = nums[i];
            for (int k = i; k < n - 1; k++) {
                nums[k] = nums[k + 1];
            }
            nums[n - 1] = temp;
        }
    }
}



/* M2: improve from M1: reverse in the end. There's actually no need to move the array in [i + 1, len -1] as ascending order for each round of loop. We leave it as the descending order, and reverse once in the end.
Time O(n), space O(1)
*/
class Solution {
    public void nextPermutation(int[] nums) {
        if (nums.length <= 1) {return;}
        int n = nums.length;
        int i;
        for (i = n - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) { // now nums[i+1] to nums[n - 1] is in descending order, thus we compare it with nums[i + 1]
                int j;
                for (j = i + 1; j < n; j++) {
                    if (nums[i] >= nums[j]) {break;}
                }
                int temp = nums[i];
                nums[i] = nums[j - 1];
                nums[j - 1] = temp;
                break;         
            }
        }
        reverse(nums, i+1, n-1); 
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



// Review self: similar to M2: use BS to find the first large element in the non-descending order array[i to n-1]
// time still O(n), but the above M2 is O(n+n), whereas this method is O(n + logn)
class Solution {
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        int i = n - 1;
        while(i > 0) {
            if (nums[i] > nums[i-1]) {break;}
            i--;
        }
        if (i!= 0) {
            int p = bsfindFirstlarge(nums, nums[i-1], i, n - 1);
            int temp = nums[i-1];
            nums[i-1] = nums[p];
            nums[p] = temp;
        }
        
        reverse(nums, i, n-1);
    }
    
    
    private int bsfindFirstlarge(int[] nums, int target, int start, int end) {
        while (start < end - 1) {
            int mid = start + (end - start) / 2;
            if (nums[mid] <= target) {end = mid - 1;}
            else {start = mid;}
        }
        return nums[end] > target? end : start;
    }
    
    private void reverse(int[] nums, int i, int j) {
        while (i < j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            i++;
            j--;
        }
    }
    
}


// Review
/*Initial thought
The key is to move backwards and to find the first pos that has cur num < prev num. Then we need to find the last number > cur number in the decreasing subarray[i+1:end].(we can use binary search here since the array is sorted, but that won't change the O(n) time).  Then we switch that number with curnum, and reverse subarray[i+1:end].
time O(n)
space O(1)
*/
class Solution {
    public void nextPermutation(int[] nums) {
        if (nums.length == 1) {return;}
        int i = nums.length - 2;
        while (i>= 0) {
            if(nums[i] < nums[i+1]) {break;}
            i--;
        }
        // find the last larger number of curnum
        if(i >= 0) {
            int j = i + 1;
            while(j < nums.length && nums[j] > nums[i]) {j++;}
            // swap (i, j)
            j--;
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        // reverse nums[i+1, end]
        reverse(nums, i+1, nums.length - 1);        
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




// Review 23/2/3 - same idea as above, slightly diff implementation, not single time pass, forgot some details
/* Thoughts
require in-place. Since we want to find the next in permutation, we need to focus on the right side of the array.
As we know, for an array of number in non-ascending order, it must be the largest permutation. Thus we want to find the first position from right that unsatisfies this trend, and switch the number at that position with the last number bigger than it in the non-ascending subarray following it. And then reverse the following non-ascending subarray to make it non-descending.
special case: if the whole array is actually non-ascending, then we simply reverse the whole array to return the min of the permutation.

time O(n) space O(1)
*/
class Solution {
    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while(i >= 0) {
            if (nums[i] < nums[i+1]) {break;}
            i--;
        }
        if (i < 0) {
            reverse(nums, 0, nums.length-1);
            return;
        }
        int j = i+1;
        while(j < nums.length) {
            // should have equal sign here
            if (nums[j] <= nums[i]) {
                break;
            }
            j++;
        }
        swap(nums, i, j-1);
        reverse(nums, i+1, nums.length - 1);
        return;
    }


    private void reverse(int[] nums, int i, int j) {
        while ( i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}






