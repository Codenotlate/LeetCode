// This method  TLE
// too hard skip for now
class Solution {
    public String shortestSuperstring(String[] words) {
        StringBuilder curStr = new StringBuilder();
        int[] visited = new int[words.length];
       	String[] res = new String[1];
       	int[] minLength = new int[]{Integer.MAX_VALUE};
       	permutation(words, visited, curStr, res, minLength, 0);
       	return res[0];
    }

    private void permutation(String[] words, int[] visited, StringBuilder curStr, String[] res, int[] minLength, int addCount) {
    	if (addCount == words.length) {
    		if (curStr.length() < minLength[0]) {
    			minLength[0] = curStr.length();
    			res[0] = curStr.toString();
    		}
    		return;
    	}

    	for (int i = 0; i < words.length; i++) {
    		if (visited[i] != 1) {
    			visited[i] = 1;
    			int startAddPos = addPos(curStr, words[i]);
    			int oriSize = curStr.length();
    			if (startAddPos != -1) {
    				for (int k = startAddPos; k < words[i].length(); k++) {
	    				curStr.append(words[i].charAt(k));
	    			}
    			}
       			permutation(words, visited, curStr, res, minLength, addCount + 1);
    			// backtracking
    			curStr.delete(oriSize, curStr.length());
    			visited[i] = 0;
    		}
    	}
    }

    private int addPos(StringBuilder s1, String s2) {
    	int i = 0;
    	int j = 0;
    	while (i < s1.length()) {
    		if (s1.charAt(i) != s2.charAt(j)) {
    			i++;
    			continue;
    		}
    		int i_inside = i;
    		int j_inside = j;
    		while (s1.charAt(i_inside) == s2.charAt(j_inside)) {
    			i_inside++; 
    			j_inside++;
    			if (j_inside >= s2.length()) {return -1;} // meaning s2 is substring of s1 totally
    			if (i_inside >= s1.length()) {return j_inside;}
    		}
    		i++;
    	}
    	return j;
    }
}












