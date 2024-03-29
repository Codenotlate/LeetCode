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



// Whenever we must work with a set of elements (emails) that are connected (belong to the same user), we should always consider visualizing our input as a graph. 

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



// Phase3 refer solution
class Solution {
    // build the graph and do dfs/bfs to search connect group
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        // build the graph and also track the email to name map
        Map<String, Set<String>> graph = new HashMap<>();
        Map<String, String> emailToName = new HashMap<>();
        
        for (List<String> acct: accounts) {
            for (int i = 1; i < acct.size(); i++) {
                graph.putIfAbsent(acct.get(i), new HashSet<>());
                if (i >= 2) {
                    String email1 = acct.get(1);
                    String emailOther = acct.get(i);
                    // build the double direction(undirectional) edge between the node
                    graph.get(email1).add(emailOther);
                    graph.get(emailOther).add(email1);
                }
                emailToName.put(acct.get(i), acct.get(0));
            }
        }
        
        
        // start doing dfs on graph
        Set<String> visited = new HashSet<>();
        List<List<String>> res = new LinkedList<>();      
        for (String email: graph.keySet()) {
            List<String> curList = new LinkedList<>();
            if (visited.contains(email)) {continue;}
            dfs(graph, curList, email, visited);
            Collections.sort(curList);
            curList.add(0, emailToName.get(email));
            res.add(new LinkedList(curList));         
        }
        return res;
        
    }
    
    private void dfs(Map<String, Set<String>> graph, List<String> curList, String email, Set<String> visited) {
        visited.add(email);
        curList.add(email);
        for (String nextEmail: graph.get(email)) {
            if (!visited.contains(nextEmail)) {
                dfs(graph, curList, nextEmail, visited);
            }
        }
    }  
    
} 
    



// Review: similar as above, but since we loop each account and check whether its first email has been visited, we can skip the emailToName map used above.
/*Initial thought
Build the graph, for each original account, only need to connect the rest with the first email. Later, traverse the graph by traverse each account's first email. We also need to track the visited emails.
time O(nklog(nk))
sapce O(nk)
Another way is using UF. time and space the same as above method.
*/
// M1 graph way
class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, List<String>> graph = new HashMap<>();
        for (List<String> acct: accounts) {
            String firstEmail = acct.get(1);
            graph.putIfAbsent(firstEmail, new LinkedList<>());
            for (int i = 2; i < acct.size(); i++) {
                graph.putIfAbsent(acct.get(i), new LinkedList<String>());
                graph.get(firstEmail).add(acct.get(i));
                graph.get(acct.get(i)).add(firstEmail);
            }
        }
        // traverse the graph
        Set<String> visited = new HashSet<>();
        List<List<String>> res = new LinkedList<>();
        for (List<String> acct: accounts) {
            if (visited.contains(acct.get(1))) {continue;}
            List<String> curList = dfs(graph, acct.get(1), visited);
            Collections.sort(curList);
            curList.add(0, acct.get(0));
            res.add(curList);
        }
        return res;
    }
    
    private List<String> dfs(Map<String, List<String>> graph, String node, Set<String> visited) {
        List<String> res = new LinkedList<>();
        if(visited.contains(node)) {return res;}
        visited.add(node);
        res.add(node);
        for(String next: graph.get(node)) {
            res.addAll(dfs(graph, next, visited));
        }
        return res;
    }
}

// Try UF way next time. for each account, label all emails in that account with account index in accounts. Maintain a map <email, groupIdx>. If one email is already in the emailToId map, then we connect current idx with that groupIdx.
// after traverse all accounts and build the disjoint sets. Traverse each account, find the root of current groupIdx and put all emails in the set of that root groupIdx. Note we need to use set here cause we will have dup existing in both accounts.
// final step convert the list of set to the result, added the name using the groupIdx and in required order.





