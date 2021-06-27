class Solution {
	// recursive way: Time O(n), space O(n)
    public boolean validPalindrome(String s) {
        return validHelper(s.toCharArray(), new int[]{1}, 0, s.length() - 1);
    }
    private boolean validHelper(char[] s, int[] count, int start, int end) {
    	// base case
    	if (start >= end) {return true;}

    	if (s[start] == s[end]) {
    		return validHelper(s, count, start + 1, end - 1);
    	} else {
    		if (count[0] < 1) {return false;} // meaning that at most one element has been used before
    		count[0] -= 1;
    		return validHelper(s, count, start + 1, end) || validHelper(s, count, start, end - 1);
    	}
    }

}



class Solution {
	//iterative way, similar idea as first one
	// time O(n) space O(1)
    public boolean validPalindrome(String s) {
        int start = 0;
        int end = s.length() - 1;
        while (start < end) {
        	if (s.charAt(start) == s.charAt(end)) {
        		start++;
        		end--;
        	} else {
        		return isPalindrome(s, start, end - 1) || isPalindrome(s, start + 1, end);
        	}
        }
        return true;
    }

    private boolean isPalindrome(String s, int start, int end) {
    	while (start < end) {
    		if (s.charAt(start) != s.charAt(end)) {
    			return false;
    		}
    		start++;
    		end--;
    	}
    	return true;
    }
}



// Phase3 not self
class Solution {
    public boolean validPalindrome(String s) {
        //main idea: recursively decrease the size of the problem
        int start = 0;
        int end = s.length() - 1;
        while (start <= end) {
            if (s.charAt(start) == s.charAt(end)) {
                start++;
                end--;
            } else {
                return isPalindrome(s, start, end - 1) || isPalindrome(s, start + 1, end);
            }
        }
        return true;
        
    }
    
    
    private boolean isPalindrome(String s, int start, int end) {
        while (start <= end) {
            if (s.charAt(start) != s.charAt(end)) {return false;}
            start++;
            end--;
        }
        return true;
    }
}