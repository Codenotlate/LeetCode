// Phase3 solution 
// M1 using recursive way
// in each recursion find the smallest possible char that has all chars included in following substring, and use that char as the leftmost char in the result. 
// recursive call on its substring after removing all leftmost char
// time O(n * 26) = O(n)
// space O(n * 26 ) = O(n)

class Solution {
    public String removeDuplicateLetters(String s) {
    	// don't forget base case
    	if (s.length() == 0) {return "";}

        int[] count = new int[26];
        for (char c: s.toCharArray()) {
        	count[c - 'a']++;
        }
        int leftMostPos = 0;
        for (int i = 0; i < s.length(); i++) {
        	if(s.charAt(i) < s.charAt(leftMostPos)) {leftMostPos = i;}
        	if(--count[s.charAt(i) - 'a'] == 0) {break;}
        }

        return s.charAt(leftMostPos) + removeDuplicateLetters(s.substring(leftMostPos + 1).replaceAll(s.charAt(leftMostPos) + "", ""));

    }
} 


// M2 solution: greedy way
// iterate each char, remove the char before it if it's larger then cur char and make sure it still exists in following substring
// using stringBuilder direcly as a stack to store result
class Solution {
    public String removeDuplicateLetters(String s) {
        int[] count = new int[26];
        int[] seen = new int[26];
        for (char c: s.toCharArray()) {
        	count[c - 'a']++;
        }

        StringBuilder res = new StringBuilder();
        

        for (char c: s.toCharArray()) {
        	count[c - 'a']--;
        	int i = res.length() - 1;
        	if (seen[c - 'a'] != 1) {
        		while (i >= 0 && res.charAt(i) > c && count[res.charAt(i) - 'a'] > 0) {
        			// don't forget to label those removed as unseen
	    			seen[res.charAt(i) - 'a'] = 0;
	    			res.deleteCharAt(i--);
	    		} 
	        	res.append(c);
	        	seen[c - 'a'] = 1;        	
        	}
        	
        }

        return res.toString();
    }
}








