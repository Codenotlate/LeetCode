/*Initial thought
Simply use an array with maxSize length, track currentSize. 
When init, set up the array with len as maxSize. time: O(1)
For push, check if curSize == arr.len - 1, if not, just have arr[curSize++] = x; time O(1)
For pop, return curSize == 0 ? -1 : arr[--curSize] time O(1)
For increment, change index in range [0, min(k,len)-1] += val. time O(min(k,n))

improved way(self): try to optimize increment to O(1) as well. Similar to interval problem trick: use another array, only label the start pos of the range with val, and label (end+1) pos with -val. This makes increment O(1), when we do pop, we can calculate the accumulated sum for all current elements and add to stack array, also clear the label array. This way pop is also O(1) on average.
[1,2,3] (2,100)
[100,0,-100]
[1+100,2+100,3+0]

improved way(from solution): similarly use another array to store the increment. arr[i] means for range [0, i] we need to increment arr[i]. So when pos i is popped out, we need to combine this effore into arr[i-1]. All action except init is O(1), init in Java is O(n). Also we can simply using a stack instead of array. But array also works.

*/
class CustomStack {
    int[] stack;
    int curSize;
    int[] inc;

    public CustomStack(int maxSize) {
        stack = new int[maxSize];
        inc = new int[maxSize];
        curSize = 0;
    }
    
    public void push(int x) {
        if(curSize == stack.length) {return;}
        stack[curSize++] = x;
    }
    
    public int pop() {
        if (curSize == 0) {return -1;}
        curSize--;
        if(curSize > 0) {inc[curSize-1] += inc[curSize];}
        int num = stack[curSize] + inc[curSize];
        inc[curSize] = 0; // don't forget to clear it to 0
        return num;
    }
    
    public void increment(int k, int val) {
        int idx = Math.min(k, inc.length) - 1;
        inc[idx] += val;
    }
}





// not the best way
class CustomStack {
    int[] stack;
    int curSize;

    public CustomStack(int maxSize) {
        stack = new int[maxSize];
        curSize = 0;
    }
    
    public void push(int x) {
        if(curSize == stack.length) {return;}
        stack[curSize++] = x;
    }
    
    public int pop() {
        return curSize == 0? -1 : stack[--curSize];
    }
    
    public void increment(int k, int val) {
        for (int i = 0; i < k && i < curSize; i++) {
            stack[i] += val;
        }
    }
}




// Review - get hint from above M1
class CustomStack {
    int[] arr;
    int size;
    int[] add; // add[i] represents the value needs to be added for range[0:i]
    int peek;

    public CustomStack(int maxSize) {
        arr = new int[maxSize];
        size = maxSize;
        add = new int[maxSize];
        peek = -1;
    }
    
    public void push(int x) {
        if (peek+1 < size) {
            peek++;
            arr[peek] = x;
        }
    }
    
    public int pop() {
        if (peek < 0) {return -1;}
        int res = arr[peek] + add[peek];
        if (peek >= 1) {add[peek-1] += add[peek];}
        add[peek] = 0;
        peek--;
        return res;
    }
    
    public void increment(int k, int val) {
        if (peek < 0) {return;}
        int right = Math.min(peek, k-1);
        add[right] += val;
    }
}

