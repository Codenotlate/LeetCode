class Solution {
    public int lengthLongestPath(String input) {
        String[] nsplit = input.split("\n");
        Stack<Pair<String, Integer>> stack = new Stack<>();
        int maxLen = 0;
        int curLen = 0;
        for (String ns: nsplit) {
            // split by |\t| again to get the count and the actual string
            String[] tsplit = ns.split("\t");
            int count = 0;
            String actualStr = "";
            for (String ts: tsplit) {
                if (ts.equals("")) {count++;}
                else {actualStr = ts;}
            }
            
            // compare with the peek of the stack
            while (!stack.isEmpty() && stack.peek().getValue() >= count) {
                Pair<String, Integer> popped = stack.pop();
                curLen -= popped.getKey().length() + 1;
            }
            stack.push(new Pair(actualStr, count));
            curLen += actualStr.length() + 1;
            if (actualStr.indexOf(".") != -1) {
                maxLen = Math.max(maxLen, curLen - 1);
            }           
        }
        return maxLen;
    }
}


// an optimized way from solution
// similar idea, but the stack element is just the accumulated length, find number of \t using lastIndexOf() + 1.  And pop out stack based on the number of \t comparing to the size of the stack


// https://leetcode.com/problems/longest-absolute-file-path/discuss/86615/9-lines-4ms-Java-solution
public int lengthLongestPath(String input) {
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(0); // "dummy" length
    int maxLen = 0;
    for(String s:input.split("\n")){
        int lev = s.lastIndexOf("\t")+1; // number of "\t"
        while(lev+1<stack.size()) stack.pop(); // find parent
        int len = stack.peek()+s.length()-lev+1; // remove "/t", add"/"
        stack.push(len);
        // check if it is file
        if(s.contains(".")) maxLen = Math.max(maxLen, len-1); 
    }
    return maxLen;
}

public int lengthLongestPath(String input) {
    String[] paths = input.split("\n");
    int[] stack = new int[paths.length+1];
    int maxLen = 0;
    for(String s:paths){
        int lev = s.lastIndexOf("\t")+1, curLen = stack[lev+1] = stack[lev]+s.length()-lev+1;
        if(s.contains(".")) maxLen = Math.max(maxLen, curLen-1);
    }
    return maxLen;
}