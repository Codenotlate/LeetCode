/** 2024/4/12
First build the next greater result for nums2, and also do the element to index map.
Then traverse nums1 to get the result based on the index from the map and corresponding greater element.
Time O(len1+len2) Space O(len2) if not count output space

 */

class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        int[] greater = new int[nums2.length];
        Arrays.fill(greater, -1);
        for (int i = 0; i < nums2.length; i++) {
            map.put(nums2[i],i);
            while (!stack.isEmpty() && stack.peek() < nums2[i]) {
                greater[map.get(stack.pop())] = nums2[i];
            }
            stack.push(nums2[i]);
        }
        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            res[i] = greater[map.get(nums1[i])];
        }
        return res;

    }
}