package edu.gatech.instacareplus.data.model

class Message {
    var text: String? = null
    var memberData: MemberData? = null
    var belongsToCurrentUser:Boolean? = false
}

class MemberData  {
    var name: String? = null
    var userId: Int? = null
    var color: String? = null
}
