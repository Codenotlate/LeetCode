/* Initial thought
maintain a board[width][height], label the snake occupied cell as 1, board[0][0] should be label as 1 first.
Also maintain a queue, add the occupied cell there as well.
store the food array and maintain a current foodIdx pointing to the current food since food appears one by one.
Also maintain a headrow and headcol of the snake head. And maintain current score.

For move: change current head position based on direction first: R -> headcol++; L-> headcol--; U->headrow--; D->headrow++;
if (updated position is out of board || updated position already labeled as occupied by snake body) {return -1;}
otherwise, label updated position as occupied in board. Add that position into the queue.
* And if the updated position is the same as current food position, move foodIdx++, score++.
* otherwise, poll out the earlist position from the queue, and label that position back to 0 in the board.
return score.

time O(1) for setup and move also O(1)
space O(w*h + foodlen)
*/

// might be faster if use hashSet instead of the board, but time complexity should be the same
// try next time
class SnakeGame {
    Queue<int[]> queue;
    int[][] board;
    int[][] food;
    int foodIdx;
    int headrow;
    int headcol;
    int score;
    

    public SnakeGame(int width, int height, int[][] food) {
        queue = new LinkedList<>();
        board = new int[height][width];
        this.food = food;
        foodIdx = 0;
        headrow = 0;
        headcol = 0;
        score = 0;
        
        queue.add(new int[]{0,0});
        board[0][0] = 1;
    }
    
    public int move(String direction) {
        if (direction.equals("R")) {headcol++;}
        else if(direction.equals("L")) {headcol--;}
        else if(direction.equals("U")) {headrow--;}
        else {headrow++;}
        
        if (headrow < 0 || headrow >= board.length || headcol < 0 || headcol >= board[0].length) {return -1;}
        if (foodIdx < food.length && headrow == food[foodIdx][0] && headcol == food[foodIdx][1]) {
            foodIdx++;
            score++;
        } else {
            int[] empty = queue.poll();
            board[empty[0]][empty[1]] = 0;
        }
        // note this can't be checked above the if else check, because we may have length 4 snake with tail and head as neighbor cells.
        if (board[headrow][headcol] != 0) {return -1;}
        queue.add(new int[]{headrow, headcol});
        board[headrow][headcol] = 1;
        return score;
    }
}



















/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */