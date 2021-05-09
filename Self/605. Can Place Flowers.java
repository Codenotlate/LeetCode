class Solution {
	// the idea is to track the consecutive length of 0s
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
    	// set the initial zeros as 1
    	int zeros = 1;
    	int count = 0;
    	for (int f: flowerbed) {
    		if (f == 0) {zeros++;}
    		else {
    			count = (zeros - 1) / 2;
    			n -= count;
    			// stop the loop early
    			if (n <= 0) {return true;}
    			zeros = 0;
    		}
    	}
    	if (zeros != 0) { // meaning ends with zero
    		count = zeros / 2; // similar idea as set as 1 in the begining
    		n -= count;
    	}
    	return n <= 0;
        
    }
}


// another way is to actually change the 0 to 1 each time and check the adj

public class Solution {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int i = 0, count = 0;
        while (i < flowerbed.length) {
            if (flowerbed[i] == 0 && (i == 0 || flowerbed[i - 1] == 0) && (i == flowerbed.length - 1 || flowerbed[i + 1] == 0)) {
                flowerbed[i++] = 1;
                count++;
            }
             if(count>=n)
                return true;
            i++;
        }
        return false;
    }
}