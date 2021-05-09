class Solution {
    public boolean isValid(String s) {
        Map<Character, Character> map = new HashMap<>();
        map.put(')','(');
        map.put(']','[');
        map.put('}','{');

        Stack<Character> stack = new Stack<>();
        for (char c: s.toCharArray()) {
        	if (map.keySet().contains(c)) {
        		if (stack.isEmpty() || stack.peek() != map.get(c)) {
        			return false;
        		}
        		stack.pop();
        	} else {
        		stack.add(c);
        	}
        }
        return stack.isEmpty();

    }
}

class Solution {
	// fastest way
	// use char[] instead of stack, a bit quicker
	// and get rid of hashmap, since hashmap and hash functions take time too
    public boolean isValid(String s) {
        char[] stack = new char[s.length()];
		int head = -1;
        for (char c: s.toCharArray()) {
        	if (c == '(') {stack[++head] = ')';}
        	else if (c == '{') {stack[++head] = '}';}
        	else if (c == '[') {stack[++head] = ']';}
        	else {
        		if (head == -1 || stack[head] != c) {
        			return false;
        		}
        		head -= 1;
        	}
        }
        return head == -1;

    }
}