// 22/12/28
/* Thoughts
UF way without explicit UF class
emailMap <email, id>, same id for all emails in the same list. connect the id using UF.
Go through email in the list, add <email, current idx> to emailMap, if email already in emailMap.keys, connect two id. 
e.g.
0 - 1
2
3
resMap <id, set<emails>>, store a set of emails.
After the UF built up, go throught the idx of the list of list again, check the parent of each id, resMap.putIfAbsent(parentid, Set<String>), resMap.get(id).addAll(all the emails from this list).
After the whole iteration, go through the resMap, name will be accounts.get(id).get(0), sort the emails in the set and add to the list.

time O(m * n + n * mlog(n*m)) = O(mn* log(mn))
*/
class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, Integer> emailMap = new HashMap<>();
        Map<Integer, Set<String>> resMap = new HashMap<>();
        int n = accounts.size();
        int[] parents = new int[n];
        for (int i = 0; i < n; i++) {parents[i] = i;}
        int[] ranks = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < accounts.get(i).size(); j++) {
                String email = accounts.get(i).get(j);
                if (emailMap.keySet().contains(email)) {
                    connectUF(i, emailMap.get(email), parents, ranks);
                } else {
                    emailMap.put(email, i);
                }
            }
        }
        // UF built-up now
        for (int i = 0; i < n; i++) {
            int parentId = findUF(i, parents); // note this should be findUF not directly from parents.
            resMap.putIfAbsent(parentId, new HashSet<>());
            for (int j = 1; j < accounts.get(i).size(); j++) {
                resMap.get(parentId).add(accounts.get(i).get(j));
            }           
        }
        // convert to the res
        List<List<String>> res = new ArrayList<>();
        for (int id : resMap.keySet()) {
            List<String> emails = new LinkedList<>(resMap.get(id));
            Collections.sort(emails);
            emails.add(0, accounts.get(id).get(0));
            res.add(emails);
        }
        return res;
    }


    private void connectUF(int p, int q, int[] parents, int[] ranks) {
        int parentP = findUF(p, parents);
        int parentQ = findUF(q, parents);
        if (ranks[parentP] > ranks[parentQ]) {
            parents[parentQ] = parentP;
        } else {
            parents[parentP] = parentQ;
            if (ranks[parentP] == ranks[parentQ]) {ranks[parentQ]++;}
        }
    }

    private int findUF(int p, int[] parents) {
        while (parents[p] != p) {
            parents[p] = parents[parents[p]];
            p = parents[p];
        }
        return p;
    }
}



// 22/12/28
/*Thoughts
Another way is to build a graph from it and do bfs/dfs to traverse all emails connect to each other. For emails in the same list, they can all connect to the first email.
At the same time, if we traverse the graph directly, we need to maintain a email to account Map. Alternatively, if we traverse the graph using the first email of each list in accounts, we don't need to maintain this map, since we can already get the account name info.
time O(mnlog(mn)) space O(mn)
 */
class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, Set<String>> graph = new HashMap<>();
        for (List<String> l: accounts) {
            String keyEmail = l.get(1);
            graph.putIfAbsent(keyEmail, new HashSet<>());
            for (int i = 1; i < l.size(); i++) {
                graph.putIfAbsent(l.get(i), new HashSet<>());
                graph.get(keyEmail).add(l.get(i));
                graph.get(l.get(i)).add(keyEmail);
            }
        }

        // graph is built, do dfs or bfs on it.
        List<List<String>> res = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        for (List<String> l: accounts) {
            ArrayList<String> emails = dfs(l.get(1), graph, visited);
            if (emails.size() == 0) {continue;}
            Collections.sort(emails);
            emails.add(0, l.get(0));
            res.add(emails);
        }
        return res;

    }

    private ArrayList<String> dfs(String cur, Map<String, Set<String>> graph, Set<String> visited) {
        ArrayList<String> res = new ArrayList<>();
        if (visited.contains(cur)) {return res;}       
        visited.add(cur);
        res.add(cur);
        for (String next: graph.get(cur)) {
            res.addAll(dfs(next, graph, visited));
        }
        return res;

    }
}






