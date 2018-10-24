package com.example.demo.repository;

import com.example.demo.entities.Location;
import com.example.demo.entities.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.annotation.Native;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    void deleteByPolicyList(List<Policy> policyList);

    @Query(value = "DELETE FROM tbl_location_has_tbl_policy  WHERE tbl_policy_id = :policyId ",
    nativeQuery = true)
    @Modifying
    void deleteLocationPolicyByPolicyId(@Param("policyId") Integer policyId);
}
