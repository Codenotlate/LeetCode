// This problem needs review: still not very clear about the time complexity

// For example a standard rule of thumb that is followed for solving shortest path problems is that we mostly use Breadth-first search for unweighted graphs and use Dijkstra's algorithm for weighted graphs. An implied condition to apply the Dijkstra's algorithm is that the weights of the graph must be positive. If the graph has negative weights and can have negative weighted cycles, we would have to employ another algorithm called the Bellman Ford's. The point here is that the properties of the graph and the goal define the kind of algorithms we might be able to use.
// https://leetcode.com/problems/cheapest-flights-within-k-stops/solution/


// -------------------- Dijkstra way ----------------------//
// time O(V^k * log(V^k)) ? not sure (based on runtime in LC, it's not this slow)

// TLE, skip -- Phase2 self 
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


// Phase2 self M1: slightly improved from M1: add a map to store visited node and stops
// use this map to skip adding the edges if the node has a larger stops number compare to what we have in the map
// since if it's visited later, it definitely has larger dist, 
// much faster than above method
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


// -------------------- M2: BellmanFord way ----------------------//
// time: O(E * K)
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        for (int i = 0; i < k + 1; i++) { // run at most 
        	//Note: we need to use a copies temp of result from last round （regular BF doesn't care how many rounds of loop may not need this）
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



// -------------------- M3: DFS way ----------------------//
// time: O(V^K)

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // build the graph
        Map<Integer, ArrayList<int[]>> graph = new HashMap<>();
        for (int[] e: flights) {
            graph.putIfAbsent(e[0], new ArrayList());
            graph.get(e[0]).add(new int[]{e[1], e[2]});
        }

        int[] answer = new int[]{Integer.MAX_VALUE};
        // do dfs starting from src, if the steps is largeer than k - stop. 
        // If dst is visited within k stops, update the answer[].
        dfs(graph, src, dst, k + 1, 0, answer);

        return answer[0] == Integer.MAX_VALUE ? -1 : answer[0];
    }

    private void dfs(Map<Integer, ArrayList<int[]>> graph, int cur, int dst, int k, int cost, int[] answer) {
        if (k < 0 || cost >= answer[0]) {return;}
        if (cur == dst) {
            answer[0] = Math.min(answer[0], cost); 
            return;
        }
        if (graph.keySet().contains(cur)) {
            for (int[] next: graph.get(cur)) {
                if (cost + next[1] >= answer[0]) {continue;}  // no need to go deeper
                dfs(graph, next[0], dst, k - 1, cost + next[1], answer);
            }
        }
    }
}



// -------------------- M4: BFS way ----------------------//
// time: O(V^K) (will get TLE in LC)
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // build the graph
        Map<Integer, ArrayList<int[]>> graph = new HashMap<>();
        for (int[] e: flights) {
            graph.putIfAbsent(e[0], new ArrayList());
            graph.get(e[0]).add(new int[]{e[1], e[2]});
        }

        // do bfs, need to cound level, once hit k then stop
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{src, 0});
        int level = -1;
        int answer = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                if (cur[1] >= answer) {continue;}
                if (cur[0] == dst) {
                    answer = Math.min(answer, cur[1]); 
                    continue;
                }
                if (graph.keySet().contains(cur[0])) {
                    for (int[] next: graph.get(cur[0])) {
                        if (next[1] + cur[1] >= answer) {continue;}
                        queue.add(new int[]{next[0], next[1] + cur[1]});
                    }
                }
            }

            if (++level > k) {break;}
        }

        return answer == Integer.MAX_VALUE ? -1 : answer;

    }
}












//----------------------------------------------------------------

// Phase3 self
// Dijkstra modified version - similar as above M1 Dijkstra way
// difference is above M1 don't check whether the neighbor is visited when add into PQ, and do the check when it's popped to skip it. Here we do the check before we add that node into the PQ. A already visited node will only be added to PQ, if its current stop is smaller than the stop number recorded in the visited map.
class Solution {
    // Dijkstra, but one modification: in normal Dij, if a node is already visited, we won't reconsider it to put it into the PQ. But here we have the constraints on number of stops. Thus we will reconsider the visited node if its stop# is smaller than the stop# when its earlier popped out of the PQ. 
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // Build the graph
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] e: flights) {
            graph.putIfAbsent(e[0], new LinkedList<int[]>());
            graph.get(e[0]).add(new int[]{e[1], e[2]});
        }
        
        // visited map to record the #stop of a node when its popped out of the PQ
        Map<Integer, Integer> visited = new HashMap<>();
        // PQ stores each node as int[node, dist, #stops];
        PriorityQueue<int[]> pq = new PriorityQueue<>((e1, e2) -> (e1[1] - e2[1]));
        pq.add(new int[]{src, 0, 0});
        k++;
        while (!pq.isEmpty()) {
            int[] curNode = pq.poll();
            if (curNode[0] == dst) {return curNode[1];}
            if (curNode[2] == k) {continue;}
            // record curNode stop# into visited if smaller, and check its neighbors
            visited.put(curNode[0], curNode[2]);
            if (!graph.keySet().contains(curNode[0])) {continue;}
            for (int[] next: graph.get(curNode[0])) {
                int nextNode = next[0];
                if (visited.keySet().contains(nextNode) && visited.get(nextNode) <= curNode[2] + 1) {continue;}
                pq.add(new int[]{nextNode, curNode[1] + next[1], curNode[2]+1});
            }
            
        }
        return -1;
    }
}


















