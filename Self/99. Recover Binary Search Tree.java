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
	// M1 : inorder using stack iteratively
	// space O(n) time O(n)
    public void recoverTree(TreeNode root) {
        TreeNode prev = null;
        TreeNode first = null;
        TreeNode last = null;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
        	while (cur != null) {
        		stack.push(cur);
        		cur = cur.left;
        	}
        	cur = stack.pop();
        	if (prev != null && prev.val > cur.val) {
        		if (first == null) {first = prev;}
        		last = cur;
        	}
        	prev = cur;
        	cur = cur.right;
        }
        if (first != null && last != null) {
        	int temp = first.val;
        	first.val = last.val;
        	last.val = temp;
        }
    }
}


// M2: Morris inorder way
// time O(n) space O(1)
class Solution {
    public void recoverTree(TreeNode root) {
        TreeNode cur = root;
        TreeNode first = null;
        TreeNode last = null;
        TreeNode prev = null;
        while (cur != null) {
        	if (cur.left == null) {
        		// visit cur
        		if (prev != null && prev.val > cur.val) {
		    		if (first == null) {first = prev;}
		    		last = cur;
		    	}
		    	prev = cur;
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
        			// visit cur
        			if (prev != null && prev.val > cur.val) {
			    		if (first == null) {first = prev;}
			    		last = cur;
			    	}
			    	prev = cur;
        			cur = cur.right;
        		}
        	}
        }
        swapVal(first, last);
    }


    private void swapVal(TreeNode first, TreeNode last) {
    	if (first != null && last != null) {
    		int temp = first.val;
    		first.val = last.val;
    		last.val = temp;
    	}
    }
}



// M1 self review
class Solution {
    public void recoverTree(TreeNode root) {
        // inorder iterative traverse way space O(h)
        Stack<TreeNode> stack = new Stack<>();
        TreeNode lg = null;
        TreeNode sm = null;
        TreeNode cur = root;
        TreeNode prev = null;
        
        while (!stack.isEmpty() || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            if (prev != null && cur.val < prev.val) {
                if (lg == null) {lg = prev; sm = cur;}
                else {sm = cur; break;}
            }
            prev = cur;
            cur = cur.right;
        }
        
        // swap the value of lg and sm
        int temp = lg.val;
        lg.val = sm.val;
        sm.val = temp;
    }
}

//M2 morris way self review
class Solution {
    // M2: self implemented Morris way time O(n) space O(1)
    public void recoverTree(TreeNode root) {
        TreeNode lg = null;
        TreeNode sm = null;
        TreeNode prev = null;
        TreeNode cur = root;
        
        while (cur != null) {
            if (cur.left == null) {
                if (prev != null && prev.val > cur.val) {
                    if (lg == null) {lg = prev;}
                    sm = cur;
                }
                prev = cur;
                cur = cur.right;
            } else {
                TreeNode rightMost = cur.left;
                while (rightMost.right != null && rightMost.right != cur) {
                    rightMost = rightMost.right;
                }
                if (rightMost.right == null) {
                    rightMost.right = cur;
                    cur = cur.left;
                }
                else {
                    rightMost.right = null;
                    if (prev != null && prev.val > cur.val) {
                        if (lg == null) {lg = prev;}
                        sm = cur;
                    }
                    prev = cur;
                    cur = cur.right;
                }
            }
        }
        
        int temp = lg.val;
        lg.val = sm.val;
        sm.val = temp;
    }
}


// Phase3 self
// morris way time O(n) space O(1)
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
    //Use Morris inorder traverse to make space O(1)
    public void recoverTree(TreeNode root) {
        TreeNode prevPoll = null, curPoll = null, lg = null, sm = null;
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left == null) {
                prevPoll = curPoll;
                curPoll = cur;
                if (prevPoll != null && prevPoll.val > curPoll.val) {
                    if (sm == null) {lg=prevPoll;}
                    sm = curPoll;
                }
                cur = cur.right;
            } else {
                TreeNode rightMost = cur.left;
                while (rightMost.right!= null && rightMost.right != cur) {
                    rightMost = rightMost.right;
                }
                if (rightMost.right == null) {
                    rightMost.right = cur; 
                    cur = cur.left;
                }else {
                    rightMost.right = null;
                    prevPoll = curPoll;
                    curPoll = cur;
                    if (prevPoll.val > curPoll.val) {
                        if (sm == null) {lg=prevPoll;}
                        sm = curPoll;
                    }
                    cur = cur.right;
                }
            }
        }
        
        
        int tempval = lg.val;
        lg.val = sm.val;
        sm.val = tempval;
    }
}





// Reveiw
/*Initial thought
since BST, inorder traverse should get sorted array. In this problem, one pair in the sorted array is swapped. Set the left side to be min and right side to be max, we need to find the first reverse order pair and track the nodes, and replace the smaller nodes with the smaller one in the second pair fo reverse order pair if there exists.
regular inorder traverse require O(n) space for the stack. Since here requires O(1) space, we can use Morris traverse here. Along the way only need to track the prev searched node.
time O(n) space O(1)
3 2 1
1 3 2 4
1 2 5 4 3 6
*/
class Solution {
    public void recoverTree(TreeNode root) {
        TreeNode smallNode = null, largeNode = null;
        TreeNode cur = root;
        TreeNode prev = null;
        while (cur != null) {
            if (cur.left != null) {
                TreeNode rightMost = cur.left;
                while(rightMost.right != null && rightMost.right != cur) {rightMost = rightMost.right;}
                if(rightMost.right == null) {
                    rightMost.right = cur; 
                    cur = cur.left;
                }
                else {
                    rightMost.right = null;
                    if (prev != null && cur.val < prev.val) {
                        if(smallNode == null) {largeNode = prev;}
                        smallNode = cur;
                    }
                    prev = cur;
                    cur = cur.right;
                }
            } else {
                if (prev != null && cur.val < prev.val) {
                    if(smallNode == null) {largeNode = prev;}
                    smallNode = cur;
                }
                prev = cur;
                cur = cur.right;
            }
        }
        
        int temp = smallNode.val;
        smallNode.val = largeNode.val;
        largeNode.val = temp;
    }
}




















