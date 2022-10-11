/* Summary
A few ways to solve: two bit manipulation ways and one normal way(not working). Time and Space all O(1) given only 32 bits.
M1: have a mask starting as 1, and move 32 steps. In each step, do mask << 1 to move left one bit. And do mask & n at each step, if the result is not zero, meaning n has a 1 in that bit, add to result.
M2: instead of checking each bit, do a little bit trick: n&(n-1) will always remove the least 1 bit in n. so we do this until n is zero, then the number of operations is the result.
M3: not using any bit related way, but similar to the idea of M1. using n%2 and n/2 at each step to check 1 at each bit.
M3 is not going to work since in Java, the compiler represents the signed integers using 2's complement notation. Therefore, in Example 3 (n = 11111111111111111111111111111101), the input represents the signed integer. -3.
*/
// M1
public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int mask = 1;
        int count = 0;
        for (int i = 0; i < 32; i++) {
            if ((n & mask) != 0) {count++;}
            mask <<= 1;
        }
        return count;
    }
}

//M2
public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n = n & (n-1);
            count++;
        }
        return count;
    }
}


//M3 - NOT Working in Java!
public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int count = 0;
        while ( n != 0) {
            if (n % 2 == 1) {count++;}
            n /= 2;
        }
        return count;
    }
}