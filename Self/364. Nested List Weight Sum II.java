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
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
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
    public int depthSumInverse(List<NestedInteger> nestedList) {
        int maxD = 0;
        int plainSum = 0;
        int[] track = new int[]{maxD, plainSum};
        int dSum = dfs(nestedList, 1, track);
        return (track[0] + 1) * track[1] - dSum;
    }
    
    private int dfs(List<NestedInteger> list, int level, int[] track) {
        int sum = 0;
        track[0] = Math.max(track[0], level);
        for (NestedInteger nest: list) {
            if (nest.isInteger()) {
                sum += nest.getInteger() * level;
                track[1] += nest.getInteger();
            } else {
                sum += dfs(nest.getList(), level + 1, track);
            }
        }
        return sum;
    }
}

class Solution {
    public int depthSumInverse(List<NestedInteger> nestedList) {
        int level = 1;
        int dsum = 0;
        int plainSum = 0;
        Queue<NestedInteger> queue = new LinkedList<>();
        queue.addAll(nestedList);
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                NestedInteger curNest = queue.poll();
                if (curNest.isInteger()) {
                    dsum += curNest.getInteger() * level;
                    plainSum += curNest.getInteger();
                } else {
                    queue.addAll(curNest.getList());
                }
            }
            level++;
        }
        return level * plainSum - dsum;
    }
}