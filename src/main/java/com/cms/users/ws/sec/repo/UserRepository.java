package com.cms.users.ws.sec.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cms.users.entity.sec.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	User findByUsernameEquals(String username);
	//User findByAccountEqualsOrSubEquals(String account,String sub);
	
	@Query("select e from User e where e.id=?#{principal.user.id}")
	User findByPrincipal();
	
    List<User> findAll();;

}
