package com.webapp.ygsschool.service;

import com.webapp.ygsschool.controller.ContactController;
import com.webapp.ygsschool.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private static Logger log= LoggerFactory.getLogger(ContactService.class);

    /*
    Save contact details into DB
    @param contact
    return boolean
     */
    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = true;
        log.info(contact.toString());
        return isSaved;
    }

}
