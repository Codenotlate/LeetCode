class Solution {
    /* Initial thought
    similar to problem 1249, we can move from left to right first.we do count++ when encounter left one and do count-- when encounter right one. 
    Two goal can acheive in this pass. First whenever we have count < 0, we need to add one left parenthese, and clear count to 0. Second, in the end if count > 0, we know there are count number of left parenthese extra, thus we need to add that many right parenthese.
    time O(n) space O(1)
    */
    public int minAddToMakeValid(String s) {
        int count = 0;
        int insertNum = 0;
        for (char c: s.toCharArray()) {
            if (c == '(') {count++;}
            else {
                count--;
                if (count < 0) {count = 0; insertNum++;}
            }
        }
        insertNum += count;
        return insertNum;
    }
}