/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

// Phase 2 self: DFS
class Solution {
    public Node copyRandomList(Node head) {
        Map<Node, Node> visited = new HashMap<>();
        return dfs(head, visited);
    }

    private Node dfs(Node head, Map<Node, Node> visited) {
        if (head == null) {return null;}
        if (visited.keySet().contains(head)) {return visited.get(head);}
        Node copy = new Node(head.val);
        visited.put(head, copy);
        copy.next = dfs(head.next, visited);
        copy.random = dfs(head.random, visited);
        return copy;
    }
}


// Phase 2 self: BFS
class Solution {
    public Node copyRandomList(Node head) {
        if (head == null) {return null;}
        Map<Node, Node> visited = new HashMap<>();
        Node copy = new Node(head.val);
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        visited.put(head, copy);

        while (!queue.isEmpty()) {
            Node origin = queue.poll();
            Node related = visited.get(origin);
            if (!visited.keySet().contains(origin.next) && origin.next != null) {
                queue.add(origin.next);
                visited.put(origin.next, new Node(origin.next.val));
            }
            if (!visited.keySet().contains(origin.random) && origin.random != null) {
                queue.add(origin.random);
                visited.put(origin.random, new Node(origin.random.val));
            }
            related.next = visited.get(origin.next);
            related.random = visited.get(origin.random);
        }
        return copy;
    }
}

// Phase3 takes a long time to debug. "void" may not work, the node change may not be deep change. Thus change to return node directly. 
class Solution {
    public Node copyRandomList(Node head) {
        Map<Node, Node> map = new HashMap<>();
        return dfs(head, map);
    }
    
    private Node dfs(Node oriCur, Map<Node, Node> map) {
        if (oriCur == null) {return null;}
        Node newCur = new Node(oriCur.val);
        map.put(oriCur, newCur);
        if (map.keySet().contains(oriCur.next)) {newCur.next = map.get(oriCur.next);}
        else {newCur.next = dfs(oriCur.next, map);}
        if (map.keySet().contains(oriCur.random)) {newCur.random = map.get(oriCur.random);}
        else {newCur.random = dfs(oriCur.random, map);}
        return newCur;
    }
}