class Solution {
    // only revert half and compare with the first half
    public boolean isPalindrome(int x) {
        if (x < 0 || (x != 0 && x % 10 == 0)) {return false;}
        int rev = 0;
        while (x > rev) {
        	int digit = x % 10;
        	rev = 10 * rev + digit;
        	x = x / 10;
        }
        return (x == rev || x == rev / 10);
    }
}


// phase3 self
class Solution {
    public boolean isPalindrome(int x) {
        // the main idea is to reverse the number and see if it's the same as original number
        if (x < 0 || x % 10 == 0 && x != 0) {return false;}
        int rev = 0;
        int n = x;
        while(x > 0) {
            rev = rev * 10 + x % 10;
            x /= 10;
        }
        return rev == n;
    }
}