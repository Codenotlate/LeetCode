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