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















