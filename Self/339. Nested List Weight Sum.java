/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *     // Constructor initializes an empty nested list.
 *     public NestedInteger();
 *
 *     // Constructor initializes a single integer.
 *     public NestedInteger(int value);
 *
 *     //      @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // Set this NestedInteger to hold a single integer.
 *     public void setInteger(int value);
 *
 *     // Set this NestedInteger to hold a nested list and adds a nested integer to it.
 *     public void add(NestedInteger ni);
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return empty list if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
class Solution {
    // phase3 self
    // M1 : dfs way
    public int depthSum(List<NestedInteger> nestedList) {
        return dfs(nestedList, 1);
    }
    
    private int dfs(List<NestedInteger> list, int level) {
        int sum = 0;
        for (NestedInteger nest: list) {
            if (nest.isInteger()) {
                sum += nest.getInteger() * level;
            } else {
                sum += dfs(nest.getList(), level + 1);
            }
        }
        return sum;
    }
}


class Solution {
    // phase3 self
    // M2 : BFS way
    public int depthSum(List<NestedInteger> nestedList) {
        int level = 1;
        int sum = 0;
        Queue<NestedInteger> queue = new LinkedList<>();
        queue.addAll(nestedList);
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                NestedInteger curNest = queue.poll();
                if (curNest.isInteger()) {
                    sum += curNest.getInteger() * level;
                } else {
                    queue.addAll(curNest.getList());
                }
            }
            level++;
        }
        return sum;
        
    }
    
    
}



// Review self
class Solution {
    /*initial thought
    BFS all nestedInteger, add to weighted sum when it's an integer, otherwise put all nestedInteger back to the queue
    time O(sum of depth) space O(max # for elements in same depth)   
    */
    public int depthSum(List<NestedInteger> nestedList) {
        int sum = 0;
        Queue<NestedInteger> queue = new LinkedList<>();
        queue.addAll(nestedList);
        int level = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                NestedInteger cur = queue.poll();
                if (cur.isInteger()) {
                    sum += cur.getInteger() * level;
                } else {
                    queue.addAll(cur.getList());
                }
            }
            level++;
        }
        return sum;
    }
}


class Solution {
    // try DFS way
    public int depthSum(List<NestedInteger> nestedList) {
        int sum = 0;
        for (NestedInteger nest: nestedList) {
            sum += dfs(nest, 1);
        }
        return sum;        
    }
    
    private int dfs(NestedInteger nest, int level) {
        if (nest.isInteger()) {return nest.getInteger() * level;}
        int sum = 0;
        for (NestedInteger next: nest.getList()) {
            sum += dfs(next, level+1);
        }
        return sum;
    }
}
