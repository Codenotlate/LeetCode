// M1: self time O(n) space O(n) (related to 987)
class Solution {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        if (root == null) {return res;}
        int idx = 0;
        // given the negative index, we can first store it in a map and then convert to list res
        Map<Integer, List<Integer>> map = new HashMap<>();
        int maxIdx = Integer.MIN_VALUE;
        int minIdx = Integer.MAX_VALUE;
        
        // given the order is from top to bottom and left to right, considering using BFS
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.add(new Pair(root, idx));
        while (!queue.isEmpty()) {
            Pair<TreeNode, Integer> curPair = queue.poll();
            int curIdx = curPair.getValue();
            TreeNode curNode = curPair.getKey();
            map.putIfAbsent(curIdx, new LinkedList<>());
            map.get(curIdx).add(curNode.val);
            minIdx = Math.min(minIdx, curIdx);
            maxIdx = Math.max(maxIdx, curIdx);
            if (curNode.left != null) {queue.add(new Pair(curNode.left, curIdx - 1));}
            if (curNode.right != null) {queue.add(new Pair(curNode.right, curIdx + 1));}   
        }
        
        for (int i = minIdx; i <= maxIdx; i++) {
            res.add(map.get(i));
        }
        
        return res;
    }
    
}


// Review self
class Solution {
    /* initial thought:
    use BFS, label col number for each node, when poll out from the queue, add to a map first with col num as key and List<node.val> as values. The use of BFS also guarantees the required order.
    time O(n) space O(n)
    
    */
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        if (root == null) {return res;}
        Map<Integer, List<Integer>> map = new HashMap<>();
        Queue<Pair<Integer, TreeNode>> queue = new LinkedList<>();
        queue.add(new Pair(0, root));
        
        int minCol = 101;
        int maxCol = -101;
        while (!queue.isEmpty()) {
            Pair<Integer, TreeNode> p = queue.poll();
            int colNum = p.getKey();
            minCol = Math.min(minCol, colNum);
            maxCol = Math.max(maxCol, colNum);
            TreeNode node = p.getValue();
            map.putIfAbsent(colNum, new LinkedList());
            map.get(colNum).add(node.val);
            if (node.left!= null) {queue.add(new Pair(colNum-1, node.left));}
            if (node.right!= null) {queue.add(new Pair(colNum+1, node.right));}
        }
        
        
        for (int col = minCol; col <= maxCol; col++) {
            res.add(map.get(col));
        }
        return res;
        
        
    }
}


//https://leetcode.com/problems/binary-tree-vertical-order-traversal/solution/
// method3 can also do a DFS way, sort by col then by row. But it's more troublesome. will skip it for now.