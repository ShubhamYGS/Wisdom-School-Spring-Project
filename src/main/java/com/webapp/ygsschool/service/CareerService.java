package com.webapp.ygsschool.service;

import com.webapp.ygsschool.constants.FormConstants;
import com.webapp.ygsschool.model.Career;
import com.webapp.ygsschool.repository.CareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
public class CareerService {


    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private EmailSenderService emailSenderService;

    // Save job details from career page
    public boolean saveJobDetails(Career career, MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
        boolean isSaved = false;
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        //Upload File
        if (!(fileUploadService.uploadFile(fileName, multipartFile))) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error while uploading Resume/CV file. Try again!");
            return isSaved;
        }

        career.setResume(fileName);
        career.setStatus(FormConstants.OPEN);

        // If user does not put anything for his job title (default title will be FRESHER)
        if (career.getJobTitle().isEmpty())
            career.setJobTitle(FormConstants.JOB_TITLE_FRESHER);

        Career savedJob = careerRepository.save(career);
        if (savedJob != null && savedJob.getJobId() > 0) {
            isSaved = true;
            redirectAttributes.addFlashAttribute("jobMessage", "You have successfully applied for job at Wisdom School. We will get back to you within 24 hrs.");
        }

        return isSaved;
    }

    // Showing jobs by status (OPEN, HIRED, REJECTED)
    public List<Career> showJobsByType(String status) {
        return careerRepository.findByStatus(status);
    }

    // Returning the job profile requested by user
    public Career findJobProfile(int jobId) {
        Optional<Career> career = careerRepository.findById(jobId);
        return career.get();
    }

    // Reject the candidate
    public boolean rejectCandidate(int jobId) {
        boolean isRejected = false;
        Optional<Career> career = careerRepository.findById(jobId);

        // Setting the status as rejected
        career.get().setStatus(FormConstants.REJECTED);
        Career career1 = careerRepository.save(career.get());

        // Sending email
        String subject = "Wisdom School Job Application";
        String body = "Dear " + career.get().getFirstName() + ", \n\nThank you for applying for the position of Professor at Wisdom School.\nWe regret to inform you that we are unable to consider your candidature further. \nWe wish you all the best for your future endeavors. \n\nRegards,\nWisdom School, Sangli";
        emailSenderService.sendEmail(career.get().getEmail(), subject, body);

        if (career1 != null && career1.getJobId() > 0)
            isRejected = true;
        return isRejected;
    }

    // Hire the candidate
    public boolean hireCandidate(int jobId) {
        boolean isHired = false;
        Optional<Career> career = careerRepository.findById(jobId);

        // Setting the status as hired
        career.get().setStatus(FormConstants.HIRED);
        Career career1 = careerRepository.save(career.get());

        // Sending Email
        String subject = "Wisdom School | Interview Invitation";
        String body = "Dear " + career.get().getFirstName() + ", \n\nCongratulations! Your application for the Professor position stood out to us and we would like to invite you for an interview at our Wisdom School. \nYour interview has been scheduled on " + career.get().getDatetime().toLocalDate() + " at 1:30 PM. \n\nRegards,\nWisdom School, Sangli";
        emailSenderService.sendEmail(career.get().getEmail(), subject, body);

        if (career1 != null && career1.getJobId() > 0)
            isHired = true;
        return isHired;
    }
}
