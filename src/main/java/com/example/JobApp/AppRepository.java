package com.example.JobApp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppRepository extends JpaRepository<Application, Integer> {

    //for the user to get the applications applied
    List<Application> findByUser(User user);

    //counting applications for the specific job, user by the admin
    int countByJob(Job job);

    //number of applications per job
    @Query("SELECT a.job.id, COUNT(a) FROM Application a GROUP BY a.job.id")
    List<Object[]> countApplicationsGroupedByJob();

    //number of applications created by the employer
    @Query("SELECT a FROM Application a WHERE a.job.createdBy = :employer")
    List<Application> findApplicationsByEmployer(User employer);

    @Query("SELECT a FROM Application a WHERE a.job.createdBy = :employer")
    Page<Application> findApplicationsByEmployer(User employer, Pageable pageable);

    //checks if the user applied to the job
    boolean existsByUserAndJob(User user, Job job);

    //to return the job applications of the user
    Page<Application> findByUser(User user, Pageable pageable);

}

