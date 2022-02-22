/* Initial thought
naive way: maintain a dictionary(snapid, whole array).  space is bad.
improved way: for each positon, maintain a list of (snapid, number) pair. When we do get, we binary search the snapid in the list of that position and return the number. time O(O(log(count of set))) space O(count of set + len(for the position array))
*/

class SnapshotArray {
    List<Pair<Integer, Integer>>[] posArr;
    int snapId;

    public SnapshotArray(int length) {
        posArr = new List[length];
        snapId = 0;
        for (int i = 0; i < length; i++) {
            posArr[i] = new ArrayList<>();
            posArr[i].add(new Pair(snapId, 0));
        }        
    }
    
    public void set(int index, int val) {
        int size = posArr[index].size();
        if (posArr[index].get(size-1).getKey() == snapId) {posArr[index].set(size-1, new Pair(snapId, val));}
        else {
            posArr[index].add(new Pair(snapId, val));
        }
    }
    
    public int snap() {
        snapId++;
        return snapId - 1;
    }
    
    public int get(int index, int snap_id) {
        List<Pair<Integer, Integer>> list = posArr[index];
        // use BS to find the first snapId <= target id, return its corresponding value in the pair
        int start = 0;
        int end = list.size() - 1; // note size is O(1)
        if (list.get(end).getKey() <= snap_id) {return list.get(end).getValue();}
        while (start < end - 1) {
            int mid = start + (end - start) / 2;
            if (list.get(mid).getKey() > snap_id) {
                end = mid - 1;
            } else {
                start = mid;
            }
        }
        if (list.get(end).getKey() <= snap_id) {
            return list.get(end).getValue();
        } else {
            return list.get(start).getValue();
        }
        
    }
}

/*
Time Complexity:

SnapshotArray(int length): O(n)
set(int index, int val): O(1)
snap(): O(1)
get(int index, int snap_id): O(log(Set))

https://leetcode.com/problems/snapshot-array/discuss/350562/JavaPython-Binary-Search
same idea as the discussion comment form yilin_10
The OP uses Treemap way, which takes O(log(set)) time instead of O(1) in our case.







*/