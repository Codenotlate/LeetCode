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























