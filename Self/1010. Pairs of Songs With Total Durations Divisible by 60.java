// This way is still slow. Can be improved using below other method.
class Solution {
    /* initial thought
    naive way: check each pair. time O(n^2)
    improved way:
    Using a map to store the iterated duration and its count till current, also keep track of the largest duration in current dict. Check each 60x within range [max(curdur, 60), curdur + maxdur]. Since time[i] in [0,500]. The number of 60x to check for each curdur is at most 1000/60. Thus time O(n), space O(n)
    */
    public int numPairsDivisibleBy60(int[] time) {
        Map<Integer, Integer> durMap = new HashMap<>();
        int maxdur = 0;
        int count = 0;
        for (int t: time) {
            int total = 60;
            if (t % 60 == 0) {total = t;}
            else {total = Math.max(total, (t / 60 + 1) * 60);}
            while (total <= t + maxdur) {
                if (durMap.keySet().contains(total - t)) {count += durMap.get(total - t);}                
                total += 60;
            }
            durMap.put(t, durMap.getOrDefault(t, 0) + 1);
            maxdur = Math.max(maxdur, t);
        }
        return count;
    }
}

class Solution {
    /* improve thought
    (a + b) % 60 == 0 is actually the same as a % 60 + b % 60 == 60. Thus the dict can store (t%60, count).
    time O(n) truly O(n) since no need to check multiple 60x.
    space O(n)
    */
    public int numPairsDivisibleBy60(int[] time) {
        Map<Integer, Integer> durMap = new HashMap<>();
        int count = 0;
        for (int t: time) {
            int mod = t % 60;
            if (durMap.keySet().contains((60 - mod) % 60)) {count += durMap.get((60-mod)%60);}                           
            durMap.put(mod, durMap.getOrDefault(mod, 0) + 1);
        }
        return count;
    }
}







// Review
/*Thought
This problem can be converted to find a pair where (time[i] % 60 + time[j] % 60)%60 = 0.
Thus we can do similar process in two sum. By using a map or a count array of size 60. One pass of the array. for each num, check the count for num%60 in the map or array, add that to final result.time O(n) space O(60) = O(1)
*/
class Solution {
    public int numPairsDivisibleBy60(int[] time) {
        int[] count = new int[60];
        int res = 0;
        for(int t: time) {
            if( t % 60 == 0 ) {res += count[0];}
            else {res += count[60 - t % 60];}
            count[t % 60]++;
        }
        return res;
    }
}





