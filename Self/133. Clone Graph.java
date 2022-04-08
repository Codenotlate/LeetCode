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





// Phase3 self
class Solution {
    // use dfs to traverse the original tree and then copy to new tree
    // also the map can be adjust to Integer->Node like above method to speed up
    public Node cloneGraph(Node node) {
        Map<Node, Node> map = new HashMap<>();
        return dfs(node, map);
    }
    
    private Node dfs(Node oldnode, Map<Node, Node> map) {
        if(oldnode == null) {return null;}
        if(map.keySet().contains(oldnode)) {return map.get(oldnode);}
        Node newnode = new Node(oldnode.val);
        map.put(oldnode, newnode); // note this line should be put before next dfs level to speed up
        for (Node oldnext: oldnode.neighbors) {
            newnode.neighbors.add(dfs(oldnext, map));
        }
        
        return newnode;
    }
}

class Solution {
    // similar to dfs, use bfs here, above bfs way is optimized
    public Node cloneGraph(Node node) {
        if (node == null) {return null;}
        Queue<Pair<Node,Node>> queue = new LinkedList<>();
        Map<Integer, Node> visited = new HashMap<>();
        Node newnode = new Node(node.val);
        queue.add(new Pair(node, newnode));
        visited.put(node.val, newnode);
        while (!queue.isEmpty()) {
            Pair<Node, Node> curPair = queue.poll();
            Node oldcur = curPair.getKey();
            Node newcur = curPair.getValue();
            for (Node oldnext: oldcur.neighbors) {
                if (!visited.keySet().contains(oldnext.val)) {
                    Node newnext = new Node(oldnext.val);
                    queue.add(new Pair(oldnext, newnext));
                    visited.put(oldnext.val, newnext);
                } 
                newcur.neighbors.add(visited.get(oldnext.val));
                
            }
        }
        return newnode;
        
    }
}








// Review
/* Thought

The traverse of the graph can be BFS or DFS. Since we need to do deep copy, we need to maintain a map between the old node and its corresponding new node. time O(n) space O(n) for the map
*/
//M1: BFS
class Solution {
    public Node cloneGraph(Node node) {
        if(node == null) {return node;}
        Queue<Node> queue = new LinkedList<>();
        Map<Node, Node> map = new HashMap<>();
        queue.add(node);
        Node newRoot = new Node(node.val);
        map.put(node, newRoot);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            Node copycur = map.get(cur);
            for (Node next: cur.neighbors) {
                if (map.containsKey(next)) {
                    Node copynext = map.get(next);
                    copycur.neighbors.add(copynext);
                } else {
                    Node copynext = new Node(next.val);
                    copycur.neighbors.add(copynext);
                    map.put(next,copynext);
                    queue.add(next);
                }
            }
        }
        return newRoot;
    }
}
//M2: DFS
class Solution {
    public Node cloneGraph(Node node) {
        Map<Node, Node> map = new HashMap<>();
        return clone(node, map);
    }
    
    private Node clone(Node node, Map<Node, Node> map) {
        if (node == null) {return null;}
        Node newRoot = new Node(node.val);
        map.put(node, newRoot);
        for (Node next:node.neighbors) {
            if(map.containsKey(next)) {
                Node copynext = map.get(next);
                newRoot.neighbors.add(copynext);
            } else {
                newRoot.neighbors.add(clone(next, map));
            }
        }
        return newRoot;
    }
}

















