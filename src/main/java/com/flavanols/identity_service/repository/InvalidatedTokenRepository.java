package com.flavanols.identity_service.repository;

import com.flavanols.identity_service.entity.InvalidatedToken;
import com.flavanols.identity_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
