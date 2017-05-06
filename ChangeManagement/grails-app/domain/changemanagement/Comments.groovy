package changemanagement

/*
This class captures all the comments for a request. A comments always belongs to a request.
All the attributes defined are required.
 */
class Comments {
    String addedBy
    String comments
    String commentAddedDate

    static belongsTo = [request:ChangeRequest]

    static constraints = {
        addedBy nullable: true
        comments nullable: false
        commentAddedDate nullable: true
    }
}
