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
        int result = contactRepository.saveContactMsg(contact);
        if(result > 0)
            isSaved = true;
        return isSaved;
    }

    public List<Contact> findMsgsWithOpenStaus() {
        List<Contact> contactMsgs = contactRepository.findMsgWithStatus(FormConstants.OPEN);
        return contactMsgs;
    }

    public boolean updateMsgStatus(int id, String updatedBy) {
        boolean isUpdated = false;
        int result = contactRepository.updateMsgStatus(id,updatedBy,FormConstants.CLOSE);
        if(result > 0)
            isUpdated = true;
        return isUpdated;
    }
}