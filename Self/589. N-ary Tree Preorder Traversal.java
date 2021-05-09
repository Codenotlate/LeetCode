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


// M1: recursive way, trivial
class Solution {
    public List<Integer> preorder(Node root) {
        List<Integer> res = new LinkedList<>();
        if (root == null) {return res;}
        res.add(root.val); 
        for (Node child: root.children) {
            res.addAll(preorder(child));
        }
        return res;
    }
}


// M2: iterative
class Solution {
    public List<Integer> preorder(Node root) {
        List<Integer> res = new LinkedList<>();
        if (root == null) {return res;}
        
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            res.add(cur.val);
            for (int i = cur.children.size() - 1; i >= 0; i--) {
                stack.push(cur.children.get(i));
            }
        }
        return res;
    }
}