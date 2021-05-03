// Phase 2 self: unionFind
class Solution {
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        UnionFind uf = new UnionFind(s.length());
        for (List<Integer> pair: pairs) {
        	uf.union(pair.get(0), pair.get(1));
        }

        Map<Integer, PriorityQueue<Character>> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
        	int rootVal = uf.find(i);
        	map.putIfAbsent(rootVal, new PriorityQueue<Character>());
        	map.get(rootVal).add(s.charAt(i));
        }

        char[] res = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
        	int rootVal = uf.find(i);
        	res[i] = map.get(rootVal).poll();
        }

        return new String(res);
    }


    private class UnionFind {
    	private int[] parent, rank;

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

    	public void union(int p, int q) {
    		int rootp = find(p);
    		int rootq = find(q);
    		if (rootp == rootq) {return;}
    		if (rank[rootp] > rank[rootq]) {
    			parent[rootq] = rootp;
    		} else if (rank[rootp] < rank[rootq]) {
    			parent[rootp] = rootq;
    		} else {
    			parent[rootq] = rootp;
    			rank[rootp] += 1;
    		}
    	}
    }
}