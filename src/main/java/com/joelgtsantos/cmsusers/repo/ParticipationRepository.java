package com.joelgtsantos.cmsusers.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.joelgtsantos.cmsusers.entity.Participation;

@Repository
public interface ParticipationRepository extends PagingAndSortingRepository<Participation, Long> {
	
	@Query("select e from Participation e where e.id=?#{principal.participation.id}")
	Participation findByPrincipal();
	
	@Query("select e from Participation e where e.contestId=:#{#participation.contestId} and e.userId=:#{#participation.userId}")
	Participation exist(@Param("participation") Participation participation);
	
	@Query("select id from Participation where id = 1")
	Long findOne();
	
    List<Participation> findAll();

}
