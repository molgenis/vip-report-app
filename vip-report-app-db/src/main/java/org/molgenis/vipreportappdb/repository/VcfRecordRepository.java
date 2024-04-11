package org.molgenis.vipreportappdb.repository;

import org.molgenis.vipreportappdb.model.VcfRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VcfRecordRepository extends JpaRepository<VcfRecord, Long> {}
