class Solution {
    public int findJudge(int N, int[][] trust) {
        int[] inDegree = new int[N + 1];
        int[] outDegree  = new int[N + 1];

        for (int[] edge: trust) {
        	inDegree[edge[1]]++;
        	outDegree[edge[0]]++;
        }

        for (int i = 1; i <= N; i++) {
        	if (inDegree[i] == N - 1 && outDegree[i] == 0) {
        		return i;
        	}
        }
        return -1;
    }
}

// can optimize the two array into one
// https://leetcode.com/problems/find-the-town-judge/discuss/242938/JavaC%2B%2BPython-Directed-Graph
/* Intuition:
Consider trust as a graph, all pairs are directed edge.
The point with in-degree - out-degree = N - 1 become the judge.
*/
