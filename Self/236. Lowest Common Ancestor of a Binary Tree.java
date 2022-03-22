/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // base case
        if (root == null || root == p || root == q) {return root;}

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) {return root;}
        return left == null? right : left;
    }
    /**
    The approach is pretty intuitive. Traverse the tree in a depth first manner. 
    The moment you encounter either of the nodes p or q, return some boolean flag. 
    The flag helps to determine if we found the required nodes in any of the paths.
    The least common ancestor would then be the node for which both the subtree r
    ecursions return a True flag. It can also be the node which itself 
    is one of p or q and for which one of the subtree recursions returns a True flag.
    */
}

// phase2 self
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {return null;}
        if (root == p || root == q) {return root;}
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left == null) {return right;}
        if (right == null) {return left;}
        return root;
    }
}



class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // iterative way: building a parent map until find both p and q
        // this one is slower than recursive way
        // starting from p or q backwards, 
        // find the first common ancester of them
    	Stack<TreeNode> stack = new Stack<>();
    	// use map to store parent of each node
    	Map<TreeNode, TreeNode> parents = new HashMap<>();
    	stack.push(root);
    	parents.put(root, null);

    	// store all parent nodes in map until both p and q are visited
    	while (!parents.containsKey(p) || !parents.containsKey(q)) {
    		TreeNode n = stack.pop();
    		if (n.left != null) {
    			stack.push(n.left);
    			parents.put(n.left, n);
    		}
    		if (n.right != null) {
    			stack.push(n.right);
    			parents.put(n.right, n);
    		}
    	}

    	// use a set to store the ancestors along the path from q to root
    	Set<TreeNode> ancestors = new HashSet<>();
    	while (p != null) {
    		ancestors.add(p);
    		p = parents.get(p);
    	}

    	// now search all the ancestors similarly for p from p to root
    	// return the first ancestor that is also contained in q's set
    	while (q != null) {
    		if (ancestors.contains(q)) {break;}
    		q = parents.get(q);
    	}

    	return q;
    }
}



//https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/discuss/65236/JavaPython-iterative-solution


// self review phase3
class Solution {
    // M1 recursive way
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {return null;}
        if (root == p || root == q) {return root;}
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if ((left != null && right != null) || root == p || root == q) {
            return root;
        }
        return left == null ? right : left;
    }
}


class Solution {
    // M2 iterative way
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        Map<TreeNode, TreeNode> parentMap = getMapBFS(root, p, q);
        Set<TreeNode> pParent = new HashSet<>();
        while (p != null) {
            pParent.add(p);
            p = parentMap.get(p);
        }
        
        while (!pParent.contains(q)) {
            q = parentMap.get(q);
        }    
        return q;  
    }
    
    
    
    private Map<TreeNode, TreeNode> getMapBFS(TreeNode root, TreeNode p, TreeNode q) {
        Map<TreeNode, TreeNode> res = new HashMap<>();
        if (root == null) {return res;}
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        res.put(root, null);
        while (!res.containsKey(q) || !res.containsKey(p)) {
            TreeNode cur = queue.poll();
            // mistake: don't write cur as root
            if (cur.left != null) {queue.add(cur.left); res.put(cur.left, cur);}
            if (cur.right != null) {queue.add(cur.right); res.put(cur.right, cur);}
        }
        return res;
    }
}



// Phase3 self
// time O(n) space O(n)
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        // first find p and q, and record the parent of all nodes along the way
        parentMap.put(root, null);
        find(root, p, parentMap);
        find(root, q, parentMap);
        // build the parent set of p
        Set<TreeNode> parentP = new HashSet<>();
        // don't forget to add p itself to the set
        while (p != null) {
            parentP.add(p);
            p = parentMap.get(p);
        }
        
        while(!parentP.contains(q)) {
            q = parentMap.get(q);
        }
        return q;       
    }
    
    // can actually simplified as above, use whether map keys contain p q to decide whether find p, q
    private boolean find(TreeNode root, TreeNode p, Map<TreeNode, TreeNode> map) {
        if (root == p) {return true;}
        if (root.left != null) {
            if (!map.keySet().contains(root.left)) {map.put(root.left, root);}
            if(find(root.left, p, map)) {return true;}          
        }
        if (root.right != null) {
            if (!map.keySet().contains(root.right)) {map.put(root.right, root);}
            if(find(root.right, p, map)) {return true;}         
        }
        return false;
    }
}

class Solution {
    // M2: recursive way
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {return root;}
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) {return root;}
        return left == null? right : left;
    }
}




// review
/*Initial thought
recursive way: if root is null return null; if root is either p or q, return root. Otherwise, check the result from recur(root.left, p,q) and recur(root.right, p, q). If both return not null, then return root. Otherwise, return the not-null value.
iterative way: build the parent map for both p and q, then build the ancestor set for p and traverse the parent path for q, return the first ancestor pf q that is in p set.
*/
// iterative way
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        Map<TreeNode, TreeNode> parents = new HashMap<>();
        find(root, p, q, parents);
        Set<TreeNode> pSet = new HashSet<>();
        while (p != null) {
            pSet.add(p);
            p = parents.get(p);
        }
        while (!pSet.contains(q)) {
            q = parents.get(q);
        }
        return q;
        
    }
    
    // find can be either iterative or recursive
    private void find(TreeNode root, TreeNode p, TreeNode q, Map<TreeNode, TreeNode> parents) {
        if (root == null) {return;}
        parents.put(root, null);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!parents.containsKey(p) || !parents.containsKey(q)) {
            TreeNode cur = queue.poll();
            if(cur.left != null) {queue.add(cur.left); parents.put(cur.left, cur);}
            if(cur.right != null) {queue.add(cur.right); parents.put(cur.right, cur);}
        }
    }
}
// recursive way
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {return root;}
        TreeNode leftres = lowestCommonAncestor(root.left, p, q);
        TreeNode rightres = lowestCommonAncestor(root.right, p, q);
        if (leftres != null && rightres != null) {return root;}
        return leftres == null? rightres : leftres;
    }
}


























