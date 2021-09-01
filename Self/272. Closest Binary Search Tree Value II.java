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
// M1.1/M1.2: O(n) time way
// note if we use inorder traversal + PQ with size k, then the time would be O(nlogk), but given it's a BST and inorder traversal result is sorted, we actually don't need a PQ, just simply a deque or a linkedList would work. And reduce time to O(n)
//see the top 2 comments(not the original post) in https://leetcode.com/problems/closest-binary-search-tree-value-ii/discuss/70511/AC-clean-Java-solution-using-two-stacks

// M1.1 using linkedList to keep a size = k window
class Solution {
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        LinkedList<Integer> res = new LinkedList<>();
        inorderCollect(root, res, target, k);
        return res;
    }

    private void inorderCollect(TreeNode root, LinkedList<Integer> res, double target, int k) {
    	if (root == null) {return;}
    	inorderCollect(root.left, res, target, k);
    	if (res.size() == k) {
    		if (Math.abs(res.peekFirst() - target) > Math.abs(root.val - target)) {
    			res.removeFirst();
    		} else {
    			return; // stop early, no need to go further, cause we already got the closest k numbers
    		}
    	}
    	res.add(root.val);
    	inorderCollect(root.right, res, target, k);
    }
}





// M1.2 using deque to store all inorder traversal result and remove from start or end until the size of the deque is k.
class Solution {
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        Deque<Integer> res = new LinkedList<>();
        inorder(res, root);
        while(res.size() > k) {
        	if (Math.abs(res.peekFirst() - target) > Math.abs(res.peekLast() - target)) {
                res.pollFirst();
            } else {
                res.pollLast();
            }
        }
        
        return (List) res;
    }
    
    private void inorder(Deque<Integer> res, TreeNode root) {
        if (root == null) {return;}
        inorder(res, root.left);
        res.add(root.val);
        inorder(res, root.right);
    }
}




// M2 - optimized, using two stacks (time O(H + k)), when the BST is balanced, the time is O(logN + k). Thought worst case is still O(n) if k == n.
// The amotized time for getPrev and getNext is O(1) for each call, cause every node is pushed into and popped from stack once.
// self improved base on: https://leetcode.com/problems/closest-binary-search-tree-value-ii/discuss/70503/O(logN)-Java-Solution-with-two-stacks-following-hint

class Solution {
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        // predStack and succStack used to store the next pred or next successor in inorder order. We update the stack each time we call nextPred or nextSucc if it has left subtree(for pred) / right subtree(for succ). Thus we make sure the top element in the stack is always the nextPred or the nextSucc we are looking for.
        // The next() call is amortized to O(1) because each element is put into and pop out of stack once. Since we want k elements, then there will be k times next() call which is O(k)
        // The initialize of the two stacks take O(h) which will be O(logn) for balanced tree
        // thus total time O(h + k) or O(logn + k) for balanced tree
        Stack<TreeNode> predStack = new Stack<>();
        Stack<TreeNode> succStack = new Stack<>();
        List<Integer> res = new LinkedList<>();
        
        // initialize the stacks
        TreeNode cur = root;
        while (cur != null) {
            if (cur.val < target) {
                predStack.push(cur);
                cur = cur.right;
            } else {
                succStack.push(cur);
                cur = cur.left;
            }
        }
        
        while (k-- > 0) {
            if (predStack.isEmpty()) {
                res.add(getNextSucc(succStack));
            } else if (succStack.isEmpty()) {
                res.add(getNextPred(predStack));
            } else {
                double predDiff = Math.abs((double) predStack.peek().val - target);
                double succDiff = Math.abs((double) succStack.peek().val - target);
                if (predDiff < succDiff) {
                    res.add(getNextPred(predStack));
                } else {
                    res.add(getNextSucc(succStack));
                }
            }
        }
        
        return res;
        
    }
    
    
    private int getNextPred(Stack<TreeNode> predStack) {
        TreeNode next = predStack.pop();
        int res = next.val;
        //update the predStack if the pop out node has left subtree, max(left subtree) will be the next pred one
        TreeNode cur = next.left;
        while (cur != null) {
            predStack.push(cur);
            cur = cur.right;
        }
        return res;
    }
    
    
    private int getNextSucc(Stack<TreeNode> succStack) {
        TreeNode next = succStack.pop();
        int res = next.val;
        //update the predStack if the pop out node has left subtree, max(left subtree) will be the next pred one
        TreeNode cur = next.right;
        while (cur != null) {
            succStack.push(cur);
            cur = cur.left;
        }
        return res;
    }
    
    
}
































