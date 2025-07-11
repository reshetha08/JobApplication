package com.example.JobApp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> {

    //list of jobs created by a user/employer
    List<Job> findByCreatedBy(User user);

    //gets the jobs posted by the employer
    Page<Job> findByCreatedBy(User user, Pageable pageable);

    //no of jobs created by the employer
    int countByCreatedBy(User user);

    //for the admin to know the count of jobs posted by each employer
    @Query("SELECT j.createdBy.id, COUNT(j) FROM Job j GROUP BY j.createdBy.id")
    List<Object[]> countByJobsGroupedByEmployer();

    @Query("SELECT j FROM Job j WHERE " +
            "(:domain IS NULL OR LOWER(j.domain) LIKE LOWER(CONCAT('%', :domain, '%'))) AND " +
            "(:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
            "(:experience IS NULL OR j.experience = :experience) AND " +
            "(:employerName IS NULL OR LOWER(j.createdBy.name) LIKE LOWER(CONCAT('%', :employerName, '%')))")
    Page<Job> searchJobs(@Param("domain") String domain,
                         @Param("location") String location,
                         @Param("experience") Integer experience,
                         @Param("employerName") String employerName,
                         Pageable pageable);
}
