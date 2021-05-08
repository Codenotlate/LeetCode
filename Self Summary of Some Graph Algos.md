# Self Summary of Some Graph Algos
---
## Algos used for single source shortest path

---
### Dijkstra Algo - used for ***non-negative*** weighted graph
[Dijkstra time complexity](https://www.cnblogs.com/gaochundong/p/dijkstra_algorithm.html)
* time complexity depends on how to implement the algo
   * if not using heap, then O(V^2)
   * if using binary heap, then O(E * lg V) 
      * Note: O(ElgE) is till O(ElgV)
      * This is the most common one used
   * if using Fibonacci heap, then worst case O(E + VlgV)
      * didn't know the detail for now (assume not necessary now)

* In regular dijkstra, we will stop putting the next edges of the current node into the pq once it's been visited.

* Note Dijkstra only works for ***non-negative*** weighted graph.




---
### Bellman-Ford Algo - used for general weighted graph (edge can be negative weight)

* pseudo code as below
```
# Do relaxation for each edge (n - 1) times
while i <= n - 1:
	for u, v in Edge( u -> v)
		if u.distance + w( u -> v ) < v.distance
			v.distance = u.distance + w( u -> v )
			v.previous = u
	i++

# Judge whether there is no negative weight circles in the graph
for u, v in Edge( u -> v)
	if v.distance > u.distance + w( u -> v )
		return False # there is a negative weighted circle
return True # there is no such circle
```



* Detail explanation - why only n - 1 loops are required and how to detact negative edge
[Detail](https://www.sining.io/2018/04/30/understanding-bellman-ford-algorithm/)
   * one way to understand this algo, is that it uses idea of dynamic programing. For ith loop, it guarantees the shortest path of thoes nodes that is i edges from source node, based on the (i-1)th nodes' shortest path(which is finalized).
   * note if we care about how may round of loops has finished, in each round of loop, we need to copy a temp array of current dist array. We update temp array based on values in dist array, so that we can only update the distance of node i edges from src in the i-th round of loop.



* comparison with Dijkstra
   * can deal with graph having negative edge
   * time complexity is O(VE), slower than regular Dijkstra


<br>

---
## Algo used for multi-sources shorted path
---
### Floyd-Warshall Algo
[wikipedia](https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm)

* Pseudocode
```
let dist be a |V| × |V| array of minimum distances initialized to ∞ (infinity)
for each edge (u, v) do
    dist[u][v] ← w(u, v)  // The weight of the edge (u, v)
for each vertex v do
    dist[v][v] ← 0
for k from 1 to |V|
    for i from 1 to |V|
        for j from 1 to |V|
            if dist[i][j] > dist[i][k] + dist[k][j] 
                dist[i][j] ← dist[i][k] + dist[k][j]
            end if
```
* key logic
> * 不经过节点k时，从i到j的最短距离即为shortestPath(i, j, k – 1) ；
> * 经过节点k时，从i到j的最短距离是从i到k的最短距离加上从j到k的最短距离，即shortestPath(i, k, k - 1) + shortestPath(k, j, k - 1) 。

* Why k should be the most outer loop:
> d[i][j][k] = shortest path from i -> j and intermediates from: [0 - k]
> init: d[i][j][-1] = graph[i][j], direct edges

> relation:
>       d[i][j][k] = min( d[i][j][k-1], d[i][k][k-1] + d[k][j][k-1] )
>                  = min( not pick k , pick k )

> because d[i][j][k] only depends on d[i][j][k-1] -> can use 2-d array and k must be outermost loop

<br>

* Note: 
    * This algo can be used for weighted graph with negative edges, but can't used for weighter graph with negative cycles.  Though it can be used to detect whether there's negative cycles exist. (checking whether shortesPath(i, i) < 0)
    * This algo uses Dynamic programming idea. Can be understand as
       1. k = 1, we update the path of all (i, j) pairs that can use node 1 as intermediate node.
       2. k = 2, we update the path of all (i, j) pairs that can use up to node 1 and node 2 as intermediate node
       3. ......
       4. k = V, we update the path of all (i, j) pairs that can can use up to node1, node2, ... node V (all nodes) as intermediate node.


* Complexity
   * Time - O(V^3)
   * Space - O(v^2)

* sample problem (1334)

<br>

---
## Using Other regular algos (DFS / BFS) to find shortest path
---

### See BFS & DFS in 787.
[solution](https://leetcode.com/problems/cheapest-flights-within-k-stops/discuss/361711/Java-DFSBFSBellman-Ford-Dijkstra's)
* Time O(V^V)

---

* understand and implement Floyd algo for 1334