class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        // M1 obvious way: using Set, time O(m + n) space O(m)
        Set<Integer> num1 = new HashSet<>();
        Set<Integer> result = new HashSet<>();
        for (int n: nums1) {
            num1.add(n);
        }
        for (int n: nums2) {
            if (num1.contains(n)) {
                result.add(n);
            }
        }
        
        int[] resultarr = new int[result.size()];
        int i = 0;
        for (int n: result) {
            resultarr[i] = n;
            i++;
        }
        return resultarr;
        
    }
}



// detail discussion in comment of official solution page
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        // M2: FB followup questions:
        // assume both arrays are sorted, do it with time O(m + n) space O(1)
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        
        int p1 = 0;
        int p2 = 0;
        List<Integer> result = new ArrayList<>();
        while (p1 < nums1.length && p2 < nums2.length) {
            int num1 = nums1[p1];
            int num2 = nums2[p2];
            if (num1 == num2) {
                result.add(num1);
                while (p1 < nums1.length && nums1[p1]==num1) {p1++;}
                while (p2 < nums2.length && nums2[p2]==num2) {p2++;}
            } else if (num1 < num2) {
                while (p1 < nums1.length && nums1[p1]==num1) {p1++;}
            } else {
                while (p2 < nums2.length && nums2[p2]==num2) {p2++;}
            }
        }
        
        int[] res = new int[result.size()];
        for(int i = 0; i < result.size(); i++) {
            res[i] = result.get(i);
        }
    
        return res;
        
    }
}


// can also use binary search, but time O(nogn)