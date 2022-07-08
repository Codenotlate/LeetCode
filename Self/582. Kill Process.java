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

// several ways in solution to check later
/*
Here are some good points to clarify with the interviewer, before jumping into coding.

What should we do if there is a cycle in the input?
Can there be a self-loop, such that pid[i] == ppid[i]?
What should we do if there is an orphaned process in the input? e.g. pid[i] does not exist in pid?
After clarification, write down the assumptions and only then start coding. Ensure that your code follows the assumption and handles any special cases accordingly.

Agreed. Simply solving the problem out of the gate (considering it's pretty straightforward) won't provide the interviewer much insight into your thought process and how you go about solving a problem. Probably a good habit to have when preparing for system design too.


*/