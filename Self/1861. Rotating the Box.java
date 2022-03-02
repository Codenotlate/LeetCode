/* Initial thought
for each row, move from backwards. next represents the position before curhead of the transformed array from the row end. cur starts at len-1, and move towards 0.
if cur == '.', cur--; if cur == '*', next = cur -1; cur--; if cur == '#', swap(cur, next), next--. if (cur > next) {cur = next;}
[#, #, #, .,*, #, .]

time O(mn) space O(1) exclude result space
*/
class Solution {
    public char[][] rotateTheBox(char[][] box) {
        int m = box.length;
        int n = box[0].length;
        char[][] res = new char[n][m];
        
        for (int i = 0; i< m; i++) {
            int colNum = m-i-1;
            processRow(res, box[i], colNum);
        }
        return res;
    }
    
    // covert row and add it to res column colNum
    private void processRow(char[][] res, char[] row, int colNum) {
        // copy the row char into the res column first
        for (int i = 0; i < row.length; i++) {res[i][colNum] = row[i];}
        int next = row.length - 1;
        int cur = next;
        while(cur >= 0) {
            char c = res[cur][colNum];
            if (c == '.') {
                cur--;
            } else if (c == '*') {
                next = cur - 1;
                cur--;
            } else {                
                if(cur != next) {
                    // swap
                    char temp = res[cur][colNum];
                    res[cur][colNum]= res[next][colNum];
                    res[next][colNum] = temp;
                }                
                next--;
                cur = next;
            }
        }
    }
}


// similar idea slightly diff implementation
// https://leetcode.com/problems/rotating-the-box/discuss/1210113/simplest-explanation-with-java-solution

