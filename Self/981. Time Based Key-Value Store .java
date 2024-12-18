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



// Review

/*Thought
obviously we will need to use map structure for each key. Then under each key, we need to record both time and value. And since later we will call get based on some random time, we need to find the first time in the stored array that is <= to that random time. Think about using binary search for this,  thus for each key, we will use ArrayList<int[]>, so we can do binary search on int[0], and get its corresponding value on int[1].
set: time O(1)
get: time O(log(key array len))
*/
class TimeMap {
    Map<String, List<Pair<Integer, String>>> map;

    public TimeMap() {
        map = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        if (!map.containsKey(key)) {map.put(key, new ArrayList<Pair<Integer, String>>());}
        map.get(key).add(new Pair(timestamp, value));
    }
    
    public String get(String key, int timestamp) {
        // pay attention to all the null condition
        if(!map.containsKey(key)) {return "";}
        List<Pair<Integer, String>> list = map.get(key);
        int start = 0;
        int end = list.size() - 1;
        // pay attentin to outside start and end range cases
        if (list.get(end).getKey() <= timestamp) {return list.get(end).getValue();}
        // here is to find first one >= target
        while (start < end) {
            int mid = start + (end- start) / 2;
            if (list.get(mid).getKey() > timestamp) {end = mid;}
            else {start = mid + 1;}
        }
        return start == 0 ? "" : list.get(start-1).getValue();
    }
}






// 2024.10.30
// binary tree for the value array in sorted time order for each key
// time for set O(1) get O(log(value_len))
// This is the optimal way, but consider OOP, maybe better idea to have a private class for data.
class TimeMap {
    Map<String, List> map;

    public TimeMap() {
        map = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        map.putIfAbsent(key, new ArrayList<Pair<Integer, String>>());
        map.get(key).add(new Pair(timestamp, value));
    }
    
    public String get(String key, int timestamp) {
        if (!map.containsKey(key)) {return "";}
        List<Pair<Integer, String>> values = map.get(key);
        int left = 0;
        int right = values.size()-1;
        String res = "";
        while (left < right) {
            int mid = left + (right-left) / 2;
            if (values.get(mid).getKey() <= timestamp) {
                res = values.get(mid).getValue();
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        if (values.get(left).getKey() <= timestamp) {
            res = values.get(left).getValue();
        }
        return res;
    }
}
