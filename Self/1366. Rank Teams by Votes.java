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