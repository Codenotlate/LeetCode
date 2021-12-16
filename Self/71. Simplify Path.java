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