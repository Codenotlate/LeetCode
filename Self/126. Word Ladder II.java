class Solution {
    // phase3 self: based on regular BFS way in 127
    // basically we store the String list in queue, each time we check the last string in the list to find valid next string to add.
    // two notes to remember: when we add the new List with valid next string to the queue, we need to make a copy of the curList and add the copy to the queue. Otherwise curList is a reference and maybe changed along the way.
    // Also considering the case when two same level list have the same next string, since we are not only counting the string length, but also want to track each shortest path here, we can't direcly add that string to visited after the first curList process, we need to have a visit set for that level, and only add that level visit set to total visited set after this whole level is finished processing.
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new LinkedList<>();
        Set<String> wordSet = new HashSet(wordList);
        if (!wordSet.contains(endWord)) {return res;}
        
        
        Queue<List<String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        List<String> beginList = new LinkedList<>();
        beginList.add(beginWord);
        queue.add(beginList);
        
        
        while(!queue.isEmpty()){
            int size = queue.size();
            Set<String> levelVisited = new HashSet<>();
            while (size-- > 0) {
                List<String> curList = queue.poll();
                String curStr = curList.get(curList.size() - 1);
                // if the last word is alreaydy endword, meaning this is already the shortest path length, thus we only process curlists in this level, and add to res if it has endWord as last word.
                if (curStr.equals(endWord)) {
                    res.add(new LinkedList(curList));
                    continue;
                }
                
                // find valid next Str for curStr
                for (int i = 0; i < curStr.length(); i++) {
                    char[] chars = curStr.toCharArray();
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == chars[i]) {continue;}
                        chars[i] = c;
                        String next = new String(chars);
                        if (wordSet.contains(next) && !visited.contains(next)) {
                            levelVisited.add(next);
                            List<String> nextList = new LinkedList(curList);
                            nextList.add(next);
                            queue.add(nextList);
                        }
                    }
                }
            }
            visited.addAll(levelVisited);
        }
        
        return res;
    }
}


// can optimize using trace path as the node to its parent node list (check idea from solution)
// can also use bidirectional BFS, will do in the future