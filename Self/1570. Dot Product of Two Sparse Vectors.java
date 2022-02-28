/* Initial thought
since it's sparse vector, we can only store the position where it has value. Since we need to search in dotProduct in order, we can store it as a list of int[]{pos, val}.
for sparceVector, time O(l), space O(n) - the non-zero positions #
for dotProduct, time O(n1+n2) - non-zero # for two vec

followup: if only one vector is sparse
assume n1 >> n2, then for each nonzero num in vec2, we do binary search in vec1 to find whether the same position is nonzero in vec1. time O(n2 * logn1)

*/
class SparseVector {
    List<int[]> list;
    
    SparseVector(int[] nums) {
        list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {list.add(new int[]{i, nums[i]});}
        }
    }
    
	// Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVector vec) {
        int p1 = 0;
        int p2 = 0;
        List<int[]> list2 = vec.list;
        int res = 0;
        while (p1 < this.list.size() && p2 < list2.size()) {
            int[] pair1 = this.list.get(p1);
            int[] pair2 = list2.get(p2);
            if (pair1[0] == pair2[0]) {
                res += pair1[1] * pair2[1];
                p1++;
                p2++;
            } else if (pair1[0] > pair2[0]) {
                p2++;
            } else {
                p1++;
            }
        }
        return res;
    }
}

/* Note: above two pointer way and followup solution is the best one.
Based on discussion in solution part, the second solution using hashMap is not acceptable in FB interview.
Reason:
I got this question at my FB onsite in August 2020. The interviewer did not accept my hashmap solution. He told me that hashing/lookups, while on surface look efficient, for large sparse vectors, hashing function takes up bulk of the computation that we might be better off with the first method. Wihile proposing hashmap/set solutions, take a minute to think about the complexity hashing function.
Update from recent FB onsite, interviewer didn't accept the HASHMAP solution and wanted to see the 2 pointers solution, in addition he also came up with a follow up question, what would you do if one of the vectors werent fully "sparse" -> hint use binary search

I got this question today on my FB interview. I proposed the Hash solution, and he asked the downside to it. I responded with large size of sparse vectors, hash collisions will occur when we hit memory allocation limits, etc. He asked alternative solutions and I proposed array of (index, value) pair. He asked me to code that. Then he added a constraint where one vector is considerably smaller than the other, and asked if we can improve the time complexity from O(m+n). After some scratching around, I told them that we can by doing binary-search of small vector's index over the larger one. This should improve the time-complexity. Was not sure of the exact Big Oh, but it should be better than m * log(n), since the search space should keep reducing from n. Fingers crossed for the results


And other good discussions here:
https://leetcode.com/problems/dot-product-of-two-sparse-vectors/solution/
*/
















