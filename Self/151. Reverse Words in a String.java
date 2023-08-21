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

// Review self
class Solution {
    // O(n) time and O(n) space
    public String reverseWords(String s) {
        s = s.strip(); // or s.trim()
        String[] words = s.split(" ");
        List<String> list = new LinkedList<>();
        for (String word: words) {
            if (word.isEmpty()) {continue;}
            list.add(0, word);
        }
        return String.join(" ", list);       
    }
}

// char array space O(1) way. similar to above O(1) way
class Solution {
    // convert to charArray first, try O(1) space way, without using any built-in string method
    public String reverseWords(String s) {
        char[] chars = s.toCharArray();
        // reverse the entire string
        reverse(chars, 0, chars.length - 1);
        // reverse each word in the string
        int i = 0;
        while(i < chars.length) {
            while (i < chars.length && chars[i] == ' ') {i++;}
            int j = i+1;
            while(j < chars.length && chars[j] !=' ') {j++;}
            reverse(chars, i, j-1);
            i = j;
        }
        // remove the extra spaces
        int end = 0;
        i = 0;
        while(i < chars.length && chars[i] == ' ') {i++;}
        while(i < chars.length) {
            if (chars[i] == ' ' && chars[i-1] == ' ') {i++;continue;}
            chars[end++] = chars[i];
            i++;
        }
        // don't forget this line
        if(chars[end-1] == ' ') {end--;}
        
        return new String(chars).substring(0, end);       
    }
    
    
    
    private void reverse(char[] chars, int i, int j) {
        while (i < j) {
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
            i++;
            j--;
        }
    }
}




// Review
/*Initial thought: assume the string is mutable by converting it to char array
if not require O(1) space, can use trim to delete space at the beginning and end, then split based on space to get an string array. Then reverse the string array and join them with space for the result. This way time O(n) space O(n)
Since now require O(1) space(view it as char array)
e.g. s = "   the sky is blue "
reverse the whole array first: " eulb si yks eht   "
then reverse each word group by finding them, reverse them and insert in to the array from left inplace:"blue is sky the"
This way time O(n) space O(1) if we view s as mutable char array
*/

class Solution {
    public String reverseWords(String s) {
        char[] chars = s.toCharArray();
        reverse(chars, 0, chars.length-1);
        int resIdx = 0;
        int left = 0;
        int right = 0;
        while (left < chars.length) {
            while (left < chars.length && chars[left] == ' ') {left++;}
            if(left >= chars.length) {break;}
            right = left;
            while(right < chars.length && chars[right] != ' ') {right++;}
            // reverse this part
            reverse(chars, left, right-1);
            // insert from left
            for (int i = left; i < right; i++) {
                chars[resIdx++] = chars[i];
            }
            if (resIdx <chars.length) {chars[resIdx++] = ' ';}
            left = right;
        }
        // remove the final space only when there's extra space to add space in the end
        if(resIdx <= chars.length && chars[resIdx-1] == ' ') {resIdx--;}
        return new String(chars, 0, resIdx);
    }
    
    private void reverse(char[] chars, int i, int j) {
        while (i < j) {
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
            i++;
            j--;
        }
    }
}






