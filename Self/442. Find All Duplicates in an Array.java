class Solution {
    /*Initial thought
    since the numbers are in range [1,n] and we can only use constant space. We can use negative inplace label. for number n, check number at nums[n-1],  if it's  positive, make it negative, otherwise if its already negative, add n into result list, and make it positive again.
    In the end, change all negative values back to positive to make nums unchanged.
    time O(n) space O(1)
    */
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            int n = Math.abs(nums[i]);
            if (nums[n-1] < 0) {res.add(n);}
            nums[n-1] *= -1;
        }
        
        for (int i = 0; i < nums.length; i++) {nums[i] = Math.abs(nums[i]);}
        return res;
    }
}