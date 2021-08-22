// self M1 : BFS
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root == null) {return res;}
        queue.add(root);
        int level = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            level++;
            List<Integer> curList = new LinkedList<>();
            while (size-- > 0) {
                TreeNode cur = queue.poll();
                if (level % 2 == 1) {
                    curList.add(cur.val);
                } else {
                    curList.add(0,cur.val);
                }
                if (cur.left != null) {queue.add(cur.left);}
                if (cur.right != null) {queue.add(cur.right);}     
            }
            res.add(curList);
        }
        return res;
    }
}





// self M2: DFS
class Solution {
    // DFS way
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        dfs(root, res, 0);
        return res;
    }
    
    private void dfs(TreeNode root, List<List<Integer>> res, int level) {
        if (root == null) {return;}
        if (level >= res.size()) {res.add(new LinkedList<>());}
        if (level % 2 == 0) {
            res.get(level).add(root.val);
        } else {
            res.get(level).add(0, root.val);
        }
        dfs(root.left, res, level + 1);
        dfs(root.right, res, level + 1);
    }
}





