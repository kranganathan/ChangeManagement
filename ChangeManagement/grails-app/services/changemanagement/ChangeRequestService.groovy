package changemanagement

import grails.transaction.Transactional

@Transactional
class ChangeRequestService {

    def serviceMethod() {

    }

    /*
    This method updates the change request and other related data
     */
    def updateChangeRequest(ChangeRequestProcessor pr){
        //Fetch the Change Request by ID . Then upate the state, statys, phase based on the requestAction
        System.out.println("Going to update the Change Request")
        ChangeRequest currentRequest = ChangeRequest.findById(pr.changeRequestID);
        String reqAction = pr.requestAction
        if (reqAction.equalsIgnoreCase('Accept')){
            currentRequest.setStatus("In Review")
            currentRequest.setPhase('Assess')
        }else if (reqAction.equalsIgnoreCase("Schedule")){
            currentRequest.setStatus("Scheduled")
            currentRequest.setPhase("Implement")
            currentRequest.setScheduledEndDate(pr.scheduledEndDate)
            currentRequest.setScheduledStartDate(pr.scheduledStDate)
        }else if (reqAction.equalsIgnoreCase("Implementation Start")){
            currentRequest.setStatus("In Progress")
            currentRequest.setScheduledEndDate(pr.scheduledEndDate)
            currentRequest.setScheduledStartDate(pr.scheduledStDate)
        }else if (reqAction.equalsIgnoreCase("Implementation Completed")){
            currentRequest.setStatus("Completed")
            currentRequest.setPhase("Close")
            currentRequest.setState("Closed")
        }else if (reqAction.equalsIgnoreCase("Cancel")){
            currentRequest.setStatus("Cancelled")
            currentRequest.setPhase("Close")
            currentRequest.setState("Closed")
        }else if (reqAction.equalsIgnoreCase("Reject")){
            currentRequest.setStatus("Rejected")
            currentRequest.setPhase("Close")
            currentRequest.setState("Closed")
        }


        currentRequest.save(flush:true)
        System.out.println("Updated the Change Request")
        //Now add the comment
        System.out.println("Going to update the comments")
        Comments comment = new Comments()
        comment.addedBy = pr.processedBy
        comment.comments = pr.comments
        comment.commentAddedDate = new Date().format("yyyy/MM/dd")
        comment.request = currentRequest
        comment.save(flsh:true)
        System.out.println("Updated the comments")

        if (currentRequest.phase.equalsIgnoreCase("Assess")){
            Milestones ml = new Milestones(phase:'Assess', milestoneName:'Request Acknowledged', milestoneDate: new Date().format('yyyy/MM/dd'), request:currentRequest)
            ml.save(flush:true)
        }else if (currentRequest.phase.equalsIgnoreCase("Close")) {
            if (currentRequest.status.equalsIgnoreCase("Completed")) {
                Milestones ml = new Milestones(phase: 'Implement', milestoneName: 'Request Implemented', milestoneDate: new Date().format('yyyy/MM/dd'), request: currentRequest)
                ml.save(flush: true)
            }
            Milestones m2 = new Milestones(phase: 'Close', milestoneName: 'Request Completed', milestoneDate: new Date().format('yyyy/MM/dd'), request: currentRequest)
            m2.save(flush: true)
        }
    }

}
