class Solution {
	// DFS: recursive way
	// time O(n^2) space O(n)
    public int findCircleNum(int[][] isConnected) {
        int count = 0;
        int n = isConnected.length;
        int[] visited = new int[n];

        for (int i = 0; i < n; i++) {
        	if (visited[i] == 0) {
        		dfs(isConnected, visited, i);
        		count++;
        	}
        }
        return count;
    }

    private void dfs(int[][] isConnected, int[] visited, int i) {
    	visited[i] = 1;
    	for (int j = 0; j < isConnected.length; j++) {
    		if (isConnected[i][j] == 1 && visited[j] != 1) {
    			dfs(isConnected, visited, j);
    		}
    	}
    }
}


class Solution {
	// DFS; iterative way
    public int findCircleNum(int[][] isConnected) {
        int count = 0;
        int n = isConnected.length;
        Stack<Integer> stack = new Stack<>();
        int[] visited = new int[n];

        for (int i = 0; i < n; i++) {
        	if (visited[i] != 1) {
        		stack.push(i);
		        visited[i] = 1;

		        while (!stack.isEmpty()) {
		        	int cur = stack.pop();
		        	for (int j = 0; j < n; j++) {
		        		if (isConnected[cur][j] == 1 && visited[j] != 1) {
		        			stack.push(j);
		        			visited[j] = 1;
		        		}
		        	}

        		}
        		count++;
        	}
        }
        return count;
    }
}


// method 3: union find
// time O(n^2) space O(n)
class Solution {
	private class UnionFind {
		private int count = 0;
		private int[] rank, parent;
		
		public UnionFind(int n) {
			count = n;
			rank = new int[n];
			parent = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = i;
			}
		}

		public int find(int p) {
			while (parent[p] != p) { // path compression, making find O(1)
				parent[p] = parent[parent[p]];
				p = parent[p];
			}
			return p;
		}

		public void union(int p, int q) {
			int rootP = find(p);
			int rootQ = find(q);
			if (rootQ == rootP) return;

			if (rank[rootP] > rank[rootQ]) {
				parent[rootQ] = rootP;
			} else if (rank[rootP] < rank[rootQ]) {
				parent[rootP] = rootQ;
			} else {
				parent[rootP] = rootQ;
				rank[rootQ]++;
			}
			count--;
		}

		public int count() {
			return count;
		}
	}



    public int findCircleNum(int[][] isConnected) {
    	UnionFind uf = new UnionFind(isConnected.length);
        for (int i = 0; i < isConnected.length; i++) {
        	for (int j = i + 1; j < isConnected.length; j++) {
        		if (isConnected[i][j] == 1) {
        			uf.union(i, j);
        		}
        	}
        }
        return uf.count();
    }
}




// Phase2 self DFS iterative way
class Solution {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        int count = 0;
        int[] visited = new int[n];
        for (int i = 0; i < n; i++) {
            if (visited[i] == 0) {
                dfs(isConnected, visited, i);
                count++;
            }
        }
        return count;
    }
    
    private void dfs(int[][] isConnected, int[] visited, int i) {
        Stack<Integer> stack = new Stack<>();
        stack.push(i);
        visited[i] = 1;
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            for (int j = 0; j < isConnected.length; j++) {
                if (cur == j) {continue;}
                if (isConnected[cur][j] == 1 && visited[j] == 0) {
                    stack.push(j);
                    visited[j] = 1;
                }
            }
        }
    }
}





// Phase 3 self
// M1: DFS/BFS to traverse
class Solution {
    // BFS or DFS to traverse each group and count the time of DFS/BFS until all nodes are visited
    public int findCircleNum(int[][] isConnected) {
        int[] visited = new int[isConnected.length];
        int count = 0;
        for (int i = 0; i < isConnected.length; i++) {
            if (visited[i] == 0) {
                count++;
                traverse(isConnected, i, visited);
            }
        }
        return count;
    }
    
    private void traverseDFS(int[][] isConnected, int i, int[] visited) {
        visited[i] = 1;
        for (int j = 0; j < isConnected.length; j++) {
            if (isConnected[i][j] == 1 && visited[j] == 0) {
                traverse(isConnected, j, visited);
            }
        }
    }

    private void traverseBFS(int[][] isConnected, int i, int[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        int n = isConnected.length;
        queue.add(i);
        visited[i] = 1;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int j = 0; j < n; j++) {
                if (isConnected[cur][j] == 1 && visited[j] != 1) {
                    queue.add(j);
                    visited[j] = 1;
                }
            }
        }
    }
}


// M2: UnionFind with path compression and union by rank
class Solution {
    // try UnionFind way
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        int[] rank = new int[n];
        Arrays.fill(rank, 1);
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {parent[i] = i;}
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (isConnected[i][j] == 1) {
                    union(parent, rank, i, j);
                }
            }
        }
        
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (parent[i] == i) {count++;}
        }
        return count;
        
        
    }
    
    private int find(int[] parent, int p) {
        // with path compression
        while (parent[p] != p) {
            parent[p] = parent[parent[p]];
            p = parent[p];            
        }
        return p;
    }
    
    private void union(int[] parent, int[] rank, int p1, int p2) {
        int p1parent = find(parent, p1);
        int p2parent = find(parent, p2);
        if (p1parent == p2parent) {return;}
        if (rank[p1parent] > rank[p2parent]) {
            parent[p2parent] = p1parent;
        } else {
            parent[p1parent] = p2parent;
            if (rank[p1parent] == rank[p2parent]) {rank[p2parent]++;}
        }
    }
}












