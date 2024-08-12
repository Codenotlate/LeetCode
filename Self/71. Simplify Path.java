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








// Review
/*Thought
A pattern to use stack. And consider get the final result in a easier way, consider replace stack with double sides deque.So that when we are processing, we can focus on the end side, and when return the final result we can poll out from the start side.
First split the path by '/' into an array of string.
if cur string is "." or "", skip cur. Else if cur string is "..", pop out the last value of deque. Otherwise, add the string. After process all strings, poll out the remaining strings from the start of the deque into a stringBuilder, and separated by '/'.
time O(n) space O(n)
*/
class Solution {
    public String simplifyPath(String path) {
        String[] splits = path.split("/");
        Deque<String> deque = new LinkedList<>();
        for (String s: splits) {
            if (s.equals("") || s.equals(".")) {continue;}
            else if (s.equals("..")) {deque.pollLast();}
            else {deque.add(s);}
        }
        StringBuilder res = new StringBuilder();
        while(!deque.isEmpty()) {
            res.append("/").append(deque.pollFirst());
        }
        return res.length() == 0? "/" : res.toString();
    }
}



// 20240811
class Solution {
    public String simplifyPath(String path) {
        Deque<String> deque = new LinkedList<>();
        String[] split = path.split("/");
        for (String s: split) {
            if (s.equals("") || s.equals(".")) {continue;}
            if (s.equals("..")) {
                if (!deque.isEmpty()) {deque.pollLast();}
            } else {
                deque.addLast(s);
            }
        }
        if(deque.isEmpty()) {return "/";}
        String[] list = new String[deque.size()];
        for (int i = 0; i < list.length; i++) {
            list[i] = deque.pollFirst();
        }
        return "/"+String.join("/", list);


    }
}