class Solution {
    public boolean isHappy(int n) {
        Set<Integer> genNums = new HashSet<>();
        while (n != 1) {
            n = genNext(n);
            if (n == 1) {return true;}
            if (genNums.contains(n)) {return false;}
            genNums.add(n);           
        }
        return true;
        
    }
    
    private int genNext(int n) {
        int sum = 0;
        while( n > 0) {
            int digit = n % 10;
            n /= 10;
            sum += digit * digit;
        }
        return sum;
    }
}


/*Solution on why it won't goto infinite

Based on our exploration so far, we'd expect continually following links to end in one of three ways.

It eventually gets to 1.
It eventually gets stuck in a cycle.
It keeps going higher and higher, up towards infinity.
That 3rd option sounds really annoying to detect and handle. How would we even know that it is going to continue going up, rather than eventually going back down, possibly to 1?1? Luckily, it turns out we don't need to worry about it. Think carefully about what the largest next number we could get for each number of digits is.

Digits  Largest Next
1   9   81
2   99  162
3   999 243
4   9999    324
13  9999999999999   1053
For a number with 33 digits, it's impossible for it to ever go larger than 243243. This means it will have to either get stuck in a cycle below 243243 or go down to 11. Numbers with 44 or more digits will always lose a digit at each step until they are down to 33 digits. So we know that at worst, the algorithm might cycle around all the numbers under 243243 and then go back to one it's already been to (a cycle) or go to 11. But it won't go on indefinitely, allowing us to rule out the 3rd option.
Even though you don't need to handle the 3rd case in the code, you still need to understand why it can never happen, so that you can justify why you didn't handle it.


Time complexity / space complexity check solution
https://leetcode.com/problems/happy-number/solution/

time O(logn) space O(logn)


Also Approach 2: Floyd's Cycle-Finding Algorithm can have O(1) space


*/



// 20240809
// intuitive way using a set is simple. using Floyd Cycle detection algo with O(1) space is interesting solution.
// we are basically trying to find a cycle here.
class Solution {
    public boolean isHappy(int n) {
        int slow = n;
        int fast = n;
        while(slow != 1 && fast != 1) {
            slow = getSum(slow);
            fast = getSum(getSum(fast));
            if (slow == fast && slow != 1) {return false;} // don't forget !=1 condition
        }
        return true;
    }


    private int getSum(int n) {
        int sum = 0;
        while ( n != 0) {
            int digit = n % 10;
            n /= 10;
            sum += digit*digit;
        }
        return sum;
    }
}