// method1 DFS
// Time O(n^2) space O(N)
class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        // set up for a graph
        int N = 1001;
        ArrayList<Integer>[] graph = new ArrayList[N]; // since size of edges is 3 to 1000
        for (int i = 0; i < N; i++) {
        	graph[i] = new ArrayList<>();
        }

        for (int[] edge: edges) {
        	// already connected before adding edge
        	if (connected(edge[0], edge[1], 0, graph)) {
        		return edge;
        	}
        	// not connected, adding to graph for both nodes
        	graph[edge[0]].add(edge[1]);
        	graph[edge[1]].add(edge[0]);
        }

        // should not reach this line
        return new int[0];
    }

    private boolean connected(int cur, int end, int parent, ArrayList<Integer>[] graph) {
    	// base case
    	if (cur == end) {return true;}

    	for (int next: graph[cur]) {
    		if (next == parent) {continue;} // avoid revisit the parent of cur again
    		if (connected(next, end, cur, graph)) {
    			return true;
    		}
    	}
    	return false;
    }
}




// method 2: Union find (disjoint set)
// time O(n∂(n)) O(∂(n)) is close to O(1); space = O(n)
class Solution {
    public int[] findRedundantConnection(int[][] edges) {
    	int N = 1001;
    	DisjointSet uf = new DisjointSet(N);

    	for (int[] edge: edges) {
    		// union return false if u and v is already connected
    		if (!uf.union(edge[0], edge[1])) {
    			return edge;
    		}
    	}

    	// should not reach this line
    	return new int[0];
    }

    private class DisjointSet {
    	private int[] parent;
    	private int[] rank;

    	DisjointSet(int N) {
    		parent = new int[N];
    		rank = new int[N];
    	}

		public int find(int x) {
			if (parent[x] == 0) {return x;}
			parent[x] = find(parent[x]); // path compression
			return parent[x];
		}

		public boolean union(int x, int y) {
			int rootX = find(x);
			int rootY = find(y);
			if (rootX == rootY) {return false;}
			if (rank[rootX] > rank[rootY]) {
				parent[rootY] = rootX;
			} else if (rank[rootY] > rank[rootX]) {
				parent[rootX] = rootY;
			} else {
				parent[rootX] = rootY;
				rank[rootY]++;
			}
			return true;
		}
    }
}



// phase2 self

class Solution {
    // M1: unionFind
    public int[] findRedundantConnection(int[][] edges) {
        UnionFind uf = new UnionFind(edges.length + 1);
        for (int[] edge: edges) {
            if (uf.connected(edge[0], edge[1])) {return edge;}
            uf.union(edge[0], edge[1]);
        }
        return new int[2];
    }
    
    private class UnionFind {
        int[] rank, parent;
        
        public UnionFind (int n) {
            rank = new int[n];
            parent = new int[n];
            
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        public int find (int p) {
            while (parent[p] != p) {
                parent[p] = parent[parent[p]];
                p = parent[p];
            }
            return p;
        }
        
        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }
        
        public void union (int p, int q) {
            int rootp = find(p);
            int rootq = find(q);
            if (rank[rootp] > rank[rootq]) {
                parent[rootq] = rootp;
            } else {
                if (rank[rootp] == rank[rootq]) {
                    rank[rootq]++;
                }
                parent[rootp] = rootq;
            }
        }
    }
}


// phase2 dfs
class Solution {
    //M2: dfs way slower than UF way
    public int[] findRedundantConnection(int[][] edges) {
        List<Integer>[] graph = new List[edges.length + 1];
        for (int i = 0; i < edges.length + 1; i++) {
            graph[i] = new ArrayList<>();
        }
        // check the cycle along the process of building the graph
        for (int[] e: edges) {
            if (connect(e[0], e[1], 0, graph)) {return e;}
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }
        
        return new int[0];
    }
    
    private boolean connect(int cur, int end, int parent, List<Integer>[] graph) {
        if (cur == end) {return true;}
        
        for (int next: graph[cur]) {
            if (next == parent) {continue;}
            if (connect(next, end, cur, graph)) {
                return true;
            }            
        }
        return false;
    }
}




















