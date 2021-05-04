// Phase2 
class Solution {
    public int[] findRedundantDirectedConnection(int[][] edges) {
    	int[] parent = new int[edges.length + 1];
    	UnionFind uf = new UnionFind(edges.length + 1);
    	int[] edge1 = null;
    	int[] edge2 = null; // use to store possible answers, the two edges that causes indegree = 2
    	int[] circleLastEdge = null;
    	for (int[] edge: edges) {
    		int start = edge[0];
    		int end = edge[1];
    		if (parent[end] != 0) {
    			edge1 = new int[]{parent[end], end};
    			edge2 = edge;
    		} else {
    			parent[end] = start;
    			if (uf.connected(start, end)) {
    				circleLastEdge = edge;
    			} else {
    				uf.union(start, end);
    			}
    		}
    	}
    	if (edge2 == null) { // meaning no node has indegree == 2: case1
    		return circleLastEdge;
    	} else {
    		return circleLastEdge == null ? edge2 : edge1;
    	}        
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

// time O(n * å(n)) ~~ O(n)

//https://leetcode.com/problems/redundant-connection-ii/discuss/278105/topic