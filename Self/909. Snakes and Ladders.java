/*Initial thought
Ask for lowest number of moves: use BFS. each node has at most 6 neighbors [curpos+1, curpos+6] unless curpos+6 > n^2. If board[curpos] != -1, then curpos = board[curpos] and add the 6 neighbors based on updated curpos. until n^2 is visited, return level at that time.

time O(n^2) space O(n^2)
*/
class Solution {
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        int[] visited = new int[n*n];
        int level = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        visited[0] = 1;
        
        while(!queue.isEmpty()) {
            int size = queue.size();
            while(size-- > 0) {
                int cur = queue.poll();
                int[] pos = getPos(cur, n);
                if (board[pos[0]][pos[1]] != -1) {
                    cur = board[pos[0]][pos[1]];
                }
                if (cur == n*n) {return level;}
                for (int next = cur+1; next <= cur+6 && next <= n*n; next++) {
                    if(visited[next-1] == 0) {
                        queue.add(next);
                        visited[next-1]= 1;
                    }
                }
            }
            level++;
        }
        return -1; // should not reach this line
    }
    
    
    private int[] getPos(int cur, int n) {
        int i = n - 1 - (cur-1) / n;
        int j = i % 2 != n % 2 ? (cur-1) % n : n-1- (cur-1) % n;
        return new int[]{i, j};
    }
}










// Review
// correct one, and an bug one below
class Solution {
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        Queue<Integer> queue = new LinkedList<>();
        int[] visited = new int[n*n + 1];
        queue.add(1);
        visited[1] = 1;
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while(size-- > 0) {
                int cur = queue.poll();
                cur = checkConversion(cur, n, board);
                if (cur == n * n) {return level;}
                for (int next = cur + 1; next <= Math.min(cur + 6, n*n); next++) {       
                    if (visited[next] == 0) {
                        queue.add(next);
                        visited[next] = 1;
                    }
                }
            }
            level++;
        }
        
        return -1;
        
    }
    
    
    private int checkConversion(int num, int n, int[][] board) {
        num--;
        int r0 = num / n;
        int c0 = num % n;
        int r = n - 1 -r0;
        int c = r0 % 2 == 0 ? c0 : n-1-c0;
        return board[r][c] == -1 ? num + 1 : board[r][c];
        
    }
}



// Bug Wrong version
class Solution {
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        Queue<Integer> queue = new LinkedList<>();
        int[] visited = new int[n*n + 1];
        queue.add(1);
        visited[1] = 1;
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while(size-- > 0) {
                int cur = queue.poll();
                if (cur == n * n) {return level;}
                for (int next = cur + 1; next <= Math.min(cur + 6, n*n); next++) {
                    // bug note: this way, if next is visited, but newnum on next is not visited due to snake/ladder at most once, then that newnum is missed
                    if (visited[next] == 0) {                        
                        int newnum = checkConversion(next, n, board);
                        if (visited[newnum] == 0) {
                            queue.add(newnum);
                            visited[newnum] = 1;
                        }
                        visited[next] = 1;
                    }
                }
            }
            level++;
        }
        
        return -1;
        
    }
    
    
    private int checkConversion(int num, int n, int[][] board) {
        num--;
        int r0 = num / n;
        int c0 = num % n;
        int r = n - 1 -r0;
        int c = r0 % 2 == 0 ? c0 : n-1-c0;
        return board[r][c] == -1 ? num + 1 : board[r][c];
        
    }
}