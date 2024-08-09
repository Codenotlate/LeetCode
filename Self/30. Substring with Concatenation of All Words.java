// 2024/8/7
/* the window size is fixed here, thus after we reached the desired window size, each step right moves, left should also move.
Below way reduced some repetitive work comparing to move one char each time. 
// slightly from the solution, but similar idea
// solution have the left move 1 char per time, then expand right until fixed window size, record each substr with step size along the way with a seen map. If any substr unseen or substr count > original count, break. If stop without break, this current window is correct thus add to result.
*/
class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        Map<String, Integer> orimap = new HashMap<>();
        for (String word: words) {
            orimap.put(word, orimap.getOrDefault(word,0)+1);
        }
        int nonZeros = orimap.keySet().size();
        int left = 0;
        int right = 0;
        int step = words[0].length();

        for (int start = 0; start < step; start++) {
            left = start;
            right = left;
            int remainCount = nonZeros;
            Map<String, Integer> countmap = new HashMap(orimap);
            // since below we stop the window early when not enough chars, we didn't really recover the countmap. Thus we do copy countmap above.
            while (left <= s.length()-words.length * step) {
                while (right-left < words.length * step) {
                    String subStr = s.substring(right, right+step);
                    if (countmap.containsKey(subStr)) {
                        countmap.put(subStr, countmap.get(subStr)-1);
                        if (countmap.get(subStr)==0) {remainCount--;}
                        if (remainCount == 0) {
                            res.add(left);
                            right += step;
                            break;
                        }
                    }
                    right += step;
                }

                String leftSub = s.substring(left, left+step);
                if (countmap.containsKey(leftSub)) {
                    countmap.put(leftSub, countmap.get(leftSub)+1);
                    if (countmap.get(leftSub)==1) {remainCount++;}
                }
                left += step;
            }
        }
        return res;
    }
}