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






// Review
/*Thought
Find the chars(assume k of them) with most freq(as maxcount), and use them as the separater for different gaps. There will be (maxcount-1) * (n-k+1) empty positions after separation and wait to be filled by the rest tasks.If the rest tasks number is larger than the gap numbers. It canbe added the gap as well, since n is the min len required between two same tasks, not the max len. As long as those tasks are put as one in each separation part.
time O(n) space O(26) = O(1)

below map can also be replace by int[26]
*/
class Solution {
    public int leastInterval(char[] tasks, int n) {
        int maxCount = 0;
        int numCount = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (char c: tasks) {
            map.put(c, map.getOrDefault(c,0)+1);
            if (map.get(c) > maxCount) {
                maxCount = map.get(c);
                numCount = 1;
            } else if (map.get(c) == maxCount) {
                numCount++;
            }
        }
        
        return Math.max(numCount * maxCount + (maxCount - 1) * (n-numCount+1), tasks.length);
    }
}