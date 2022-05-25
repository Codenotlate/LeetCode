/*Thought
binary search. The result will be in range (0, maxnum). For each target we check how many count it can get and if count < k, shrink search range to [left, mid-1] else range as [mid, right]. But note need to tweak the binary search a bit to avoid infinite loop.
time O(nlog(maxr))  space O(1)
*/
class Solution {
    public int maxLength(int[] ribbons, int k) {
        int left = 1;
        int right = 0;
        for (int r: ribbons) {right = Math.max(right, r);}
        // note this (left <= right&left=mid+1&return left-1) thing is to avoid the infinite loop caused by (left < right&left = mid&return left)
        while(left <= right) {
            int mid = left + (right-left) / 2;
            int count = 0;
            for (int r: ribbons) {count += r / mid;}
            if (count < k) {right = mid -1;}
            else {left = mid + 1;}
        }
        return left - 1;
    }
}