package jkey20.dutch.model

data class Properties(
    var totalDistance: String,
    var totalTime: String,
    var totalFare :String,
    var taxiFare : String,
    var index : String,
    var pointIndex : String,
    var name : String,
    var description : String,
    var nextRoadName : String,
    var turnType: String,
    var pointType: String
)