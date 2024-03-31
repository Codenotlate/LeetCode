/** 24/3/30
Interesting question, learning notes:
since we want to remove element in logk time, we consider use Treeset. 
To find the median, we can maintain two treeSet, one store smaller elements, and one for larger elements. We maintain the size of the two treeset to be at most have diff = 1. Thus we can get median easily. Also to deal with duplicate numbers, we store the index instead of number itself.
Time O(nlogk) space O(k)

-----------------------------------------
For treeSet some common operations: All with time O(logn)
    add
    pollFirst / pollLast
    first / floor / last / ceiling
    lower / higher
    remove / removeFirst / removeLast
    contains
===== https://codegym.cc/groups/posts/treeset-in-java
TreeSet is an implementation of a red-black tree in Java. TreeSet maintains a sorted order for storing unique elements. 
--Advantages:
    * Automatic maintenance of elements in sorted order. 
    * Efficient search, insert, and delete operations. It utilizes a binary search tree structure, enabling these operations with a time complexity of O(log N). 
    * TreeSet guarantees the uniqueness of elements by utilizing their natural ordering or a custom Comparator. This is useful when working with collections that require distinct elements.
--Limitations:
    * Slightly slower than HashSet due to maintaining sorted order.
    * Does not allow null elements, as it relies on natural ordering or a custom Comparator for element comparison.

 */




class Solution {
    public double[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        double[] res = new double[n-k+1];
        // do not create it like below, will have overflow issue: https://leetcode.com/problems/sliding-window-median/solutions/96352/never-create-max-heap-in-java-like-this/
        // TreeSet<Integer> small = new TreeSet<>((a,b)->(nums[a]==nums[b]?a-b:nums[a]-nums[b]));
        // TreeSet<Integer> large = new TreeSet<>((a,b)->(nums[a]==nums[b]?a-b:nums[a]-nums[b]));
        TreeSet<Integer> small = new TreeSet<>((a,b)->(nums[a]==nums[b]?Integer.compare(a,b):Integer.compare(nums[a],nums[b])));
        TreeSet<Integer> large = new TreeSet<>((a,b)->(nums[a]==nums[b]?Integer.compare(a,b):Integer.compare(nums[a],nums[b])));


        for (int i = 0; i< n; i++) {
            large.add(i);
            small.add(large.pollFirst());
            if(small.size() > large.size()) {
                large.add(small.pollLast());
            }
            if(small.size() + large.size() == k) {
                res[i+1-k] = small.size() == large.size()? ((double)nums[small.last()]+(double)nums[large.first()])/2.0 : nums[large.first()];

                small.remove(i-k+1);
                large.remove(i-k+1);
            }
        }
        return res;
    }
}