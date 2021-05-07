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



* comparison with Dijkstra
   * can deal with graph having negative edge
   * time complexity is O(VE), slower than regular Dijkstra







* understand BellmanFord algo for 787
* understand BFS/DFS for 787
* why dijkstra is not the best for 787
* self summary the time complexity and all 4 methods

* understand and implement Floyd algo for 1334