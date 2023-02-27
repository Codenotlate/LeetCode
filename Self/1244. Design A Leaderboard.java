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




// Review 23/2/27 - self came up with M2, but not familiar with time complexity analysis for treeMap
/* Thoughts
M1: maintain size k min heap for top (time O(nlogk)). Hashmap for addScore and reset (time O(1)).
M2: Using a hashmap for mapping between playerId and score. Using a treemap for mapping between score and count of that score.
For addScore, we get the oldScore of that player if any, reduce the count by 1 in the treemap for that oldScore. (time O(logn)). Add the count by 1 in the treemap for the newScore (time O(logn)). And update/add the new <id, score> in the hashmap (time O(1)). Thus total time O(logn)
For reset, get the score of that Id using hashmap, remove it from hashMap. Then reduce the count of that score in treemap. time O(logn)
For top, get the sorted (scores,count) in the treeMap key values. Go from largest to smallest, add count*score to the result, until count == K.
About the time:
If get sorted (scores, count) is not already O(1) in treemap, then we can get it by inorder tree traverse, which takes O(N).
Alternatively, a single next() call in treemap iterator is O(logn), thus if we call it k times, then time O(klogn). [can be better than PQ way O(nlogk)]

 */
// M1:
class Leaderboard {
    Map<Integer, Integer> map;
    PriorityQueue<Integer> pq;

    public Leaderboard() {
        map = new HashMap<>();
        pq = new PriorityQueue<>();
    }
    
    public void addScore(int playerId, int score) {
        map.put(playerId, map.getOrDefault(playerId,0)+score);
    }
    
    public int top(int K) {
        int sum = 0;
        for (int id: map.keySet()) {
            int score = map.get(id);
            pq.add(score);
            K--;
            if (K < 0) {pq.poll(); K++;}
        }
        while(!pq.isEmpty()) {
            sum += pq.poll();
        }
        return sum;
    }
    
    public void reset(int playerId) {
        map.remove(playerId);
    }
}

// M2:
class Leaderboard {
    Map<Integer, Integer> map;
    TreeMap<Integer, Integer> treeMap;

    public Leaderboard() {
        map = new HashMap<>();
        treeMap = new TreeMap<>((e1,e2) ->(e2 - e1));
    }
    
    public void addScore(int playerId, int score) {
        if (map.containsKey(playerId)) {
            int oldScore = map.get(playerId);
            treeMap.put(oldScore, treeMap.get(oldScore)-1);
            if (treeMap.get(oldScore) == 0) {treeMap.remove(oldScore);}
        }
        map.put(playerId, map.getOrDefault(playerId,0)+score);
        int newScore = map.get(playerId);
        treeMap.put(newScore, treeMap.getOrDefault(newScore, 0)+1);

    }
    
    public int top(int K) {
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry: treeMap.entrySet()) {
            int count = Math.min(K, entry.getValue());
            int score = entry.getKey();
            sum += count * score;
            K -= count;
            if (K == 0) {break;}
        }
        return sum;
        
    }
    
    public void reset(int playerId) {
        int score = map.get(playerId);
        map.remove(playerId);
        treeMap.put(score, treeMap.get(score) -1);
        if (treeMap.get(score) == 0) {treeMap.remove(score);}
    }
}
