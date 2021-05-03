class Solution {
    public boolean hasAlternatingBits(int n) {
        int preLast = n & 1;
        n = n >>> 1;
        while (n != 0) {
        	if ((n & 1) == preLast) {return false;}
        	preLast = n & 1;
        	n = n >>> 1;
        }
        return true;
    }
}


// Other method: right move 1 or right move 2 pos, and use XOR
// https://leetcode.com/problems/binary-number-with-alternating-bits/discuss/108427/Oneliners-(C%2B%2B-Java-Ruby-Python)