/*Thought
self thought: Similar as LCA problem. First build parentmap (like: 5:(1,L)) until find startValue and destValue. Then build the ancester map for startValue as (ancester: dist from startValue). Then build the trace back from destValue node, put L/R into res as moving to its ancestors. If the ancestor is in startValue map.keySet(), append U for "dist" times. Reverse the string to get the result.

M2: simplified way from discussion.  While find start and end nodes, build the stringBuilder for the path string along the way (using DFS). 
Then remove the common chars in both path from left until they are different and then replace teh start node path remaining part with 'U'.

Both way time O(n). but M2 should be faster.
*/
class Solution {
    public String getDirections(TreeNode root, int startValue, int destValue) {
        StringBuilder rootToStart = new StringBuilder();
        StringBuilder rootToEnd = new StringBuilder();
        
        findPath(root, startValue, rootToStart);
        findPath(root, destValue, rootToEnd);
        rootToStart.reverse();
        rootToEnd.reverse();
        
        int sidx = 0;
        int eidx = 0;
        while (sidx < rootToStart.length() && eidx < rootToEnd.length() && rootToStart.charAt(sidx) == rootToEnd.charAt(eidx)) {
            sidx++;
            eidx++;
        }
        StringBuilder res = new StringBuilder();
        int startcount = rootToStart.length() - sidx;
        while(startcount-- > 0) {res.append('U');}
        while(eidx < rootToEnd.length()) {
            res.append(rootToEnd.charAt(eidx));
            eidx++;                
        }
        return res.toString();
    }
    
    
    private boolean findPath(TreeNode root, int val, StringBuilder path) {
        if (root == null){return false;}
        if (root.val == val) {return true;}
        if (root.left != null && findPath(root.left, val, path)) {path.append('L');}
        else if (root.right != null && findPath(root.right, val, path)) {path.append('R');}
        return path.length() > 0;
    }
}



// more elegant code : https://leetcode.com/problems/step-by-step-directions-from-a-binary-tree-node-to-another/discuss/1612105/3-Steps