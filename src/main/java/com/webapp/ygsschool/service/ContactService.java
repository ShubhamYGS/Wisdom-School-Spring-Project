package com.webapp.ygsschool.service;

import com.webapp.ygsschool.controller.ContactController;
import com.webapp.ygsschool.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContactService {

//    Slf4j annotation helps with logging functionality it automatically creates an object. so we don't have tp
//    private static Logger log= LoggerFactory.getLogger(ContactService.class);

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