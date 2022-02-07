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









