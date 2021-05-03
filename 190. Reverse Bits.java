public class Solution {
    // you need treat n as an unsigned value
    //method1: bit by bit reverse
    public int reverseBits(int n) {
    	int res = 0;
    	int power = 31;
        while (n != 0) {
        	// get the last bit and move left power bits, adding to result
        	res |= (n & 1) << power;
        	n = n >>> 1;
        	power -= 1;
        }
        return res;
    }
}


public class Solution {
    // you need treat n as an unsigned value
    // method2: byte to byte
    // also adding a map as cache to store the reversed result of byte
    // can be a solution to follow up question
    
    private Map<Byte, Integer> cache = new HashMap<>();

    public int reverseBits(int n) {
    	byte[] bytes = new byte[4];
        // divide n into 4 bytes
        for (int i = 0; i < 4; i++) {
        	bytes[i] = (byte) ((n >>> (8 * i)) & 0xff);
        }
        // move each byte to right positions in the final result
        int res = 0;
        for (int i = 0; i < 4; i++) {
        	res += reversedByte(bytes[i]);
        	if (i < 3) {res <<= 8;}
        }
        return res;
    }

    private int reversedByte(byte b) {
    	if (cache.keySet().contains(b)) {
    		return cache.get(b);
    	}
    	int res = 0;
    	for (int i = 0; i < 8; i++) {
	        res += ((b >>> i) & 1);
	        if (i < 7) {
	            res <<= 1;
	        }
    	}
    	cache.put(b, res);
    	return res;
    }
}




public class Solution {
    // you need treat n as an unsigned value
    // method 3: use divide and conquar, without using for loop
    public int reverseBits(int n) {
        n = (n >>> 16) | (n << 16);
        n = ((n & 0xff00ff00) >>> 8) | ((n & 0x00ff00ff) << 8);
        n = ((n & 0xf0f0f0f0) >>> 4) | ((n & 0x0f0f0f0f) << 4);
        n = ((n & 0xcccccccc) >>> 2) | ((n & 0x33333333) << 2);
        n = ((n & 0xaaaaaaaa) >>> 1) | ((n & 0x55555555) << 1);
        return n;
    }
}














