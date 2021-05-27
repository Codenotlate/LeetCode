// related to 151, this one is given char[] and simpler by only containing single space, thus no need to clean space
// need inplace change: reverse twice way

class Solution {
    public void reverseWords(char[] s) {
        reverse(s, 0, s.length - 1);
        reverseWord(s);
    }

    private void reverse(char[] s, int start, int end) {
    	while (start < end) {
    		char temp = s[start];
    		s[start++] = s[end];
    		s[end--] = temp; 
    	}
    }

    private void reverseWord(char[] s) {
    	int n = s.length;
    	int i = 0;
    	int j = 0;
    	while (i < n && j < n) {
    		if (s[j] == ' ') {continue;}
    		while(j < n && s[j] != ' ') {j++;}
    		reverse(s, i, j - 1);		
            j++;
            i = j;
    	}
    }

}