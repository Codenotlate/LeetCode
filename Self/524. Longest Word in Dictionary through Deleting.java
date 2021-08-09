class Solution {
	// straight forward way (most efficient way)
	// check each str in d, see if it's a deleted substring of s
	// if it is, update the result with desired order
    public String findLongestWord(String s, List<String> d) {
        String res = "";
        if (s.length() == 0) {return res;}
        for (String sub: d) {
        	if (isSubStr(s, sub)) {
        		if (sub.length() > res.length() || 
        			(sub.length() == res.length() && sub.charAt(0) < res.charAt(0))) {
        			res = sub;
        		}
        	}
        }
        return res;
    }


    private boolean isSubStr(String s, String sub) {
    	if (sub.length() == 0) {return false;}
    	int p1 = 0, p2 = 0;
    	while (p1 < s.length() && p2 < sub.length()) {
    		if (s.charAt(p1) == sub.charAt(p2)) {p2++;}
    		p1++;
    	}
    	return p2 >= sub.length();
    }
}


class Solution {
	// basically same as above
	// some minor improvement
    public String findLongestWord(String s, List<String> d) {
        String res = "";
        if (s.length() == 0) {return res;}
        for (String sub: d) {
        	if (isBetter(sub, res) && isSubStr(s, sub)) {
        		res = sub;
        	}
        }
        return res;
    }


    private boolean isBetter(String a, String b) {
	    return a.length() > b.length() ||
	           a.length() == b.length() && a.compareTo(b) < 0;
	}

	// indexOf is faster than self implemented two pointers method
	// according to https://leetcode.com/problems/longest-word-in-dictionary-through-deleting/discuss/99611/Fast-java-solution-19ms-beats-97-using-indexOf
	// discussion part of setfanpochmann, the intrinsic method in Java is faster
	// but this can't improve time complexity
    private boolean isSubStr(String a, String b) {
	    int start = -1;
	    for (int i = 0; i < b.length(); i++){
	        start = a.indexOf(b.charAt(i), start + 1);
	        if (start < 0)
	            return false;
	    }
	    return true;
	}
}


// less efficient way: sort first
// the way to write comparator
// Collections.sort(d, (a,b) -> a.length() != b.length() ? -Integer.compare(a.length(), b.length()) :  a.compareTo(b));
// or
/**
Collections.sort(d, new Comparator < String > () {
    public int compare(String s1, String s2) {
        return s2.length() != s1.length() ? s2.length() - s1.length() : s1.compareTo(s2);
    }
});
*/







// phase3 self
class Solution {
    public String findLongestWord(String s, List<String> dictionary) {
        // main idea: check each d in dict to see if it's a substr of s
        String res = "";
        for (String d: dictionary) {
            // if d is in worse order compare to current result, then no need to check if it's substr of s
            if (isBetter(d, res) && isSubstr(d, s)) {
                res = d;
            }
        }
        return res;
    }
    
    // return whether d is in preferred order than res
    private boolean isBetter(String d, String res) {
        return d.length() > res.length() || d.length() == res.length() && d.compareTo(res) < 0;
    }
    
    private boolean isSubstr(String d, String s) {
        if (d.length() > s.length()) {return false;}
        int pd = 0;
        int ps = 0;
        while (pd < d.length() && ps < s.length()) {
            if (d.charAt(pd) == s.charAt(ps)) {pd++;}
            ps++;
        }
        return pd == d.length();
    }
}