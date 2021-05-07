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














