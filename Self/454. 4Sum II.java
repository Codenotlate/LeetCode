/*Thought
O(n^2) space and time way. Get the pairsum count map for num1 & num2 and num3 & num4. Then for each pairsum in map34, check if -pairsum is in map12. res += c1 * c2 if it has.
*/
class Solution {
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer, Integer> map12 = new HashMap<>();
        for (int n1: nums1) {
            for (int n2: nums2) {
                map12.put(n1+n2, map12.getOrDefault(n1+n2,0) + 1);
            }
        }
        int res = 0;
        for (int n3: nums3) {
            for (int n4: nums4) {
                int sum = -n3-n4;
                if(map12.containsKey(sum)) {res += map12.get(sum);}
            }
        }
        return res;
    }
}




// possible followup in interview (kSum II)
/*
After you solve 4Sum II, an interviewer can follow-up with 5Sum II, 6Sum II, and so on. What they are really expecting is a generalized solution for k input arrays. Fortunately, the hashmap approach can be easily extended to handle more than 4 arrays.

Above, we divided 4 arrays into two equal groups, and processed each group independently. Same way, we will divide kk arrays into two groups. For the first group, we will have k/2 nested loops to count sums. Another k/2 nested loops will enumerate arrays in the second group and search for complements.

Further Thoughts
For an interview, keep in mind the generalized implementation. Even if your interviewer is OK with a simpler code, you'll get some extra points by describing how your solution can handle more than 4 arrays.

It's also important to discuss trade-offs with your interviewer. If we are tight on memory, we can move some arrays from the first group to the second. This, of course, will increase the time complexity.

In other words, the time complexity can range from O(n^k) to O(n^(k/2)), and the memory complexity ranges from O(1) to O(n^(k/2)) accordingly.

*/
class Solution {
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        return kSum(new int[][]{nums1, nums2, nums3, nums4});
    }
    
    public int kSum(int[][] lists) {
        Map<Integer, Integer> map = new HashMap<>();
        addToMap(lists, map, 0, 0);
        return getCount(lists, map, lists.length/2, 0);
    }
    
    private void addToMap(int[][] lists, Map<Integer, Integer> map, int listIdx, int cursum) {
        if (listIdx == lists.length / 2) {
            map.put(cursum, map.getOrDefault(cursum, 0) + 1);
            return;
        }
        for (int n: lists[listIdx]) {
            addToMap(lists, map, listIdx + 1, cursum + n);
        }
    }
    
    
    private int getCount(int[][] lists, Map<Integer, Integer> map, int listIdx, int cursum) {
        if (listIdx == lists.length) {
            return map.getOrDefault(-cursum, 0);
        }
        int res = 0;
        for (int n: lists[listIdx]) {
            res += getCount(lists, map, listIdx + 1, cursum + n);
        }
        return res;
        
    }
}