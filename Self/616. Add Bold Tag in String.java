/* Initial thought
check every word in words, label their starting position and end position in string s. then merge the interval to decide the place to insert bold tag.
l = s.length, n = words.length, k = average length of word inside words
then time O(n*l*k + nlogn(if intervals are unsorted) + l) = O(nlk + nlogn)
space O(n + l)

improved thought
If we change the order of the loop, i.e. for each position i on string s, check if there's a match for every word in words. This way, we can produce the interval in ascending order, thus we can do the interval merge along the way and no need to sort and merge later.
time is O(nlk+n+l) = O(nlk)

note the interval merge can also use the trick: num[start]++, num[end]--, and do accumulative sum in the end to find the merge result. // https://leetcode.com/problems/add-bold-tag-in-string/discuss/104262/short-java-solution
*/
class Solution {
    // improved way to get in order intervals and merge along the way
    public String addBoldTag(String s, String[] words) {
        List<int[]> intervals = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            for (String w: words) {
                if (s.startsWith(w, i)) {
                    int[] newitv = new int[]{i, i+w.length()-1};
                    merge(intervals, newitv);
                }
            }
        }
        // don't forget this line
        if (intervals.size() == 0) {return s;}
        
        StringBuilder res = new StringBuilder();
        int itvIdx = 0;
        int start = intervals.get(itvIdx)[0];
        int end = intervals.get(itvIdx)[1];
        for (int i = 0; i < s.length(); i++) {
            if(i == start) {
                res.append("<b>");
                if(itvIdx <= intervals.size() -2) {start = intervals.get(++itvIdx)[0];}
            } 
            res.append(s.charAt(i));
            if (i == end) {
                res.append("</b>");
                end = intervals.get(itvIdx)[1];
            }
        }
        return res.toString();
    }
    
    private void merge(List<int[]> intervals, int[] newitv) {
        if (intervals.size() == 0 || intervals.get(intervals.size() - 1)[1] < newitv[0] - 1){
            intervals.add(newitv); 
            return;
        }
        int[] lastitv = intervals.get(intervals.size() - 1);
        if (lastitv[1] >= newitv[1]) {return;}
        int[] updateitv = new int[]{intervals.get(intervals.size() - 1)[0], newitv[1]};
        intervals.set(intervals.size()-1, updateitv);
    }
}

/* discussion solutions
most basic one: https://leetcode.com/problems/add-bold-tag-in-string/discuss/104263/Java-solution-Same-as-Merge-Interval.
tricky ones in dealing with interval merge
https://leetcode.com/problems/add-bold-tag-in-string/discuss/104248/Java-Solution-boolean-array

Try this one next time!
tricky one use the interval merge without sort trick: start++, end--.
https://leetcode.com/problems/add-bold-tag-in-string/discuss/104262/short-java-solution

*/









