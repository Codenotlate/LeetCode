class Solution {
    // recursive space O(1) way
    public Node connect(Node root) {
        if (root == null || (root.left == null && root.right == null)) {return root;}
        Node cur = root.right;
        if (root.left != null) {
            root.left.next = root.right;
            if (root.right == null) {cur = root.left;}
        }
        if (root.next != null) {
            // need to loop the root level next until find one not null node to connect
            Node nextOne = root.next;
            while (nextOne != null) {
                if (nextOne.left != null) {
                    cur.next = nextOne.left;
                    break;
                } else if (nextOne.right != null) {
                    cur.next = nextOne.right;
                    break;
                }
                nextOne = nextOne.next;
            }
        }
        
        // Change the right and left order idea is brilliant!!
        connect(root.right);
        connect(root.left);
        return root;
    }
}

// adding iterative way, idea from discussion
