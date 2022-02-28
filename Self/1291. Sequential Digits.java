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