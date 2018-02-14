package com.cms.users.repo;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.cms.users.entity.UserExtraInformation;

@Repository
public interface UserExtraInformationRepository extends PagingAndSortingRepository<UserExtraInformation, Long> {
	UserExtraInformation findByIdEquals(Long id);
	//User findByAccountEqualsOrSubEquals(String account,String sub);
	
    List<UserExtraInformation> findAll();
}
