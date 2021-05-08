// This is a multi-source graph shortest path problem, so Floyd Warshall will be a better way compared to Dijkstra


// phase2 self: try Dijkstra for each node 
// Time O(V * ElogE) as E = O(v^2) then time is O (V^3logV) for E version
class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        // build the graph, note that we can directly discard those edges with weight > threshold already
        Map<Integer, ArrayList<int[]>> graph = new HashMap<>();
        for (int[] edge: edges) {
        	if (edge[2] > distanceThreshold) {continue;}
        	graph.putIfAbsent(edge[0], new ArrayList());
        	graph.putIfAbsent(edge[1], new ArrayList());
        	graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        	graph.get(edge[1]).add(new int[]{edge[0], edge[2]});
        }

        // do dijkstra for each node in the graph, and track the node with smallest numbers of valid neighbors
        int minNum = n;
        int resNode = -1;

        for (int i = 0; i < n; i++) {
        	int curNum = dijkstra(graph, i, distanceThreshold);
        	
        	if (curNum <= minNum) {
        		minNum = curNum;
        		resNode = i;
        	}
        }

        return resNode;
    }

    
    private int dijkstra(Map<Integer, ArrayList<int[]>> graph, int src, int thresh) {
    	Set<Integer> visited = new HashSet<>();
    	int count = 0;

    	PriorityQueue<int[]> pq = new PriorityQueue<>((e1, e2) -> (e1[1] - e2[1]));
    	pq.add(new int[]{src, 0});

    	while (!pq.isEmpty()) {
    		int[] cur = pq.poll();
    		int node = cur[0];
    		int dist = cur[1];
    		if (visited.contains(node) || dist > thresh) {continue;}
    		visited.add(node);
    		count++;
    		if (graph.keySet().contains(node)) {
    			for (int[] next: graph.get(node)) {
    				pq.add(new int[]{next[0], next[1] + dist});
    			}
    		}  		
    	}
    	return count;
    }

}





// Floyd Warshall algo: https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
// self phase2: FW way
// time O(V^3) Space O(V^2)

class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        // use this matrix to store dist for (i, j) pair
        int[][] dist = new int[n][n];
        // initialize to inf and (i, i) to 0
        for (int i = 0; i < n; i++) {
            // if we initialize this to MAX_VALUE, we need to check in the below FW algo loop
            // otherwise we may have a overflow
            Arrays.fill(dist[i], Integer.MAX_VALUE);
            // if initialize to 10^4 + 1, then no overflow risk
            dist[i][i] = 0;
        }

        
        // initialize dist with no node as intermediate to edges and (i, i) to 0
        for (int[] e: edges) {
            // since this is a undirected graph
            dist[e[0]][e[1]] = dist[e[1]][e[0]] = e[2];
        }
        
        // start the FW algo
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // we need to check this, otherwise we may have a overflow
                    if (dist[i][k] == Integer.MAX_VALUE || dist[k][j] == Integer.MAX_VALUE) {continue;}
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        // we get shortest path for all node pairs in the graph
        int minNum = n;
        int resNode = -1;

        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (dist[i][j] <= distanceThreshold) {count++;}
            }
            
            if (count <= minNum) {
                minNum = count;
                resNode = i;
            }
        }

        return resNode;

    }
}







