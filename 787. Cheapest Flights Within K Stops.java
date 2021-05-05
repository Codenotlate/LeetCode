// Phase2 self M1
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        // build the graph
        Map<Integer, ArrayList<int[]>> graph = new HashMap<>();
        for (int[] edge: flights) {
        	graph.putIfAbsent(edge[0], new ArrayList());
        	graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }

        // use Dijkstra: find the shortest path between u and v within k edges
        PriorityQueue<int[]> pq = new PriorityQueue<>((e1, e2) -> (e1[1] - e2[1]));

        pq.add(new int[]{src, 0, -1});

        while (!pq.isEmpty()) {
        	int[] cur = pq.poll();
        	int node = cur[0];
        	int dist = cur[1];
        	int stops = cur[2];
        	if (node == dst && stops <= K) {return dist;}
        	if (stops >= K) {continue;}
            // don't forget to check contains first
            if (graph.keySet().contains(node)) {
                for (int[] next: graph.get(node)) {
        		pq.add(new int[]{next[0], dist + next[1], stops + 1});
        	    }
            }
        	
        }

        return -1;
    }
}


// Phase2 self M2: slightly improved from M1: add a map to store visited node and stops
// use this map to skip adding the edges if the node has a larger stops number compare to what we have in the map
// since if it's visited later, it definitely has larger dist, 
// much faster than M1
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        // build the graph
        Map<Integer, ArrayList<int[]>> graph = new HashMap<>();
        for (int[] edge: flights) {
        	graph.putIfAbsent(edge[0], new ArrayList());
        	graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }

        // use Dijkstra: find the shortest path between u and v within k edges
        PriorityQueue<int[]> pq = new PriorityQueue<>((e1, e2) -> (e1[1] - e2[1]));
        Map<Integer, Integer> stopsMap = new HashMap<>();

        pq.add(new int[]{src, 0, -1});

        while (!pq.isEmpty()) {
        	int[] cur = pq.poll();
        	int node = cur[0];
        	int dist = cur[1];
        	int stops = cur[2];
        	if (node == dst && stops <= K) {return dist;}
        	if (stops >= K || (stopsMap.keySet().contains(node) && stopsMap.get(node) <= stops)) {continue;}
        	stopsMap.put(node, stops);
            // don't forget to check contains first
            if (graph.keySet().contains(node)) {
                for (int[] next: graph.get(node)) {
        		pq.add(new int[]{next[0], dist + next[1], stops + 1});
        	    }
            }
        	
        }

        return -1;
    }
}


// other methods: DFS/BFS/BellmanFord, Dijkstra's
// https://leetcode.com/problems/cheapest-flights-within-k-stops/discuss/361711/Java-DFSBFSBellman-Ford-Dijkstra's
// https://leetcode.com/problems/cheapest-flights-within-k-stops/discuss/686774/SUGGESTION-FOR-BEGINNERS-BFS-or-DIJKSHTRA-or-DP