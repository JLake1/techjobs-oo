package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String index(Model model, @PathVariable int id) {

        JobData jobdata = JobData.getInstance();
        Job job = jobdata.findById(id);
        model.addAttribute("job", job);

        // TODO #1 - get the Job with the given ID and pass it into the view

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "new-job";
        }

        String name = jobForm.getName();
        int employerId = jobForm.getEmployerId();
        int locationId = jobForm.getLocationId();
        int typeId = jobForm.getPositionTypeId();
        int coreId = jobForm.getCoreCompetencyId();

        Employer employer =  jobData.getEmployers().findById(employerId);
        Location location = jobData.getLocations().findById(locationId);
        PositionType type = jobData.getPositionTypes().findById(typeId);
        CoreCompetency core = jobData.getCoreCompetencies().findById(coreId);

        Job newJob = new Job(name, employer, location, type, core);

        jobData.add(newJob);

        model.addAttribute("job", newJob);

        int jobId = newJob.getId();

        return "redirect:" + jobId;

    }

}
