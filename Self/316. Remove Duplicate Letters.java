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


//Phase3 self
class Solution {
    /*initial thought
    Since only lower case letters and we need to count, using bucket counting.
    To return lexi order, consider building the result iteratively. use StringBuilder res as a stack, if cur char is already in the res, skip it. Otherwise, compare cur char with last in res. while last > cur and count[last] > 0 , keep removing last. Then add cur char to res. Until all chars in s are processed.
    time O(n) space O(26) = O(1)
    */
    
    public String removeDuplicateLetters(String s) {
        int[] count = new int[26];
        for (char c: s.toCharArray()) {
            count[c-'a']++;
        }
        StringBuilder res = new StringBuilder();
        int[] visited = new int[26];
        
        for (char cur: s.toCharArray()) {
            count[cur-'a']--;
            if (visited[cur-'a'] != 0) {continue;}
            while (res.length() > 0) {
                char last = res.charAt(res.length()-1);
                if (cur < last && count[last-'a']>0) {
                    res.deleteCharAt(res.length() - 1);
                    visited[last-'a']=0;
                } else {break;}
            } 
            res.append(cur);
            visited[cur-'a'] = 1;
            
        }
        
        return res.toString();
    }
}






// Review
/*Thought
Don't understand question wrongly.
Using stack. when to skip: when char is already in the stack. When to pop: when peek() remaining count > 0  && peek() > cur.First use int[26] to do count.
e.g."cbdfacdcbc"
*/
class Solution {
    public String removeDuplicateLetters(String s) {
        int[] count = new int[26];
        int[] seen = new int[26];
        for (char c: s.toCharArray()) {
            count[c-'a']++;
        }
        StringBuilder res = new StringBuilder();
        for (char c: s.toCharArray()) {
            if (seen[c-'a'] == 1) {count[c-'a']--;continue;}
            int i = res.length() - 1;
            while (i >= 0 && res.charAt(i) > c && count[res.charAt(i)-'a'] > 0) {
                seen[res.charAt(i)-'a'] = 0;
                res.deleteCharAt(i--);              
            }
            res.append(c);
            count[c-'a']--;
            seen[c-'a'] = 1;
            
        }
        return res.toString();
    }
}


// review 22/12/28
// use stack, but can be optimized as above using stringbuilder directly as the stack
class Solution {
    public String removeDuplicateLetters(String s) {
        Set<Character> included = new HashSet<>();
        Map<Character, Integer> counts = new HashMap<>();
        Stack<Character> stack = new Stack<>();
        for (char c: s.toCharArray()) {
            counts.putIfAbsent(c, 0);
            counts.put(c, counts.get(c) + 1);
        }
        for (char c: s.toCharArray()) {
            counts.put(c,counts.get(c) - 1);
            if (included.contains(c)) {continue;}
            while (!stack.isEmpty() && c <= stack.peek() && counts.get(stack.peek()) > 0) {
                included.remove(stack.pop());
            }
            stack.push(c);
            included.add(c);          
        }
        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty()){
            res.append(stack.pop());
        }
        res.reverse();
        return res.toString();
    }
}

