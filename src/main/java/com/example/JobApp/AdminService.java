package com.example.JobApp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminService {

    private UserRepository urepo;
    private JobRepository jrepo;
    private AppRepository arepo;
    private ModelMapper mapper;

    public String getLoggedUserEmail(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    public UserResponse getLoggedAdmin(){

        String email = getLoggedUserEmail();

        User admin = urepo.findByEmail(email).orElseThrow(()-> new UserException("Admin not found"));

        return mapper.map(admin, UserResponse.class);
    }

    public UserResponse getAdmin(int id){

        User admin = urepo.findById(id).orElseThrow(()-> new UserException("Admin not found with this Id"));

        if(admin.getRole() != Role.ADMIN){
            throw new UserException("The Id is not a Admin");
        }

        return mapper.map(admin, UserResponse.class);
    }

    public Page<UserResponse> getAllAdmin(int page, int size, String sortby){

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortby));

        Page<User> admins = urepo.findByRole(Role.ADMIN, pageable);

        List<UserResponse> res = admins.getContent().stream()
                .map(a-> mapper.map(a, UserResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(res, pageable, admins.getTotalElements());
    }

    public Page<UserResponse> getAllCustomers(int page, int size, String sortby){

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortby));

        Page<User> customers = urepo.findByRole(Role.CUSTOMER, pageable);

        List<UserResponse> res = customers.getContent().stream()
                .map(c-> mapper.map(c, UserResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(res, pageable, customers.getTotalElements());
    }

    public UserResponse getCustomer(int id){

        User customer = urepo.findById(id).orElseThrow(()-> new UserException("Customer not found"));

        if(customer.getRole() != Role.CUSTOMER){

           throw new UserException("The User is not a Customer");
        }
        UserResponse res = mapper.map(customer, UserResponse.class);

        return res;
    }

    public Page<UserResponse> getAllEmployers(int page, int size, String sortby){

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortby));

        Page<User> list = urepo.findByRole(Role.EMPLOYER, pageable);

        List<UserResponse> res = list.getContent().stream()
                .map(u-> mapper.map(u, UserResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(res, pageable, list.getTotalElements());

    }

    public UserResponse getEmployer(int id){

        User user = urepo.findById(id).orElseThrow(()-> new UserException("Employer not found"));

        if(user.getRole() != Role.EMPLOYER){
            throw new UserException("User is not a Employer");
        }

        UserResponse res = mapper.map(user, UserResponse.class);

        return res;
    }

    public Page<JobResponse> getAllJobs(int page, int size, String sortby){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortby));

        Page<Job> p = jrepo.findAll(pageable);
        List<JobResponse> res = p.getContent().stream()
                .map(job-> mapper.map(job, JobResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(res, pageable, p.getTotalElements());
    }

    public String countOfRegisteredUsers(){

          int usercount = urepo.countByRole(Role.CUSTOMER);
          int employercount = urepo.countByRole(Role.EMPLOYER);


           return "Number of resgistered customers: " + usercount + "\nNumber of registered Employers: " + employercount;

    }

    public Page<AdminQueryResponse> getAlljobsAppliedCount(int page, int size, String sortby){

        String email = getLoggedUserEmail();

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortby));

        Page<User> employers = urepo.findByRole(Role.EMPLOYER, pageable);

        List<AdminQueryResponse> res = new ArrayList<>();

        for(User emp: employers.getContent()){
            List<Job> jobs = jrepo.findByCreatedBy(emp);
            List<JobApplicationCountResponse> list = new ArrayList<>();
            for(Job j: jobs){
                int count = (j.getApplications()!=null)?j.getApplications().size():0;
                list.add(new JobApplicationCountResponse(j.getTitle(),count));
            }
            res.add(new AdminQueryResponse(emp.getId(), emp.getName(), list));
        }

        return new PageImpl<>(res, pageable, employers.getTotalElements());

    }
}
