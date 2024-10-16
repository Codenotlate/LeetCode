// Phase3  self two heap way

class MedianFinder {
	PriorityQueue<Integer> p1, p2;
    /** initialize your data structure here. */
    public MedianFinder() {

        //p1 = new PriorityQueue<>((a, b) -> (b - a)); // this may have overflow
        // better use below
        p1 = new PriorityQueue<>(Collections.reverseOrder());
        p2 = new PriorityQueue<>();
    }
    
    public void addNum(int num) {
        if (p2.size() == 0 || num >= p2.peek()) {
        	if (p2.size() > p1.size()) {
        		p1.add(p2.poll());
        	}
        	p2.add(num);
        } else if (p1.size() == 0 || num <= p1.peek()) {
        	if (p1.size() == p2.size()) {
        		p2.add(p1.poll());
        	}
        	p1.add(num);
        } else {
        	if (p1.size() == p2.size()) {
        		p2.add(num);
        	} else {
        		p1.add(num);
        	}
        }
    }
    
    public double findMedian() {
        if (p1.size() == p2.size()) {return (double) (p1.peek() + p2.peek()) / 2;}
        return (double) p2.peek();
    }
}

// a more elegant way of implementing
// https://leetcode.com/problems/find-median-from-data-stream/discuss/74047/JavaPython-two-heap-solution-O(log-n)-add-O(1)-find

/** BST solution
https://leetcode.com/problems/find-median-from-data-stream/discuss/74119/18ms-beats-100-Java-Solution-with-BST
note without sefl-balance, the BST add could be O(n) worst time
 */


// Phase3 M4 in problem solution, using TreeSet in Java
/* TreeSet uses a red-black tree that is a self-balancing BST.
It stores unique elements (thus we need to build a node class to have <value, index>, so that same values can be added to the TreeSet)
It doesn't preserve the insertion order of the elements
It sorts the elements in ascending order
It's not thread-safe
*/


// https://leetcode.com/problems/find-median-from-data-stream/discuss/137721/Java-impl-of-Approach-4-%22Multiset-and-Two-Pointers%22-from-Solution

// self implement next time








/*
Follow-ups
https://leetcode.com/problems/find-median-from-data-stream/discuss/275207/Solutions-to-follow-ups
1. If all integer numbers from the stream are between 0 and 100, how would you optimize it?
We can maintain an integer array of length 100 to store the count of each number along with a total count. Then, we can iterate over the array to find the middle value to get our median.
Time and space complexity would be O(100) = O(1).

2. If 99% of all integer numbers from the stream are between 0 and 100, how would you optimize it?
in Java we can use a TreeMap to store numbers < 0 and > 100, with the value being their count. The iteration order would be:
check TreeMap entries < 0
check array of size 100
check TreeMap of entries > 100
And you'd still need to keep a global counter to count amount of nums added in total.

*/



// 2024.10.9
/* Other ways mentioned:
M1: like insertion sort: can use binary search to find the position of the added num and move all the rest accordingly. time O(logn) + O(n) = O(n)
*/
class MedianFinder {
    PriorityQueue<Integer> left, right;

    public MedianFinder() {
        left = new PriorityQueue<Integer>((a,b) -> (b-a));
        right = new PriorityQueue<Integer>();
    }
    
    public void addNum(int num) {
        if (left.size() == 0 || left.peek() >= num) {
            left.add(num);
        } else {
            right.add(num);
        }
        // rebalance
        if (right.size() > left.size()) {
            left.add(right.poll());
        } else if (left.size() > right.size()+ 1) {
            right.add(left.poll());
        }
    }
    
    public double findMedian() {
        if (left.size() == right.size()) {
            return (left.peek() + right.peek())*1.0/2;
        }
        return left.peek();
    }
}


/*
Further Thoughts
There are so many ways around this problem, that frankly, it is scary. Here are a few more that I came across:

Buckets! If the numbers in the stream are statistically distributed, then it is easier to keep track of buckets where the median would land, than the entire array. Once you know the correct bucket, simply sort it find the median. If the bucket size is significantly smaller than the size of input processed, this results in huge time saving. @mitbbs8080 has an interesting implementation here.

Reservoir Sampling. Following along the lines of using buckets: if the stream is statistically distributed, you can rely on Reservoir Sampling. Basically, if you could maintain just one good bucket (or reservoir) which could hold a representative sample of the entire stream, you could estimate the median of the entire stream from just this one bucket. This means good time and memory performance. Reservoir Sampling lets you do just that. Determining a "good" size for your reservoir? Now, that's a whole other challenge. A good explanation for this can be found in this StackOverflow answer.

Segment Trees are a great data structure if you need to do a lot of insertions or a lot of read queries over a limited range of input values. They allow us to do all such operations fast and in roughly the same amount of time, always. The only problem is that they are far from trivial to implement. Take a look at my introductory article on Segment Trees if you are interested.

Order Statistic Trees are data structures which seem to be tailor-made for this problem. They have all the nice features of a BST, but also let you find the k th order element stored in the tree. They are a pain to implement and no standard interview would require you to code these up. But they are fun to use if they are already implemented in the language of your choice.

*/


