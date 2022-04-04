class Solution {
    /* Initial thought
    The preorder can determine the root of each subtree. And can do this recursively using range of the array to label different level of recursions.
    In the preorder valid range, fisrt find the first num in inorder array(e.g. position = x). Then leftsubtree = recur(preorder(preleft+1, preleft+1 + x -1 - inleft ), inorder(inleft, x-1)); rightsubtree = recur(preoder(preleft+1+x-inleft, preright), inorder(x+1, inright)). root(firstnum).left/right = leftsubtree/rightsubtree
    one note add: build the number->index map for inorder array to make find position O(1)
    time O(n) space O(n)
    */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {inorderMap.put(inorder[i], i);}
        return build(preorder, 0, preorder.length-1, inorder, 0, inorder.length-1, inorderMap);
    }
    
    private TreeNode build(int[] preorder, int preleft, int preright, int[] inorder, int inleft, int inright, Map<Integer, Integer> map) {
        if (preleft == preright) {return new TreeNode(preorder[preleft]);}
        if(preleft > preright) {return null;}
        int pos = map.get(preorder[preleft]);
        TreeNode root = new TreeNode(preorder[preleft]);
        TreeNode left = build(preorder, preleft+1, preleft + pos - inleft, inorder, inleft, pos-1, map);
        TreeNode right = build(preorder, preleft+pos-inleft+1, preright, inorder, pos+1, inright, map);
        root.left = left;
        root.right = right;
        return root;
    }
}

// can check discussion again





// Review
/* Thought
since preorder always put root node first, we can use it to separate left and right subtree in the inorder array. We can solve this recursively.
At each recur call, start of preorder is the root node val. build the root node. Then find the pos of the root val in inorder array(This can be achieved with a hashmap in O(1)). recursively call preorder[prestart+1, prestart+pos-instart] and inorder[instart, pos-1], the node returned from it will be the left child of cur node. Similarly, the right side recur on preorder[prestart + pos - instart +1, preend] and inorder[pos+1, inend]. return the root node.
time O(n) space O(n(height))
*/
class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }       
        return getTree(preorder, 0, preorder.length-1, inorder, 0, inorder.length-1, map); 
    }
    
    
    private TreeNode getTree(int[] preorder, int prestart, int preend, int[] inorder, int instart, int inend, Map<Integer,Integer> map) {
        if(instart > inend) {return null;}
        if(instart == inend) {return new TreeNode(inorder[instart]);}
        int pos = map.get(preorder[prestart]);
        TreeNode root = new TreeNode(preorder[prestart]);
        TreeNode left = getTree(preorder, prestart+1, prestart+pos-instart, inorder, instart, pos-1, map);
        TreeNode right = getTree(preorder, prestart+pos-instart+1, preend, inorder, pos+1, inend, map);
        root.left = left;
        root.right = right;
        return root;
    }
}





// Iterative way
// https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/discuss/34555/The-iterative-solution-is-easier-than-you-think!
// basically build the tree in preorder, and for each node determine its parent and it is left or right child by comparing the index in the inorder array. time and space same as recursive way.





