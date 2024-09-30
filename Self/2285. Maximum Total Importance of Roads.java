// 2024.9.30

// assign larger number to nodes that have more edges
// so counting the edges for each node first O(roads.len)
// sort the countings, can use count sorting O(n)
// then assign 1 to n in ascending order of countings
class Solution {
    public long maximumImportance(int n, int[][] roads) {
        int[] counts = new int[n];
        for (int[] road: roads) {
            counts[road[0]]++;
            counts[road[1]]++;
        }
        // counting sort given count ranges from 0 to n-1
        int[] countSort = new int[n];
        for (int count: counts) {
            countSort[count]++;
        }
        // Now assign 1 to n e.g. [0,4,1,0,0]
        long res = 0;
        int val = 1;
        for (int i = 0; i < n; i++) {
            if (countSort[i]==0) {continue;}
            res += (long)i * (val+val+countSort[i]-1) * countSort[i] / 2; 
            val += countSort[i];
        }
        return res;

    }
}