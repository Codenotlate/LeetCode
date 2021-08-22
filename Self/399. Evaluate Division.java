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

// need further review