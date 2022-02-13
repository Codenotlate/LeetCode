class Solution {
    /* Initial thought
    count the current number of ( and number of ). First pass from left to right, if #of) larger than #of(, then that ) must be removed.
    Second pass from right to left on the result from first pass, and remove ( if # of ( larger than # of ).
    Store the result in another stringbuilder. 
    time O(n) space O(n)
    */
    public String minRemoveToMakeValid(String s) {
        StringBuilder res1 = new StringBuilder();
        int curleft = 0;
        int curright = 0;
        for (char c: s.toCharArray()) {
            if (c != ')') {
                if (c == '(') {curleft++;}
                res1.append(c);
            }
            else {
                if(curright < curleft) {
                    res1.append(c);
                    curright++;
                }
            }
        }
        
        if (curleft == curright) {return res1.toString();}
        StringBuilder res2 = new StringBuilder();
        int removeleftNum = curleft - curright;
        for (int i = res1.length() - 1; i >= 0; i--) {
            if(res1.charAt(i) == '(' && removeleftNum > 0) {removeleftNum--; continue;}
            res2.append(res1.charAt(i));
        }
        res2.reverse();
        return res2.toString();
    }
}


// There's another 2 solutions in solution page
// https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/solution/
// above method is the same idea as method3 in solution.