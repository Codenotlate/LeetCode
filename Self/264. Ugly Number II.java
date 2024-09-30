// 2024.9.26
// TLE for n=332
class Solution {
    public int nthUglyNumber(int n) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        int lastpopout = 0;
        while (n > 0) {
            int popout = pq.poll();
            if (lastpopout != popout) {n--;}
            if (n == 0) {return popout;}
            lastpopout = popout;
            pq.add(popout*2);
            pq.add(popout*3);
            pq.add(popout*5);
        }
        return -1; // should not go to this line
    }
}


// Try avoid add dup into pq directly (passed)
class Solution {
    public int nthUglyNumber(int n) {
        PriorityQueue<Long> pq = new PriorityQueue<>();
        Set<Long> seen = new HashSet<>();
        pq.add(1L);
        seen.add(1L);
        int[] mults = new int[]{2,3,5};
        while (n > 0) {
            long popout = pq.poll();
            n--;
            if (n == 0) {return (int)popout;}
            for(int m: mults) {
                long next = (long)popout * m;
                if (!seen.contains(next)) {
                    pq.add(next);
                    seen.add(next);
                }
            }
        }
        return -1; // should not go to this line
    }
}



// There is a another O(n) way
//optimiza from above idea: each time we know who is the smaller one of the three, thus we don't need to add all three of them. However, there's no good way to remember where to start next time.
// change from another perspective: we have arrays like ugly array itself time these three factors (1/2/3/4/5/6/8/9/12/..) * (2/3/5). Thus we can keep track of three indexes for each of 2/3/5 in the ugly array, and choose smallest one like for 3 sorted array.
class Solution {
    public int nthUglyNumber(int n) {
        int idx2=0, idx3=0, idx5=0;
        long[] ugly = new long[n];
        ugly[0] = 1;
        for (int i = 1; i < n; i++) {
            long num1 = ugly[idx2] * 2;
            long num2 = ugly[idx3] * 3;
            long num3 = ugly[idx5] * 5;
            long min = Math.min(Math.min(num1, num2), num3);
            ugly[i] = min;
            if (min == num1) {
                idx2++;
            }
            if (min == num2) { // note: should not put else if here, put if to handle duplicate case.
                idx3++;
            }
            if (min == num3) {
                idx5++;
            }
        }
        return (int)ugly[n-1];
    }
}
