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

    public boolean saveJobDetails(Career career, MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
        boolean isSaved = false;
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        //Upload File
        if (!(fileUploadService.uploadFile(fileName, multipartFile))) {
            redirectAttributes.addFlashAttribute("errorMessage","Error while uploading Resume/CV file. Try again!");
            return false;
        }

        career.setResume(fileName);
        career.setStatus(FormConstants.OPEN);
        if(career.getJobTitle().isEmpty())
            career.setJobTitle(FormConstants.JOB_TITLE_FRESHER);

        Career savedJob = careerRepository.save(career);
        if(savedJob != null && savedJob.getJobId()>0) {
            isSaved = true;
            redirectAttributes.addFlashAttribute("jobMessage","You have successfully applied for job at Wisdom School. We will get back to you within 24 hrs.");
        }

        return isSaved;
    }

    public List<Career> showJobsByType(String status) {
        List<Career> careerList = careerRepository.findByStatus(status);
        return careerList;
    }

    public Career findJobProfile(int jobId) {
        Optional<Career> career = careerRepository.findById(jobId);
        return career.get();
    }
}
