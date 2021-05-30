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