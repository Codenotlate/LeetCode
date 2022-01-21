// Phase2 self
class Solution {
    public int makeConnected(int n, int[][] connections) {
        UnionFind uf = new UnionFind(n);
        int available = 0;
        for (int[] edge: connections) {
        	if (uf.connected(edge[0], edge[1])) {
        		available++;
        	} else {
        		uf.union(edge[0], edge[1]);
        	}
        }
        int need = uf.size() - 1;
        return available < need ? -1 : need;
    }

    private class UnionFind {
    	private int[] parent, rank;
    	private int size;

    	public UnionFind(int n) {
    		parent = new int[n];
    		rank = new int[n];
    		size = n;
    		for (int i = 0; i < n; i++) {
    			parent[i] = i;
    		}
    	}

    	public int find(int p) {
    		while (parent[p] != p) {
    			parent[p] = parent[parent[p]];
    			p = parent[p];
    		}
    		return p;
    	}

    	public void union(int p, int q) {
    		int rootp = find(p);
    		int rootq = find(q);
    		if (rootp == rootq) {return;}
    		int diff = rank[rootp] - rank[rootq];
    		if (diff < 0) {
    			parent[rootp] = rootq;
    		} else {
    			parent[rootq] = rootp;
    			if (diff == 0) {
    				rank[rootp]++;
    			}
    		}
    		size--;
    	}

    	public boolean connected(int p, int q) {
    		return find(p) == find(q);
    	}

    	public int size() {
    		return size;
    	}
    }


}


// slightly diff: decide whther connections len >= n - 1 first, then return size - 1
class Solution {
    public int makeConnected(int n, int[][] connections) {
    	if (connections.length < n - 1) {return -1;}
        UnionFind uf = new UnionFind(n);
        for (int[] edge: connections) {
        	if (!uf.connected(edge[0], edge[1])) {
        		uf.union(edge[0], edge[1]);
        	}
        }
       	return uf.size() - 1;
    }

    private class UnionFind {
    	private int[] parent, rank;
    	private int size;

    	public UnionFind(int n) {
    		parent = new int[n];
    		rank = new int[n];
    		size = n;
    		for (int i = 0; i < n; i++) {
    			parent[i] = i;
    		}
    	}

    	public int find(int p) {
    		while (parent[p] != p) {
    			parent[p] = parent[parent[p]];
    			p = parent[p];
    		}
    		return p;
    	}

    	public void union(int p, int q) {
    		int rootp = find(p);
    		int rootq = find(q);
    		if (rootp == rootq) {return;}
    		int diff = rank[rootp] - rank[rootq];
    		if (diff < 0) {
    			parent[rootp] = rootq;
    		} else {
    			parent[rootq] = rootp;
    			if (diff == 0) {
    				rank[rootp]++;
    			}
    		}
    		size--;
    	}

    	public boolean connected(int p, int q) {
    		return find(p) == find(q);
    	}

    	public int size() {
    		return size;
    	}
    }


}



// can also do BFS /DFS, the key is to find # of connected groups
// https://leetcode.com/problems/number-of-operations-to-make-network-connected/discuss/477660/Java-Count-number-of-connected-components-Clean-code


// Phase 3 self
class Solution {
    // we at least need n-1 edges, otherwise return -1 directly.
    // If we have at least n-1 edges, then it's guaranteed we can connect all computers by moving some edges.
    // min number of movements should be the number of unconnected groups - 1. Thus we can use bfs or dfs to count the group number
    public int makeConnected(int n, int[][] connections) {
        if (n - 1 > connections.length) {return -1;}
        // build the adjacent matrix
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] edge: connections) {
            graph.putIfAbsent(edge[0], new LinkedList<Integer>());
            graph.putIfAbsent(edge[1], new LinkedList<Integer>());
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        // start bfs/dfs, count the group number
        int[] visited = new int[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if(visited[i] == 0) {
                traverse(graph, i, visited);
                count++;
            }           
        }
        
        return count-1;
    }
    
    
    private void traverse(Map<Integer, List<Integer>> graph, int i, int[] visited) {
        // bfs way
        Queue<Integer> queue = new LinkedList<>();
        queue.add(i);
        visited[i] = 1;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            // don't forget this line!!!!
            if (graph.get(cur) == null) {continue;}
            for (int next: graph.get(cur)) {
                if (visited[next] == 0) {
                    queue.add(next);
                    visited[next] = 1;
                }
            }
        }
    }

    private void traverse(Map<Integer, List<Integer>> graph, int i, int[] visited) {
        // dfs way
        if (graph.get(i) == null) {return;}
        for (int next: graph.get(i)) {
            if (visited[next] == 1) {continue;}
            visited[next] = 1;
            traverse(graph, next, visited);
        }
        
    }
}


// M2: UF way: similar as above UF M2
class Solution {
    // we at least need n-1 edges, otherwise return -1 directly.
    // If we have at least n-1 edges, then it's guaranteed we can connect all computers by moving some edges.
    // min number of movements should be the number of unconnected groups - 1. Thus we can use bfs or dfs to count the group number.
    // Another way is to use UF to get the group number. time all O(V+E)
    public int makeConnected(int n, int[][] connections) {
        if (n - 1 > connections.length) {return -1;}
        
        int[] parent = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {parent[i] = i;}
        
        for (int[] edge: connections) {
            union(parent, rank, edge[0], edge[1]);
        }
        
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (parent[i] == i) {count++;}
        }
        return count-1;
    }
    
    
    private void union(int[] parent, int[] rank, int p, int q) {
        int rootp = find(parent, p);
        int rootq = find(parent, q);
        if (rank[rootp] > rank[rootq]) {
            parent[rootq] = rootp;
        } else {
            parent[rootp] = rootq;
            if (rank[rootp] == rank[rootq]) {rank[rootq]++;}
        }
    }
    
    private int find(int[] parent, int p) {
        while (parent[p] != p) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }
}






