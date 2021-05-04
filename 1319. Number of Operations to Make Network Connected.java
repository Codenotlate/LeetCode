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











