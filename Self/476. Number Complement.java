class Solution {
	// first find 0000100000, the get 0000011111, and ^ with original number
    public int findComplement(int num) {
        int mask = 1;
        int temp = num;
        while (temp != 0) {
        	temp >>>= 1;
        	mask <<= 1;
        }
        mask = mask - 1;
        return num ^ mask;
    }
}


class Solution {
	// first find 0000100000, the get 0000011111, and ^ with original number
    public int findComplement(int num) {
        int mask = Integer.highestOneBit(num);
        return num ^ ((mask << 1) - 1);
    }
}


class Solution {
	// first find 0000100000, the get 0000011111, and ^ with original number
    public int findComplement(int num) {
        int mask = 1 << 30;
        while ((mask & num) == 0) {
        	mask >>>= 1;
        }
        return num ^ ((mask << 1) - 1);
    }
}



/**
对于 10000000 这样的数要扩展成 11111111，可以利用以下方法：

mask |= mask >> 1    11000000
mask |= mask >> 2    11110000
mask |= mask >> 4    11111111
public int findComplement(int num) {
    int mask = num;
    mask |= mask >> 1;
    mask |= mask >> 2;
    mask |= mask >> 4;
    mask |= mask >> 8;
    mask |= mask >> 16;
    return (mask ^ num);
}

*/