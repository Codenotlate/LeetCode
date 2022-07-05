/*Thought
M0: Build a trie for all strings in products.(time O(nk)) Then for each char in searchWord, dfs to get the first 3 words for that char.
time O(nk + m*3*k)
M1: sort the products array first, then do binary search for each prefix to find the first one word with that prefix, check 3 words starting that position and put into result if valid.
time O(nklogn + m^2*logn) where nk is the total char number in products, m is the length of searchword.
M2: sort the products array first, then use two pointer for products start and end position. For each char of searchword, check if start pos word and end pos word are valid, if not, start++/end--, until the word is valid, checking the following two words, and put valid ones into result.
time O(nklogn + 3mk + n)


// check the discussion here: https://leetcode.com/problems/search-suggestions-system/discuss/1024115/Lessons-learned
// https://leetcode.com/problems/search-suggestions-system/discuss/436151/JavaPython-3-Simple-Trie-and-Binary-Search-codes-w-comment-and-brief-analysis.
// the two pointers method is in comment of solution page

*/
// M1: sort + binary search
class Solution {
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products);
        List<List<String>> res = new LinkedList<>();
        List<String> charRes = new LinkedList<>();
        String prefix = "";
        int start = 0;
        for (char c: searchWord.toCharArray()) {
            prefix += c;
            int firstIdx = findFirst(products, prefix, start);
            for (int i = 0; i <3 && firstIdx + i < products.length; i++) {
                if (products[firstIdx+i].length() < prefix.length()) {continue;}
                String substr = products[firstIdx+i].substring(0, prefix.length());
                if (substr.compareTo(prefix) == 0) {
                    charRes.add(products[firstIdx+i]);
                }
            }
            res.add(new LinkedList(charRes));
            charRes = new LinkedList<>();
            start = firstIdx;
        }
        return res;
    }
    
    private int findFirst(String[] products, String prefix, int start) {
        int end = products.length - 1;
        while (start < end) {
            int mid = start + (end- start) / 2;
            if (products[mid].compareTo(prefix) < 0) {start = mid + 1;}
            else {end = mid;}
        }
        return start;
    }
}


// M2: sort + two pointers
class Solution {
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products);
        // note the empty edge case, should have searchWord.len empty lists in the result
        List<List<String>> res = new ArrayList<>();
        for (int i = 0; i < searchWord.length(); i++) {res.add(new LinkedList<>());}
        int start = 0;
        int end = products.length-1;
        int charIdx = 0;
        while (charIdx < searchWord.length()) {
            char targetChar = searchWord.charAt(charIdx);
            while (start < products.length && (charIdx >= products[start].length() || products[start].charAt(charIdx) != targetChar)) {start++;}
            while (end >= 0 && (charIdx >= products[end].length() || products[end].charAt(charIdx) != targetChar)) {end--;}
            if (start > end) {break;}
            for (int i = 0; i < 3 && start + i <= end; i++) {
                res.get(charIdx).add(products[start+i]);
            }
            charIdx++;
        }
        return res;
        
    }
    
    
}


// Try M0 Trie way next time