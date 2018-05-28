package com.cms.users.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cms.users.entity.Contest;

@Repository
public interface ContestRepository extends PagingAndSortingRepository<Contest, Long> {
	Contest findByNameEquals(String name);
	//User findByAccountEqualsOrSubEquals(String account,String sub);
	
	@Query("select e from User e where e.id=?#{principal.user.id}")
	Contest findByPrincipal();
	
    List<Contest> findAll();;

}