// Review 23/2/2
/* Thoughts
The requirement is reverse and remove all extra spaces in middle and two sides.
M1: split the string using space into an array. reverse the array, then append to result and skip the "" in the array. time O(n) space O(n)
M2: Similar idea as M1 without using build-in methods like split. Then we can do the split manully, and put each word in a stack/deque(add in front), then poll out accordingly with space added in between.
M3: if s is given as a char[] and we can do inplace change, then we can have one way with time O(n) space O(1). 
The idea is to reverse the whole char[] first, then start from left to right, skip spaces, and reverse each word in char[] inplace. The third round, we remove all the extra spaces.
s = "  hello  world  "
1st round: "  dlrow  olleh  "
2nd round: "  world  hello  "
3rd round: "world hello"

*/
// M1
class Solution {
    public String reverseWords(String s) {
        String[] split = s.split(" ");
        StringBuilder res = new StringBuilder();
        for (int i = split.length - 1; i >= 0; i--) {
            String str = split[i];
            if (str.equals("")) {continue;}
            res.append(str).append(" ");
        }
        res.deleteCharAt(res.length() - 1);
        return res.toString();
    }
}
// M3 - less precise than above ways, above ways combine 2nd and 3rd rounds together.
class Solution {
    public String reverseWords(String s) {
        char[] chars = s.toCharArray();
        // 1st round
        reverse(chars,0, chars.length-1);
        // 2nd round
        int left = 0; 
        while(left < chars.length) {
            while(left < chars.length && chars[left] == ' ') {left++;}
            int right = left;
            while(right < chars.length && chars[right] != ' ') {right++;}
            reverse(chars, left, right-1);
            left = right;
        }
        // 3rd round
        int resIdx = 0;
        left = 0;
        while(left < chars.length) {
            while(left < chars.length && chars[left] == ' ') {left++;}
            int right = left;
            while(right < chars.length && chars[right] != ' ') {right++;}
            for (int i = left; i < right && i < chars.length; i++) {
                if (i == left && resIdx != 0) {chars[resIdx++] = ' ';}
                chars[resIdx++] = chars[i];
            }
            left = right;
        }

        // now chars[:resIdx-1] is the answer
        // String(char[] value, int offset, int count) - Allocates a new String that contains characters from a subarray of the character array argument.
        return new String(chars, 0, resIdx);
    }

    private void reverse(char[] chars, int i, int j) {
        while (i < j) {
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
            i++;
            j--;
        }
    }
}




// 23/8/20 - still can't solve without bug for M3
/*
M1: using built-in trim, split and join
M2: two pointers to get word, and add to a deque/stack, pop out in reverse order and get the result.
M3: Assume it's char[], can do it in-place. By reverse each word and the reverse the whole string.
 */
// M1
class Solution {
    public String reverseWords(String s) {
        s = s.trim();
        String[] splited = s.split("\s+");
        int start = 0;
        int end = splited.length - 1;
        while (start < end) {
            String temp = splited[start];
            splited[start] = splited[end];
            splited[end] = temp;
            start++;
            end--;
        }
        return String.join(" ", splited);
    }
}
//M1.1
class Solution {
    public String reverseWords(String s) {
        s = s.trim();
        String[] splited = s.split("\s+");
        Collections.reverse(Arrays.asList(splited));
        return String.join(" ", splited);
    }
}
// M2:
class Solution {
    public String reverseWords(String s) {
        Stack<String> stack = new Stack<>();
        int left = 0;
        while(left < s.length()) {
            while(left < s.length() && s.charAt(left) == ' ') {left++;}
            int right = left;
            StringBuilder word = new StringBuilder();
            while(right < s.length() && s.charAt(right) != ' ') {
                word.append(s.charAt(right));
                right++;
            }
            if(!word.toString().isEmpty()) {stack.push(word.toString());}
            left = right;
        }

        StringBuilder res = new StringBuilder();
        while(!stack.isEmpty()) {
            res.append(stack.pop());
            if (!stack.isEmpty()) {res.append(" ");}
        }
        return res.toString();
    }
}
// M3:
class Solution {
    public String reverseWords(String s) {
        char[] chars = s.toCharArray();
        int left = 0;
        int resIdx = 0;
        while(left < s.length()) {
            while(left < s.length() && s.charAt(left) == ' '){left++;}
            int right = left;
            while(right < s.length() && s.charAt(right) != ' '){
                right++;
            }
            reverse(chars, left, right-1);
            // this line is important
            if (resIdx != 0 && left < right){chars[resIdx++] = ' ';}
            for (int i = left; i < right; i++) {
                chars[resIdx++] = chars[i];
            }
            
            left = right;
        }
        if(resIdx - 1 < chars.length && chars[resIdx-1] == ' ') {resIdx--;}
        reverse(chars, 0, resIdx - 1);
        return new String(chars, 0, resIdx);
    }


    private void reverse(char[] arr, int start, int end) {
        while(start < end) {
            char temp = arr[start];
            arr[start]= arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
}














