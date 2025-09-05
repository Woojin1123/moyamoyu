package com.moyamoyu.repository;

import com.moyamoyu.entity.Moim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoimRepository extends JpaRepository<Moim,Long> {
}
