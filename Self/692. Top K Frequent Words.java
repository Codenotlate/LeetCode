class Solution {
    /* Initial thought
    first pass to get the count dict for each word. Then loop the dict, add each word is keyset to a minPQ with size k. The comparator of the minPQ will be self defined based on freq in dict and also the lexi order if the freq is the same.
    time O(nlogk) space O(n + k) = O(n)  for the dict and the heap.
    */
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> freqMap = new HashMap<>();
        for (String w: words) {
            freqMap.put(w, freqMap.getOrDefault(w, 0) + 1);
        }
        // note the order for same freq ones are reverse lexi order, since this is the minHeap, it stores the reverse order result
        PriorityQueue<String> minHeap = new PriorityQueue<>((w1, w2) -> freqMap.get(w1) == freqMap.get(w2) ? w2.compareTo(w1): freqMap.get(w1) - freqMap.get(w2));
        int size = 0;
        
        for (String w: freqMap.keySet()) {
            minHeap.add(w);
            size++;
            if (size > k) {minHeap.poll();}
        }
        
        List<String> res = new LinkedList<>();
        // remeber the process here
        while(!minHeap.isEmpty()) {res.add(0, minHeap.poll());}
        return res;
    }
}

// summary for solutions
// https://leetcode.com/problems/top-k-frequent-words/discuss/431008/Summary-of-all-the-methods-you-can-imagine-of-this-problem
// Note for BucketSort + BST(TreeSet) way, The worse time complexity of is O(nlogn) if all string have one time.
// TreeSet in Java intro: https://www.geeksforgeeks.org/treeset-in-java-with-examples/

// next time try the bucket sort + Trie method