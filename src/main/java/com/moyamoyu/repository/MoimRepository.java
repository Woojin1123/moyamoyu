package com.moyamoyu.repository;

import com.moyamoyu.entity.Moim;
import com.moyamoyu.entity.enums.MoimCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoimRepository extends JpaRepository<Moim,Long> {
    Page<Moim> findAllByCategory(MoimCategory valueOf, Pageable pageable);
}
