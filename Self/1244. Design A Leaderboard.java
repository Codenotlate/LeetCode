/*Thought (hint from solution)
M0: naive way. using a hashmap to store. addScore and reset both O(1). For top, we need to sort all values in the map each time and get the top k. time O(nlogn)
M1: improved from M0, using size k pq instead of sort. for top, maintain a size k pq, time O(nlogk) space O(n+k)
M2: using treeMap. maintain a regular hashmap <id, score>. Then also use a treemap for <score, count>. For each operation, we change the hashmap, and search the score in treemap to update its count. If count == 0, remove that score key from treemap. addScore and reset will be O(logn). For top, we iterate the entrySet of the treeMap, and stop once we got k values from it. This requires the treemap to be init as reverse order.
*/
class Leaderboard {
    Map<Integer, Integer> idmap;
    TreeMap<Integer, Integer> valuemap;

    public Leaderboard() {
        idmap = new HashMap<>();
        // note the treemap init as reverse order
        valuemap = new TreeMap<>(Collections.reverseOrder());
    }
    
    public void addScore(int playerId, int score) {
        if (idmap.containsKey(playerId)) {
            int oldscore = idmap.get(playerId);
            idmap.put(playerId, idmap.get(playerId)+score);
            int count = valuemap.get(oldscore);
            if(count == 1) {valuemap.remove(oldscore);}
            else {valuemap.put(oldscore, count-1);}            
        } else {
            idmap.put(playerId, score);
        }
        int newscore = idmap.get(playerId);
        valuemap.put(newscore, valuemap.getOrDefault(newscore, 0) +1);
    }
    
    public int top(int K) {
        int res = 0;
        for (Map.Entry<Integer,Integer> entry: valuemap.entrySet()) {
            int count = entry.getValue();
            int score = entry.getKey();
            res += Math.min(count, K) * score;
            K -= count;
            if(K < 0) {break;}
            
        }
        return res;
    }
    
    public void reset(int playerId) {
        int oldscore = idmap.get(playerId);
        idmap.remove(playerId);
        int count = valuemap.get(oldscore);
        if(count == 1) {valuemap.remove(oldscore);}
        else {valuemap.put(oldscore, count-1);}            
    }
}

