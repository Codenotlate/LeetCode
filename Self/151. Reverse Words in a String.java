// Phase3 self
// M1 time O(n) space O(n)
// use built-in method like : str.split("\\s+"), str.trim(), Collections.reverse(). Arrays.asList()
// regular expression pattern: https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html

class Solution {
    public String reverseWords(String s) {
        s = s.trim();
        List<String> wordList = Arrays.asList(s.split("\\s+")); // in Java we need to use // to represent / and /s means space, + means one or more
        Collections.reverse(wordList);
        return String.join(" ", wordList);
    }
}

// M2: use split to, then use a deque to reverse the order by adding in front of the deque
// time O(n) space O(n)
class Solution {
    public String reverseWords(String s) {
        int left = 0;
        int right = s.length() - 1;

        // trim the space in front and in tail
        while (left <= right && s.charAt(left) == ' ') {left++;}
        while (left <= right && s.charAt(right) == ' ') {right--;}
        Deque<String> d = new ArrayDeque<>();
        StringBuilder word = new StringBuilder();

        while (left <= right) {
        	char c = s.charAt(left);

        	if (word.length() != 0 && c == ' ') {
        		d.addFirst(word.toString());
        		word.setLength(0);
        	} else if (c != ' ') {
        		word.append(c);
        	}
        	left++;
        }
        d.addFirst(word.toString());

        return String.join(" ", d);
    }
}


// M3: if given char[], can do it in O(1) space
// reverse the whole char[], then reverse each word, finally clean all the excess spaces
class Solution {
    public String reverseWords(String s) {
        char[] str = s.toCharArray(); // assume we are given this char array
        int n = str.length;
        reverse(str, 0, n - 1);

        reverseWord(str, n);

        return cleanSpace(str);
    }

    private void reverse(char[] s, int start, int end) {
    	while (start < end) {
    		char temp = s[start];
    		s[start] = s[end];
    		s[end] = temp;
    		start++;
    		end--;
    	}
    }

    private void reverseWord(char[] s, int n) {
    	int start = 0;
    	int end = 0;

    	while (end < n && start < n) {
    		while (start < n && s[start] == ' ') {
    			start++;
    		}
    		while (end < start || end < n && s[end] != ' ') {
    			end++;
    		}
    		reverse(s, start, end - 1);
    		start = end + 1;
    	}
    }

    private String cleanSpace(char[] s) {
    	int i = 0;
    	int j = 0;
    	int n = s.length;
    	while (j < n) {
    		while (j < n && s[j] == ' ') {j++;}
	    	while (j < n && s[j] != ' ') {s[i++] = s[j++];}
	    	// add one space, remember to check i<n
	    	if(j < n && i < n) {s[i++] = ' ';}
    	}
    	// remember to remove the last space if any
    	if (s[i - 1] == ' ') {i--;}
    	return new String(s).substring(0, i);

    }
}
//https://leetcode.com/problems/reverse-words-in-a-string/discuss/47720/Clean-Java-two-pointers-solution-(no-trim(-)-no-split(-)-no-StringBuilder)




//Phase3 self 
class Solution {
    public String reverseWords(String s) {
        char[] str = s.toCharArray();
        int n = str.length;
        // step1: reverse the whole
        reverse(str, 0, n - 1);
        // step2: reverse each world
        int left = 0;
        int right = 0;
        while (left < str.length) {
            while (left < str.length && str[left] == ' '){left++; right++;}
            while (right < str.length && str[right] != ' ') {right++;}
            reverse(str, left, right - 1);
            left = right;
        }
        // step3: remove the unnecessary white space
        left = 0;
        right = 0;
        while (right < str.length) {
            while (right < str.length && str[right] == ' ') {right++;}
            while (right < str.length && str[right] != ' ') {str[left++] = str[right++];}
            // add a space behind
            if (left < str.length) {str[left++] = ' ';} 
        }
        // not use if
        while (str[left - 1] == ' ') {left--;}
        return new String(str).substring(0, left);
    }
    
    private void reverse(char[] s, int start, int end) {
        int l1 = start;
        int l2 = end;
        while (l1 < l2) {
            char temp = s[l1];
            s[l1] = s[l2];
            s[l2] = temp;
            l1++;
            l2--;
        }
    }
}








