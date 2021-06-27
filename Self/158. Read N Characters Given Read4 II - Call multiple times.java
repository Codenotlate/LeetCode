/**
 * The read4 API is defined in the parent class Reader4.
 *     int read4(char[] buf4); 
 */


// Phase3 self M1
public class Solution extends Reader4 {
    /**
     * @param buf Destination buffer
     * @param n   Number of characters to read
     * @return    The number of actual characters read
     */
    private char[] prev4 = new char[4];
    private int nextReadPos = 4;
    private int endPos = 4;


    public int read(char[] buf, int n) {
    	int copyNum = 0;
    	
    	while (nextReadPos < endPos) {
    		buf[copyNum++] = prev4[nextReadPos++];
    		if (copyNum == n) {return copyNum;}
    	}

        while (endPos == 4) {
        	nextReadPos = 0;
        	endPos = read4(prev4);
        	
        	while (nextReadPos < endPos) {
        		buf[copyNum++] = prev4[nextReadPos++];
        		if (copyNum == n) {return copyNum;}
        	}
        }
        // file is exhausted
        nextReadPos = 4;
        endPos = 4;
        return copyNum;
    }
}


// Phase3 self improve based on M1
public class Solution extends Reader4 {
    /**
     * @param buf Destination buffer
     * @param n   Number of characters to read
     * @return    The number of actual characters read
     */
    private char[] prev4 = new char[4];
    private int nextReadPos = 4;
    private int endPos = 4;


    public int read(char[] buf, int n) {
    	int copyNum = 0;

        while (endPos == 4 || nextReadPos < endPos) {
        	if (nextReadPos == endPos) {
        		endPos = read4(prev4);
        		nextReadPos = 0;
        	}
        	
        	while (nextReadPos < endPos) {
        		buf[copyNum++] = prev4[nextReadPos++];
        		if (copyNum == n) {return copyNum;}
        	}
        }
        // file is exhausted
        return copyNum;
    }
}


// can also use copyNum < n as outside loop and break when readNum != 4
//https://leetcode.com/problems/read-n-characters-given-read4-ii-call-multiple-times/discuss/49598/A-simple-Java-code
// https://leetcode.com/problems/read-n-characters-given-read4-ii-call-multiple-times/discuss/49615/Clean-solution-in-Java



// copy from file to prev4 and then to buf is slow
/* one follow up is how to speed the copy: the idea is two rules:
whenever there is content in the buf4, copy from buf4
whenever there is enough space in buffer, copy from file

https://leetcode.com/problems/read-n-characters-given-read4-ii-call-multiple-times/discuss/188293/Google-follow-up-question.-Speed-up-the-copy.

But in Java we can't implement this

*/














