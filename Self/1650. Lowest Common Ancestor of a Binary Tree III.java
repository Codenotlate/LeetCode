/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node parent;
};
*/

class Solution {
    /* Initial thought
    Since we already have p and q, and they have the parent node pointer. We can use the pointer to get all the ancestor for p till the root and store in a set. Do similar trace for q. return the first ancestor of q that is contained in p ancester set.
    time O(height) space O(height)
    Addnote: this should be a simplified version of another LCA problem, which requires us to find p/q, and build their ancestor map along the way by ourselves.
    */
    public Node lowestCommonAncestor(Node p, Node q) {
        Set<Node> pSet = new HashSet<>();
        Node cur = p;
        while (cur != null) {pSet.add(cur); cur = cur.parent;}
        cur = q;
        while(cur != null) {
            if (pSet.contains(cur)) {return cur;}
            cur = cur.parent;
        }
        return null; // should not reach this line if guarantee a result
    }
}


// good solution summary
// https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iii/discuss/950242/Multiple-solution-approaches-in-Java-(with-comments-and-explanation)

class Solution {
    /* improved thought
    since we have parent pointer here, this problem is actually to check the intersection node for two linkedlist starting from p and q. Thus we can use two pointers to make space O(1).
    There are two ways to find intersection for two LL:
    method1: find the length of each LL, move the longer one to start at the node having same length towards intersection as the shorter one. The two pointers will natually meet at intersection.
    method2: view this as a circle detection problem by connecting the end of the shorter LL to the head of the longer LL.
    */
    public Node lowestCommonAncestor(Node p, Node q) {
        int l1 = getLen(p);
        int l2 = getLen(q);
        if (l1 < l2) {Node temp = p; p = q; q= temp;}
        int k = Math.abs(l1 - l2);
        while(k-- > 0) {p = p.parent;}
        while(p!= q) {
            p = p.parent;
            q = q.parent;
        }
        return p;
    }
    
    private int getLen(Node p) {
        int count = 0;
        while(p != null) {p = p.parent; count++;}
        return count;
    }
}

class Solution {
    /* improved thought
    method2: view this as a circle detection problem by connecting the end of the shorter LL to the head of the longer LL.
    */
    public Node lowestCommonAncestor(Node p, Node q) {
        Node p1 = p, p2 = q;
        while (p1!= p2) {
            p1 = p1 == null? q : p1.parent;
            p2 = p2 == null? p : p2.parent;
        }
        return p1;
    }
    
   
}








// Review
/*Thought
Here each node has a pointer to its parent node. This can convert to find the intersect of two LL.
M1:O(n) space way. Using a set to store all ancestors for p  or q, then check the first ancestor of q or p that is included in the set.
M2:O(1) space way. two pointers starting from p and q. If we draw two LinkedLists out:
----I---
  --I---
Then if the second LL pointer move to the start of the first LL when it reaches the end of LL2, and LL1 pointer reaches start of LL2 when reaches end. Then since there path length will be the same, they will ultimately meet at the intersection node.
time O(n) space O(1)
*/
// M2: O(1) space way
class Solution {
    public Node lowestCommonAncestor(Node p, Node q) {
        Node pp = p;
        Node qq = q;
        while(pp != qq) {
            pp = pp.parent == null? q : pp.parent;
            qq = qq.parent == null? p : qq.parent;
        }
        return pp;
    }
}