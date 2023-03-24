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


/*Note: need to consider n = -inf, we can't convert it to -n. will cause overflow.
*/
class Solution {
    public double myPow(double x, int n) {
        if(n == 0 || n == 1) {return n==0 ? 1 : x;}
        if (n < 0) {return 1/x * myPow(1.0/x, -(n+1));}
        if ( n % 2 == 0) {
            double half = myPow(x, n/2);
            return half * half;
        } else {
            double half = myPow(x, n/2);
            return x *half * half;
        }
    }
}

// can try iterative way next time





// Review - 23/3/24 (after few text run debug)
/* Thoughts
M1: recursively based on pow(x, n/2) * pow(x, n/2). Note need to store the result of pow(x, n/2), otherwise too slow
Take care of overflow issue.
time O(logn) space O(logn)

M2: iterative way from solution. time the same. space O(1). - same algos not quite understand.

 */

class Solution {
    public double myPow(double x, int n) {
        if (n == 0) {return 1.0;}
        if (n == 1) {return x;}
        if (n == -1) {return 1.0/x;}
        double factor = n % 2 == 0? 1:x;
        if ( n < 0) {factor = 1.0 / factor;}
        double next = myPow(x, n/2);
        return factor * next * next;
    }
}