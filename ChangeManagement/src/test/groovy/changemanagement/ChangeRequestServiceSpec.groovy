package changemanagement

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ChangeRequestService)
@Mock([ChangeRequest, Comments, Contacts, Milestones])
class ChangeRequestServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test for updateChangeRequest"() {
        when:
        ChangeRequest req = new ChangeRequest(category:'Change',type:'service request',item:'password change', title: 'Change password', description: 'Change password',
                impact:'Low', urgency:'Low', risk:'Low', state:'Open', status:'Submitted', createdDate: new Date().format('yyyy/MM/dd'), phase:'Implement')
        req.save(flush:true)

        ChangeRequestProcessor reqProc = new ChangeRequestProcessor()
        reqProc.changeRequestID = req.getId()
        reqProc.requestAction = "Accept"
        reqProc.processedBy = "Mary"
        reqProc.comments = "This is for test"
        service.updateChangeRequest(reqProc)
        then:
        req.status == 'In Review'
    }
}
