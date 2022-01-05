// phase 2: based on 79
// this method now gets TLE in lc
class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new LinkedList<>();
        if (board.length == 0 || board[0].length == 0) {return res;}
        int m = board.length; int n = board[0].length;        
        for (String word: words) {
            // reset visited for each word
            int[][] visited = new int[m][n];
            boolean hasWord = false;
            if (m * n < word.length()) {continue;}
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (board[i][j] == word.charAt(0) && dfs(board, word, 0, i, j, visited)) {
                        // once we know the word is in board, we can add this word to res and move on to next word
                        hasWord = true;
                        res.add(word);
                        break;
                    }
                }
                if (hasWord) {break;}
            }
            
        }
        
        return res;
    }
    
    // exactly same as 79
    private boolean dfs(char[][] board, String word, int wordPos, int i, int j, int[][] visited) {
        if (wordPos >= word.length()) {return true;}
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || visited[i][j] == 1) {return false;}
        if (board[i][j] == word.charAt(wordPos)) {
            visited[i][j] = 1;
            int[][] dir = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] d: dir) {
                if (dfs(board, word, wordPos + 1, i + d[0], j + d[1], visited)) {
                    return true;
                }
            }
            visited[i][j] = 0;
        }
        return false;
    }
}




// https://leetcode.com/problems/word-search-ii/discuss/59780/Java-15ms-Easiest-Solution-(100.00)
// analysis for Trie solution
/*
Someone correct me if I'm missing something, but this seems wrong to me. In the naive approach, you iterate through the word list, and for each word you iterate through every cell, and for each cell you run DFS. Note importantly that only in the DFS do you iterate through the word length of every word, in the outermost loop I described you are just iterating through the number of words. So, I believe the runtime would be O(m * n * num_words * 4^wl). But, in fact this is wrong too I think because if you have a decently sized word 4^wl may be larger than m * n, but DFS can only go to m * n maximum iterations, so I think runtime is in fact O(m * n * num_words * min(4^wl, m * n)).

Now, how does this change for a trie? In the worst case, the words will share NO prefixes, and so traversing the tree will be equivalent to traversing the list of words, and thus the runtime is EXACTLY the same.

The runtime does get better in the best case, if all the words share the same prefix until their last letter, in which case you only have to iterate through one word basically and you get a runtime of O(m * n * min(4^wl, m * n)). However, this is slightly wrong because to create the trie you need to iterate through wl * num_words, so the runtime is actually O(m * n * min(4^wl, m * n) + wl * num_words). (Indeed, this factor was in the other runtimes too but it was insignificant and thus dropped there.)
*/


// Phase3 self (pure dfs + backtracting -> TLE)
class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        int m = board.length;
        int n = board[0].length;
        Set<String> res = new HashSet<>();
        int[][] visited = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (String word: words) {
                    if (res.contains(word)) {continue;}
                    dfs(board, i, j, word, 0, visited, res);
                }
            }
        }
        return new LinkedList(res);
    }
    
    
    private void dfs(char[][] board, int i, int j, String word, int idx, int[][] visited, Set<String> res) {
        if (idx == word.length() - 1) {
            if (board[i][j] == word.charAt(idx)) {
                res.add(word);
            }
            return;
        }
        
        if (board[i][j] != word.charAt(idx)) {return;}
        visited[i][j] = 1;
        int[][] dirs = new int[][] {{-1,0},{1,0},{0,-1}, {0, 1}};
        for (int[] d: dirs) {
            int newi = i + d[0];
            int newj = j + d[1];
            if (newi >= 0 && newi < board.length && newj >= 0 && newj < board[0].length && visited[newi][newj] == 0) {
                dfs(board, newi, newj, word, idx+1, visited, res);
            }
        }
        visited[i][j] = 0;
        return;        
    }
}


// phase3 solution: using Trie + dfs + backtracking
// good solution article: https://leetcode.com/problems/word-search-ii/solution/
class Solution {
    private class Trie {
        Trie[] links;
        String word;
        int childNum; // opt3


        public Trie() {
            links = new Trie[26];
            word = null;
            childNum = 0;
        }
    }
    
    
    public List<String> findWords(char[][] board, String[] words) {
        // step1 Build the Trie based on the words
        Trie root = new Trie();
        for (String word: words) {
            Trie cur = root;
            for (char c: word.toCharArray()) {
                if (cur.links[c-'a'] == null) {cur.links[c-'a'] = new Trie(); cur.childNum++;}
                cur = cur.links[c -'a'];
            }
            cur.word = word; // opt1
        }
        
        // step2 dfs + backtracking each cell(i,j) in board with trie
        List<String> res = new LinkedList<>();
        int m = board.length;
        int n = board[0].length;
        int[][] visited = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = board[i][j];
                if (root.links[c-'a'] != null) {
                    dfs(board, i, j, root, visited, res);
                }
            }
        }
        return res;
    }
    
    
    private void dfs(char[][] board, int i, int j, Trie parent, int[][] visited, List<String> res) {
        char c = board[i][j];
        Trie child = parent.links[c - 'a'];
        
        if (child.word != null) {
            res.add(child.word);
            child.word = null; // opt2
        }
        visited[i][j] = 1;
        int[][] dirs = new int[][] {{-1,0},{1,0},{0,-1}, {0, 1}};
        for (int[] d: dirs) {
            int newi = i + d[0];
            int newj = j + d[1];
            if (newi >= 0 && newi < board.length && newj >= 0 && newj < board[0].length && visited[newi][newj] == 0 && child.links[board[newi][newj] - 'a'] != null) {
                dfs(board, newi, newj, child, visited, res);
            }
        }
        
        visited[i][j] = 0;
        // opt3: remove visited leaf node
        
        if (child.childNum == 0) {parent.links[c-'a'] = null;}       
    }
    
}





























