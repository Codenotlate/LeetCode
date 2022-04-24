/* Initial thought
naive way is to pass votes to get the count for every char at each position. Then sort the chars based on their count in each position array.
time O(n * l + l*l*logl) = O(26n + 26^2log26) since l <= 26
space O(n*26) = O(n)
*/
class Solution {
    public String rankTeams(String[] votes) {
        int l = votes[0].length();
        int[][] counts = new int[26][l]; // counts[i][j] represents counts of char 'A'+i in the jth place.
        Set<Character> uniChars = new HashSet<>();
        for (String v: votes) {
            for(int i = 0; i < v.length(); i++) {
                uniChars.add(v.charAt(i));
                counts[v.charAt(i)-'A'][i]++;
            }
        }
        
        List<Character> res = new LinkedList(uniChars);
        Collections.sort(res, (c1, c2)-> {
            for(int j = 0; j < l; j++) {
                if(counts[c1-'A'][j] != counts[c2-'A'][j]) {return counts[c2-'A'][j] - counts[c1-'A'][j];}               
            }
            return c1-c2;
        });
        
        StringBuilder resStr = new StringBuilder();
        for(char c: res) {resStr.append(c);}
        return resStr.toString();
        
    }
}


// similar in post: https://leetcode.com/problems/rank-teams-by-votes/discuss/524853/Java-O(26n%2B(262-*-log26))-Sort-by-high-rank-vote-to-low-rank-vote
// https://leetcode.com/problems/rank-teams-by-votes/discuss/533307/Java-Straight-Forward-Clean-code


/* Not able to custom sort char array, Thus need to use a list of Characters instead of char array.
Sorting an array of char with a specific Comparator is not conventional.
So not surprising that the Arrays API doesn't provide such a method.
Generally, Comparator and Comparable implementations rely only on the state of the compared objects to return the result, not on an external variable as in your example.
Similarly the Arrays API doesn't provide any method to create a Stream of char because streaming characters is also not a very conventional need.
So you don't have a straight built-in way to solve your issue.
*/

/*We also can't use asList to convert char array to List<Character>. We can use stream in Java8*/



class Solution {
    public String rankTeams(String[] votes) {
        int k = votes[0].length();
        int[][] map = new int[k][26];
        for (String v: votes) {
            for (int i  = 0; i < v.length(); i++) {
                map[i][v.charAt(i) - 'A']++;
            }
        }
        
        List<Character> chars = new LinkedList<>();
        for (char c: votes[0].toCharArray()) {chars.add(c);}
        Collections.sort(chars, (c1, c2) -> {
            for (int i = 0; i < k; i++) {
                if (map[i][c1 -'A'] != map[i][c2-'A']) {return map[i][c2-'A']-map[i][c1-'A'];}
            }
            return c1-c2;
        });
        
        
        StringBuilder res = new StringBuilder();
        for (char c: chars) {res.append(c);}
        return res.toString();
    }
}






















