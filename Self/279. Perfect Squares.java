class Solution {
	// Method 1: DFS (Time Limit Exceeded)
	// time? space O(1)
    public int numSquares(int n) {
        // base case
        if (n < 4) {return n;}

        int k = (int) Math.sqrt(n);
        int minNum = n;
        for (int i = 1; i <= k; i++) {
        	minNum = Math.min(minNum, numSquares(n - i * i));
        }
        return minNum + 1;
    }
}

class Solution {
	// method2: based on dfs, using DP to avoid repetitive work
	// f[i] represents the min num result for n = i
	// Time O(n*sqrt(n)) space O(n)
    public int numSquares(int n) {
        int[] f = new int[n + 1];
        f[1] = 1;
        int i = 2;
        while (i <= n) {
        	f[i] = i;
        	int k = 1;
        	while (i - k * k >= 0) {
        		f[i] = Math.min(f[i], f[i - k * k] + 1);
        		k++;
        	}
        	i++;
        }
        return f[n];
    }
}
// explain for time complexity O(nsqrt(n))
// https://leetcode.com/problems/perfect-squares/discuss/848256/Java-DP-O(nlog-n)-solution-Time-Complexity-Explained
// method 2.1 use idea similar to knapsack problems
class Solution {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        // since we are going to get min value, it's important to initialize with infinite positive
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        int k = 1;
        while (k * k <= n) {
            for (int j = k * k; j <= n; j++) {
                dp[j] = Math.min(dp[j], dp[j - k * k] + 1);
            }
            k++;
        }
        return dp[n];
    }
}




class Solution {
	// method3: BFS. view every n as a node, when 2 nodes diff is a perfect squares,
	// there's an edge between these two nodes. 
	// Then problem reduces to find the shortest path from node n to node 0.
    public int numSquares(int n) {
        List<Integer> squares = findPerfectSquares(n);
        Queue<Integer> queue = new LinkedList<>();
        int[] visited = new int[n + 1];
        // add the first node into queue
        queue.add(n);
        visited[n] = 1;
        int res = 0;

        while (!queue.isEmpty()) {
        	int size = queue.size();
        	while (size > 0) {
        		int cur = queue.poll();
        		for (int square: squares) {
        			int next = cur - square;
        			if (next < 0) {
        				continue;
        			} else if (next == 0) {
        				return res + 1;
        			} else {
        				if (visited[next] == 0) {
        					queue.add(next);
        					visited[next] = 1;
        				}
        			}
        		}
        		size--;
        	}
        	res++;
        }
        // should not reach this line
        return -1;


    }

    // return the array of squares that <= n.
    private List<Integer> findPerfectSquares(int n) {
    	List<Integer> squares = new LinkedList<>();
    	int i = 0;
    	int diff = 1;
    	while (i + diff <= n) {
    		i += diff;
    		squares.add(i);
    		diff += 2;
    	}
    	return squares;
    }
}






// Phase3 self M2 above
// time O(n * sqrt(n)) space O(n)
class Solution {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, 10001);
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j*j <= i; j++) {
                if (j * j == i) {dp[i] = 1;}
                else {
                    dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
                }
            }
        }
        return dp[n];
    }
}


// review self
class Solution {
    // using dp to track dp[1] to dp[n]
    // dp[i] = 1 + Math.min(dp[i - j * j] where 1 <=j * j <= i), init dp[0] = 1 since dp[i] = 0 when i = j * j
    // time O(n*sqrt(n)) space (n)
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, n + 1);
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            int j = 1;
            while (j * j <= i) {
                dp[i] = Math.min(dp[i], 1 + dp[i - j*j]);
                j++;
            }
        }
        return dp[n];
    }
}
//https://leetcode.com/problems/perfect-squares/solution/
//5 kinds of solutions



