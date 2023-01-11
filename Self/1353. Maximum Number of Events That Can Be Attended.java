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
// good explanation !!!
// https://leetcode.com/problems/maximum-number-of-events-that-can-be-attended/discuss/510262/Detailed-analysisLet-me-lead-you-to-the-solution-step-by-step






// Review - still from solution
/*Thought
Sort array by start, then add all ongoing events to pq to sort by end. cur time starts with the min start time. Add all events with start time <= cur to pq, then pop out all events with end time < cur.Poll one out of pq and add one to result.
if pq not empty, cur = max(cur+1, pq.peek().start). Otherwise cur is the start time for next event in events to be processed.
time O(nlogn) space O(n)

cases:
[[1,2],[1,2],[3,3],[1,5],[1,5]]
[[1,5],[1,5],[1,5],[2,3],[2,3]]
*/
class Solution {
    public int maxEvents(int[][] events) {
        Arrays.sort(events, (e1,e2)->(e1[0]-e2[0]));
        int i = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((e1,e2)->(e1[1]-e2[1]));
        int cur = events[0][0];
        int count = 0;
        while (i < events.length || !pq.isEmpty()) {
            while(i< events.length && events[i][0] <= cur) {pq.add(events[i]);i++;}
            while (!pq.isEmpty() && pq.peek()[1] < cur) {pq.poll();}
            if (!pq.isEmpty()) {
                pq.poll();
                count++;
                cur++;
            } else {
                if(i < events.length) {cur = events[i][0];}
            }
        }
        return count;
    }
}


// Review 23/1/11 - same as above
// review the good explanation post above again! - the thinking process in an interview
/* Self Note from solution:
for interval problems, always think first about sort and pq with greedy way (think about how you would do in reality).
For this problem, the way we do it in reality is to first sort by start time, then for all events satisfy the current day, we choose the one with smallest end time. 
To achieve it, we first sort the array based on start time. Then have the curDay start as events[0][0]. Then have all events having start time <= curDay inserted to a pq (sort by end time). After the insertion, pop out those invalid ones having end time < curDay. Then poll one remaining valid one out, res++. curDay++.

Time O(nlogn for sort + nlogn for pq related) = O(nlogn)  space O(n)
 */
class Solution {
    public int maxEvents(int[][] events) {
        Arrays.sort(events, (e1,e2) -> (e1[0] - e2[0]));
        PriorityQueue<int[]> pq = new PriorityQueue<>((e1,e2)->(e1[1]-e2[1]));
        int curDay = events[0][0];
        int ei = 0;
        int res = 0;
        while (!pq.isEmpty() || ei < events.length) {
            while(ei < events.length && events[ei][0]<= curDay) {
                pq.add(events[ei]);
                ei++;
            }
            // pop out invalid ones
            while(!pq.isEmpty() && pq.peek()[1] < curDay) {pq.poll();}
            if (!pq.isEmpty()) {
                pq.poll();
                res++;
                curDay++;
            } else {
                if(ei < events.length) {curDay = events[ei][0];}
            }
            
        }
        return res;
    }
}






