package com.example.JobApp;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerService {

    private UserRepository urepo;
    private JobRepository jrepo;
    private AppRepository arepo;
    private ModelMapper mapper;

    public String getLoggedInUserEmail(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

   /* public List<JobResponse> getAllJobs(){

        List<Job> jobs = jrepo.findAll();

        return jobs.stream()
                .map(job -> mapper.map(job, JobResponse.class))
                .collect(Collectors.toList());
    }*/

    public UserResponse getCustomer(){

        String email = getLoggedInUserEmail();

        User customer = urepo.findByEmail(email).orElseThrow(()-> new UserException("Customer not Found"));

        return mapper.map(customer, UserResponse.class);
    }

    public Page<JobResponse> getAllJobs(int page, int size, String sortby){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortby));

        Page<Job> p = jrepo.findAll(pageable);
        List<JobResponse> res = p.getContent().stream()
                .map(job-> mapper.map(job, JobResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(res, pageable, p.getTotalElements());
    }
/*
    public List<AppResponse> getAppliedJobs(){

        String email = getLoggedInUserEmail();

            User user = urepo.findByEmail(email).orElseThrow(()-> new RuntimeException("user not found"));

        List<Application> applications = arepo.findByUser(user);

        return applications.stream()
                .map(app-> mapper.map(app, AppResponse.class))
                .collect(Collectors.toList());

    }*/

    public Page<AppResponse> getAppliedJobs(int page, int size, String sortby){
        Pageable pageable = PageRequest.of(page,size,Sort.by(sortby));

        String email = getLoggedInUserEmail();

        User user = urepo.findByEmail(email).orElseThrow(()-> new RuntimeException("user not found"));

       Page<Application> applications = arepo.findByUser(user, pageable);

       List<AppResponse> res = applications.getContent().stream()
               .map(a -> mapper.map(a, AppResponse.class))
               .collect(Collectors.toList());

       return new PageImpl<>(res, pageable, applications.getTotalElements());

    }

    public AppResponse applyForJob(int jobid){

        String email = getLoggedInUserEmail();

        User user = urepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));

        Job job = jrepo.findById(jobid).orElseThrow(()-> new RuntimeException("job not found"));

        if(arepo.existsByUserAndJob(user, job)){
               throw new RuntimeException("you have already applied for this job");
        }

        Application app = Application.builder().user(user)
                .status(Status.APPLIED)
                .job(job)
                .appliedDate(LocalDate.now())
                .build();

        if(job.getApplications() == null){
            job.setApplications(new ArrayList<>());
        }

        job.getApplications().add(app);

        jrepo.save(job);

        arepo.save(app);

        return mapper.map(app, AppResponse.class);
    }

    public String deleteApplication(int appId){

        String email = getLoggedInUserEmail();

        User user = urepo.findByEmail(email).orElseThrow(()-> new UserException("User not found"));

        Application app = arepo.findById(appId).orElseThrow(()-> new AppException("Application not found"));

        if(!app.getUser().getEmail().equals(email)){
            throw new UserException("User are not authorised to delete this application");
        }

        user.getApplications().removeIf(a-> a.getId()==appId);

        urepo.save(user);

        arepo.delete(app);

        return "Application deleted Successfully";

    }

    public Page<JobResponse> searchJobs(String domain, String location, Integer experience, String employerName, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Job> jobPage = jrepo.searchJobs(domain, location, experience, employerName, pageable);

        List<JobResponse> responseList = jobPage.getContent().stream()
                .map(job -> mapper.map(job, JobResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(responseList, pageable, jobPage.getTotalElements());
    }
 }
