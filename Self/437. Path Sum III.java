/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    // inefficient way: dfs within dfs
    // the path sum is calculated repetitively
    // Time O(nlogn) balanced case and O(n^2) skewed case

    public int pathSum(TreeNode root, int sum) {
    	// base case 
    	if (root == null) {return 0;}
    	int num = pathSumWithRoot(root, sum) + pathSum(root.left, sum) + pathSum(root.right, sum);
    	return num;   
    }

    private int pathSumWithRoot(TreeNode root, int sum) {
    	if (root == null) {return 0;}
    	int num = 0;
    	if (root.val == sum) {num += 1;}
    	num += pathSumWithRoot(root.left, sum - root.val) + pathSumWithRoot(root.right, sum - root.val);
    	return num;
    }


}


class Solution {
    // better way: using hashmap to store path sum and the # of paths having that sum
    // and avoid unnecessary repetitive path sum calculation
    // Time O(n) since each node is visited only once
    // key idea is if sum(root to some middle node) = sum(root to cur node) - target sum
    // then sum(middle root to cur node) = target sum

    public int pathSum(TreeNode root, int sum) {
    	// define the hashMap
    	HashMap<Integer, Integer> preSumMap = new HashMap<>();
    	//put root info inside the map
    	preSumMap.put(0, 1);
    	return helper(root, 0, sum, preSumMap);
    }

    private int helper(TreeNode cur, int prefixSum, int targetSum, HashMap<Integer, Integer> map) {
    	if (cur == null) {return 0;}
    	int curSum = prefixSum + cur.val;
    	int resNum = map.getOrDefault(curSum - targetSum, 0);
    	// include info from cur node and pass to its children
    	map.put(curSum, map.getOrDefault(curSum, 0) + 1);
    	resNum += helper(cur.left, curSum, targetSum, map) + helper(cur.right, curSum, targetSum, map);
    	// backtracking: eliminate the impact from cur
    	map.put(curSum, map.getOrDefault(curSum, 0) - 1);
    	return resNum;
    }

}



// phase 2 self:
class Solution {
    public int pathSum(TreeNode root, int sum) {
        int[] res = new int[1];
        HashMap<Integer, Integer> pathSumMap = new HashMap<>();
        // don't forget this one line
        pathSumMap.put(0,1);
        dfs(root, sum, 0, pathSumMap, res);
        return res[0];
    }
    
    private void dfs(TreeNode root, int sum, int cumSum, HashMap<Integer, Integer> pathSumMap, int[] res) {
        if (root == null) {return;}
        cumSum += root.val;
        if (pathSumMap.keySet().contains(cumSum - sum)) {
            res[0] += pathSumMap.get(cumSum - sum);
        }
        
        pathSumMap.put(cumSum, pathSumMap.getOrDefault(cumSum, 0) + 1);
        dfs(root.left, sum, cumSum, pathSumMap, res);
        dfs(root.right, sum, cumSum, pathSumMap, res);
        pathSumMap.put(cumSum, pathSumMap.get(cumSum) - 1);
    }
}

// phase3 solution

class Solution {
    // note: set is not good enough here because you may have multiple path with the same pathSum in one dfs.
    public int pathSum(TreeNode root, int targetSum) {
        int[] count = new int[1];
        Map<Integer, Integer> pathSumMap = new HashMap<>();
        pathSumMap.put(0,1); // don't forget this line
        dfs(root, targetSum, pathSumMap, 0, count);
        return count[0];
    }
    
    private void dfs(TreeNode root, int target, Map<Integer, Integer> sumMap, int cumSum, int[] res) {
        if (root == null) {return;}
        cumSum += root.val;
        // note we will add all number of paths with that pathSum
        res[0] += sumMap.getOrDefault(cumSum - target, 0);
        sumMap.put(cumSum, sumMap.getOrDefault(cumSum, 0) + 1);
        dfs(root.left, target, sumMap, cumSum, res);
        dfs(root.right, target, sumMap, cumSum, res);
        // backtracking
        sumMap.put(cumSum, sumMap.getOrDefault(cumSum, 0) - 1);
    }
}



// review
/*Initial thought
dfs + backtracking + accumulated path sum. for each path, we keep the accum path sum in a map with count, and if current sum - targetSum is inside the map keyset, then res+=count. And we need to backtrack that sum from the map when move to another child node, cause here the path is required to be only from parent to its children.
time O(n) space O(h)
*/
class Solution {
    public int pathSum(TreeNode root, int targetSum) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1); // don't forget this line
        return getSum(root, map, 0, targetSum);
    }
    
    private int getSum(TreeNode root, Map<Integer,Integer> map, int curSum, int targetSum) {
        if (root == null) {return 0;}
        int res = 0;
        if (map.containsKey(curSum + root.val - targetSum)) {
            res+=map.get(curSum + root.val - targetSum);
        }
        map.put(curSum + root.val, map.getOrDefault(curSum + root.val, 0)+1);
        res += getSum(root.left, map, curSum + root.val, targetSum);
        res += getSum(root.right, map, curSum + root.val, targetSum);
        map.put(curSum + root.val, map.get(curSum + root.val) - 1);
        return res;
    }
}


















