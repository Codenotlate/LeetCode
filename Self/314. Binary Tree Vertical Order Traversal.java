// M1: self time O(n) space O(n) (related to 987)
class Solution {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        if (root == null) {return res;}
        int idx = 0;
        // given the negative index, we can first store it in a map and then convert to list res
        Map<Integer, List<Integer>> map = new HashMap<>();
        int maxIdx = Integer.MIN_VALUE;
        int minIdx = Integer.MAX_VALUE;
        
        // given the order is from top to bottom and left to right, considering using BFS
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.add(new Pair(root, idx));
        while (!queue.isEmpty()) {
            Pair<TreeNode, Integer> curPair = queue.poll();
            int curIdx = curPair.getValue();
            TreeNode curNode = curPair.getKey();
            map.putIfAbsent(curIdx, new LinkedList<>());
            map.get(curIdx).add(curNode.val);
            minIdx = Math.min(minIdx, curIdx);
            maxIdx = Math.max(maxIdx, curIdx);
            if (curNode.left != null) {queue.add(new Pair(curNode.left, curIdx - 1));}
            if (curNode.right != null) {queue.add(new Pair(curNode.right, curIdx + 1));}   
        }
        
        for (int i = minIdx; i <= maxIdx; i++) {
            res.add(map.get(i));
        }
        
        return res;
    }
    
}


// Review self
class Solution {
    /* initial thought:
    use BFS, label col number for each node, when poll out from the queue, add to a map first with col num as key and List<node.val> as values. The use of BFS also guarantees the required order.
    time O(n) space O(n)
    
    */
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        if (root == null) {return res;}
        Map<Integer, List<Integer>> map = new HashMap<>();
        Queue<Pair<Integer, TreeNode>> queue = new LinkedList<>();
        queue.add(new Pair(0, root));
        
        int minCol = 101;
        int maxCol = -101;
        while (!queue.isEmpty()) {
            Pair<Integer, TreeNode> p = queue.poll();
            int colNum = p.getKey();
            minCol = Math.min(minCol, colNum);
            maxCol = Math.max(maxCol, colNum);
            TreeNode node = p.getValue();
            map.putIfAbsent(colNum, new LinkedList());
            map.get(colNum).add(node.val);
            if (node.left!= null) {queue.add(new Pair(colNum-1, node.left));}
            if (node.right!= null) {queue.add(new Pair(colNum+1, node.right));}
        }
        
        
        for (int col = minCol; col <= maxCol; col++) {
            res.add(map.get(col));
        }
        return res;
        
        
    }
}


//https://leetcode.com/problems/binary-tree-vertical-order-traversal/solution/
// method3 can also do a DFS way, sort by col then by row. But it's more troublesome. will skip it for now.








// Review

/*Initial thought
M1 way: DFS
notice for each node if it's at index p, then its left child is at p-1 and right child at p+1. We can use a map to store the list of integers in each position and also track minpos and maxpos along the way. [Wrong!!!: For each position, we can do preorder dfs so the order will be from top to bottom and left to right. !!!!This is wrong idea, e.g. for example3 in the description, we can't guarantee it's from top to bottom.] Thus for each node stored in the map list of each pos, we also need to store its row and col number. So that we it's added to res, we can sort them based on row and col.
time O(kllogl) space O(n) where k is the number of unique cols and l is the average len of the list at each col. Because we need to sort.
*/
class Solution {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        Map<Integer, List<int[]>> map = new HashMap<>();
        int[] idxRange = new int[2];
        dfs(root, 0, 0, map, idxRange);
        List<List<Integer>> res = new LinkedList<>();
        int i = idxRange[0];
        while(map.size() != 0 && i <= idxRange[1]) {
            List<int[]> list = map.get(i);
            Collections.sort(list, (l1,l2) -> (l1[1] == l2[1]? l1[2] - l2[2] : l1[1] - l2[1]));
            List<Integer> curlist = new LinkedList<>();
            for (int[] l: list) {curlist.add(l[0]);}
            res.add(curlist);
            i++;
        }
        return res;
    }
    
    
    private void dfs(TreeNode root,int row, int col, Map<Integer,List<int[]>> map, int[] idxRange) {
        if (root == null) {return;}
        map.putIfAbsent(col, new LinkedList<>());
        map.get(col).add(new int[]{root.val, row, col});
        idxRange[0] = Math.min(col, idxRange[0]);
        idxRange[1] = Math.max(col, idxRange[1]);
        dfs(root.left, row+1, col-1, map, idxRange);
        dfs(root.right, row+1, col+1, map, idxRange);
    }
}

