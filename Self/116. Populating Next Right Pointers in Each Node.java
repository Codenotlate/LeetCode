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



// M3 iterative way: actually similar idea as BFS by process each level at one time, but using parent.next instead of queue.
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



// Phase3 self
// initial idea: can use BFS, on each level, record prevpoll node, prevpoll.next = curpoll; prevpoll = curpoll;
// followup require constant space, and assuming implicit slack space caused by recursion doesn't count. Thus we can use recursion (dfs)

class Solution {
    // M4: try BFS way first, time O(n), space O(n)
    public Node connect(Node root) {
        if (root == null) {return root;}
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            Node prev = null;
            while (size-- > 0) {
                Node cur = queue.poll();
                if (prev != null) {
                    prev.next = cur;
                }
                prev = cur;
                if (cur.left != null) {queue.add(cur.left);}
                if (cur.right != null) {queue.add(cur.right);}
            }
        }
        return root;
    }
}

// same as above M2
class Solution {
    // M2: try dfs: utilize the next pointer set for parent level
    public Node connect(Node root) {
        if (root == null || root.left == null) {return root;}
        root.left.next = root.right;
        if (root.next != null) {root.right.next = root.next.left;}
        connect(root.left);
        connect(root.right);
        return root;
    }
}

class Solution {
    // M5: try iterative way, similar as above M3: the key is to find the leftmost node of each level and iterate each level in order with the help of next pointer.
    public Node connect(Node root) {
        if (root == null || root.left == null) {return root;}
        Node leftstart = root;
        while(leftstart.left!= null) {
            Node cur = leftstart;
            while(cur != null) {
                cur.left.next = cur.right;
                if(cur.next!= null) {cur.right.next = cur.next.left;}
                cur = cur.next;
            }
            leftstart = leftstart.left;
        }
        return root;
    }
}




// Review
/*Initial thought
M1 way: iterative way. loop nodes level by level, for each cur level check its leftmost child, and use it for next level iteration. In current level, iterate till curnode == null by curnode = curnode.next. And for each curnode, connect its left child and right child and update child level tail node to the right child. Move on to next curnode in the cur level. This way has time O(n) space O(1)
M2 way: recursive way. similar as iterative way, in the cur level, we connect cur.left.next = cur.right, and also cur.right.next = cur.next.left.


*/
// M1 iterative way
class Solution {
    public Node connect(Node root) {
        if(root == null) {return root;}
        Node curHead = root;
        while(curHead.left != null) {
            Node nextHead = curHead.left;
            while(curHead != null) {
                curHead.left.next = curHead.right;
                if(curHead.next != null) {curHead.right.next = curHead.next.left;}
                curHead = curHead.next;
            }
            curHead = nextHead;
        }
        return root;
    }
}
// M2 recursive way
class Solution {
    public Node connect(Node root) {
        if(root == null) {return root;}
        if(root.left != null) {
            root.left.next = root.right;
            if (root.next != null) {root.right.next = root.next.left;}
        }
        root.left = connect(root.left);
        root.right = connect(root.right);
        return root;
    }
}








