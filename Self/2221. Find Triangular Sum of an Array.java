/*Thought
special case: when nums.len == 1, return that value.
naive way: check with interviewer, if we can change the nums array in place. Otherwise, make a copy of the array. each time update that array inplace for each round of composition. The final result will be stored at index 0 in the end.
time O(n^2) space O(1)/O(n)

Math O(n) way: figure out how many times an element contributes to the final result. The style is just a pascal triangle.
1 * C(4,0) + 2 * C(4,1) + 3 * C(4,2) + 4 * C(4,3) + 5 * C(4,4)
= 1 + 8 + 18 + 16 + 5 = 48
48 % 10 = 8

But need to consider the issue about combination calculation overflow issue. real O(n) way check below.
https://leetcode.com/problems/find-triangular-sum-of-an-array/discuss/1907380/O(n)-time-O(1)-space-Python-189-ms-C%2B%2B-12-ms-Java-4-ms
Otherwise, we need to build the pascal triangle, which takes O(n^2) time actually.
*/
// naive O(1) space O(n^2) time way
class Solution {
    public int triangularSum(int[] nums) {
        if (nums.length == 1) {return nums[0];}
        for (int end = nums.length - 1; end >= 1; end--) {
            for (int inner = 0; inner < end; inner++) {
                nums[inner] = (nums[inner] + nums[inner + 1]) % 10;
            }
        }
        return nums[0];
    }
}