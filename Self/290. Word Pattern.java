//20240809
class Solution {
    public boolean wordPattern(String pattern, String s) {
        Set<String> seenP = new HashSet<>();
        String[] pToS = new String[26];
        Arrays.fill(pToS,"");
        String[] words = s.split(" ");
        if (words.length != pattern.length()) {return false;}
        for (int i = 0; i < words.length; i++) {
            if (!pToS[pattern.charAt(i)-'a'].equals("") && !pToS[pattern.charAt(i)-'a'].equals(words[i])) {
                return false;
            }
            if (pToS[pattern.charAt(i)-'a'].equals("") && seenP.contains(words[i])) {return false;}
            pToS[pattern.charAt(i)-'a']=words[i];
            seenP.add(words[i]);
        }
        return true;
    }
}


// this extra seenP can also be replaced by checking if pattern and words have same number of unique elements.
// another way can also map both to index like question 205
class Solution {
    public boolean wordPattern(String pattern, String s) {
        Set<String> seenP = new HashSet<>();
        String[] pToS = new String[26];
        String[] words = s.split(" ");
        if (words.length != pattern.length()) {return false;}
        for (int i = 0; i < words.length; i++) {
            if (pToS[pattern.charAt(i)-'a']!=null && !pToS[pattern.charAt(i)-'a'].equals(words[i])) {
                return false;
            }
            if (pToS[pattern.charAt(i)-'a']==null && seenP.contains(words[i])) {return false;}
            pToS[pattern.charAt(i)-'a']=words[i];
            seenP.add(words[i]);
        }
        return true;
    }
}