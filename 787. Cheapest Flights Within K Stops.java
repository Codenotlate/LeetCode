// This problem needs review: still not very clear about the time complexity

// -------------------- Dijkstra way ----------------------//
// time O(V^k * log(V^k)) ? not sure

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


// other methods: DFS/BFS/BellmanFord, Dijkstra's (need to review this post, time complexity analysis in comment.)
// https://leetcode.com/problems/cheapest-flights-within-k-stops/discuss/361711/Java-DFSBFSBellman-Ford-Dijkstra's
// https://leetcode.com/problems/cheapest-flights-within-k-stops/discuss/686774/SUGGESTION-FOR-BEGINNERS-BFS-or-DIJKSHTRA-or-DP

// This also might not be called dijkstra...


// self practice, using self class City & dijkstra, same idea as M2 above
class Solution {
    private class City {
        public int city, dist, stops;
        
        public City (int c, int d, int s) {
            city = c;
            dist = d;
            stops = s;
        }
    }
    
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        // build the graph
        Map<Integer, ArrayList<int[]>> graph = new HashMap<>();
        for (int[] e: flights) {
            graph.putIfAbsent(e[0], new ArrayList<>());
            graph.get(e[0]).add(new int[]{e[1], e[2]});
        }
        
        // dijkstra, but this time only record visited is not enough, we also need to check stops # k.
        // we can not skip a node from pq only because it's visited, we can skip a node and dist only when it's visited and the previous stops# is smaller than current stop number
        // thus in the pq, it's going to be a self class, with three attributes, citynode, dist, stops#
        PriorityQueue<City> pq = new PriorityQueue<>((c1, c2) -> (c1.dist - c2.dist));
        Map<Integer, Integer> visitedDist = new HashMap<>();
        
        pq.add(new City(src, 0, -1));
        
        while (!pq.isEmpty()) {
            City curCity = pq.poll();
            if (curCity.city == dst && curCity.stops <= K) {return curCity.dist;}
            if (curCity.stops >= K || (visitedDist.keySet().contains(curCity.city) && curCity.stops >= visitedDist.get(curCity.city))) {continue;}
            visitedDist.put(curCity.city, curCity.stops);
            
            if (graph.keySet().contains(curCity.city)) {
                for (int[] next: graph.get(curCity.city)) {
                    pq.add(new City(next[0], next[1] + curCity.dist, curCity.stops + 1));
                }
            }
        }
        return -1;
    }
}


// -------------------- BellmanFord way ----------------------//
// time: O(E * K)
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        for (int i = 0; i < k + 1; i++) { // run at most 
        	//Note: we need to use a copies temp of result from last round
        	// becasue otherwise, we may update a node dist based on the dist of other node that has been updated in the same round
        	// in that case, we can no longer guarantee that only nodes i edges from src will be updated on the i-th round of loop
        	// in this problem, meaning the stop can exceed k.
        	int[] temp = Arrays.copyOf(dist, n);
        	for (int[] e: flights) {
        		if (dist[e[0]] == Integer.MAX_VALUE) {continue;}
        		temp[e[1]] = Math.min(dist[e[0]] + e[2], temp[e[1]]);
        	}
        	dist = temp;
        }

        return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
    }
}






























