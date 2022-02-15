class Solution {
    /* Initial thought
    since the change should be inplace, use one pointer to label the next position of the valid result. anothe pointer points to cur char being processed.
    for each cur char, compare it with last char in cur result. if they are the same, count++. otherwise, add lastchar to result, convert count to char of digits(special case to deal with 1) and add to result, and update lastchar to cur char, reset count to 1.
    time O(n) space O(1)
    */
    public int compress(char[] chars) {
        char lastchar = ' ';
        int count = 0;
        int respos = 0;
        for (int i = 0; i <= chars.length; i++) {
            if (i < chars.length && (lastchar == ' ' || lastchar == chars[i])) {
                count++;
                lastchar = chars[i];
            } else {
                chars[respos++] = lastchar;
                //note according to the discussion, below toString used log10(n) extra space. To achieve o(1) space, we can add the reverse number into char, track the start and end range and reverse it back in place.
                if (count != 1) { 
                    String countstr = Integer.toString(count);
                    for (char n: countstr.toCharArray()) {chars[respos++] = n;}    
                }                  
                if (i < chars.length) {lastchar = chars[i];}
                count = 1;
            }
        }
        return respos;
    }
}