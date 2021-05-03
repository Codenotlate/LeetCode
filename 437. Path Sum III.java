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





















