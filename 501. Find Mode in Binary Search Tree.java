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
	// method 1: time O(n) space O(n)
    public int[] findMode(TreeNode root) {
        if (root == null) {return new int[0];}
        // traverse the tree and use a map to track <value, count>
        // also record maxCount along the way and return
        HashMap<Integer, Integer> map = new HashMap<>();
        List<Integer> list = new LinkedList<>();
        int[] maxCount = new int[1];
        preorder(root, map, maxCount);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
        	if (entry.getValue() == maxCount[0]) {
        		list.add(entry.getKey());
        	}
        }
        // copy elements in list to int[]
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
        	res[i] = list.get(i);
        }
        return res;
    }

    private void preorder(TreeNode root, HashMap<Integer, Integer> map, int[] maxCount) {
    	if (root == null) {return;}
    	int newCount = 1;
    	if (map.containsKey(root.val)) {
    		newCount = map.get(root.val) + 1;    		
    	} 
    	map.put(root.val, newCount);
    	maxCount[0] = Math.max(maxCount[0], newCount);
    	preorder(root.left, map, maxCount);
    	preorder(root.right, map, maxCount);
    }
}


class Solution {
	// method2 time and space O(n) still
	// inorder traversal, resList only keep val has current maxCount.
    public int[] findMode(TreeNode root) {
        if (root == null) {return new int[0];}
        
        List<Integer> list = new LinkedList<>();
        // use count to pass maxCount[0] and curCount[1] and curValue[2]
        int[] count = new int[]{-1, -1, -1};
        inorder(root, list, count);

        // copy elements in list to int[]
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
        	res[i] = list.get(i);
        }
        return res;
    }

    private void preorder(TreeNode root, List<Integer> list, int[] count) {
    	if (root == null) {return;}
    	inorder(root.left, list, count);
    	// maxCount = count[0]; curCount = count[1]; curValue = count[2] 
    	if (root.val == count[2]) {
    		count[1] += 1;
    	} else {
    		count[1] = 1;
    		count[2] = root.val;
    	}
    	// if curCount > maxCount, new mode appears, clear the list and add the new mode
    	if (count[1] > count[0]) {
    		count[0] = count[1];
    		list.clear();
    		list.add(root.val)
    	} else if (count[1] == count[0]) {
    		// multiple modes
    		list.add(root.val);
    	}
    	
    	inorder(root.right, list, count);
    }
}


// method3: O(1) inorder twice, first time to get modes number, 
// the second time, put val in.

//(https://leetcode.com/problems/find-mode-in-binary-search-tree/discuss/98101/Proper-O(1)-space)



























