class Solution {
    /* Initial thought
    based on 2sum, this is similar as fix one value, and 2sum for other values behind it.
    Again, use a hashset for inner 2sum.
    time O(n^2) space O(n) for the hashSet
    
    Another way is related to sorted 2sum problem, if it's sorted we can use two pointers without extra space. 
    time O(nlogn(sorting) + n^2) = O(n^2)  space O(logn) to O(n) depends on the sorting algo
    
    one thing to consider for both methods is that the solution list is unique. Thus for M1, for inner loop, we add result to a set.For outer loop, we skip dup values by recording the visited outer value.
    For M2, for inner loop.when move left and right pointers after a valid pair, we skip values same as original left value. For outer loop, we skip the dup values if it is the same as its prev value.
    */
    public List<List<Integer>> threeSum(int[] nums) {
        // M2 sort + two pointers
        List<List<Integer>> res = new LinkedList<>();
        Arrays.sort(nums);
        // note add the nums[i] <= 0 to stop early, since the array is sorted.
        for (int i = 0; i < nums.length && nums[i] <= 0; i++) {
            // skip dup
            if (i > 0 && nums[i] == nums[i-1]) {continue;}
            // do inner 2sum
            int left = i + 1;
            int right = nums.length - 1;
            int target = -nums[i];
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum == target) {
                    // note: use asList instead of using a curList, which needs to do backtracking
                    res.add(Arrays.asList(nums[i], nums[left++], nums[right--]));
                    // avoid dup
                    while(left < right && nums[left] == nums[left-1]) {left++;}
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }           
        }
        return res;
    }
} 



class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        // M1 unsort + hashmap + set res convert to list
        Set<List<Integer>> res = new HashSet<>();
        Set<Integer> seen = new HashSet<>(); // avoid outer dup
        
        for (int i = 0; i < nums.length; i++) {
            if (seen.contains(nums[i])) {continue;}
            seen.add(nums[i]);
            // inner two sum
            int target = -nums[i];
            Set<Integer> curSet = new HashSet<>();
            for (int j = i+1; j < nums.length; j++) {
                if (curSet.contains(target - nums[j])) {
                    // need to sort the triplet, otherwise the res set can't remove dup
                    List<Integer> triplet = Arrays.asList(nums[i],nums[j], target-nums[j]);
                    Collections.sort(triplet);
                    res.add(triplet);
                }
                curSet.add(nums[j]);
            }
        }
        return new LinkedList(res);
    }
} 



// check the solution page
// https://leetcode.com/problems/3sum/solution/






/*For interview
Addressing several question regarding the "No-Sort" approach. We recommend approach 1 for interviews, and approach 3 is to address a possible follow-up question (e.g. "what if you cannot sort the array"). The point here is that it's possible, though the efficiency would heavily depend on the input. If we have a very large array with many duplicates and a few matching triplets, the "No-Sort" approach would be more memory efficient.

Figuring out and communicating pros and cons of each approach is very important during an interview.
*/









/* space complexity analysis for sorting algo
Space complexity of O(1) stems from using in-place sorting algorithms like Bubble Sort, Insertion Sort etc. But these algorithms have worst case runtime complexity of O(n^2). In this problem, we need something that sorts in O(nlogn). Mergesort, Quicksort give you this but Mergesort takes O(n) space owing to the auxiliary array need to copy the merged result and quicksort takes O(logn) space owing to the recursive implementation.

Heapsort actually gives you the best of both worlds - O(nlogn) time complexity and O(1) space complexity. So, asymptotically, you are right that we can solve this problem in O(1) space complexity. But since Quicksort is almost always used in practice in most library sorting algorithms, we have to be content with O(logn) space complexity.
*/