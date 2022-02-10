class RandomizedSet {
    /* initial thought
    we maintain a map: element -> index in the below list, and because it's a rondomized set, we don't need to consider duplicates element cases.
    We also maintain a list, and a size number to track the current size of the set(List).
    When insert: we check if that element is already in map.keySet(), if yes, return false. If no, we add the element in the end of the list and put (element, index = size) into the map. update size++.
    When remove: we check if that element is in map.keySet(). if no, return false. If yes, get the index in the list, swap list[size-1] with list[index], size--. remove that element key-value pair from the map and also update the index for the last element in the map.
    When getRandom: we randomly generate an index number between 0 to size-1. return list[randomIdx].
    */
    
    List<Integer> list;
    Map<Integer, Integer> idxMap;
    int size;
    Random rand = new Random();
    
    public RandomizedSet() {
        list = new ArrayList<>();
        idxMap = new HashMap<>();
        size = 0;
    }
    
    public boolean insert(int val) {
        if (idxMap.containsKey(val)) {return false;}
        list.add(size, val);
        idxMap.put(val, size);
        size++;
        return true;        
    }
    
    public boolean remove(int val) {
        if (!idxMap.containsKey(val)) {return false;}
        int idx = idxMap.get(val);
        int lastElement = list.get(size-1);
        list.set(idx, lastElement);
        idxMap.put(lastElement, idx);
        size--;
        idxMap.remove(val);
        return true;
    }
    
    public int getRandom() {
        int randomIdx = rand.nextInt(size);
        return list.get(randomIdx);
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */