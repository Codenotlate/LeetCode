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
    // M1: recursive way
    public List<Integer> postorder(Node root) {
        List<Integer> res = new LinkedList<>();
        if (root == null) {return res;}
        for (Node child: root.children) {
            res.addAll(postorder(child));
        }
        res.add(root.val);
        return res;
    }   
}

class Solution {
    // M2: preorder reverse way
    public List<Integer> postorder(Node root) {
        List<Integer> res = new LinkedList<>();
        if (root == null) {return res;}
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            res.add(0, cur.val);
            for (Node child: cur.children) {
                stack.push(child);
            }
        }
        return res;
        
    }
}




class Solution {
    // M3: double push way
    public List<Integer> postorder(Node root) {
        List<Integer> res = new LinkedList<>();
        if (root == null) {return res;}
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        stack.push(root);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            // remember to check whether stack is empty before using peek
            if(!stack.isEmpty() && cur == stack.peek()) {
                // in this case the order of popped out of stack is actually the order of res
                for (int i = cur.children.size() - 1; i >= 0; i--) {
                    stack.push(cur.children.get(i));
                    stack.push(cur.children.get(i));
                }
            } else {
                res.add(cur.val);
            }
            
        }
        return res;
    }
}













