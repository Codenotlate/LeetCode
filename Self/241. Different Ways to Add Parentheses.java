class Solution {
	// main idea is divide and conquer
	// each time we encounter an operator, we divide th string to two substrings
	// and recursively call on each substring
	// base case is when the input is a number without operators
	// the time complexity is O(n!) - 1,2,2,5,14,42,132,429... Catalan numbers
    public List<Integer> diffWaysToCompute(String input) {
        Map<String, List<Integer>> map = new HashMap<>();
        return diffWaysHelper(input, map);
    }

    private List<Integer> diffWaysHelper(String input, Map<String, List<Integer>> map) {
    	List<Integer> res = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
        	char c = input.charAt(i);
        	if ("*+-".indexOf(c) != -1) {
        		String p1 = input.substring(0, i);
        		String p2 = input.substring(i+1);
        		List<Integer> left = map.getOrDefault(p1, diffWaysHelper(p1, map));
        		List<Integer> right = map.getOrDefault(p2, diffWaysHelper(p2, map));
        		for (int l: left) {
        			for (int r: right) {
        				switch (c) {
        					case '+':
        						res.add(l + r);
        						break;
        					case '-':
        						res.add(l - r);
        						break;
        					case '*':
        						res.add(l * r);
        						break;
        				}
        			}
        		}
        	}
        }
        // special case: input is a number, no operator included
        if (res.size() == 0) {
        	res.add(Integer.valueOf(input));
        }
        // store the string and corresponding res into map for future use
        map.put(input, res);
        return res;
    }
}



// method2: can also do preprocess first, 
// convert string to a list of strings(separate digits with operators)
// https://leetcode.com/problems/different-ways-to-add-parentheses/discuss/66333/Java-recursive-(9ms)-and-dp-(4ms)-solution


// NOTE!!!!!!!!!
/* Below two funtions use int[] as map key is useless
You can't. arrays use the default identity-based Object.hashCode() implementation and there's no way you can override that. Don't use Arrays as keys in a HashMap / HashSet!
Use pair instead.
*/




// Phase 2 self
// class Solution {
//     public List<Integer> diffWaysToCompute(String expression) {
//         Map<int[], List<Integer>> map = new HashMap<>();
//         return helper(expression, 0, expression.length() - 1, map);       
//     }
    
    
//     private List<Integer> helper(String expression, int start, int end, Map<int[], List<Integer>> map) {
//         List<Integer> res = new LinkedList<>();
//         int[] index = new int[]{start, end};
//         for (int i = start; i <= end; i++) {
//             char c = expression.charAt(i);
//             if ("+-*".indexOf(c) != -1) {
//                 List<Integer> left = map.getOrDefault(index, helper(expression, start, i - 1, map));
//                 List<Integer> right = map.getOrDefault(index, helper(expression, i + 1, end, map));
//                 for (int l: left) {
//                     for (int r: right) {
//                         if (c == '*') {res.add(l * r);}
//                         else if (c == '+') {res.add(l + r);}
//                         else {res.add(l - r);}
//                     }
//                 }
//             }
//         }
        
//         if (start <= end) {
//             if (res.isEmpty()) {res.add(Integer.valueOf(expression.substring(start, end + 1)));}
//             map.put(index, res);
//         }
//         return res;
//     }
// }



// Phase3 self
// class Solution {
//     // divide and conquar + dp record results
//     public List<Integer> diffWaysToCompute(String expression) {
//         int n = expression.length();
//         // dp[i][j] track all the results from expression[i][j], including j.
//         // Generic array creation error: List<Integer>[][] dp = new List<Integer>[n][n]; Thus try using a map
//         Map<int[], List<Integer>> dp = new HashMap<>();
//         return calHelper(expression, 0, n-1, dp);      
//     }
    
//     private List<Integer> calHelper(String s, int start, int end, Map<int[], List<Integer>> dp) {
//         int[] key = new int[]{start, end};
//         if(dp.keySet().contains(key)) {return dp.get(key);}
//         List<Integer> res = new LinkedList<>();
//         // loop and divide when encounter operator
//         for(int i = start; i < end; i++) {          
//             char c = s.charAt(i);
//             if (c == '+' || c == '-' || c == '*') {
//                 List<Integer> left = calHelper(s, start, i-1, dp);
//                 List<Integer> right = calHelper(s, i+1, end, dp);
//                 for(int l: left) {
//                     for(int r: right) {
//                         if (c == '+') {res.add(l + r);}
//                         else if (c == '-') {res.add(l - r);}
//                         else {res.add(l * r);}
//                     }
//                 }
//             }
//         }
//         if (res.size() == 0) { // meaning just number, no operator contains
//             res.add(Integer.valueOf(s.substring(start, end + 1)));
//         }
//         dp.put(key, res);
//         return res;
//     }
// }



/* Initial thought
divide and conquar based on each operation. left side returns a list, and right side returns a list. calculate every pair from the two list with cur operator to add the result to the list of this level.
Given string is immutable in java, we convert it to char array and label the start and end position.
*/
class Solution {
    public List<Integer> diffWaysToCompute(String expression) {
        return compute(expression.toCharArray(), 0, expression.length() - 1, new HashMap<Pair<Integer, Integer>, List<Integer>>());
    }
    
    private List<Integer> compute(char[] chars, int start, int end, HashMap<Pair<Integer, Integer>, List<Integer>> map) {
        Pair<Integer,Integer> pair = new Pair(start, end);
        if(map.containsKey(pair)) {return map.get(pair);}
        // divide and conquar
        List<Integer> res = new LinkedList<>();
        for (int i = start; i <= end; i++) {
            char c = chars[i];
            if (c== '+' || c=='-' ||c=='*') {
                List<Integer> left = compute(chars, start, i-1, map);
                List<Integer> right = compute(chars, i+1, end, map);
                for (int l: left) {
                    for(int r: right) {
                        if (c == '+') {
                            res.add(l + r);
                        } else if (c == '-') {
                            res.add(l - r);
                        } else {
                            res.add(l* r);
                        }
                    }
                }
            }
        }
        // meaning no +-* operator in chars, then return this whole number
        if(res.size() == 0) {
            int num = 0;
            for(int k = start; k <= end; k++) {
                num = num * 10 + (chars[k]-'0');
            }
            res.add(num);
        }
        map.put(pair, res);
        return res;
    }
}


















