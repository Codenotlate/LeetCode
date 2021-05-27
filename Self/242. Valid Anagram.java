class Solution {
    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length()) {return false;}
        Map<Character, Integer> map = new HashMap<>();
        // build map with char-> count pairs
        for (char c: s.toCharArray()) {
        	map.put(c, map.getOrDefault(c, 0) + 1);
        }
        for (char c: t.toCharArray()) {
        	if (!map.keySet().contains(c) || map.get(c) == 0) {
        		return false;
        	}
        	map.put(c, map.get(c) - 1);
        }
        return true;

    }
}



// Phase3 self
// time O(n), space O(1)
class Solution {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {return false;}
        int[] charCount = new int[26];
        for (int i = 0; i < s.length(); i++) {
            charCount[s.charAt(i) - 'a']++;
            charCount[t.charAt(i) - 'a']--;
        }
        
        for (int n: charCount) {
            if (n != 0) {return false;}
        }
        return true;
    }
}

class Solution {
    // for followup: use hashmap instead of array
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {return false;}
        Map<Character, Integer> countMap = new HashMap<>();
        
        for (int i = 0; i < s.length(); i++) {
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);
            countMap.putIfAbsent(c1, 0);
            countMap.put(c1, countMap.get(c1) + 1);
            countMap.putIfAbsent(c2, 0);
            countMap.put(c2, countMap.get(c2) - 1);
        }
        
        for (char c: countMap.keySet()) {
            if (countMap.get(c) != 0) {return false;}
        }
        return true;
    }
}