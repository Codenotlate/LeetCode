class Solution {
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