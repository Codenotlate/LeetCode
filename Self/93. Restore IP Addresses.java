class Solution {
	// method1: DFS backtracing recursive
	// time O(3^4)
    public List<String> restoreIpAddresses(String s) {
        List<String> res = new LinkedList<>();
        if (s.length() == 0) {return res;}
        StringBuilder prefix = new StringBuilder();
        prefix.append("");
        combine(prefix, s, res, 0);
        return res;
    }

    private void combine(StringBuilder prefix, String s, List<String> res, int count) {
    	// base case
    	if (count == 4 || s.length() == 0) {
	    	if (count == 4 && s.length() == 0) {
	    		// delete the last dot
	    		prefix.deleteCharAt(prefix.length() - 1);
	    		res.add(prefix.toString());
	    	}
	    	return;
	    }
    	int oriLen = prefix.length();

    	for (int k = 1; k <= Math.min(3, s.length()); k++) {
    		String curs = s.substring(0, k);
    		if (curs.charAt(0) == '0' && k != 1) {break;}
    		String nexts = s.substring(k);
    		if (Integer.valueOf(curs) <= 255) {
    			prefix.append(curs).append('.');
    			combine(prefix, nexts, res, count + 1);
    			prefix.delete(oriLen, prefix.length());
    		} 
    	}
    }
}


// brutal force way, split into 4 parts and check if all 4 parts are valid
// https://leetcode.com/problems/restore-ip-addresses/discuss/30949/My-code-in-Java
// another interesting one
// https://leetcode.com/problems/restore-ip-addresses/discuss/30972/WHO-CAN-BEAT-THIS-CODE




// Phase 2 self:
class Solution {
    public List<String> restoreIpAddresses(String s) {
        StringBuilder curStr = new StringBuilder();
        List<String> res = new LinkedList<>();
        dfs(s, 0, curStr, 0, res);
        return res;
    }
    
    private void dfs(String s, int curIdx, StringBuilder curStr, int curSize, List<String> res) {
        if (curSize == 4) {
            if (curIdx == s.length()) {
                curStr.deleteCharAt(curStr.length() - 1);
                res.add(curStr.toString());
            }
            return;       
        }
        
        
        for (int i = 1; i <= 3 && curIdx + i <= s.length(); i++) {
            String next = s.substring(curIdx, curIdx + i);
            if (isValid(next)) {
                int oriLen = curStr.length();
                curStr.append(next + ".");
                dfs(s, curIdx + i, curStr, curSize + 1, res);
                curStr.delete(oriLen, curStr.length());
            }
        }
    }
    
    private boolean isValid(String next) {
        if (next.length() != 1 && next.charAt(0)=='0' || Integer.valueOf(next) > 255) {return false;}
        return true;
    }
}



// Phase 3 self

class Solution {
    public List<String> restoreIpAddresses(String s) {
        //Phase 3 self : DFS + backtracking
        List<String> res = new LinkedList<>();
        if (s.length() < 4) {return res;}
        StringBuilder curStr = new StringBuilder();
        dfs(s, 0, res, curStr,0);
        return res;
    }
    
    private void dfs(String s, int curPos, List<String> res, StringBuilder curStr, int curCount) {
        // base case
        // pay attention to this end condition
        if (curCount >= 4 || curPos >= s.length()) {
            if (curCount == 4 && curPos == s.length()) {
                curStr.deleteCharAt(curStr.length() - 1);
                res.add(curStr.toString());
            }
            return;
        }
        
        // backtracking
        // don't forget curPos + i <= s.length() condition
        for (int i = 1; i <= 3 && curPos + i <= s.length(); i++) {
            String part = s.substring(curPos, curPos + i);
            if (isValid(part)) {
                int oriSize = curStr.length();
                curStr.append(part);
                curStr.append('.');
                dfs(s, curPos + i, res, curStr, curCount + 1);
                curStr.delete(oriSize, curStr.length());
            }
        }
    }
    
    private boolean isValid(String s) {
        if (s.length() != 1 && s.charAt(0) == '0' || s.length() > 3) {return false;}
        return Integer.valueOf(s) <= 255;
        
    }
}






// Phase 3 self - new String.join() method introduced, but this requires the list to be of strings
// https://stackoverflow.com/questions/1751844/java-convert-liststring-to-a-joind-string
class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<String> res = new LinkedList<>();
        List<String> curList = new LinkedList<>();
        dfs(s, 0, curList, res);
        return res;
    }
    
    private void dfs(String s, int curIdx, List<String> curList, List<String> res) {
        // base case
        if (curList.size() == 4) {
            if (curIdx == s.length()) {
                res.add(String.join(".",curList));
            }
            return;
        }
        
        for (int i = 1; i <= 3 && curIdx + i <= s.length(); i++) {
            String cur = s.substring(curIdx, curIdx + i);
            if (cur.charAt(0) == '0' && cur.length() >1 || Integer.valueOf(cur) > 255) {
                continue;
            }
            curList.add(cur);
            dfs(s, curIdx + i, curList, res);
            curList.remove(curList.size() - 1);
        }
    }
}















