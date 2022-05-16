class Solution {
    public boolean validWordAbbreviation(String word, String abbr) {
        int i = 0;
        int curnum = 0;
        for (char c: abbr.toCharArray()) {
            if (Character.isDigit(c)) {
                curnum = curnum * 10 + c-'0';
                if (curnum == 0) {return false;}
            } else {
                i += curnum;
                curnum = 0;
                if ( i > word.length() - 1 || word.charAt(i) != c) {return false;}
                i++;
            }
        }
        // pay attention to cases like : hq & 1 or h & 2
        return i + curnum == word.length();
    }
}