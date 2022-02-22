/* Initial thought
similar as the swap to get the next greater element problem. First convert it to an char array. If we move backwards, keep move as long as the tail array is non-ascending. If the cur digit is smaller than cur+1 digit,  binary search the array [cur+1, end], find the last larger(since decreasing range) than cur digit, and swap cur with it, then reverse the [cur+1, end] to get the next greater.
note: when convert back from char array to integer, need tocheck if it is within int range, otherwise return -1. Also if we finish looping the whole char array, meaning it's already the largest in all combination, we return -1.
time O(l + logl + l) = O(l)
space O(l)
where l represents the number of digits in n.


Add: the above binary search can be changed to simple linear search, since the time complexity is the same,and implementation is easier.
*/
class Solution {
    public int nextGreaterElement(int n) {
        char[] chars = Integer.toString(n).toCharArray();
        for (int i = chars.length-1; i >= 0; i--) {
            if (i < chars.length - 1 && chars[i] < chars[i+1]) {
                // want to find the last one in larger than chars[i], use linear search instead of binary search to be simpler since this won't worse time complexity
                int j = 0;
                for (j = i + 1; j < chars.length; j++) {
                    if (chars[j] <= chars[i]) {break;}
                }
                // swap i with j-1
                char temp = chars[i];
                chars[i] = chars[j-1];
                chars[j-1] = temp;
                // reverse the range[i+1, end]
                reverse(chars, i+1, chars.length-1);
                long num = Long.valueOf(new String(chars));
                return num > Integer.MAX_VALUE ? -1 : (int) num;
            }
        }
        return -1;
    }
    
    
    private void reverse(char[] chars, int left, int right) {
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
    }
}

/* Discussion in solution: https://leetcode.com/problems/next-greater-element-iii/solution/
This explanation is terrible. It explains the "how" but not the "why"...

Why do you have to walk from right to left? Because we want the least significant digit that is greater than the current number.
Why do you have to find a[j] and swap? We're trying to find a digit which is only 1 distance greater than a[i-1] so that this can become new number and is 1 greater than the new dip.
Why do you have to reverse? We're trying to make this number as small as possible. Because we know that the sequence is increasing from right to left in step 1, we can reverse it to be a increasing sequence from left to right.

How to explain it youtube: https://www.youtube.com/results?search_query=back+to+back+next+permutation


*/