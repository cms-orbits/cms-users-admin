package com.cms.users.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cms.users.entity.Participation;

@Repository
public interface ParticipationRepository extends PagingAndSortingRepository<Participation, Long> {
	
	@Query("select e from Participation e where e.id=?#{principal.participation.id}")
	Participation findByPrincipal();
	
    List<Participation> findAll();;

}
