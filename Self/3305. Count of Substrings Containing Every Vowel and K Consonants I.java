// 2024.9.29 - tricky sliding window question
// exactly same question as 3306. from weekly contest, but 3306 has tigher time requirement, thus this way won't work
class Solution {
    public int countOfSubstrings(String word, int k) {
        Set<Character> vowels = new HashSet<>(Arrays.asList('a', 'i','o','u','e'));
        int left = 0;
        int right = 0;
        int rescount = 0;
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
            // expand valid until invalid
            int next = right;
            while (curvows.size() == 5 && nonvows == k) {
                rescount++;
                if (next >= word.length() || !vowels.contains(word.charAt(next))) {break;}            
                next++;
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