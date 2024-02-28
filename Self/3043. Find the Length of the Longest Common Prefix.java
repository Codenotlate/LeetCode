// from weekly contest

// we only care the longest length and not which pair produces that. Thus we can put all prefix from arr1 to a set first, and go through all prefix from arr2 to get the result. time O(m+n)
class Solution {
    public int longestCommonPrefix(int[] arr1, int[] arr2) {
        int maxLen = 0;
        Set<Integer> prefixSet = new HashSet<>();
        for (int i = 0; i < arr1.length; i++) {
            int n1 = arr1[i];
            while(n1 != 0) {
                prefixSet.add(n1);
                n1 /= 10;
            }           
        }
        
        for (int j = 0; j < arr2.length; j++) {     
            int n2 = arr2[j];
            while (n2 != 0) {
                if (prefixSet.contains(n2)) {
                    maxLen = Math.max(maxLen, Integer.toString(n2).length());
                    break;
                }
                n2 /= 10;
            }

        }
        return maxLen;
    }
}



// another way using Trie - try next time
// Use arr1 to build the Trie
class Solution {
    private class Node {
        Node[] children = new Node[10];
    }

    public int longestCommonPrefix(int[] arr1, int[] arr2) {
        int maxLen = 0;
        Node root = new Node();
        for (int num: arr1) {
            insert(root, num);
        }
        for (int num:arr2) {
            if (Integer.toString(num).length() <= maxLen) {continue;}
            maxLen = Math.max(maxLen,query(root, num));
        }
        return maxLen;
    }

    private void insert(Node root, int num) {
        char[] chars = Integer.toString(num).toCharArray();
        for (char c: chars) {
            if (root.children[c-'0'] == null) {
                root.children[c-'0'] = new Node();
            }           
            root = root.children[c-'0'];
        }
    }

    private int query(Node root, int num) {
        char[] chars = Integer.toString(num).toCharArray();
        int len = 0;
        for (char c: chars) {
            if (root.children[c-'0'] == null) {break;}
            len++;
            root = root.children[c-'0'];
        }
        return len;
    }
}