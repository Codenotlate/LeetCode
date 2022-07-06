/*Thought
Basically want to find all nodes in the subtree with kill node as the root node.
Go through the two lists, build the map representing the parent node with the list of its children nodes.
The dfs starting the kill node.
time O(n) space O(n)
edge cases: if kill node not contained in the map, meaning it has no child, return itself.

*/
class Solution {
    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < pid.size(); i++) {
            map.putIfAbsent(ppid.get(i), new LinkedList<>());
            map.get(ppid.get(i)).add(pid.get(i));
        }
        List<Integer> res = new LinkedList<>();
        getNodes(map, kill, res);
        return res;
    }
    
    private void getNodes(Map<Integer, List<Integer>> map, int kill, List<Integer> res) {
        if (!map.containsKey(kill)) {res.add(kill); return;}
        res.add(kill);
        for (int next: map.get(kill)) {
            getNodes(map, next, res);
        }
    }
}