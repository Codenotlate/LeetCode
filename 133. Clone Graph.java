/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
*/

// phase 2, self, DFS way
class Solution {
    public Node cloneGraph(Node node) {
        Map<Integer, Node> visited = new HashMap<>();
        return dfs(node, visited);
    }

    private Node dfs(Node node, Map<Integer, Node> visited) {
        if (node == null) {return null;}
        if (visited.keySet().contains(node.val)) {return visited.get(node.val);}
        Node copy = new Node(node.val);
        visited.put(node.val, copy);
        for (Node neighbor: node.neighbors) {
            Node copyNeighbor = dfs(neighbor, visited);
            if (copyNeighbor != null) {
                copy.neighbors.add(copyNeighbor);
            }
        }
        return copy;
    }
}



// self BFS way:
class Solution {
    public Node cloneGraph(Node node) {
        if (node == null) {return null;}
        Map<Integer, Node> visited = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        Node copy = new Node(node.val);
        queue.add(node);
        visited.put(node.val, copy);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            Node related = visited.get(cur.val);
            for (Node neighbor: cur.neighbors) {
                if (!visited.keySet().contains(neighbor.val)) {
                    queue.add(neighbor);
                    visited.put(neighbor.val, new Node(neighbor.val));

                } 
                related.neighbors.add(visited.get(neighbor.val));
            }
        }
        return copy;


    }
}



























