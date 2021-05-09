class Solution {
	// recursive way
    public int getSum(int a, int b) {
        // base case
        if (b == 0) {return a;}

        int carry = (a & b) << 1;
        int sumWithoutCarry = a ^ b;
        return getSum(sumWithoutCarry, carry);
    }
}



class Solution {
	// iterative way
    public int getSum(int a, int b) {
        while (b != 0) {
        	int carry = (a & b) << 1;
        	a = a ^ b;
        	b = carry;
        }
        return a;
    }
}
