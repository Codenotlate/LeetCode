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
