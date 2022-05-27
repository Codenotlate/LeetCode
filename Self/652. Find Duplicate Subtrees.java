
/*Thought
M1: postorder to build the tree String, put into a map with string as a key and count as value,add to res when a string has count == 2.
time O(n^2) The inner O(n) time comes from hashing a string with length n in the map to update or get count.

M2: can improve M1 to O(n), try to eliminate the hash part time to O(1) by using number id representing string.
Instead of put all nodes from left and right subtrees and add cur val, which can be length n. we can set an id for each subtree serial, then current serial becomes (cur.val#leftid#rightid), which can have O(1) length. Thus we need two maps, one map for <STring serial O(1) len, int id>, and another for <int id, int count> which makes the hashing part O(1) and overall time O(n).

space for both ways are O(n)
*/
// M1: O(n^2) time
class Solution {
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        Map<String, Integer> map = new HashMap<>();
        List<TreeNode> res = new LinkedList<>();
        searchTree(root, map, res);
        return res;
    }
    
    private String searchTree(TreeNode root, Map<String, Integer> map, List<TreeNode> res) {
        if (root == null) {return "n";}
        StringBuilder serial = new StringBuilder(root.val + "#");
        serial.append(searchTree(root.left, map, res));
        serial.append(searchTree(root.right, map, res));
        String serialStr = serial.toString();
        int oriCount = map.getOrDefault(serialStr,0);
        map.put(serialStr, oriCount + 1);
        if (oriCount == 1) {res.add(root);}
        return serialStr;
    }
}


// M2 time O(n) way
class Solution {
    private int curid = 1;
    
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        List<TreeNode> res = new LinkedList<>();
        Map<String, Integer> strToId = new HashMap<>();
        Map<Integer, Integer> idCount = new HashMap<>();
        searchTree(root, strToId, idCount, res);
        return res;
    }
    
    private int searchTree(TreeNode root, Map<String, Integer> strToId, Map<Integer,Integer> idCount, List<TreeNode> res) {
        if (root ==null) {return 0;}
        int leftid = searchTree(root.left, strToId, idCount, res);
        int rightid = searchTree(root.right, strToId, idCount, res);
        String curStr = root.val + "#" + leftid + "#"+rightid;
        int strId = strToId.getOrDefault(curStr, curid);
        if (strId == curid) {curid++; strToId.put(curStr, strId);}
        idCount.put(strId, idCount.getOrDefault(strId,0)+1);
        if (idCount.get(strId) == 2) {res.add(root);}
        return strId;
    }
}