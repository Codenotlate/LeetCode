// Phase 2 Self: UnionFind way
class Solution {
    public boolean equationsPossible(String[] equations) {
        UnionFind uf = new UnionFind(26);
        for (String e: equations) {
        	if (e.charAt(1) == '=') {
        		uf.union(e.charAt(0) - 'a', e.charAt(3) - 'a');
        	}
        }

        for (String e: equations) {
        	if (e.charAt(1) == '!' && uf.isConnected(e.charAt(0) - 'a', e.charAt(3) - 'a')) {
        		return false;
        	}
        }

        return true;
    }

    private class UnionFind {
    	int[] parent, rank;

    	public UnionFind(int n) {
    		parent = new int[n];
    		rank = new int[n];
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

    	public void union(int p, int q){
    		int rootp = find(p);
    		int rootq = find(q);
    		if (rootp == rootq) {return;}
    		if (rank[rootq] > rank[rootp]) {
    			parent[rootp] = rootq;
    		} else if (rank[rootq] < rank[rootp]) {
    			parent[rootq] = rootp;
    		} else {
    			parent[rootq] = rootp;
    			rank[rootp]++;
    		}
    	}

    	public boolean isConnected(int p, int q) {
    		return find(p) == find(q);
    	}
    }
}


// a precise UF way: without union by rank
// https://leetcode.com/problems/satisfiability-of-equality-equations/discuss/234486/JavaC%2B%2BPython-Easy-Union-Find




// phase2 self dfs way
// first use == to build the graph
// then use != to do dfs, checking whether connected
class Solution {
    public boolean equationsPossible(String[] equations) {
        Map<Character, List<Character>> graph = new HashMap<>();
        for (String e: equations) {
        	if (e.charAt(1) == '=') {
        		char left = e.charAt(0);
        		char right = e.charAt(3);
        		graph.putIfAbsent(left, new ArrayList<>());
        		graph.putIfAbsent(right, new ArrayList<>());
        		graph.get(left).add(right);
        		graph.get(right).add(left);
        	}
        }

        for (String e:equations) {
        	if (e.charAt(1) == '!') {
        		if (dfs(graph, e.charAt(0), e.charAt(3), new HashSet<Character>())) {
        			return false;
        		}
        	}
        }

        return true;
    }


    private boolean dfs(Map<Character, List<Character>> graph, char left, char right, Set<Character> visited) {
    	if (left == right) {return true;}
    	// pay attention to char not in the graph
    	if (visited.contains(left) || !graph.keySet().contains(left) || !graph.keySet().contains(right)) {return false;}
    	visited.add(left);
    	for (char next: graph.get(left)) {
    		if (dfs(graph, next, right, visited)) {
    			return true;
    		}
    	}
    	return false;
    }
}






