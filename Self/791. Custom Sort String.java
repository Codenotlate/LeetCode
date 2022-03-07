/* Initial thought
M1: build a char: pos map for order string, then use it to sort s with customer comparater based on the position of char in order map. Note for those char in s but not in order, we can assume they are the largest(or smallest).

time O(l1 + l2log(l2))
space O(l1 + logl2)

M2: count sort. Build a map: char-> pos for order string, then set up a count array with the length same as order, count[i] represents the count in s for char order[i]. Do initial loop of s, update the count, and add those char in s that are not in order in the initial count loop.
traverse the count array, and add those chars in order.
time O(l1 + l2 + l1) = O(l2)
space O(l1 + 26) = O(1) since l1 <= 26.

*/
// M2
class Solution {
    public String customSortString(String order, String s) {
        Map<Character, Integer> posMap = new HashMap<>();
        for (int i = 0; i < order.length(); i++) {
            posMap.put(order.charAt(i), i);
        }
        int[] counts = new int[order.length()];
        StringBuilder res = new StringBuilder();
        for (char c: s.toCharArray()) {
            if(posMap.containsKey(c)) {
                counts[posMap.get(c)]++;
            } else {
                res.append(c);
            }
        }
        
        for (int i = 0; i < counts.length; i++) {
            int k = counts[i];
            while(k-- > 0) {res.append(order.charAt(i));}
        }
        return res.toString();
    }
}



// similar idea in post: https://leetcode.com/problems/custom-sort-string/discuss/116615/JavaPython-3-one-Java-10-liner-Python-6-liner-and-2-liner-solutions-w-comment
// but it uses a single count array for s. Traverse the order string to append those into res first. And then traverse the count array to add those values in s but not in order.












