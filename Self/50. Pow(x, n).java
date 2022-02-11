class Solution {
    /* initial thought
    naive way: simply multiply by n times, then the time is O(n) space O(1)
    improved way: binary separate n into 1, 2, 4, 8,..., n/2. time O(logn) space O(logn)
    Both ways need to deal with negative n separately, by doing 1 / x^abs(n).
    
    */
    // recursive way time & space O(logn)
    public double myPow(double x, int n) {
        // note: here we use below instead of return getPow(1/x, -n) is because -2^31 <= n <= 2^31-1. So if we use -n, then when n = -inf. there will be overflow issue
        if ( n < 0) {return 1.0/x * getPow(1.0/x, -(n+1));} 
        return getPow(x, n);
    }
    
    public double getPow(double x, int n) {
        if (n == 0 || n == 1) {return n==0? 1: x;}
        double half = getPow(x, n / 2);
        if (n % 2 == 0) {            
            return half * half;
        } else {
            return half * half * x;
        }
    }
}


// from solution: iterative way using bit representation
// https://leetcode.com/problems/powx-n/discuss/19563/Iterative-Log(N)-solution-with-Clear-Explanation