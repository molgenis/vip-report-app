package org.molgenis.vipreportappbackend.repository;

import org.molgenis.vipreportappbackend.model.VcfRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface VcfRecordRepository
    extends JpaRepository<VcfRecord, Long>, JpaSpecificationExecutor<VcfRecord> {
  @Query(
      value =
          """
            SELECT effect.vcfRecord.id FROM Effect effect GROUP BY effect.vcfRecord.id ORDER BY MAX(effect.capiceScore) DESC
          """,
      countQuery =
          """
          SELECT COUNT(v)
          FROM VcfRecord v
          """)
  Page<Long> findAllVcfRecordIds(Pageable pageable);
}
