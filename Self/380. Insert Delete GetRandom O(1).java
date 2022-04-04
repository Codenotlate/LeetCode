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










// Review - can also use two maps
/*Thought
use a map recording <value,index(id)>. Also use a size var to track the current size. When insert, just add the val and size+1 to map. size++. 
When remove, remove the val from map. change the id of the last val to the id of this removed val. size--. And since we need to know the val of last idx, we also need a map to record <idx, value>.
When getRandom,  generate a random number from 0 to size-1. And get that val from the idxToVal map.
*/
class RandomizedSet {
    Map<Integer, Integer> idxToVal;
    Map<Integer, Integer> valToIdx;
    int size;

    public RandomizedSet() {
        size = 0;
        idxToVal = new HashMap<>();
        valToIdx = new HashMap<>();
    }
    
    public boolean insert(int val) {
        if(valToIdx.containsKey(val)) {return false;}
        valToIdx.put(val, size);
        idxToVal.put(size, val);
        size++;
        return true;
    }
    
    public boolean remove(int val) {
        if(valToIdx.containsKey(val)) {
            int idx = valToIdx.get(val);
            valToIdx.remove(val);
            // update last element idx if idx < size - 1. If we remove above val later, then no need to check idx < size-1
            if(idx < size - 1) {
                int lastNum = idxToVal.get(size-1);
                valToIdx.put(lastNum, idx);
                idxToVal.put(idx, lastNum);
                idxToVal.remove(size-1);
            }           
            size--;
            return true;
        }
        return false;
    }
    
    public int getRandom() {
        Random rand = new Random();
        int idx = rand.nextInt(size);
        return idxToVal.get(idx);
    }
}
