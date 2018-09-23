package com.joelgtsantos.cmsusers.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.joelgtsantos.cmsusers.entity.UserExtraInformation;

@Repository
public interface UserExtraInformationRepository extends PagingAndSortingRepository<UserExtraInformation, Long> {
    List<UserExtraInformation> findAll();
    
    @Query("select e from UserExtraInformation e, User u where e.id = u.id and u.email =?1")
	UserExtraInformation findByEmail(String email);
}
