/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int[] findFrequentTreeSum(TreeNode root) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] freqAndCount = new int[2];
        getSubSum(root, map, freqAndCount);
        int nums = freqAndCount[1];
        int maxFreq = freqAndCount[0];
        int i = 0;
        int[] res = new int[nums];
        for (Map.Entry<Integer, Integer> e: map.entrySet()) {
        	if (e.getValue() == maxFreq) {
        		res[i] = e.getKey();
        		i++;
        	}
        }
        return res;
    }

    private int getSubSum(TreeNode root, Map<Integer, Integer> map, int[] freqAndCount) {
    	if (root == null) {return 0;}
    	int sum = root.val + getSubSum(root.left, map, freqAndCount) + getSubSum(root.right, map, freqAndCount);
    	int sumfreq = map.getOrDefault(sum, 0) + 1;
    	map.put(sum, sumfreq);
    	if (sumfreq > freqAndCount[0]) {
    		freqAndCount[0] = sumfreq;
    		freqAndCount[1] = 1;
    	} else if (sumfreq == freqAndCount[0]) {
    		freqAndCount[1] += 1;
    	}
    	return sum;
    }
}