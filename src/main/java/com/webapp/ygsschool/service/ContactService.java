package com.webapp.ygsschool.service;

import com.webapp.ygsschool.constants.FormConstants;
import com.webapp.ygsschool.model.Contact;
import com.webapp.ygsschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;
//    Slf4j annotation helps with logging functionality it automatically creates an object. so we don't have tp
//    private static Logger log= LoggerFactory.getLogger(ContactService.class);

    /*
    Save contact details into DB
    @param contact
    return boolean
     */


    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = false;
        contact.setStatus(FormConstants.OPEN);
        contact.setCreatedBy(FormConstants.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());
        Contact savedContact = contactRepository.save(contact);
        if( savedContact!= null && savedContact.getContactId()>0)
            isSaved = true;
        return isSaved;
    }

    public List<Contact> findMsgsWithOpenStaus() {
        List<Contact> contactMsgs = contactRepository.findByStatus(FormConstants.OPEN);
        return contactMsgs;
    }

    public boolean updateMsgStatus(int id, String updatedBy) {
        boolean isUpdated = false;

        //Updating the status to close if the contact_id is present
        Optional<Contact> contact = contactRepository.findById(id);
        contact.ifPresent(contact1 -> {
            contact1.setStatus(FormConstants.CLOSE);
            contact1.setUpdatedBy(updatedBy);
            contact1.setUpdatedAt(LocalDateTime.now());
        });

        //After updating the contact status details. Saving/updating the same contact object again by get method
        Contact updatedContact = contactRepository.save(contact.get());
        if(updatedContact != null && updatedContact.getUpdatedBy()!=null)
            isUpdated=true;
        return isUpdated;
    }
}