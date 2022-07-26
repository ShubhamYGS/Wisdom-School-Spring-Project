package com.webapp.ygsschool.service;

import com.webapp.ygsschool.constants.FormConstants;
import com.webapp.ygsschool.model.Contact;
import com.webapp.ygsschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    // Save message details from contact form
    public boolean saveMessageDetails(Contact contact) {
        boolean isSaved = false;
        contact.setStatus(FormConstants.OPEN);
        Contact savedContact = contactRepository.save(contact);
        if (savedContact != null && savedContact.getContactId() > 0)
            isSaved = true;
        return isSaved;
    }

    // Showing messages with open status
    public Page<Contact> findMsgsWithOpenStatus(int pageNum, String sortField, String sortDir) {
        // Pagination related stuff
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        Page<Contact> msgPage = contactRepository.findByStatus(
                FormConstants.OPEN, pageable);
        return msgPage;
    }

    // Updating message status to close
    public boolean updateMsgStatus(int id, String updatedBy) {
        boolean isUpdated = false;

        //Updating the status to close if the contact_id is present
//        Optional<Contact> contact = contactRepository.findById(id);
//        contact.ifPresent(contact1 -> {
//            contact1.setStatus(FormConstants.CLOSE);
//        });

        //After updating the contact status details. Saving/updating the same contact object again by get method
        int updatedContactRow = contactRepository.updateStatusById(FormConstants.CLOSE, LocalDateTime.now(), updatedBy, id);
        if (updatedContactRow > 0)
            isUpdated = true;
        return isUpdated;
    }
}