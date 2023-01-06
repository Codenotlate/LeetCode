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




// Reveiw
/*Initial thought
This is a graph problem. each node has at most two neighbors. And we need to deep copy this graph. Meaning we need to search the whole graph, and at the same time maintain the map between old node and new node, and copy each edge and node in the new graph.
*/
// BFS way
class Solution {
    public Node copyRandomList(Node head) {
        if (head == null) {return null;}
        Map<Node, Node> map = new HashMap<>(); // its keyset can also be used as visited set
        Node copyhead = new Node(head.val);
        map.put(head, copyhead);
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        while(!stack.isEmpty()) {
            Node cur = stack.pop();
            if (cur.next != null) {
                if(!map.containsKey(cur.next)) {
                    Node nextcopy = new Node(cur.next.val);
                    stack.push(cur.next);
                    map.put(cur.next, nextcopy);
                }               
                map.get(cur).next = map.get(cur.next);                
            }
            if (cur.random != null) {
                if(!map.containsKey(cur.random)) {
                    Node randomcopy = new Node(cur.random.val);
                    map.put(cur.random, randomcopy);
                    stack.push(cur.random);
                }
                map.get(cur).random = map.get(cur.random);                
            }
        }
        return map.get(head);
    }
}

// DFS way - need further practice
class Solution {
    public Node copyRandomList(Node head) {
        Map<Node, Node> map = new HashMap<>(); 
        return dfs(head, map);
    }
    
    private Node dfs(Node head, Map<Node, Node> map) {
        if (head == null) {return null;}
        if(map.containsKey(head)) {return map.get(head);}
        Node copyHead = new Node(head.val);
        map.put(head, copyHead);
        copyHead.next = dfs(head.next, map);
        copyHead.random = dfs(head.random, map);
        return copyHead;
    }
}




// Review 23/1/5
/* Earlier view this as a graph and traverse it. This time try M2 and M3.
M2: view it directly as a LL. two similar ways, both O(n) space since we need to maintain a map from old node to new node.
    M2.1 two pass. First pass, go throught all nodes, and build all new nodes and recorded in the map. Second pass address al the random pointers using the map to find corresponding new nodes.
    M2.2 one pass. For each node, if not in the map already, build the new node and put into map. Otherwise directly get the corresponding new node from the map. Similar logic applies to the nodes random/next pointers pointing to. Mimic the connecting ways in the new LL.

M3: O(1) way. Three pass. First pass, build new nodes and put them in between old nodes. Second pass, address random pointers. Third pass, revert all next pointers adjusted in the first pass, and address the next pointers for the new LL.


 */
// M2.2
class Solution {
    public Node copyRandomList(Node head) {
        Map<Node, Node> map = new HashMap<>();
        Node oldhead = head;
        while(head != null) {
            if (!map.containsKey(head)) {
                map.put(head, new Node(head.val));
            }
            Node newhead = map.get(head);
            Node oldRandom = head.random;
            Node oldNext = head.next;
            if (oldRandom != null && !map.containsKey(oldRandom)) {
                map.put(oldRandom, new Node(oldRandom.val));
            }
            if (oldNext != null && !map.containsKey(oldNext)) {
                map.put(oldNext, new Node(oldNext.val));
            }
            if (oldRandom != null) {
                newhead.random = map.get(oldRandom);
            }
            if (oldNext != null) {
                newhead.next = map.get(oldNext);
            }
            head = head.next;
        }
        return map.get(oldhead);
    }
}

// M3
class Solution {
    public Node copyRandomList(Node head) {
        // first pass
        Node cur = head;
        while(cur != null) {
            Node newnode = new Node(cur.val);
            newnode.next = cur.next;
            cur.next = newnode;
            cur = newnode.next;
        }
        // second pass
        cur = head;
        while(cur != null) {
            Node oldRandom = cur.random;
            // don't forget to check null here
            if (oldRandom != null) {cur.next.random = oldRandom.next;}           
            cur = cur.next.next;
        }
        // third pass
        cur = head;
        Node dummy = new Node(-1);
        Node newCur = dummy;
        while(cur != null) {
            newCur.next = cur.next;
            newCur = newCur.next;
            cur.next = cur.next.next;
            newCur.next = null;
            cur = cur.next;
        }
        return dummy.next;
    }
}


















