/*Thought
having a map of map for stationTime: <startstation, <endstation, pair(totalTime, count)>>
having another map for customer: <id, pair(startstation, startTime)>
When we do checkin, we add that info to customer map.
When we do checkout, we find that id in customer map, calculate duration as endTime - startTime, then use startstation, and endstation to find update the totalTime and count correspondingly in the stationTime map. (From discussion, to avoid overflow, better store the current average and count instead of totalTime)
When getAverageTime, we return totalTime/count, or just curmean;
All O(1) time for all 3 operations.

This question in interview is more about the system design or OOD part instead of the actual implementation.
*/
class UndergroundSystem {
    Map<String, Map<String, Pair<Double, Integer>>> stationTime;
    Map<Integer, Pair<String, Integer>> customerTime;

    public UndergroundSystem() {
        stationTime = new HashMap<>();
        customerTime = new HashMap<>();
    }
    
    public void checkIn(int id, String stationName, int t) {
        customerTime.put(id, new Pair(stationName, t));
    }
    
    public void checkOut(int id, String stationName, int t) {
        Pair<String, Integer> checkinData = customerTime.get(id);
        int duration = t - checkinData.getValue();
        String startStation = checkinData.getKey();
        if (!stationTime.containsKey(startStation) || !stationTime.get(startStation).containsKey(stationName)) {
            stationTime.putIfAbsent(startStation, new HashMap<>());
            stationTime.get(startStation).put(stationName, new Pair(duration * 1.0, 1));
        } else {
            Pair<Double, Integer> curTimeInfo = stationTime.get(startStation).get(stationName);
            int count = curTimeInfo.getValue();
            Double newAveTime = (curTimeInfo.getKey() * count + duration) * 1.0 / (count + 1);
            stationTime.get(startStation).put(stationName, new Pair(newAveTime, count+1));
        }
        // improvement from solution, since we no longer need the checkin data, we can delete them
        customerTime.remove(id);
    }
    
    public double getAverageTime(String startStation, String endStation) {
        return stationTime.get(startStation).get(endStation).getKey();
    }
}
/* above way is simialr to solution, but solution uses "startStation->endStation" as the key of the map. thus we can use one map instead of map of map for stationTime.

(from solution)Number of HashMaps to use

In the discussion above, we broke the data into 4 separate HashMaps. While this would be an okay way to implement it, we often group similar data together. This would give us the following two HashMaps:

checkInData = a new HashMap (id -> startStation, checkInTime)
journeyData = a new HashMap (startStation, endStation -> total, count)

In a realistic system, there's likely to be more operations than just getAverageTime(...), and the data will likely be put into and pulled from a database. Database design is another massive area of system design where many factors need to be taken into account to come up with the design that best achieves what is needed for the particular queries that will need to be answered.

Here, we chose to use the Pair class for the Java implementation, but whether or not this is good design is probably down to personal preference. It does have the benefit of being less code to write (and less whiteboard space in an interview!). However, it is a bit contextually strange that one member of the Pair is known as "the key" and the other as "the value".

Where we need to represent a key with both a start station and an end station, the code below appends the strings with a -> between them to make a single string. This is safe for the constraints we're given, as station names can only be letters or numbers. In a real world system, this would need to be thought through carefully. In Python, we can simply use a tuple as the key (without the code becoming needlessly verbose).

Other options would include using nested HashMaps or defining your own objects.


*/




/* From solution
If you were asked this question in a real interview, you should expect to discuss real-world issues related to it. For example, it would not be realistic to store the data in volatile computer memory. In practice, computers fail (e.g. lose power) so we need to store the data in a permanent medium, such as a database.

Additionally, we might need to consider scalability. In a large city, such as Tokyo, the metro system gets 7 million passenger trips per day! With a little math, we can quickly estimate that during peak travel time, there must be thousands of check-ins and check-outs every second. This is a lot of data that one computer would need to receive through its network connection! To make this work, we'd probably be using more than one computer. This introduces concurrency issues that would need to be addressed.

It's also likely that this module would have to fit in with other code in the passenger-tracking system. It's not likely that a little average time tracker will get exclusive use of all resources.

For your actual implementation, you'll probably be expected to use HashMaps. This is common in system design interview questions: you're given a complex real world problem to explore and then are asked to implement a small piece of it.



some discussion part from solution:

Saving the total time vs the average time

One design decision we need to make is whether to store the total-time or the average-time for each possible route. The benefit of storing the average-time is that the system will be able to store a lot more data before being affected by overflow (remember, the total-count is always eventually going to be affected). The downside of storing average-time though is that we need to update it with division every time a new journey is made on that route. This leads to compounded floating-point error.

Given that overflow isn't an issue with the problem constraints we're given here, it's safest to store the total-time so that we can avoid the floating-point error. In a real world system, you would use a Decimal library that supports arbitrary precision and doesn't suffer from floating-point error.






*/