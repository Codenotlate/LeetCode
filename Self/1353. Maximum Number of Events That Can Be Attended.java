/*Initial thought
Wrong! -> sort the array based on smaller start time and if same start time then smaller end time, cur time starts with the events[0] start time. if (curtime <= event[i][end]): res++; i++; curtime = max(event[i][start], curtime + 1)

from solution:
sort the array based on start time. Then use a pq to have the ongoing meeting with earliest end time on the top. We use pq because we want to find the one with earliest end time among all current ongoing events. And pq represents all the ongoing events at current time.
The process is sort array on start time first. Then if there's no ongoing events in pq, we fast forward curtime to be the events[i][0]. Then we add all ongoing events (while(events[i][0] <= curtime) {pq.add(events[i][1]); i++}) into the pq. And we pop out those events ending before curtime (while(pq.peek() < curtime){pq.poll();}). Now pq has all the events available for us to attend at curtime, we choose the peek one with earliest end time. res++; pq.poll(); curtime++.

time O(nlogn) space O(n)
*/
class Solution {
    public int maxEvents(int[][] events) {
        Arrays.sort(events, (e1, e2) -> (e1[0] - e2[0]));
        int res = 0;
        int eventIdx = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int curtime = 0;
        
        while (!pq.isEmpty() || eventIdx < events.length) {
            if(pq.isEmpty()) {curtime = events[eventIdx][0];}
            while(eventIdx < events.length && events[eventIdx][0] <= curtime) {pq.add(events[eventIdx][1]); eventIdx++;}
            while(!pq.isEmpty() && pq.peek() < curtime) {pq.poll();}
            if(!pq.isEmpty()) {
                pq.poll();
                res++;
            }
            curtime++;
        }
        
        return res;
    }
}



// https://leetcode.com/problems/maximum-number-of-events-that-can-be-attended/discuss/510263/JavaC%2B%2BPython-Priority-Queue
// good explanation
// https://leetcode.com/problems/maximum-number-of-events-that-can-be-attended/discuss/510262/Detailed-analysisLet-me-lead-you-to-the-solution-step-by-step












