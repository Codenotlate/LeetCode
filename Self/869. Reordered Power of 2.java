/*From solution:
since n is a number in [1, 10^9], Thus there are limited number of powerOf2 numbers inside the range. Thus we can get all of those power2 numbers and compare them sorted with n sorted to see the result.
And don't need to worry about 0 in the first digit case, those invalid strings won't match any power2 numbers, so won't impact the result.


Basically, the way used here is to define a hash method to compare number n with a set of power2 numbers based on the limited range of n.
Another way to hash: https://leetcode.com/problems/reordered-power-of-2/solutions/149843/c-java-python-straight-forward/
 */
class Solution {
    public boolean reorderedPowerOf2(int n) {
        char[] charN = String.valueOf(n).toCharArray();
        Arrays.sort(charN);
        String sortedN = new String(charN);
        for (int i = 0; i < 30; i++) {
            char[] charP2 = String.valueOf(1 << i).toCharArray();
            Arrays.sort(charP2);
            String p2str = new String(charP2);
            if (p2str.equals(sortedN)) {return true;}
        }
        return false;
    }
}

// Need review for the time complexity etc
/*Several posts good:
https://leetcode.com/problems/reordered-power-of-2/solutions/149843/c-java-python-straight-forward/
https://leetcode.com/problems/reordered-power-of-2/solutions/151949/simple-java-solution-based-on-string-sorting/
https://leetcode.com/problems/reordered-power-of-2/solutions/149819/reordered-power-of-2/
https://leetcode.com/problems/reordered-power-of-2/solutions/2483557/Python-oror-O(log(n))-time-O(1)-space-oror-Faster-than-100-oror-Explained-and-compared/
*/