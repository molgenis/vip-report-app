package org.molgenis.vipreportappdb.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.molgenis.vipreportappdb.model.Gene;
import org.molgenis.vipreportappdb.model.VcfRecord;
import org.molgenis.vipreportappdb.repository.GeneRepository;
import org.molgenis.vipreportappdb.repository.VcfRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VcfRecordService {
  private final VcfRecordRepository vcfRecordRepository;
  private final GeneRepository geneRepository;

  @Transactional(propagation = Propagation.NEVER)
  public void saveVcfRecords(List<VcfRecord> vcfRecords) {
    vcfRecordRepository.saveAll(vcfRecords);
  }

  @Transactional(propagation = Propagation.NEVER)
  public Gene saveGene(Gene gene) {
    return geneRepository.save(gene);
  }
}
