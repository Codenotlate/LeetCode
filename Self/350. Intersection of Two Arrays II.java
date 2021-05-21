// Phase3 self
/* This is an easy question, but the followup questions would be meaningful for interview

*/


// M1: unsorted, O(max(l1,l2)) time O(min(l1, l2)) space
class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
        	int[] temp = nums1;
        	nums1 = nums2;
        	nums2 = temp;
        }

        // scan nums1 to build the hashMap
        Map<Integer, Integer> nums1Map = new HashMap<>();
        for (int n: nums1) {
        	nums1Map.put(n, nums1Map.getOrDefault(n, 0) + 1);
        }

        List<Integer> resList = new LinkedList<>();

        for (int n: nums2) {
        	if (nums1Map.containsKey(n) && nums1Map.get(n) > 0) {
        		resList.add(n);
        		nums1Map.put(n, nums1Map.get(n) - 1);
        	}
        }

        int[] res = new int[resList.size()];
        for (int i = 0; i < resList.size(); i++) {
            res[i] = resList.get(i);
        }
        return res;
        
    }
}



// M2: assume the array is sorted, then use two pointers to do it in O(ax(l1,l2)) time and O(1) space

class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        int i = 0;
        int j = 0;

        List<Integer> resList = new ArrayList<>();

        while (i < nums1.length && j < nums2.length) {
        	if (nums1[i] == nums2[j]) {
        		resList.add(nums1[i]);
        		i++;
        		j++;
        	} else if (nums1[i] < nums2[j]) {
        		i++;
        	} else {
        		j++;
        	}
        }

        int[] res = new int[resList.size()];
        for (int k = 0; k < resList.size(); k++) {
        	res[k] = resList.get(k);
        }
        return res;
    }
}

/* follow-up questions
https://leetcode.com/problems/intersection-of-two-arrays-ii/solution/

another idea:
What if nums1's size is small compared to nums2's size? Which algorithm is better?
This one is a bit tricky. Let's say nums1 is K size. Then we should do binary search for every element in nums1. Each lookup is O(log N), and if we do K times, we have O(K log N).
If K this is small enough, O(K log N) < O(max(N, M)). Otherwise, we have to use the previous two pointers method.
let's say A = [1, 2, 2, 2, 2, 2, 2, 2, 1], B = [2, 2]. For each element in B, we start a binary search in A. To deal with duplicate entry, once you find an entry, all the duplicate element is around that that index, so you can do linear search scan afterward.

Time complexity, O(K(logN) + N). Plus N is worst case scenario which you have to linear scan every element in A. But on average, that shouldn't be the case. so I'd say the Time complexity is O(K(logN) + c), c (constant) is number of linear scan you did.


comment in this answer
https://leetcode.com/problems/intersection-of-two-arrays-ii/discuss/82243/Solution-to-3rd-follow-up-question

*/
















