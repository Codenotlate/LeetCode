/*Initial thought
[has bug]Since it involves string change along the way, think about using Stack. Also have a count map, so that the stack only includes unique char and the count of that char is tracked in the map.
Loop the string, when cur == stack.peek, we add the count of the peek char, and is the count == k, we pop that char out of stack, and change its count to 0. If cur != peek, we simply add the count of cur in map, and push cur into the stack
After one pass, we pop out the remaining chars in the stack with the count in the map to a stringBuilder and reverse it to return the result.

Bug-fixed one from above: instead of using map which counts the historial count, we should put(char, current count) in the stack, so that we can compare to k using the current consecutive len for the peek char.

time O(n)
space O(n)
*/

// This way is same idea as solution approach4
class Solution {
    public String removeDuplicates(String s, int k) {
        Stack<Pair<Character,Integer>> stack = new Stack<>();
        for (char cur: s.toCharArray()) {
            if (!stack.isEmpty() && cur == stack.peek().getKey()) {
                // note there's no direct method to change the value of the pair, so we need to pop it out first and push it back if the count < k-1.
                Pair<Character,Integer> p = stack.pop();
                int count = p.getValue();
                if(count < k - 1) {stack.push(new Pair(cur, count+1));}
            } else {
                stack.push(new Pair(cur,1));
            }
        }
        
        StringBuilder res = new StringBuilder();
        while(!stack.isEmpty()) {
            Pair<Character,Integer> p = stack.pop();
            char c = p.getKey();
            int count = p.getValue();
            while(count-- > 0) {res.append(c);}
        }
        res.reverse();
        return res.toString();
    }
}


// One question: the delete in stringBuilder is O(n) time complexity?
// https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/discuss/392933/JavaC%2B%2BPython-Two-Pointers-and-Stack-Solution

/* Another two pointer way
Algorithm

1) Initialize the slow pointer (j) with zero.

2) Move the fast pointer (i) through the string:

    Copy s[i] into s[j].

    If s[j] is the same as s[j - 1], increment the count on the top of the stack.

        Otherwise, push 1 to the stack.
    If the count equals k, decrease j by k and pop from the stack.

3) Return j first characters of the string.


*/













