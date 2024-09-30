// 2024.9.29
// exactly same as 3305, optimized solution O(n)

// Sliding window, expand right to have a valid window, then shrink left side
// each window has count for unique vowels and count for Consonants, a valid window has 5 unique vowels and exactly k consonants.
class Solution {
    public long countOfSubstrings(String word, int k) {
        long countK = countAtleastK(word, k);
        long countK_1 = countAtleastK(word, k+1);
        return countK-countK_1;
        
    }
    
    
    private long countAtleastK(String word, int k) {
        Set<Character> vowels = new HashSet<>(Arrays.asList('a', 'i','o','u','e'));
        int left = 0;
        int right = 0;
        long rescount = 0;
        Map<Character, Integer> curvows = new HashMap<>();
        int nonvows = 0;
        
        while (left < word.length()) {
            // find first valid window
            while (right < word.length() && (curvows.size() != 5 || nonvows < k)) {
                char cur = word.charAt(right);
                if (vowels.contains(cur)) {
                    curvows.put(cur, curvows.getOrDefault(cur, 0)+1);
                } else {
                    nonvows++;
                }
                right++;
            }
            if (curvows.size()==5 && nonvows >= k) {
                rescount += word.length() - right + 1;
            }
            // move left
            char curleft = word.charAt(left);
            if (vowels.contains(curleft)) {
                if (curvows.get(curleft) == 1) {
                    curvows.remove(curleft);
                } else {
                    curvows.put(curleft, curvows.get(curleft) -1);
                }
            } else {
                nonvows--;
            }
            left++;            
        }
        return rescount;
        
    }
}