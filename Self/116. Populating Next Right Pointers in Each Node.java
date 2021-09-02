// all methods below have space O(1), considering followup mentions recursion stack isn't counted as extra space, we can use either recusive way or iterative way
// M1 recursive - naive way
class Solution {
    public Node connect(Node root) {
        if (root == null) {return null;}
        recur(root.left, root.right);
        return root;
    }
    
    private void recur(Node a, Node b) {
        if (a == null || b == null) {return;}
        a.next = b;
        recur(a.left, a.right);
        recur(a.right, b.left);
        recur(b.left, b.right);
    }
}



// M2 improved from M1 - recursive
class Solution {
    public Node connect(Node root) {
        if (root == null || root.left == null) {return root;}
        root.left.next = root.right;
        if (root.next != null) {root.right.next = root.next.left;}
        connect(root.left);
        connect(root.right);
        return root;
    }
}



// M3 iterative way with similar idea as M2
class Solution {
    public Node connect(Node root) {
        if (root == null) {return null;}
        Node leftMost = root;
        while (leftMost.left != null) {
            Node cur = leftMost;
            while (cur != null) {
                cur.left.next = cur.right;
                if (cur.next != null) {cur.right.next = cur.next.left;}
                cur = cur.next;
            }
            leftMost = leftMost.left;
        }
        return root;
    }
}