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


// Review self
class Solution {
    /* Initial thought
    Use BFS traverse by level order. Each parent level will connect for its child level using the next pointer built between the parent level(which is built by the parent level of this parent level).
    Need to consider child is null case. Use a curChild to label the tail of the child level linkedlist.
    use iterative way to use O(1) space. recursive way is also find according to the requirement in the followup.    
    */
    // Try iterative way
    public Node connect(Node root) {
        if (root ==  null) {return root;}
        Node cur = root;
        while(cur != null) {
            Node nextStartDum = new Node();
            Node curChild = nextStartDum;
            while (cur != null) {
                if (cur.left != null) {
                    curChild.next = cur.left;
                    curChild = curChild.next;
                }
                if (cur.right != null) {
                    curChild.next = cur.right;
                    curChild = curChild.next;
                }
                cur = cur.next;
            }
            cur = nextStartDum.next;
        }
        return root;
        
    }
}






// Review

/*Thought
M1 iterative way: for each layer, it connext the next pointer for its children layer. Each layer consists a LL, we can use a dummyhead to label the start of the child layer(used when child layer becomes current layer). And use the already built next pointer from current layer to move to the next one. For each node in the child layer, if it has left/right node then append to the layer tail and udpate tail.
time O(n) space O(1)
*/
class Solution {
    public Node connect(Node root) {
        if (root == null) {return root;}
        Node leftStart = root;
        
        while(leftStart != null) {
            Node dummyhead = new Node();
            Node curtail = dummyhead;
            Node cur = leftStart;
            while (cur != null) {
                if (cur.left != null) {curtail.next = cur.left; curtail = curtail.next;}
                if (cur.right != null) {curtail.next = cur.right; curtail = curtail.next;}
                cur = cur.next;
            }
            
            leftStart = dummyhead.next;
        }
        
        return root;
    }
}