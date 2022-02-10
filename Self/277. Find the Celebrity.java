/* The knows API is defined in the parent class Relation.
      boolean knows(int a, int b); */

// M1 Naive way
public class Solution extends Relation {
    /*Initial thought:naive way
    for each node, count its indegree and outdegree. traverse the graph and see if there exists one node with indegree = n-1 and outdegree = 0.
    The problem is how to traverse the graph with knows function only. Should be the same as traverse the graph, for i = 0 to n-1, for j = 0 to n-1, call knows(i,j) to determine whether there's an edge in between.
    time O(n^2) space O(n)    
    */
    public int findCelebrity(int n) {
        int[] indegree = new int[n];
        int[] outdegree = new int[n];
        for (int i = 0; i< n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == i) {continue;}
                if(knows(i, j)) {indegree[j]++; outdegree[i]++;}
            }
        }
        
        for (int i = 0; i< n; i++) {
            if (indegree[i] == n-1 && outdegree[i] == 0) {return i;}
        }
        return -1;
    }
}


// M2 followup with fewer call to knows
public class Solution extends Relation {
    /*followup:
    in the naive way, the number of calls to knows is n^2. Need to reduce it to 3*n.
    Learned from hint1: if knows(a,b) == 0, then b can't be the celebrity, no need to check b nodes any more. If knows(a,b) == 1, then a can't be the celebrity. Thus we can always eliminate one node from the answer range.
    first loop to get the one potential node for celebrity (n calls to knows). Then check this node with all the rest node to see if it has zero outdegree and n-1 indegree.(2n calls to knows). Thus in total 3n calls to knows.
    */
    public int findCelebrity(int n) {
        int a = 0;
        int b = 1;
        while (b < n) {
            if (knows(a,b)) {int next = b+1; a = b; b = next;}
            else {b+=1;}
        }
        for(int i = 0; i < n; i++) {
            if (i == a) {continue;}
            if (knows(a, i) || !knows(i,a)) {return -1;}
        }
        return a;
    }
}


// solution of this problem is good
// Approach 3: Logical Deduction with Caching: need to check this approach as a good possible followup in interview