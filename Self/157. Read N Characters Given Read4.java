/**
 * The read4 API is defined in the parent class Reader4.
 *     int read4(char[] buf4);
 */

// Phase3 self
// time O(n) space O(1)
public class Solution extends Reader4 {
    /**
     * @param buf Destination buffer
     * @param n   Number of characters to read
     * @return    The number of actual characters read
     */
    public int read(char[] buf, int n) {
        int res = 0;
        char[] temp = new char[4];
        while (n > 0) {
        	int num = read4(temp);
        	// need to consider when n < 4
        	for (int i = res; i < res + num && i < res + n; i++) {
        		buf[i] = temp[i - res];
        	}
        	res += Math.min(num, n);
        	// need to consider when file is < 4
        	if (num < 4) {return res;}
        	n -= 4;
        }

        
        return res;
    }
}


// from solution, same idea as M1, code in a more elegant way
public class Solution extends Reader4 {
    public int read(char[] buf, int n) {
        int copiedChars = 0, readChars = 4;
        char[] buf4 = new char[4];
        
        while (copiedChars < n && readChars == 4) {
            readChars = read4(buf4);
            
            for (int i = 0; i < readChars; ++i) {
                if (copiedChars == n)
                    return copiedChars;
                buf[copiedChars] = buf4[i];
                ++copiedChars;    
            }    
        }
        return copiedChars;
    }
}