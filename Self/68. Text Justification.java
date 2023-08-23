// 23/8/23 - first time took 50min and several rounds of debug. Next break it down to several smaller functions.
/*
Greedy way: use sliding window to first figure out what words can be put in the current line. Calculate the total char length(count in at least one space in between) as well as words count along the way. Based on maxWidth, calculate the space count between words. (maxWidth - charLength) / (wordcount - 1). If we we have remainder, then for the first remainder number we do average space count + 1.
Last line is a special case.

time: O(n*l) space O(maxWidth)
"""Unlike most problems on LeetCode, this is one that can be solved by just doing exactly what the problem statement is telling us to do.

You don't need any data structures or algorithmic tricks to solve this problem. The point of this problem is to test your ability to quickly write clean code while navigating edge cases."""
 */
class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new LinkedList<>();
        int left = 0;
        int charLen = 0;
        while(left < words.length) {
            int right = left;
            charLen = 0; // don't forget to reset for each round
            while(right < words.length) {
                int space = right == left ? 0 : 1;
                if (charLen + words[right].length() + space <= maxWidth) { // make sure we are not counting in non valid words char length
                    charLen += words[right].length() + space;
                } else {
                    break;
                }
                right++;
            }

            StringBuilder line = new StringBuilder();
            if(right < words.length){
                // put the current words into a string for non-last lines               
                int gapCount = right - left - 1;
                int spaceBase = gapCount > 0 ? (maxWidth - charLen + gapCount) / gapCount : maxWidth - charLen + gapCount;// edge case: when one word in a line
                int remainder = gapCount > 0 ? (maxWidth - charLen + gapCount) % gapCount : 0;
                for(int i = left; i < right; i++) {
                    line.append(words[i]);
                    if(line.length() < maxWidth) { // edge case: when one word in a line
                        int spaceCount = i - left < remainder ? spaceBase+1 : spaceBase;
                        while(spaceCount-- > 0) {line.append(" ");}
                    }                    
                }
            } else {
                // meaning it's last line
                for (int i = left; i < right; i++) {
                    if(i != left) {line.append(" ");}
                    line.append(words[i]);
                }
                int spaceCount = maxWidth - line.length();
                while(spaceCount-- > 0) {line.append(" ");}
            }
           
            res.add(line.toString());
            left = right;
        }
        return res;
    }
}