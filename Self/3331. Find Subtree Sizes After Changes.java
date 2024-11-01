// 2024.10.27
// self M1: build the newparent by sourcing back for each node using parent array. Then build a tree based on the new parent, and countSubTree based on tree structure.
// issue: build the newparent from child node, this contains repetitive work and can be O(n^2) in worst case. Thus optimiza this in M2: building the tree directly, and do dfs to adjust the parent of those nodes.
//M1
class Solution {
    private class TreeNode {
        char val;
        int index;
        List<TreeNode> children;
        
        public TreeNode(char val, int index) {
            this.val = val;
            this.index = index;
            this.children = new LinkedList<>();
        }
    }
    
    private TreeNode buildTree(int[] parent, String s) {
        int n = parent.length;
        TreeNode[] nodes = new TreeNode[n];
        for (int i = 0; i < n; i++) {
            if (nodes[i]==null) {
                nodes[i] = new TreeNode(s.charAt(i), i);
            }
            if (parent[i] != -1) {
                if (nodes[parent[i]] == null) {
                    nodes[parent[i]] = new TreeNode(s.charAt(parent[i]), parent[i]);
                }
                nodes[parent[i]].children.add(nodes[i]);
            }       
        }
        return nodes[0];
    }
    
    private void countSubTree(TreeNode node, int[] res) {
        if (node == null) {return;}
        int count = 1;
        for (TreeNode child: node.children) {
            countSubTree(child, res);
            count += res[child.index];
        }
        res[node.index] = count;        
    }
    
    public int[] findSubtreeSizes(int[] parent, String s) {
        int[] newparent = parent.clone();
        for (int i = 0; i < newparent.length; i++) {
            int cur = i;
            while (parent[cur] != -1 && s.charAt(i) != s.charAt(parent[cur])) {
                cur = parent[cur];
            }
            if (parent[cur] != -1 && s.charAt(i) == s.charAt(parent[cur])) {
                newparent[i] = parent[cur];
            }
        }
        int[] res = new int[parent.length];
        countSubTree(buildTree(newparent, s), res);
        return res;
        
        
    }
}

// M2
class Solution {
    private class TreeNode {
        char val;
        int index;
        List<TreeNode> children;
        
        public TreeNode(char val, int index) {
            this.val = val;
            this.index = index;
            this.children = new LinkedList<>();
        }
    }
    
    private TreeNode buildTree(int[] parent, String s) {
        int n = parent.length;
        TreeNode[] nodes = new TreeNode[n];
        for (int i = 0; i < n; i++) {
            if (nodes[i]==null) {
                nodes[i] = new TreeNode(s.charAt(i), i);
            }
            if (parent[i] != -1) {
                if (nodes[parent[i]] == null) {
                    nodes[parent[i]] = new TreeNode(s.charAt(parent[i]), parent[i]);
                }
                nodes[parent[i]].children.add(nodes[i]);
            }       
        }
        return nodes[0];
    }
    
    private void countSubTree(TreeNode node, int[] res) {
        if (node == null) {return;}
        int count = 1;
        for (TreeNode child: node.children) {
            countSubTree(child, res);
            count += res[child.index];
        }
        res[node.index] = count;        
    }

    // DFS + backtracking
    private void adjustParent(TreeNode cur, TreeNode[] path, int[] newparent) {
        if (cur == null) {return;}
        TreeNode prev = path[cur.val-'a'];
        if (prev != null) {
            newparent[cur.index] = prev.index;
        } 
        path[cur.val-'a'] = cur;
        for (TreeNode child: cur.children) {
            adjustParent(child, path, newparent);
        }
        path[cur.val-'a'] = prev; // backtracking, remove the effect from current level
    }
    
    // don't need to worry about changing in place, cause since we are adjusting parent to the closest same char one, and we start with lower level node, the result won't be impacted.
    public int[] findSubtreeSizes(int[] parent, String s) {
        TreeNode root = buildTree(parent, s);
        int[] newparent = parent.clone(); // still need it cause we can't traverse the tree and at the same time changing the parent.
        // DFS to adjust parent
        TreeNode[] path = new TreeNode[26];
        adjustParent(root, path, newparent);
        int[] res = new int[parent.length];
        countSubTree(buildTree(newparent, s), res);
        return res;
        
        
    }
}