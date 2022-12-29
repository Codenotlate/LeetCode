/* Thoughts
interval problem : basically whether the new interval will be overlapping withe any existing intervals.
Self think: sort the intervals based on starting time in order (since it's added to the list, it's guaranteed the existing intervals are non-overlapping). Whenever we have a new interval, we use binary search to find the place and neighboring existing intervals. Then compare with two neighboring intervals, see if it can be added.
In the self thinking, the array will be store in an arraylist, thus the inserting will be O(n) time, which will make n intevals added having O(n^2) time.
The optimal solution is to use treemap, which store the intervals in sorted order, and both the search of the neighboring intervals and the insertion of new interval will be log(n) time. Thus the overall time will be reduced to log(1) + log(2) + ...+ log(n) ~ O(nlogn)

Note: TreeSet with order defined as a[0] - b[0] also works. The time is the same.
 */
 // TreeMap way
class MyCalendar {
    TreeMap<Integer, Integer> map;

    public MyCalendar() {
        map = new TreeMap<>();
    }
    
    public boolean book(int start, int end) {
        Integer prevStart = map.floorKey(start);
        Integer nextStart = map.ceilingKey(start);
        if ((prevStart == null || map.get(prevStart) <= start) && (nextStart == null ||nextStart >= end)) {
            map.put(start, end);
            return true;
        }
        return false;
    }
}
/* TreeMap methods summary
ceilingKey(K key)
Returns the least key greater than or equal to the given key, or null if there is no such key.

firstKey()
Returns the first (lowest) key currently in this map.

floorKey(K key)
Returns the greatest key less than or equal to the given key, or null if there is no such key.

higherKey(K key)
Returns the least key strictly greater than the given key, or null if there is no such key.

lastKey()
Returns the last (highest) key currently in this map.

lowerKey(K key)
Returns the greatest key strictly less than the given key, or null if there is no such key.

put(K key, V value)
Associates the specified value with the specified key in this map.

remove(Object key)
Removes the mapping for this key from this TreeMap if present.
*/

// TreeSet way
class MyCalendar {
    TreeSet<int[]> set;

    public MyCalendar() {
        set = new TreeSet<>((int[] i1, int[] i2)-> (i1[0] - i2[0]));
    }
    
    public boolean book(int start, int end) {
        int[] newInt = new int[]{start, end};
        int[] prev = set.floor(newInt);
        int[] next = set.ceiling(newInt);
        if ((prev != null && prev[1] > start) || (next != null && next[0] < end)) {return false;}
        set.add(newInt);
        return true;
    }
}