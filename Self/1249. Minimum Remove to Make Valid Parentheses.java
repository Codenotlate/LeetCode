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






// Review
/*Thought
Using stack. Also need to count '(' and ')'. At any point, the excess of ) to ( will need to be removed. Thus we can do count++ for '(' and count-- for ). Then when count <= 0, we will skip that ). Then similarly we move backwards, to remove excess (.
we may use two stacks or one stack + stringBuilder(need reverse). time O(n) space O(n)
*/
class Solution {
    public String minRemoveToMakeValid(String s) {
        Stack<Character> leftStack = new Stack<>();
        int count = 0;
        for (char c: s.toCharArray()) {
            if(c == '(') {count++; leftStack.push(c);}
            else if (c == ')') {
                if(count > 0) {
                    count--;
                    leftStack.push(c);
                }
            } else {
                leftStack.push(c);
            }
        }
        StringBuilder res = new StringBuilder();
        while(!leftStack.isEmpty()){ 
            char c = leftStack.pop();
            if(count > 0 && c == '(') {count--;continue;}
            res.append(c);
        }
        res.reverse();
        return res.toString();
        
        
    }
}
// time and space the same as above, but somehow faster in lc
class Solution {
    public String minRemoveToMakeValid(String s) {
        StringBuilder leftStack = new StringBuilder();
        int count = 0;
        for (char c: s.toCharArray()) {
            if(c == '(') {count++;}
            else if (c == ')') {
                if(count <= 0) {continue;}
                count--;
            }
            leftStack.append(c);
        }
        StringBuilder res = new StringBuilder();
        for (int i = leftStack.length() - 1; i >= 0; i--){ 
            char c = leftStack.charAt(i);
            if(count > 0 && c == '(') {count--;continue;}
            res.append(c);
        }
        res.reverse();
        return res.toString();        
    }
}