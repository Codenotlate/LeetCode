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

// Phase3 self

class Solution {
    /* Initial thought
    Use BFS to label the level of each position, i.e. its depth. If the polled out NestedInteger is not an integer, we get the list of nestedIntegers inside and add back to queue again. Record the level * number for number when it's an integer.
    convert the formula for weightedSum: a1(D-d1+1) + a2(D-d2+1) +a3(D-d3+1) = (a1+a2+a3)*(D+1) -a1d1-a2d2-a3d3
    thus we need to record element sum and MaxDepth along the way
    time O(total sum of all elements' depth) space O(nestedList.size)?   
    */
    public int depthSumInverse(List<NestedInteger> nestedList) {
        int weightedSum = 0;
        int plainSum = 0;
        Queue<NestedInteger> queue = new LinkedList<>();
        queue.addAll(nestedList);
        int level = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                NestedInteger cur = queue.poll();
                if (cur.isInteger()) {
                    int curNum = cur.getInteger();
                    plainSum += curNum;
                    weightedSum += curNum * level;
                } else {
                    queue.addAll(cur.getList());
                }
            }
            level++;           
        }
        return level * plainSum - weightedSum;
    }
}


class Solution {
    // Try DFS too
    public int depthSumInverse(List<NestedInteger> nestedList) {
        int[] tracks = new int[3]; //tracks[0] = weightedSum, track[1] = plainSum, track[2] = maxDepth
        for (NestedInteger nest: nestedList) {
            dfs(nest, 1, tracks);
        }        
        return tracks[1] * (tracks[2] + 1) - tracks[0];
    }
    
    private void dfs(NestedInteger nest, int depth, int[] tracks) {
        if (nest.isInteger()) {
            tracks[0] += nest.getInteger() * depth;
            tracks[1] += nest.getInteger();
            tracks[2] = Math.max(tracks[2], depth);
            return;            
        }
        for (NestedInteger next: nest.getList()) {
            dfs(next, depth+1, tracks);
        }
    }
}




// Reveiw - can also try above BFS and DFS other implementation
/*Initial thought
a1*(maxD - d1 + 1) + a2*(maxD - d2 + 1) + a3*(maxD - d3 + 1) => (a1+a2+a3) *(maxD + 1) - (a1d1+a2d2+a3d3)
Use recursive functions to get element*depth sum for an nestedlist object. So the recursive function will keep track of three vars: plainSum, maxD, weightedSum.
*/
class Solution {
    public int depthSumInverse(List<NestedInteger> nestedList) {
        int[] res = new int[3];
        helper(nestedList, 0, res);
        int plainSum = res[0];
        int maxD = res[1];
        int weightedSum = res[2];
        return  plainSum * (maxD + 1) - weightedSum;
    }
    
    
    private void helper(List<NestedInteger> list, int depth, int[] res) {
        for (NestedInteger l: list) {
            if (l.isInteger()) {
                int num = l.getInteger();
                res[0] += num;
                res[1] = Math.max(res[1], depth);
                res[2] += num * depth;
            } else {
                helper(l.getList(), depth+1, res);
            }
            
        }
        
    }
}





// Review 23/2/24 - similar as above DFS way. Above BFS way can review next time.
// Below special test case is newly added. Thus above DFS methods can't handle this case. But above BFS way also handles this case.
/* Thoughts
result sum = (maxDepth+1)*listSum - (a1d1+a2d2+...andn)
first step: how to get maxDepth.
For each object in the nestedList, recursively get its depth. Actually we can get the maxDepth, listSum, depth*number all from the recursive call. The recursive call can return the element*depth sum, and at the same time record the current depth, listSum and maxDepth so far in its parameters.
time O(n) space O(depth) -> O(n)


Debug special case: [[1,1],2,[1,1],[[[[]]]]]
*/
class Solution {
    public int depthSumInverse(List<NestedInteger> nestedList) {
        int weightSum = 0;
        int[] maxDepthAndSum = new int[]{0, 0};
        for (NestedInteger ni: nestedList) {
            weightSum += depthSumRecur(ni, 1, maxDepthAndSum);
        }
        return (maxDepthAndSum[0] + 1)*maxDepthAndSum[1] - weightSum;
        
    }

    private int depthSumRecur(NestedInteger ni, int depth, int[] maxDepthAndSum) {
        // base case1: ni already an integer 
        if (ni.isInteger()) {
            maxDepthAndSum[0] = Math.max(maxDepthAndSum[0], depth);
            maxDepthAndSum[1] += ni.getInteger();
            return ni.getInteger()*depth;
        }
        // base case2: ni is an empty list
        // note this should be listed after base case1, as single interger also satisfies this case
        if (ni.getList().size() == 0) {
            maxDepthAndSum[0] = Math.max(maxDepthAndSum[0], depth);
            return 0;
        }
        // ni still a nested
        int weightSum = 0;
        for (NestedInteger nextni: ni.getList()) {
            weightSum += depthSumRecur(nextni, depth+1, maxDepthAndSum);
        }
        return weightSum;
    }
}


























