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
	// method1: inorder recursively, with global var
	// can also do it iteratively, or recursively but without global var
	// Global variables may cause problems, 
	// so sometimes in the interview we will be asked to solve a problem without using global variable.
	private int minDiff = Integer.MAX_VALUE;
	private Integer lastVisit = null;
    public int getMinimumDifference(TreeNode root) {
        // inorder traversal, update minDiff, and keep track of last visited node
		inorder(root);
		return minDiff;
    }

    private void inorder(TreeNode root) {
    	if (root == null) {return;}

    	inorder(root.left);
    	if (lastVisit != null) {
    		minDiff = Math.min(root.val - lastVisit, minDiff);
    	}
    	lastVisit = root.val;
    	inorder(root.right);

    }
}


class Solution {
	// method2: recursively without global var
    public int getMinimumDifference(TreeNode root) {
        // use a List to store minDiff and lastVisit
       	ArrayList<Integer> initial = new ArrayList<>(){};
       	initial.add(Integer.MAX_VALUE);
       	initial.add(null);

        return inorder(root, initial).get(0);
    }

    private ArrayList<Integer> inorder(TreeNode root, ArrayList<Integer> list) {
    	if (root == null) {return list;}
    	list = inorder(root.left, list);

    	if (list.get(1) != null) {
    		list.set(0, Math.min(list.get(0), root.val - list.get(1)));
    	}
    	list.set(1, root.val);

    	list = inorder(root.right, list);
    	return list;
    }
}

//above m2 way: self phase 2
class Solution {
    public int getMinimumDifference(TreeNode root) {
        Integer[] minDiff = new Integer[] {null};        
        inorder(root, minDiff, null);
        return minDiff[0];
    }
    
    private Integer inorder(TreeNode root, Integer[] minDiff, Integer lastNum) {
        if (root == null) {return lastNum;}
        lastNum = inorder(root.left, minDiff, lastNum);
        if (lastNum != null && (minDiff[0] == null || root.val - lastNum <= minDiff[0])) {
            minDiff[0] = root.val - lastNum;
            
        }
        lastNum = root.val;
        lastNum = inorder(root.right, minDiff, lastNum);
        return lastNum;
    }
}



class Solution {
	// method 3: using iterative inorder
    public int getMinimumDifference(TreeNode root) {
        int lastVisit = -1;
        int minDiff = Integer.MAX_VALUE;

        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while(cur != null || !stack.isEmpty()) {
        	if (cur != null) {
        		stack.add(cur);
        		cur = cur.left;
        	} else {
        		TreeNode top = stack.pop();
        		if (lastVisit != -1) {
        			minDiff = Math.min(minDiff, top.val - lastVisit);
        		}
        		lastVisit = top.val;

        		cur = top.right;
        	}

        }
        return minDiff;

    }
}

















