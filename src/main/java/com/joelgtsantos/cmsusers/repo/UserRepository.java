package com.joelgtsantos.cmsusers.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.joelgtsantos.cmsusers.entity.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	User findByUsernameEquals(String username);
	//User findByAccountEqualsOrSubEquals(String account,String sub);
	
	User findByEmailEquals(String email);
	
	@Query("select e from User e where e.id=?#{principal.user.id}")
	User findByPrincipal();
	
    List<User> findAll();;

}
