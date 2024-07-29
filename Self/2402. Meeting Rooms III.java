// 2024.7.29
/** A reflection on the natural way we solve this manually. First sort array by start and check one by one. Have a pq representing rooms in use, this will be sort by end time and then room number. Have another pq representing rooms that are unused, this will be sorted by the room number and then end time.
For each cur meeting, first free all rooms from inuse pq that ends before cur meeting. And add those rooms into unused pq. Then pop out one room from unused pq, and assign this room to cur meeting into in use pq. If there's no room in unused, then cur meeting must be delayed and wait until the earliest meeting ended in in use pq. Adjust the end time for cur meeting according to the delay. And add into in use pq.
Whenever a meeting is added into in use pq, keep track of the count for each room being assigned.

Time O(nlogn)
space O(n)
*/
class Solution {
    public int mostBooked(int n, int[][] meetings) {
        Arrays.sort(meetings, (a,b) -> (a[0] != b[0]?a[0]-b[0]:a[1]-b[1]));
        int[] counts = new int[n];
        PriorityQueue<int[]> unusedRooms = new PriorityQueue<>((a,b)-> (a[0] != b[0]?a[0]-b[0]:a[1]-b[1]));
        PriorityQueue<int[]> inuseRooms = new PriorityQueue<>((a,b)-> (a[1] != b[1]?a[1]-b[1]:a[0]-b[0]));
        
        for (int i = 0; i < n; i++) {
            unusedRooms.add(new int[]{i, Integer.MIN_VALUE});
        }

        for (int[] cur: meetings) {
            while (!inuseRooms.isEmpty() && inuseRooms.peek()[1] <= cur[0]) {
                unusedRooms.add(inuseRooms.poll());
            }
            int nextRoom = -1;
            if (!unusedRooms.isEmpty()) {
                nextRoom = unusedRooms.poll()[0];
            } else if (!inuseRooms.isEmpty() && inuseRooms.peek()[1] > cur[0]) {
                int[] next = inuseRooms.poll();
                nextRoom = next[0];
                cur[1] += next[1] - cur[0];
            }
            inuseRooms.add(new int[]{nextRoom, cur[1]});
            counts[nextRoom]++;
        }

        int maxCount = -1;
        int maxRoomId = -1;
        for (int i = 0; i < n; i++) {
            if (counts[i] > maxCount) {
                maxCount = counts[i];
                maxRoomId = i;
            }
        }
        return maxRoomId;
    }
}