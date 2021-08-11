// Phase3 self

// similar to 49, the key is also to find a unique code for each group
// in this problem, we use the diff between two adjacent chars
// time O(nk) space (nk)

class Solution {
    public List<List<String>> groupStrings(String[] strings) {
        Map<String, List<String>> map = new HashMap<>();

        for (String s: strings) {
        	String code = getCode(s);
        	map.putIfAbsent(code, new ArrayList<>());
        	map.get(code).add(s);
        }

        return new ArrayList(map.values());
    }


    private String getCode(String s) {
    	int n = s.length();
    	StringBuilder sb = new StringBuilder();
    	for (int i = 1; i < n; i++) {
    		int diff = (s.charAt(i) - s.charAt(i - 1));
    		// don't forget to adjust those < 0 within the range of 0 to 25
    		if (diff < 0) {diff += 26;}
    		sb.append('#');
    		sb.append(diff);
    	}

    	return sb.toString();
    }
}


// self review
class Solution {
    public List<List<String>> groupStrings(String[] strings) {
        Map<String, List<String>> map = new HashMap<>();
        
        for (String str: strings) {
            String diffKey = getDiffStr(str);
            map.putIfAbsent(diffKey, new ArrayList<>());
            map.get(diffKey).add(str);
        }
        
        return new ArrayList(map.values());
    }
    
    
    private String getDiffStr(String s) {
        StringBuilder res = new StringBuilder();
        res.append('#');
        char prev = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            int diff = (s.charAt(i) - prev + 26) % 26;
            res.append(diff).append('#');
            prev = s.charAt(i);
        }
        return res.toString();
    }
} 