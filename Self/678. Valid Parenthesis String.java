/*from solution
like deal with only '(' and ')' valid problem, we care about the number of '(' to match for the rest of the string. but here we also have '*', so the number of '(' becomes a range instead. when cur char is '(', min++, max++, when it's ')', min--, max--; and when it's '*', min--, max++. Also we need to know there's no need to keep min < 0, because it's already invalid. And since we need the substring to be valid along the way, we need maxNum to be >= 0, otherwise return false directly.
Then for the final range [min, max] of the whole string, we know its number of '(' left is within the range and as long as 0 is inside that range, this string is valid, meaning we can convert '*' in some way to have same amount of '(' ')' in the string. Thus we only need to check whether min == 0.

*/
class Solution {
    public boolean checkValidString(String s) {
        int minNum = 0;
        int maxNum = 0;
        for (char c: s.toCharArray()) {
            if (c == '(') {
                minNum++;
                maxNum++;
            } else if (c == ')') {
                minNum = Math.max(0, minNum-1);
                maxNum--;
                if(maxNum< 0) {return false;}
            } else {
                minNum = Math.max(0, minNum-1);
                maxNum++;
            }
        }
        return minNum == 0;
    }
}

//https://leetcode.com/problems/valid-parenthesis-string/discuss/543521/Java-Count-Open-Parenthesis-O(n)-time-O(1)-space-Picture-Explain
// https://leetcode.com/problems/valid-parenthesis-string/discuss/107577/Short-Java-O(n)-time-O(1)-space-one-pass




// Review: still from solution
/*Thought
if we do count++ for (, and count-- for ), then we need the count to be no less than 0 along the way and equal to 0 in the end. 
When we encounter '*', there are 2 choices. The count will be in a range. And we want the final range to include 0. If along the way, range max <0, return false directly. Otherwise, min and max are changed along the way. Notice, we can't change min to <0, because we need to guarantee count >= 0 along the way, otherwise it's already invalid. And would be wrong with below example.
"(*)("
*/
class Solution {
    public boolean checkValidString(String s) {
        int mincount = 0;
        int maxcount = 0;
        for (char c: s.toCharArray()) {
            if (c == '*') {
                maxcount++;
                mincount = Math.max(0,mincount-1);
            } else if (c == '(') {
                maxcount++;
                mincount++;
            } else {
                mincount = Math.max(0,mincount-1);
                maxcount--;
                if (maxcount < 0) {return false;}
            }
            
        }
        return mincount == 0;
    }
}