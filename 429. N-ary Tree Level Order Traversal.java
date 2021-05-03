/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/

class Solution {
    // M1: self phase2 BFS using queue and size variable
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> res = new LinkedList<>();
        if (root == null) {return res;}
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> levelRes = new LinkedList<>();
            while (size > 0) {
                Node cur = queue.poll();
                levelRes.add(cur.val);
                for (Node child : cur.children) {
                    queue.add(child);
                }
                size--;
            }
            res.add(levelRes);
        }
        return res;
    }
}