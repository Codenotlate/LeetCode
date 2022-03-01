/* Initial thought
Use inorder traverse, init a dummy head and curtail to represent the curtail of the doublyLL.
time O(n)
space O(n) for regular inorder using stack or recursive inroder. O(1) for morris traverse.
*/

// regular iterative inorder
class Solution {
    public Node treeToDoublyList(Node root) {
        // don't forget this line
        if(root == null) {return root;}
        Node dummy = new Node(-1);
        Node curTail = dummy;
        Stack<Node> stack = new Stack<>();
        Node cur = root;
        while (cur!= null || !stack.isEmpty()) {
            while(cur!= null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            curTail.right = cur;
            cur.left = curTail;
            curTail = curTail.right;
            cur = cur.right;
        }
        
        dummy.right.left = curTail;
        curTail.right = dummy.right;
        return dummy.right;
    }
}

// morris inorder: for each cur node, find the rightmost child in the left subtree and its right pointer pointes to cur node.
class Solution {
    public Node treeToDoublyList(Node root) {
        if(root == null) {return root;}
        Node dummy = new Node(-1);
        Node curTail = dummy;
        Node cur = root;
        while(cur != null) {
            if (cur.left != null) {
                Node rightmost = cur.left;
                while(rightmost.right != null && rightmost != cur) {rightmost = rightmost.right;}
                if (rightmost != cur) {rightmost.right = cur; cur = cur.left;}
                else {
                    curTail.right = cur;
                    cur.left = curTail; 
                    curTail = cur; 
                    cur = cur.right;
                }          
            } else {
                curTail.right = cur;
                cur.left = curTail; 
                curTail = cur; 
                cur = cur.right;
            }
        }
        
        dummy.right.left = curTail;
        curTail.right = dummy.right;        
        return dummy.right;
    }
}



