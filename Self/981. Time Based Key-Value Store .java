class TimeMap {
    Map<String, List<Pair<Integer, String>>> map;

    public TimeMap() {
        map = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        // !!!!!!!!!! USE ARRAYLIST NOT LINKEDLIST!!!!!!!!!!!!!!!
        map.putIfAbsent(key, new ArrayList<>());
        map.get(key).add(new Pair(timestamp, value));
    }
    
    public String get(String key, int timestamp) {
        if (!map.containsKey(key)) {return "";}
        int idx = binarySearch(timestamp, map.get(key));
        if (idx < 0) {return "";}
        return map.get(key).get(idx).getValue();
    }
    
    // return the idx of the first one <= t
    private int binarySearch(int t, List<Pair<Integer, String>> list) {
        int start = 0;
        int end = list.size() - 1;
        if (list.get(start).getKey().intValue() > t) {return -1;}
        while (start < end - 1) {
            int mid = start + (end - start) / 2;
            int midNum = list.get(mid).getKey().intValue();
            if (midNum == t) {return mid;}
            else if (midNum > t) {
                end = mid -1;
            } else {
                start = mid;
            }
        }
        
        return list.get(end).getKey().intValue() <= t ? end : start;
    }
}

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */




/*
https://leetcode.com/problems/time-based-key-value-store/discuss/226663/TreeMap-Solution-Java
Check the discussion of it ------ "While this is nice and concise solution, we are not leveraging on the condition that every subsequent set that is called will strictly have higher timestamp value. this means we could use Arraylist instead of a treemap. Also I think problem author wanted us to write raw binary search instead of using out-of-box treemap and using the floorkey."

https://leetcode.com/problems/time-based-key-value-store/discuss/282703/Java-binary-search-and-treemap-solution
discussion has the time complexity analysis of the two methods.
First solution ->
time complexity of set() ---- O(1) best case, O(n) worst case, because ArrayList needs to be rearranged in case internal array is overflowed.
time complexity of get() ---- O(log n) since we perform binary search

Second solution ->
time complexity of set() ---- O(logK) , because it's just simple put() operation in TreeMap. Insertion in treeMap is log(k) where k is number of timestamps for that key,
time complexity of get() ---- O(log n) , because tree map internally uses binary search tree for sorting out the elements.

*/

