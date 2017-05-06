package changemanagement

import grails.test.mixin.*
import spock.lang.*

@TestFor(ChangeRequestController)
@Mock([ChangeRequest, Comments, Contacts, Milestones])
class ChangeRequestControllerSpec extends Specification {

    ChangeRequestService changeRequestService

    def setup() {
        changeRequestService = new ChangeRequestService()
        controller.changeRequestService = changeRequestService
    }

    def populateValidParams(params) {
        assert params != null
        params << [id:1,category:'Change',type:'service request',item:'password change', title: 'Change password', description: 'Change password',
        impact:'Low', urgency:'Low', risk:'Low', state:'Open', status:'Scheduled', createdDate: new Date().format('yyyy/MM/dd'), phase:'Implement']
    }

    def populateAllValidParams(params) {
        assert params != null
        params << [requestInstance:[category:'Change',type:'service request',item:'password change', title: 'Change password', description: 'Change password',
                                    impact:'Low', urgency:'Low', risk:'Low', state:'Open', status:'Scheduled', createdDate: new Date().format('yyyy/MM/dd'), phase:'Implement'],
                   commentsInstance:[addedBy:'firstName',commentAddedDate:new Date().format('yyyy/MM/dd'),comments:'Test new Comment'],
                   contactsInstance:[contactName:"name",phone:'9898970987', email:"test@gmail.com"]]

    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
        model.changes!= null
        model.changes instanceof ArrayList
    }

    void "Test the showAll Requests"() {

        when:"The showAllRequests is executed"
        controller.showAllRequests()

        then:"The model is correct"
        model.changes!= null
        model.changes instanceof ArrayList
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.changeRequest!= null
    }

    void "Test the createCompleteChangeRequest action returns the correct model"() {
        when:"The create action is executed"
        controller.createCompleteChangeRequest()

        then:"The model is correctly created"
        model.cmd!= null
        model.cmd instanceof CreateCompleteRequest

    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def changeRequest = new ChangeRequest()
            changeRequest.validate()
            controller.save(changeRequest)

        then:"The create view is rendered again with the correct model"
            model.changeRequest!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            changeRequest = new ChangeRequest(params)

            controller.save(changeRequest)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/changeRequest/show/1'
            controller.flash.message != null
            ChangeRequest.count() == 1
    }

    void "Test for the processRequest method"() {

        when:"The method is provided with valid instance"
        response.reset()
        populateValidParams(params)
        def changeRequest = new ChangeRequest()
        changeRequest = new ChangeRequest(params)
        ChangeRequest req1 = changeRequest.save(flush:true)
        controller.processRequest(req1)

        then:"A redirect is issued to the processChange action"
        model.pr != null
     }




        void "Test the save complete request persists an instance"() {
        when:"The save action is executed with a valid instance"
        CreateCompleteRequest cmd = new CreateCompleteRequest()
        cmd.requestInstance = new ChangeRequest(category:'Change',type:'service request',item:'password change', title: 'Change password', description: 'Change password',
                impact:'Low', urgency:'Low', risk:'Low', state:'Open', status:'Scheduled', createdDate: new Date().format('yyyy/MM/dd'), phase:'Implement')

        cmd.commentsInstance = new Comments(addedBy:'firstName',commentAddedDate:new Date().format('yyyy/MM/dd'),comments:'Test new Comment')

        cmd.contactsInstance = new Contacts(contactName:'name',phone:'9898970987', email:'test@gmail.com')

        controller.saveCompleteRequest(cmd)

        then:"A request is created with all the components"
        //response.redirectedUrl == 'changeRequest/show/1'
        //controller.flash.message == null
        ChangeRequest.count() == 1
    }



    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def changeRequest = new ChangeRequest(params)
            controller.show(changeRequest)

        then:"A model is populated containing the domain instance"
            model.changeRequest == changeRequest
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def changeRequest = new ChangeRequest(params)
            controller.edit(changeRequest)

        then:"A model is populated containing the domain instance"
            model.changeRequest == changeRequest
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/changeRequest/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def changeRequest = new ChangeRequest()
            changeRequest.validate()
            controller.update(changeRequest)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.changeRequest == changeRequest

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            changeRequest = new ChangeRequest(params).save(flush: true)
            controller.update(changeRequest)

        then:"A redirect is issued to the show action"
            changeRequest != null
            response.redirectedUrl == "/changeRequest/show/$changeRequest.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/changeRequest/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def changeRequest = new ChangeRequest(params).save(flush: true)

        then:"It exists"
            ChangeRequest.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(changeRequest)

        then:"The instance is deleted"
            ChangeRequest.count() == 0
            response.redirectedUrl == '/changeRequest/index'
            flash.message != null
    }
}
