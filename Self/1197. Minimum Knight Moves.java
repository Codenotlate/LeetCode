// good solution summary
// https://leetcode.com/problems/minimum-knight-moves/discuss/947138/Python-3-or-BFS-DFS-Math-or-Explanation
// M3: DP / DFS + memo
class Solution {
    /* from solution3: DFS + memo / DP
    also utilize symmetry, only calculate for abs(x) and abs(y).
    And since we want to go to (x,y) in min steps, if we look from (x,y) backwards, each time the direction can be (-2,-1) or(-1,-2).
    Then another key obeservation is base case near (0,0). notice for (x+y == 2), min steps = 2.(detail see solution article)
    */
    public int minKnightMoves(int x, int y) {
        Map<Pair<Integer,Integer>, Integer> memo = new HashMap<>();
        return dfs(Math.abs(x), Math.abs(y), memo);
    }
    
    private int dfs(int x, int y,  Map<Pair<Integer,Integer>, Integer> memo) {
        if (x + y == 0) {return 0;}
        if (x + y == 2) {return 2;}
        Pair<Integer,Integer> key = new Pair(x, y); 
        if (memo.containsKey(key)) {return memo.get(key);}
        int res = Math.min(dfs(Math.abs(x -2), Math.abs(y-1), memo), dfs(Math.abs(x-1), Math.abs(y-2), memo)) + 1;
        memo.put(key, res);
        return res;
    }
}





// M2: based on below traditional M1, but with pruning
class Solution {
    /* traditional BFS can be optimized by pruning some options, notice the symmetrics, thus (+/-x,+/-y) should be the same as (|x|, |y|). And since we only consider x >= 0 and y>=0, for each (x, y) we realize for next position choice, if outside the range:  -1 <=i <= x+2,  -1<= j <= y+2.  Then (i,j) won't be a good next position choice, cause it makes it further from (x,y) and it's unnecessary.
    */
    public int minKnightMoves(int x, int y) {
        if (x == 0 && y== 0) {return 0;} // remember to deal with special case
        Set<Pair<Integer, Integer>> startSet = new HashSet<>();
        Set<Pair<Integer, Integer>> endSet = new HashSet<>();
        x = Math.abs(x);
        y = Math.abs(y);
        startSet.add(new Pair(0,0));
        endSet.add(new Pair(x, y));
        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        visited.add(new Pair(0,0));
        
        int[][] dirs = new int[][]{{1,2},{2,1},{2,-1},{1,-2},{-1,-2},{-2,-1},{-2,1},{-1,2}};
        
        int level = 0;
        while (!startSet.isEmpty()) {
            // always make search the smaller set
            if (startSet.size() > endSet.size()) {
                Set<Pair<Integer,Integer>> temp = startSet;
                startSet = endSet;
                endSet = temp;
            }
            // start BFS
            Set<Pair<Integer, Integer>> nextSet = new HashSet<>();
            for (Pair<Integer,Integer> cell: startSet) {
                for(int[] d: dirs) {
                    int nextx = cell.getKey() + d[0];
                    int nexty = cell.getValue()+d[1];
                    Pair<Integer,Integer> next = new Pair(nextx, nexty);
                    if (endSet.contains(next)) {return level + 1;}
                    if (!visited.contains(next) && nextx >= -1 && nextx <= x+2 && nexty >= -1 && nexty <= y+2) {nextSet.add(next); visited.add(next);}
                }
                
            }
            startSet = nextSet;
            level++;            
        }
        return -1; // should not reach this line
    }
}

// comment in this post discussed the expected solution in an interview. (if you come up with bfs with pruning that would be sufficient for the interview.)
// https://leetcode.com/problems/minimum-knight-moves/discuss/392053/Here-is-how-I-get-the-formula-(with-graphs)



// M1: traditional bidirectional BFS using set
// Tried similar directional BFS like 127, but the time req for this problem in leetcode is very strict, the result is good, all 45 tests passed but TLE.
// Though the total time complexity is the area of the circle: time and space both O(max(x,y)^2)

/* From solution M2:
Note: in theory, the above implementation of bidirectional BFS should be faster than the unidirectional BFS. However, in reality, this is not the case for the Java implementation, due to heavy usage of sophisticated data structures, which are inefficient compared to simple arrays.

In addition to the bidirectional exploration optimization, there is also a technique called pruning that has been mentioned in some posts.

Pruning means to remove the unwanted parts, and that is exactly what this technique does. It focuses only on the directions that might eventually lead to the discovery of the target while ignoring other directions thereby reducing our total search space.

Indeed, it can improve our performance. However, it can be tricky to ensure the correctness of the algorithm. If we accidentally prune a valid branch, we might fail to obtain the correct answer in the end. Under the circumstance of passing an interview in a short time frame, one must weigh the risks associated with prunning against the potential gain.
*/
class Solution {
    /* Initial thought
    Using BFS, each node has 8 neighbors, return level number when reach target cell.Can use bidirectional BFS to reduce search range.
    */
    public int minKnightMoves(int x, int y) {
        if (x == 0 && y== 0) {return 0;} // remember to deal with special case
        Set<Pair<Integer, Integer>> startSet = new HashSet<>();
        Set<Pair<Integer, Integer>> endSet = new HashSet<>();
        startSet.add(new Pair(0,0));
        endSet.add(new Pair(x, y));
        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        visited.add(new Pair(0,0));
        
        int[][] dirs = new int[][]{{1,2},{2,1},{2,-1},{1,-2},{-1,-2},{-2,-1},{-2,1},{-1,2}};
        
        int level = 0;
        while (!startSet.isEmpty()) {
            // always make search the smaller set
            if (startSet.size() > endSet.size()) {
                Set<Pair<Integer,Integer>> temp = startSet;
                startSet = endSet;
                endSet = temp;
            }
            // start BFS
            Set<Pair<Integer, Integer>> nextSet = new HashSet<>();
            for (Pair<Integer,Integer> cell: startSet) {
                for(int[] d: dirs) {
                    Pair<Integer,Integer> next = new Pair(cell.getKey() + d[0], cell.getValue()+d[1]);
                    // note this line should be put here instead of the cur level check
                    if (endSet.contains(next)) {return level + 1;}
                    if (!visited.contains(next)) {nextSet.add(next); visited.add(next);}
                }
                
            }
            startSet = nextSet;
            level++;            
        }
        return -1; // should not reach this line
    }
}