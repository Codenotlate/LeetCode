// Phase2: Dijkstra's Algo using heap(PQ)
class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        // build a graph 
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
        	graph[i] = new ArrayList<>();
        }
        for (int[] e: times) {
        	graph[e[0]].add(new int[]{e[1], e[2]});
        }

        //Dijkstra algo
        Map<Integer, Integer> dist = new HashMap<>();
        // PQ always poll the node with smallest dist in the queue
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(
        	(edgeDist1, edgeDist2) -> (edgeDist1[1] - edgeDist2[1])
        );
        int longestDist = 0;

        pq.add(new int[]{k, 0});
        while (!pq.isEmpty()) {
        	int[] cur = pq.poll();
        	int curDist = cur[1];
        	int curNode = cur[0];
            if (dist.keySet().contains(curNode)) {continue;}
        	dist.put(curNode, curDist);
        	longestDist = Math.max(longestDist, curDist);
        	// check whether all nodes are visited, stop the process early
        	if (dist.size() == n) {return longestDist;}

        	for (int[] nextEdge: graph[curNode]) {
        		if (dist.keySet().contains(nextEdge[0])) {continue;}
        		pq.add(new int[]{nextEdge[0], curDist + nextEdge[1]});
        	}
        }

        
        return -1;
    }
}

// https://leetcode.com/problems/network-delay-time/discuss/210698/Java-Djikstrabfs-Concise-and-very-easy-to-understand
// another Nellman-Ford in Java see comment (O (VE)), can be slower than Dijkstra
// https://leetcode.com/problems/network-delay-time/discuss/109982/C%2B%2B-Bellman-Ford
// for why outer loop n times: If you think about it, in any graph (w/o a negative cycle) the shortest path from any u to v has at most (n-1) edges.




// phase2: self (replace the map dist in m1 with a simple visited array, cause it's no need to track the dist)
class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        // use dijkstra algo
        // build the graph
        Map<Integer, ArrayList<int[]>> graph= new HashMap<>();
        for (int[] e: times) {
            graph.putIfAbsent(e[0], new ArrayList<>());
            graph.get(e[0]).add(new int[]{e[1], e[2]});
        }
        
        // build the PQ for dijkstra, visited to record all visited nodes
        PriorityQueue<int[]> pq = new PriorityQueue<>((e1, e2) -> (e1[1] - e2[1]));
        // Map<Integer, Integer> dist = new HashMap<>(); 
        // we actually don't need to record the dist, casue it's dijkstra, it guarantees for the same node, the first time it's polled out of the pq, that is its shortest path from source node.
        int[] visited = new int[n + 1];
        int longestDist = 0;
        int countVisited = 0;
        
        
        pq.add(new int[]{k, 0});
        
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            if (visited[cur[0]] == 1) {continue;}
            visited[cur[0]] = 1;
            countVisited++;
            longestDist = Math.max(longestDist, cur[1]);
            if (countVisited == n) {return longestDist;}
            if (graph.keySet().contains(cur[0])) {
                for (int[] next: graph.get(cur[0])) {
                    if (visited[next[0]] == 1) {continue;}
                    pq.add(new int[]{next[0], next[1] + cur[1]});
                }
            }            
        }          
        
        // return result based on visited set
        return -1;
    }
}












