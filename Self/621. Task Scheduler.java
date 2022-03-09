/* from solution
Notice as long as we separate the most frequent chars with n distance gap, we are good with the rest chars. Thus the problem converts to use the most frequent char/chars to be the gap and generate idle positions between the gap waiting to be filled by the rest of the chars. The final result will be the length of the tasks + number of idle positions.
A...A...A
Three conditions:
1) only one char has most frequency. And the rest chars are not enough to fill the gaps. Then # of idle pos = (maxCount - 1) * n - (tasks.length - maxCount)
2) more than one chars(x) have most frequency. And the rest chars are not enough to fill the gaps. Then # of idle pos = (maxCount - 1) * (n - x + 1) - (tasks.length - maxCount * x)
3) the rest chars number is more than the idle pos number, then those extra ones can be added into the gap as well. Since n distance is the min size of a gap, there is no problem if the gap size is larger than n. Thus # of idle pos in above two conditions need to be updated with 0 as lower bound.
Final formula:
# of idle pos = Math.max(0, (maxCount - 1) * (n - x + 1) - (tasks.length - maxCount * x))
result len = tasks.length + # of idle pos

time O(tasks.len)
space O(unique char in tasks) = O(26) = O(1)

*/
class Solution {
    public int leastInterval(char[] tasks, int n) {
        int[] counts = new int[26];
        int maxfreq = 0;
        for (char c: tasks) {
            counts[c-'A']++;
            maxfreq = Math.max(maxfreq, counts[c-'A']);
        }
        int maxCount = 0;
        for(int c: counts) {
            if(c == maxfreq) {maxCount++;}
        }
        int idleNum = Math.max(0, (maxfreq - 1) * (n - maxCount + 1) - (tasks.length - maxfreq * maxCount));
        return idleNum + tasks.length;
    }
}


// detail explanation
// https://leetcode.com/problems/task-scheduler/discuss/104500/Java-O(n)-time-O(1)-space-1-pass-no-sorting-solution-with-detailed-explanation