package changemanagement

import changemanagement.ChangeRequest
import changemanagement.Comments
import changemanagement.Contacts

class BootStrap {

    def init = { servletContext ->
        ChangeRequest req1= new ChangeRequest(category:'Security',type:'Change Request',item:'password change', title: 'Change password', description: 'Change password',
                impact:'Low', urgency:'Low', risk:'Low', state:'Open', status:'Submitted', createdDate: new Date().format('yyyy/MM/dd'), phase:'Submit')
        saveObject(req1)

        //Add contacts to the change request
        Contacts contact = new Contacts(contactName:'name',phone:'9898970987', email:'test@gmail.com',request:req1)
        saveObject(contact)

        //Add comments to the change request
        Comments comment = new Comments(addedBy:'firstName',commentAddedDate:new Date().format('yyyy/MM/dd'),comments:'Test new Comment', request:req1)
        saveObject(comment)

        ChangeRequest req2=new ChangeRequest(category:'Security',type:'Change Request',item:'Softwared Upgrade', title: 'Upgrade Software', description: 'To address vulnerability upgrade software',
                impact:'Low', urgency:'Low', risk:'Low', state:'Closed', status:'Completed', createdDate: new Date().format('yyyy/MM/dd'), phase:'Close')
        saveObject(req2)

        //Add contacts to the change request
        Contacts contact2 = new Contacts(contactName:'Kname',phone:'9898970987', email:'test@gmail.com',request:req2)
        saveObject(contact2)

        //Add comments to the change request
        Comments comment2 = new Comments(addedBy:'Name',commentAddedDate:new Date().format('yyyy/MM/dd'),comments:'Test new Comment', request:req2)
        saveObject(comment2)

    }
    def destroy = {
    }

    def saveObject(object) {
        if (!object.save(flush:true)) {
            object.errors.allErrors.each { println it }
        }
    }
}
