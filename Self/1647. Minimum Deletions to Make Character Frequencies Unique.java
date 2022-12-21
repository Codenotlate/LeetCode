// self naive way
// time O(n + k + klogk) space O(k), k <= 26
// time is slow, but i think the O() time is good. Need to recheck the other solutions.
class Solution {
    public int minDeletions(String s) {
        Map<Character, Integer> freqs = new HashMap<>();
        Map<Integer, Integer> counts = new HashMap<>();
        for (char c: s.toCharArray()) {
            freqs.putIfAbsent(c, 0);
            freqs.put(c, freqs.get(c) + 1);
        }
        for (char f: freqs.keySet()) {
            int c = freqs.get(f);
            counts.putIfAbsent(c, 0);
            counts.put(c, counts.get(c) + 1);
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>((c1, c2) -> c2 - c1);
        pq.add(0);
        for (int f: counts.keySet()) {
            pq.add(f);
        }

        int res = 0;
        int rem = 0;
        while (pq.peek() != 0) {
            int popfreq = pq.poll();
            int popcount = counts.get(popfreq) + rem;
            if (popcount <= 1) {continue;}
            int fillCount = Math.min(popfreq - pq.peek() - 1, popcount - 1);
            res += fillCount * (fillCount + 1) / 2;
            rem = popcount - 1 - fillCount;
            res += rem * (popfreq - pq.peek());
        }
        return res;
    }
}