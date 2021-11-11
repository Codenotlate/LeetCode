// phase 3 solution
// this is a simialr question to 300 LIS, just 2dim
//https://leetcode.com/problems/russian-doll-envelopes/discuss/82763/Java-NLogN-Solution-with-Explanation
class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        // sort increasing in e[0] and decreasing in e[1]
        Arrays.sort(envelopes, new Comparator<int[]> () {
        	public int compare(int[] e1, int[] e2) {
        		if (e1[0] == e2[0]) {
        			return e2[1] - e1[1];
        		} else {
        			return e1[0] - e2[0];
        		}
        	}
        });

        // then do LIS for e[2] array
        int[] secDim = new int[envelopes.length];
        for (int i = 0; i < envelopes.length; i++) {
        	secDim[i] = envelopes[i][1];
        }

        return lengthOfLIS(secDim);
    }


    private int lengthOfLIS(int[] nums) {
    	List<Integer> topList = new ArrayList<>();
    	if (nums.length == 0) {return 0;}
    	
    	for (int n: nums) {
    		int pos = findFirstLte(topList, n);
    		if (pos == -1) {
    			topList.add(n);
    		} else {
    			topList.set(pos, n);
    		}
    	}

    	return topList.size();

    }

    private int findFirstLte(List<Integer> list, int k) {
    	if (list.size() == 0) {return -1;}
    	int start = 0;
    	int end = list.size() - 1;

    	while (start < end) {
    		int mid = start + (end - start) / 2;
    		if (list.get(mid) < k) {
    			start = mid + 1;
    		} else {
    			end = mid;
    		}
    	}

    	return list.get(start) >= k ? start : -1;
    }
}




// Phase 3 self
class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        // The key is to sort the first ele in ascending order - which makes sure no need to worry about the 1st dim afterwards
        // and then sort the second ele in descending order - which makes sure when we do LIS on 2nd dim regardless of whether they have same 1st dim, we won't increase the length twice for the same 1st dim ones since it's in desc order.
        // time O( nlogn) space O(n)
        // sort first and define the compare
        Arrays.sort(envelopes, new Comparator<int[]>() {
            public int compare(int[] e1, int[] e2) {
                if (e1[0] == e2[0]) {
                    return e2[1] - e1[1];
                } else {
                    return e1[0] - e2[0];
                }
            }
        });
        
        // then do LIS to 2nd dim
        int[] arr = new int[envelopes.length];
        for (int i = 0; i < envelopes.length; i++) {
            arr[i] = envelopes[i][1];
        }
        return LIS(arr);
    }
    
    
    private int LIS(int[] arr) {
        if (arr.length == 0) {return 0;}
        List<Integer> seq = new ArrayList<>();
        seq.add(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            int idx = findFirstLte(seq, arr[i]);
            if (idx == -1) {
                seq.add(arr[i]);
            } else {
                seq.set(idx, arr[i]);
            }
        }
        return seq.size();
    }
    
    private int findFirstLte(List<Integer> seq, int t) {
        int start = 0;
        int end = seq.size() -1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            int num = seq.get(mid);
            if (num < t) {start = mid + 1;}
            else {end = mid;}
        }
        return seq.get(start) >= t ? start : -1;
    }
}














