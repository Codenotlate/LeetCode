class Solution {
    public int countBinarySubstrings(String s) {
        // iterate through string and find each 01 or 10 pair, then expand.
        int count = 0;
        for (int i = 0; i < s.length() - 1; i++) {
        	if (s.charAt(i) == '0' && s.charAt(i + 1) == '1' || 
        		(s.charAt(i) == '1' && s.charAt(i + 1) == '0')) {
        		int j = i;
        		int k = i + 1;
        		while (j >= 0 && k < s.length() && (s.charAt(j) == s.charAt(i)) && (s.charAt(k) == s.charAt(i + 1))) {
        			count++;
        			j -= 1;
        			k += 1;
        		}
        	}
        }
        return count;
    }


}



class Solution {
    // count number of consecutive same number and once change to another number part, add min(pre, cur) to res
	public int countBinarySubstrings(String s) {
        int cur = 1, pre = 0, res = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) cur++;
            else {
                res += Math.min(cur, pre);
                pre = cur;
                cur = 1;
            }
        }
        return res + Math.min(cur, pre);
    }
}


// phase3 self
class Solution {
    public int countBinarySubstrings(String s) {
        // the main idea: find the 01 or 10 position and extend them
        int count = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) != s.charAt(i + 1)) {
                count += extend(s, i, i+1);
            }
        }
        return count;
    }
    
    private int extend(String s, int start, int end) {
        int count = 0;
        char left = s.charAt(start);
        char right = s.charAt(end);
        while (start >= 0 && end < s.length() && s.charAt(start) == left && s.charAt(end) == right) {
            start--;
            end++;
            count++;
        }
        return count;
    }
}