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
public class EmployerService {

    private UserRepository urepo;

    private JobRepository jrepo;

    private AppRepository arepo;

    private ModelMapper mapper;

    public String getLoggedInEmployerEmail(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    public Page<UserResponse> getAllEmployers(int page, int size, String sortby){

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortby));

        Page<User> list = urepo.findByRole(Role.EMPLOYER, pageable);

        List<UserResponse> res = list.getContent().stream()
                .map(u-> mapper.map(u, UserResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(res, pageable, list.getTotalElements());

    }

    public UserResponse getEmployerLogged(){

        String email = getLoggedInEmployerEmail();

        User employer = urepo.findByEmail(email).orElseThrow(()-> new UserException("Employer not found with the email"));

        return mapper.map(employer, UserResponse.class);

    }

    public Page<JobResponse> getAllJobsByEmployer(int page, int size, String sortby){

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortby));

        String email = getLoggedInEmployerEmail();

        User user = urepo.findByEmail(email).orElseThrow(()-> new UserException("Employer not found"));

        Page<Job> jobs = jrepo.findByCreatedBy(user, pageable);

        List<JobResponse> res = jobs.getContent().stream()
                .map(j-> mapper.map(j, JobResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(res, pageable, jobs.getTotalElements());

    }

    public JobResponse createJob(JobRequest request){

        String email = getLoggedInEmployerEmail();

        User employer = urepo.findByEmail(email)
                .orElseThrow(()-> new UserException("Employer not found"));

        Job job = mapper.map(request, Job.class);

        job.setCreatedBy(employer);

        job.setApplications(new ArrayList<>());

        job.setCreatedDate(LocalDate.now());

        Job saved = jrepo.save(job);

        return mapper.map(saved, JobResponse.class);

    }

    public AppResponse updateStatus(AppRequest request){

        String email = getLoggedInEmployerEmail();

        User employer = urepo.findByEmail(email).orElseThrow(()-> new UserException("Employer not found"));

        Application application = arepo.findById(request.getAppId())
                .orElseThrow(()-> new AppException("application not found"));

        if(!application.getJob().getCreatedBy().equals(employer)){
            throw new AppException("application invalid");
        }

        application.setStatus(request.getStatus());
        Application res = arepo.save(application);

        User applicant = application.getUser();

        if(applicant.getApplications() != null) {
            for (Application a : applicant.getApplications()){
                if(a.getId()==res.getId()){
                    a.setStatus(res.getStatus());
                    break;
                }
            }
        }

        return mapper.map(res, AppResponse.class);

    }

    public Page<JobApplicationCountResponse> getApplicationCountPerJobPosted(int page, int size, String sortby){

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortby));

        String email = getLoggedInEmployerEmail();

        User employer = urepo.findByEmail(email).orElseThrow(()-> new UserException("user not found"));

        Page<Job> jobs = jrepo.findByCreatedBy(employer, pageable);

        List<JobApplicationCountResponse> res = new ArrayList<>();

        for(Job j : jobs.getContent()){
            int count = (j.getApplications() != null) ? j.getApplications().size() : 0;
            res.add(new JobApplicationCountResponse(j.getTitle(), count));
        }

        return new PageImpl<>(res, pageable, jobs.getTotalElements());

    }

    public String deleteJob(int id){

        String email = getLoggedInEmployerEmail();

        User user = urepo.findByEmail(email).orElseThrow(()-> new UserException("Employer not found"));

        Job job = jrepo.findById(id).orElseThrow(()-> new AppException("Job not found"));

        if(!job.getCreatedBy().getEmail().equals(email)){
            throw new UserException("you are not authorised to delete the job");
        }

        jrepo.delete(job);

        return "job deleted successfully";
    }

}
