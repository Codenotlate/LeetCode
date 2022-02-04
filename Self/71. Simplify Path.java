class Solution {
    // phase3 self
    // time O(N) space O(N)
    public String simplifyPath(String path) {
        String[] splits = path.split("/");
        Stack<String> stack = new Stack<>();
        for (String s: splits) {
            if (s.equals("") || s.equals(".")) {continue;}
            if (s.equals("..")) {
                if(!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                stack.push(s);
            }
        }
        
        Stack<String> rev = new Stack<>();
        while (!stack.isEmpty()) {
            rev.push(stack.pop());
        }
        StringBuilder sb = new StringBuilder("/");
        while (!rev.isEmpty()) {
            sb.append(rev.pop());
            sb.append("/");
        }
        // don't forget "/" special case
        if (sb.length() > 1) {sb.deleteCharAt(sb.length() - 1);}
        
        return sb.toString();
    }
}

// Review self
// Note: Better use a deque instead of above stack and beloew list. Because for stack, deque will have pop as O(1) and also can iterate from start to end without reverse the stack. For list, deque can pop as O(1), but the remove in list is actually O(n)
class Solution {
    /* initial thought:
    split the original path with '/', add valid strings to the result list and convert to String as the result with join('/').
    if the string is "." or "", skip it, if the string is "..", remove the last string added to the result list
    */
    // List way ----- not prefered
    public String simplifyPath(String path) {
        String[] splited = path.split("/");
        List<String> strList = new LinkedList<>();
        for (String s: splited) {
            if (s.equals("") || s.equals(".")) {continue;}
            if (s.equals("..")) {
                if(strList.size() > 0) {strList.remove(strList.size()-1);}
            }
            else {strList.add(s);}
        }
        StringBuilder res = new StringBuilder();
        for (String str: strList) {
            res.append("/").append(str);
        }
        return res.length() == 0? "/" :res.toString();
    }


    // improve to deque way to make remove last element as O(1)
    public String simplifyPath(String path) {
        String[] splited = path.split("/");
        Deque<String> strDeque = new LinkedList<>();
        for (String s: splited) {
            if (s.equals("") || s.equals(".")) {continue;}
            if (s.equals("..")) {
                if(strDeque.size() > 0) {strDeque.pollLast();}
            }
            else {strDeque.add(s);}
        }
        StringBuilder res = new StringBuilder();
        for (String str: strDeque) {
            res.append("/").append(str);
        }
        return res.length() == 0? "/" :res.toString();
    }
}