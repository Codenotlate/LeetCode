// Phase2 self M1: UnionFind time O(n^2 * k)
class Solution {
	private class UnionFind {
		int[] parent, rank;
		int size;

		public UnionFind(int n) {
			parent = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = i;
			}
			rank = new int[n];
			size = n;
		}

		public int find(int p) {
			while (parent[p] != p) {
				parent[p] = parent[parent[p]];
				p = parent[p];
			}
			return p;
		}

		public void union(int p, int q) {
			int findp = find(p);
			int findq = find(q);
			if (findp == findq) {return;}
			if (rank[findp] > rank[findq]) {
				parent[findq] = findp;
			} else if (rank[findp] < rank[findq]) {
				parent[findp] = findq;
			} else {
				parent[findp] = findq;
				rank[findq]++;
			}
			size--;
		}

		public int size() {
			return size;
		}
	}


    public int numSimilarGroups(String[] strs) {
        int n = strs.length;
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n; i++) {
        	for (int j = i + 1; j < n; j++) {
        		if (isSimilar(strs[i], strs[j])) {
        			uf.union(i, j);
        		}
        	}
        }
        return uf.size();
    }


    private boolean isSimilar(String s1, String s2) {
    	int diff = 0;
    	for (int i = 0; i < s1.length(); i++) {
    		if (s1.charAt(i) != s2.charAt(i)) {
    			diff++;
    			if (diff > 2) {return false;}
    		}
    	}
    	return true;
    }
}



// Phase2 Self M2: DFS time O(n^2 *k)
class Solution {
    public int numSimilarGroups(String[] strs) {
        int[] visited = new int[strs.length];
        int count = 0;
        for (int i = 0; i < strs.length; i++) {
        	if (visited[i] == 0) {
        		dfs(strs, i, visited);
        		count++;
        	}
        }
        return count;
    }

    private void dfs(String[] strs, int i, int[] visited) {
    	if (visited[i] != 0) {return;}

    	visited[i] = 1;
    	for (int j = 0; j < strs.length; j++) {
    		if (isSimilar(strs[i], strs[j])) {
    			dfs(strs, j, visited);
    		}
    	}
    }

    private boolean isSimilar(String s1, String s2) {
    	int diff = 0;
    	for (int i = 0; i < s1.length(); i++) {
    		if (s1.charAt(i) != s2.charAt(i)) {
    			diff++;
    			if (diff > 2) {return false;}
    		}
    	}
    	return true;
    }
}


// solution page has a piecewise idea of combining two methods






















