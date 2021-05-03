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
        int[] count = new int[]{0, 0, -1};
        inorder(root, list, count);

        // copy elements in list to int[]
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
        	res[i] = list.get(i);
        }
        return res;
    }

    private void inorder(TreeNode root, List<Integer> list, int[] count) {
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
    		list.add(root.val);
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
// I think there's a small bug in this solution
/*
if (modes != null)
	modes[modeCount] = currVal;
modeCount++;

The temporary modeCount could be larger then the size of modes, 
which is the final modeCount, thus need to add a condition:
modeCount < modes.size, before adding currVal
-------------
correction for above: no bug. because we don't reset maxCount after 1st inorder traverse.
*/

// M3: two inorder pass, first time to record # of results and second time to update res[]
class Solution {
    private int[] res;
    private int maxCount;
    private int curCount;
    private int curVal;
    private int modeCount;


    public int[] findMode(TreeNode root) {
        inorder(root);
        res = new int[modeCount];
        modeCount = 0;
        curCount = 0;
        // maxCount no need to reset 0. we only add values with maxCount count into res.
        inorder(root);
        return res;
    }

    private void inorder(TreeNode root) {
        if (root == null) {return;}
        inorder(root.left);
        countValue(root);
        inorder(root.right);
    }

    private void countValue(TreeNode root) {
        // use !=, otherwise, root.val <= initial curVal = 0 won't be counted.
        if (root.val != curVal) {
            curCount = 0;
            curVal = root.val;
        }
        curCount += 1;
        if (curCount == maxCount) {
            if (res != null) {
                res[modeCount] = root.val;
            }
            modeCount++;
        } else if (curCount > maxCount) { // in the 2nd round of inorder, this is impossible to happen
            modeCount = 1;
            maxCount = curCount;
        }
    }
}



// M4: based on M3, real space O(1)
// replace O(n) recursive inorder traversal to O(1) morris inorder
class Solution {
    private int[] res;
    private int maxCount;
    private int curCount;
    private int curVal;
    private int modeCount;


    public int[] findMode(TreeNode root) {
        inorder(root);
        res = new int[modeCount];
        modeCount = 0;
        curCount = 0;
        // maxCount no need to reset 0. we only add values with maxCount count into res.
        inorder(root);
        return res;
    }

    // change inorder to morris
    private void inorder(TreeNode root) {
        if (root == null) {return;}
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left == null) {
                countValue(cur);
                cur = cur.right;
            } else {
                TreeNode rightMost = cur.left;
                while (rightMost.right != null && rightMost.right != cur) {
                    rightMost = rightMost.right;
                }
                if (rightMost.right == null) {
                    rightMost.right = cur;
                    cur = cur.left;
                } else {
                    rightMost.right = null;
                    countValue(cur);
                    cur = cur.right;
                }

            }
        }
    }

    private void countValue(TreeNode root) {
        // use !=, otherwise, root.val <= initial curVal = 0 won't be counted.
        if (root.val != curVal) {
            curCount = 0;
            curVal = root.val;
        }
        curCount += 1;
        if (curCount == maxCount) {
            if (res != null) {
                res[modeCount] = root.val;
            }
            modeCount++;
        } else if (curCount > maxCount) { // in the 2nd round of inorder, this is impossible to happen
            modeCount = 1;
            maxCount = curCount;
        }
    }
}























