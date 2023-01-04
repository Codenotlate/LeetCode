/* Initial thought
first observation, the len of the number should be [low.len, high.len]. The starting position number should be in range [low starting number, 9 - low.len +1].
[1000, 13000]
l1 = 4, l2 = 5
start in [1, 6]
for each start, append[last+1] until len reach l2. Check the len and number before adding to res.

time O(l1 + l2 + 9 * l2) = O(l2)
space O(l2) if exclude result space
==================================
After check solution: 
since l1, l2 is in range 2-9, all time and space can be viewed as O(1)
above idea is correct for the basic logic. But for implementation, two ways. Since the max number of result strings is limited to 8+7+6+...+1 = 36. We can simply generate all of them and add those qualified into the result. Or we can use sliding window to get all qualified substring of string "123456789".

*/
// M1: generating all possible solution
class Solution {
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> res = new LinkedList<>();
        for (int start = 1; start <= 9; start++) {
            int cur = start;
            for (int d = start+1; d <= 9; d++) {
                cur = cur * 10 + d;
                if (cur >= low && cur <= high) {res.add(cur);}
            }
        }
        Collections.sort(res);
        return res;
    }
}
// M2: 123456789 + sliding Window
class Solution {
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> res = new LinkedList<>();
        StringBuilder s = new StringBuilder("123456789");
        int lowLen = String.valueOf(low).length();
        int highLen = String.valueOf(high).length();
        for (int len = lowLen; len <= highLen; len++) {
            for (int start = 0; start < 9 - len + 1; start++) {
                String curStr = s.substring(start, start+len);
                int cur = Integer.valueOf(curStr);
                if (cur >= low && cur <= high) {res.add(cur);}
            }
        }
        return res;
    }
}




// Review: still need to from solution


// Review 23/1/3
// Derive the result from string "123456789". time O(8 * 8) = O(1) space O(1)
// one diff from the above sliding window way is to not generate substring each time and instead calculate the value based on the previous value. This may save some space, but since overall space is O(1), no optimize to this.
class Solution {
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> res = new LinkedList<>();
        String digits = "123456789";
        int lowlen = String.valueOf(low).length();
        int highlen = Math.min(String.valueOf(high).length(),digits.length()); //don't forget to cap highlen to 9.
        
        for (int size = lowlen; size <= highlen; size++) {
            int value = Integer.valueOf(digits.substring(0, size));
            if (value >= low && value <= high) {res.add(value);}
            for (int end = size; end < digits.length(); end++) {
                int leftDigit = digits.charAt(end - size) - '0';
                int rightDigit = digits.charAt(end) - '0';
                value = (value - (int)Math.pow(10, size-1) * leftDigit) * 10  + rightDigit;
                if (value >= low && value <= high) {res.add(value);}
                if (value > high) {break;}
            }
        }
        return res;
    }
}

// Also a BFS way to generate all nums in order
// https://leetcode.com/problems/sequential-digits/solutions/853592/python-solution-using-queue-explained/


