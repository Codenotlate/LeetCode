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