/*
M2 way BFS:
similar as dfs, we can do col+1, col-1 for children node and store in a map, and track mincol and maxcol. The benefit of using BFS is that it guarantees we have the order at each col list as first from top to bottom and then from left to right.
time O(n) space O(n)
*/
class Solution {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        if (root == null) {return res;}
        Map<Integer, List<Integer>> map = new HashMap<>();
        int minIdx = 0;
        int maxIdx = 0;
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.add(new Pair(root, 0));
        
        while(!queue.isEmpty()) {
            Pair<TreeNode, Integer> cur = queue.poll();
            int col = cur.getValue();
            TreeNode node = cur.getKey();
            minIdx = Math.min(minIdx, col);
            maxIdx = Math.max(maxIdx, col);
            map.putIfAbsent(col, new LinkedList<>());
            map.get(col).add(node.val);
            if(node.left != null) {queue.add(new Pair(node.left, col-1));}
            if(node.right != null) {queue.add(new Pair(node.right, col+1));}
        } 
        
        for (int i = minIdx; i <= maxIdx; i++) {
            res.add(map.get(i));
        }
        
        return res;
    }
    
}







// Review 23/2/27 - basically the same as above ways
/*Thoughts
M1: DFS way. for each node, we label its index as left = root-1 and right = root + 1, and also its row number. Along the way, we store a map as label:List<int[]{num, rowNum}>. And also record down the min label. When adding number to the list, we need to sort the order w.r.t. its row number. After the traverse of the whole tree, we start with min label, and add all lists into final result.
Time O(w*hlogh) space O(n) where w/h is the width/height of the tree
M2: BFS way. similar as DFS, we assign label to each node, then when the node is poll out from the queue, we add it to the corresponding list in the map. We only need column label in BFS, because we have guaranteed the row number will be from top to bottom.
Time O(n)  space O(n)

 */
// M1: DFS way 
class Solution {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        Map<Integer, List<int[]>> map = new HashMap<>();
        int[] minCol = new int[]{0};
        searchHelper(root, 0, 0, map, minCol);
        List<List<Integer>> res = new ArrayList<>();
        int colNum = minCol[0];
        while(map.containsKey(colNum)) {
            Collections.sort(map.get(colNum), (p1, p2) -> (p1[1] - p2[1]));
            List<Integer> curList = new ArrayList<>();
            for (int[] p: map.get(colNum)) {curList.add(p[0]);}
            res.add(curList);
            colNum++;
        }
        return res;
    }

    private void searchHelper(TreeNode root, int curCol, int curRow, Map<Integer, List<int[]>> map, int[] minCol) {
        if (root == null) {return;}
        map.putIfAbsent(curCol, new ArrayList<int[]>());
        map.get(curCol).add(new int[]{root.val, curRow});
        minCol[0] = Math.min(minCol[0], curCol);
        searchHelper(root.left, curCol-1, curRow+1, map, minCol);
        searchHelper(root.right, curCol+1, curRow+1, map, minCol);
    }
}

// BFS way
class Solution {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<List<Integer>> res = new LinkedList<>();
        int minCol = 0;

        if (root == null) {return res;}
        queue.add(new Pair(root, 0));
        while(!queue.isEmpty()) {
            Pair<TreeNode, Integer> cur = queue.poll();
            TreeNode curNode = cur.getKey();
            int curCol = cur.getValue();
            minCol = Math.min(minCol, curCol);
            map.putIfAbsent(curCol, new ArrayList<>());
            map.get(curCol).add(curNode.val);
            //!!!!! don't typo curNode as root !!!
            if (curNode.left != null) {queue.add(new Pair(curNode.left, curCol-1));}
            if (curNode.right != null) {queue.add(new Pair(curNode.right, curCol+1));}
        }
        // put map into res
        while(map.containsKey(minCol)) {
            res.add(map.get(minCol));
            minCol++;
        }
        return res;
    }
}

