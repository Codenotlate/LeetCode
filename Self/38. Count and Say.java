// phase 3 self

class Solution {
    public String countAndSay(int n) {
        if (n == 1) {return "1";}
        String res = "1";

        for (int i = 1; i < n; i++) {
        	res = say(res);
        }
        return res;
    }

    private String say(String s) {
    	if (s.length() == 1) {return "1" + s;}

    	StringBuilder res = new StringBuilder();

    	int count = 1;
    	for (int i = 1; i < s.length(); i++) {
    		if (s.charAt(i) == s.charAt(i - 1)) {count++;}
    		else {
    			res.append(count);
    			res.append(s.charAt(i - 1));
    			count = 1;
    		}
    	}
    	// don't forget to add the last part
    	res.append(count);
    	res.append(s.charAt(s.length() - 1));
    	return res.toString();
    }
}


// self review
class Solution {
    public String countAndSay(int n) {
        if (n == 1) {return "1";}
        String s = "1";
        for (int i = 1; i < n; i++) {
            s = say(s);
        }
        return s;
    }
    
    private String say(String s) {
        int count = 1;
        int i = 1;
        StringBuilder res = new StringBuilder();
        while (i < s.length()) {
            // remember to check i < s.len for every loop
            while (i < s.length() && s.charAt(i) == s.charAt(i - 1)) {
                count++;
                i++;
            }
            res.append(count).append(s.charAt(i - 1));
            count = 1;
            i++;
        }
        // note special case,when i = len + 1
        if (i == s.length()) {
            res.append(count).append(s.charAt(i - 1));
        }        
        return res.toString();
    }
}