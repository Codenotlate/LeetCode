class Solution {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        double[] res = new double[queries.size()];
        Map<String, Map<String, Double>> graph = buildGraph(equations, values);
        for (int i = 0; i < queries.size(); i++) {
        	res[i] = dfs(graph, queries.get(i).get(0), queries.get(i).get(1), 1.0, new HashSet<String>());
        }
        return res;
    }

    private Map<String, Map<String, Double>> buildGraph(List<List<String>> equations, double[] values) {
    	Map<String, Map<String, Double>> graph = new HashMap<>();
    	for (int i = 0; i < values.length; i++) {
    		String s1 = equations.get(i).get(0);
    		String s2 = equations.get(i).get(1);
    		graph.putIfAbsent(s1, new HashMap());
    		graph.putIfAbsent(s2, new HashMap());
    		graph.get(s1).put(s2, values[i]);
    		graph.get(s2).put(s1, 1 / values[i]);
    	}
    	return graph;
    }

    private double dfs(Map<String, Map<String, Double>> graph, String cur, String end, double preRes, Set<String> visited) {
    	if (!graph.keySet().contains(cur) || !graph.keySet().contains(end)) {return -1;}
    	if (cur.equals(end)) {return preRes;}
    	if (visited.contains(cur)) {return -1;}

    	visited.add(cur);
    	for (String next: graph.get(cur).keySet()) {
    		double res = dfs(graph, next, end, preRes * graph.get(cur).get(next), visited);
    		if (res != -1) {return res;}
    	}
    	// no need to backtracking, since if it's in the path to the answer then we would have returned the answer the first time we visit it
    	// if it's not the answer, then we can directly return -1 in the beginning
    	//visited.remove(cur);
    	return -1;
    }
}

/* Reason for no backtracking
In one query, there is no possible way for running same node twice. (Even if so, since the correct relation between two node can only be one number rather than multiple number, the second time we visit a node means that this node is a dead end, it cannot reach the target node. Otherwise it would return target answer the first time we visit it)
this is the main difference from the general backtrack problem, where there may be multiple valid answer.
*/
//https://leetcode.com/problems/evaluate-division/discuss/171649/1ms-DFS-with-Explanations
//some followup questions



// another way: union find solution
// main idea is for a/b = 2, we view b as the parent of a. 
// if b is the root, its parent is itself, then dist[b] = 1
// then dist[a] = dist[parent[a]] * values(a/b)
// this is actually the basic idea of math, assume the most basic var as 1 then calculate the value of all others,
// and when new query comes, we can directly calculate the answer by using the represented values of those chars
// the path compression in find function here also updates dist map

// need further review（22/1/29 memo: still not understand the UF way）



// Review self
class Solution {
    /*
    Build the double directional graph, then use dfs for each query's first node. return result as -1 if any of the nodes in the query is not in the graph.    
    time O(n*(V+E))
    */
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Map<String, Double>> graph = new HashMap<>();
        // build the graph
        for (int i = 0; i < values.length; i++) {
            String node1 = equations.get(i).get(0);
            String node2 = equations.get(i).get(1);
            graph.putIfAbsent(node1, new HashMap<>());
            graph.putIfAbsent(node2, new HashMap<>());
            graph.get(node1).put(node2, values[i]);
            graph.get(node2).put(node1, 1.0 / values[i]);
        }
        
        double[] res = new double[queries.size()];
        
        // do dfs calculation for each query
        for(int i = 0; i < queries.size(); i++) {
            String node1 = queries.get(i).get(0);
            String node2 = queries.get(i).get(1);
            res[i] = dfs(graph, node1, node2, new HashSet<String>(), 1.0);
        }
        return res;
    }
    
    
    private double dfs(Map<String, Map<String, Double>> graph, String cur, String node2, Set<String> visited, double preNum) {
        if(!graph.keySet().contains(node2) || !graph.keySet().contains(cur)) {return -1.0;}
        if(cur.equals(node2)) {return preNum;}        
        visited.add(cur);
        for(String next: graph.get(cur).keySet()) {
            double value = graph.get(cur).get(next);
            if (!visited.contains(next)) {
                double res = dfs(graph, next, node2, visited, preNum * value);
                if(res!= -1) {return res;}
            }
        }
        return -1;
    }
}







// Review

/*Thought
Build the graph based on equations and values. Each pair will contribute to directionl edges: a-> b = 2, then b->a = 0.5. The graph will be <String>: List<pair<String, Double>>
when calculate for queries, first check if both strings in the graph, otherwise return -1.  Then check if two strings are the same, if same return 1. Then we can start with first string and do dfs to find the second string and multiply the value along the way.
*/
class Solution {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, List<Pair<String, Double>>> graph = new HashMap<>();
        Map<Pair<String,String>, Double> memo = new HashMap<>();
        for(int i = 0; i < equations.size(); i++) {
            List<String> equa = equations.get(i);
            String s1 = equa.get(0);
            String s2 = equa.get(1);
            double val = values[i];
            graph.putIfAbsent(s1, new LinkedList<>());
            graph.putIfAbsent(s2, new LinkedList<>());
            graph.get(s1).add(new Pair(s2, val));
            graph.get(s2).add(new Pair(s1, 1.0 /val));
            memo.put(new Pair(s1, s2), val);
            memo.put(new Pair(s2,s1), 1.0/val);
        }
        
        double[] res = new double[queries.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = calQuery(queries.get(i), graph, memo, new HashSet<String>());
        }
        return res;
    }
    
    
    
    private double calQuery(List<String> query, Map<String, List<Pair<String, Double>>> graph, Map<Pair<String, String>, Double> memo, Set<String> visited) {
        String s1 = query.get(0);
        String s2 = query.get(1);
        if (memo.containsKey(new Pair(s1, s2))) {return memo.get(new Pair(s1, s2));}     
        if (!graph.containsKey(s1) || !graph.containsKey(s2) || visited.contains(s1)) {return -1;}
        if (s1.equals(s2)) {return 1;}
        
        visited.add(s1);

        for (Pair<String, Double> next: graph.get(s1)) {
            List<String> nextList = new LinkedList<>();
            nextList.add(next.getKey());
            nextList.add(s2);
            double nextRes = calQuery(nextList, graph, memo, visited);
            if (nextRes > 0) {
                double res = next.getValue() * nextRes;
                memo.put(new Pair(s1, s2), res);
                return res;
            }
        }
        
        return -1;
        
    }
    
}



























