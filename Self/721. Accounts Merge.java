// Phase 2 self: m1: unionFind

class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        // use map emailToIdx to store email as the key and index of accounts as index
        // since emails in same list must in the same group, thus can use index i to label the group
    	int n = accounts.size();
        UnionFind uf = new UnionFind(n);
        Map<String, Integer> emailToIdx = new HashMap<>();
        //time O(m*n)
        for (int i = 0; i < n; i++) {
        	// j starts with 1, skip the name in idx 0
        	for (int j = 1; j < accounts.get(i).size(); j++) {
        		String email = accounts.get(i).get(j);
        		if (!emailToIdx.keySet().contains(email)) {
        			emailToIdx.put(email, i);
        		} else {
        			uf.union(i, emailToIdx.get(email));
        		}
        	}
        }
        // have built the disjoint set for index after above
        // then for each disjoint set, use the index of the set root as key, 
        // and add all emails under this same root index as values
        // time O(m*n)
        Map<Integer, Set<String>> emailGroups = new HashMap<>();
        for (int i = 0; i < n; i++) {
        	emailGroups.putIfAbsent(uf.find(i), new HashSet<>());
        	for (int j = 1; j < accounts.get(i).size(); j++) {
        		emailGroups.get(uf.find(i)).add(accounts.get(i).get(j));
        	}
        }

        // then convert emailGroups to the result
        // time O(sum of ailogai) where sum of ai equal to # of unique emails in accounts
        List<List<String>> res = new ArrayList<>();
        for (int idx: emailGroups.keySet()) {
        	List<String> acct = new LinkedList(emailGroups.get(idx));
        	Collections.sort(acct);
        	acct.add(0, accounts.get(idx).get(0));
        	res.add(acct);
        }

        return res;


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
    		if (rootq == rootp) {return;}
    		if (rank[rootq] > rank[rootp]) {
    			parent[rootp] = rootq;
    		} else if (rank[rootq] < rank[rootp]) {
    			parent[rootq] = rootp;
    		} else {
    			parent[rootp] = rootq;
    			rank[rootq]++;
    		}
    	}
    }

}
// union find idea without using regular union find class
// https://leetcode.com/problems/accounts-merge/discuss/109157/JavaC%2B%2B-Union-Find



// Phase2 self M2: build graph + BFS/DFS
class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        // build the graph
        Map<String, Set<String>> graph = new HashMap<>();
        Map<String, String> names = new HashMap<>();
        for (List<String> account: accounts) {
        	for (int i = 1; i < account.size(); i++) {
        		graph.putIfAbsent(account.get(i), new HashSet<>());
        		if (i >= 2) {
        			graph.get(account.get(1)).add(account.get(i));
        			graph.get(account.get(i)).add(account.get(1));
        		}
        		names.put(account.get(i), account.get(0));
        	}
        }

        // do dfs or bfs to the graph
        List<List<String>> res = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (String e: graph.keySet()) {       	
        	List<String> emails = new LinkedList<>();
            if (visited.contains(e)) {continue;} // need this line, otherwise below emails.get(0) could be nullpointer error
        	dfs(graph, e, visited, emails);
        	Collections.sort(emails);
        	emails.add(0, names.get(emails.get(0)));
        	res.add(emails);
        }

        return res;
    }

    private void dfs(Map<String, Set<String>> graph, String cur, Set<String> visited, List<String> emails) {
    	if (visited.contains(cur)) {return;}
    	visited.add(cur);
    	emails.add(cur);
    	for (String next: graph.get(cur)) {
    		dfs(graph, next, visited, emails);
    	}
    }
}



