//https://leetcode.com/problems/perfect-squares/discuss/71488/Summary-of-4-different-solutions-(BFS-DP-static-DP-and-mathematics)
// BFS Java way in first comment
class Solution {
    // try BFS way: one edge between two numbers with diff = perfect square numbers
    // time O(sqrt(n)^h) space O(sqrt(n)^ h)
    // start with 0, and build up the graph, if meet n, return current path len
    public int numSquares(int n) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.add(0);
        visited.add(0);
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                int j = 1;
                while (j * j + cur <= n) {
                    if (j * j + cur == n) {return level+1;}
                    if (!visited.contains(cur +j * j)) {queue.add(cur + j * j); visited.add(cur+j*j);}        
                    j++;
                }
            }
            level++;            
        }
        return -1;
    }
}
// This BFS starts at 0 to reach n, above M3 BFS starts at n to reach 0.




// Review
/*Initial thought
DP. for i = k * k, dp[i] = 1. for other i, dp[i] = min(dp(i-k*k)) + 1
dp(12) = min(dp(12-9), dp(12-4), dp(12-1)) + 1
time O(n* sqrt(n)) space O(n)

Another way hint from solution, view this as an n-nary tree, where the edge between each node is the perfect square number diff between the two nodes number. Then we use BFS to find the shortest path from node n to node 0. There are (sqrt(n)) nodes each layer and the height if the shortest height required to get n. Thus time O(sqrt(n) * h) space O(h)
*/

// way1: DP
class Solution {
    public int numSquares(int n) {
        int[] dp =new int[n+1];
        Arrays.fill(dp, n+1);
        dp[0] = 0;
        for (int i =1; i <= n; i++) {
            int k = 1;
            while (k * k <= i) {
                dp[i] = Math.min(dp[i], 1 + dp[i - k * k]);
                k++;
            }
        }
        return dp[n];
    }
}

// way2: n-nary tree, two ways, can start from node 0 to reach node n, or start with node n and reach node 0. Here choose the second way.
class Solution {
    public int numSquares(int n) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> set = new HashSet<>();
        queue.add(n);
        set.add(n);
        int level = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            while(size-->0) {
                int cur = queue.poll();
                if (cur == 0) {return level;}
                int k = 1;
                while(k * k <= cur) {
                    int next = cur - k * k;
                    if (set.contains(next)) {k++;continue;}
                    queue.add(next);
                    set.add(next);
                    k++;
                }
            }
            level++;
        }
        return -1; // should not reach this line
    }
}




// Review 23/1/30 - 4 summarized ways from above
/*Thoughts
M1: DP way(bottom-up): dp[i] = i is squareNum? 1 : min(dp[i-squareNum]) + 1
check whether is squareNum takes O(sqrt(n)) time, get all the squareNum < i also take O(sqrt(i)) time.
Thus overall time O(n * sqrt(n)) space O(n)

M2: similarly recursive + memo way(top-down, can also do top-down dp way): time O(n * sqrt(n)) space O(n)

M3: view this as a n-nary tree, when diff is a squareNum, the two numbers have a edge connecting them. Thus the issue is to find the shortest path between 0 and n.
If we view it as find the path from 0 to n, then it's bottom-up. If from n to 0, then it's top-down.

All 4 ways share same complexity.
*/

// M1: bottom-up dp
class Solution {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, 10001);
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }
}
// M2: top-down recursion + memo
class Solution {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        return squareHelper(n, dp);
    }

    private int squareHelper(int n, int[] dp) {
        if (n == 0) {return 0;}
        if (dp[n] != 0) {return dp[n];}
        int res = Integer.MAX_VALUE;
        for (int i = 1; i * i <= n; i++) {
            res = Math.min(squareHelper(n-i*i, dp)+1, res);
        }
        dp[n] = res;
        return res;
    }
}
// M3: bottom-up/top-down b-nary tree, here choose bottom-up
class Solution {
    public int numSquares(int n) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.add(0);
        visited.add(0);

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                int j = 1;               
                while(cur + j * j <= n) {
                    if (cur + j * j == n) {return level + 1;}
                    if (visited.contains(cur + j * j)) {j++;continue;}
                    queue.add(cur + j * j);
                    visited.add(cur + j * j);
                    j++;
                }
            }
            level++;
        }
        return -1; // should not reach this row
    }
}











