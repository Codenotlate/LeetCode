// M1: self time O(n) space O(n)
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