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
// https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/discuss/37811/Simple-solution-using-constant-space
// basically just level order traversal using the next pointer from the parent level. Use a dummyhead node(headchild) to track the start of next level, and use curchild to track the tail of the next-linkedlist in child level.
// time O(n) space O(1)
class Solution {
    public Node connect(Node root) {
        Node rootcopy = root;
        Node headchild = new Node(0);
        Node curchild = headchild;
        
        while (rootcopy != null) {
            headchild.next = null;
            curchild = headchild;
            while (rootcopy != null) {
                if (rootcopy.left != null) {
                    curchild.next = rootcopy.left;
                    curchild = curchild.next;
                }
                if (rootcopy.right != null) {
                    curchild.next = rootcopy.right;
                    curchild = curchild.next;
                }
                rootcopy = rootcopy.next;
            }
            rootcopy = headchild.next;
        }
        
        return root;
        
    }
}