package taxipark
/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    this.allDrivers.filter{
            myDriverList -> this.trips.none{
            myTripList -> myTripList.driver == myDriverList
            }
    }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    this.allPassengers.filter{
            myPassengerList -> this.trips.filter{
            myTripList -> myPassengerList in myTripList.passengers
            }.count()>=minTrips
    }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    this.allPassengers.filter {
            myPassengerList -> this.trips.count {
                myTripsList -> myTripsList.driver == driver && myPassengerList in myTripsList.passengers
            } > 1
    }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    this.allPassengers.filter {
            myPassengerList ->
        this.trips.filter { it -> myPassengerList in it.passengers && it.discount != null }.count() >
            this.trips.filter { it -> myPassengerList in it.passengers && it.discount == null }.count()
    }.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    val maxDuration =
        this.trips.map {
                myTripList -> (myTripList.duration)
        }.groupBy { it / 10
        }.maxByOrNull { (_, duration) -> duration.size
        }?.key

    return maxDuration?.let {
        val start = maxDuration.times(10)
        val end = start.plus(9)
        IntRange(start, end)
    }
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if(this.trips.isEmpty()) {
        return false
    } else {
        val totalTripsCost = this.trips.map { it.cost }.sum()
        val mapCostByDriverSorted =  trips
            .groupBy { it.driver }
            .mapValues { (_, trips) -> trips.sumByDouble { it.cost }}
            .toList()
            .sortedByDescending { (_, value) -> value}.toMap()

        var currentSum = 0.0
        var numberOfDrivers = 0
        for (value in mapCostByDriverSorted.values){
            numberOfDrivers++
            currentSum += value
            if (currentSum >= (totalTripsCost * 0.8)) break
        }

        return numberOfDrivers <= (allDrivers.size * 0.2)
    }
}