package changemanagement

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ChangeRequestController {
    ChangeRequestService changeRequestService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        //respond ChangeRequest.list(params), model:[changeRequestCount: ChangeRequest.count()]
        showAllRequests()
    }

    def showAllRequests(){
        ArrayList<ChangeRequest> changeReqList = new ArrayList<ChangeRequest>()
        changeReqList = ChangeRequest.findAllByState("Open")
        render view:"/changeRequest/showlist", model:[changes:changeReqList]
    }

    def showAllClosedRequests(){
        ArrayList<ChangeRequest> changeReqList = new ArrayList<ChangeRequest>()
        changeReqList = ChangeRequest.findAllByState("Closed")
        render view:"/changeRequest/showClosedlist", model:[changes:changeReqList]
    }

    def show(ChangeRequest changeRequest) {
        respond changeRequest

    }

    def create() {
        respond new ChangeRequest(params)
        //createCompleteChangeRequest()
    }

    def processRequest(ChangeRequest request){
        System.out.println("The Change request id is " + request.getId())
        def pr = new ChangeRequestProcessor()
        ChangeRequest requestDetails = ChangeRequest.findById(request.getId())
        System.out.println("The Change request state is " + requestDetails.getState())
        System.out.println("The Change request status is " + requestDetails.getStatus())
        System.out.println("The Change request Id is " + requestDetails.getId())
        pr.state = requestDetails.getState()
        pr.status = requestDetails.getStatus()
        pr.changeRequestID = requestDetails.getId()
        if (requestDetails.getStatus().equalsIgnoreCase("Scheduled") || requestDetails.getStatus().equalsIgnoreCase("In Progress")){
            System.out.println("The Schedule start date  is " + requestDetails.getScheduledStartDate())
            pr.scheduledStDate = requestDetails.getScheduledStartDate()
            pr.scheduledEndDate = requestDetails.getScheduledEndDate()
        }
        render view:'/changeRequest/processChangeRequest', model:[pr:pr,changeRequest:requestDetails]
    }

    def createCompleteChangeRequest() {
        def cmd = new CreateCompleteRequest(
                requestInstance:new ChangeRequest(),
                commentsInstance: new Comments(),
                contactsInstance: new Contacts())
        render view:'/changeRequest/createCompleteRequest', model:[cmd:cmd]
        //respond cmd, model:[cmd:cmd]
    }

    def fulfillRequest(ChangeRequestProcessor pr){
        System.out.println("The  action selected on the form is " + pr.requestAction)
        System.out.println("The comments added on the form are " + pr.comments)
        System.out.println("Comments were added by " + pr.processedBy)
        System.out.println("The Change Request ID is " + pr.changeRequestID)

        changeRequestService.updateChangeRequest(pr)
        showAllRequests()

     }

    @Transactional
    def saveCompleteRequest(CreateCompleteRequest cmd) {

        if (cmd.hasErrors()) {
            System.out.println("The error is " + cmd.errors)
            transactionStatus.setRollbackOnly()
            render view: '/changeRequest/createCompleteRequest', model: [cmd: cmd]
            return
        }

        //ChangeRequestService.saveEverythingAtOnce(cmd)
        ChangeRequest req1 = cmd.requestInstance.save(flush:true)

        cmd.contactsInstance.setRequest(req1)
        cmd.contactsInstance.setContactType("Requestor")
        cmd.contactsInstance.save(flush: true)

        cmd.commentsInstance.setRequest(req1)
        cmd.commentsInstance.setAddedBy(cmd.contactsInstance.getContactName())
        cmd.commentsInstance.setCommentAddedDate(cmd.requestInstance.getCreatedDate())
        cmd.commentsInstance.save(flush: true)

        Milestones milestone = new Milestones()
        milestone.setMilestoneName("Request Received")
        milestone.setPhase("Submit")
        milestone.setMilestoneDate(new Date().format("yyyy/MM/dd"))
        milestone.setRequest(req1)
        milestone.save(flush: true)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'everything.created.message', args: [cmd.requestInstance.item])
                redirect cmd.requestInstance
            }
            '*' { respond cmd.requestInstance, [status: CREATED] }
        }
    }


        @Transactional
    def save(ChangeRequest changeRequest) {
        if (changeRequest == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (changeRequest.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond changeRequest.errors, view:'create'
            return
        }

        changeRequest.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'changeRequest.label', default: 'ChangeRequest'), changeRequest.id])
                redirect changeRequest
            }
            '*' { respond changeRequest, [status: CREATED] }
        }
    }

    def edit(ChangeRequest changeRequest) {
        respond changeRequest
    }

    @Transactional
    def update(ChangeRequest changeRequest) {
        if (changeRequest == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (changeRequest.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond changeRequest.errors, view:'edit'
            return
        }



        changeRequest.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'changeRequest.label', default: 'ChangeRequest'), changeRequest.id])
                redirect changeRequest
            }
            '*'{ respond changeRequest, [status: OK] }
        }
    }

    @Transactional
    def delete(ChangeRequest changeRequest) {

        if (changeRequest == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }



        changeRequest.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'changeRequest.label', default: 'ChangeRequest'), changeRequest.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'changeRequest.label', default: 'ChangeRequest'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
