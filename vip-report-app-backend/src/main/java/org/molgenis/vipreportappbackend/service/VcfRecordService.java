package org.molgenis.vipreportappbackend.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.molgenis.vipreportappbackend.model.VcfRecord;
import org.molgenis.vipreportappbackend.model.dto.VcfRecordDto;
import org.molgenis.vipreportappbackend.model.mapper.VcfRecordMapper;
import org.molgenis.vipreportappbackend.repository.VcfRecordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VcfRecordService {
  private final VcfRecordRepository vcfRecordRepository;
  private final VcfRecordMapper vcfRecordMapper;

  @Transactional(readOnly = true)
  public Page<VcfRecordDto> findAll(Specification<VcfRecord> specification, Pageable pageable) {
    Page<VcfRecord> vcfRecordPage = vcfRecordRepository.findAll(specification, pageable);
    return mapVcfRecordPageToVcfRecordDtoPage(vcfRecordPage);
  }

  @Transactional(readOnly = true)
  public Page<VcfRecordDto> findAll(Pageable pageable) {
    Page<Long> vcfRecordIdsPage = vcfRecordRepository.findAllVcfRecordIds(pageable);
    List<VcfRecord> unsortedVcfRecordList =
        vcfRecordRepository.findAllById(vcfRecordIdsPage.getContent());

    List<VcfRecord> vcfRecordList = new ArrayList<>();
    for (Long id : vcfRecordIdsPage.getContent()) {
      vcfRecordList.add(
          unsortedVcfRecordList.stream()
              .filter(vcfRecord -> vcfRecord.getId().equals(id))
              .findFirst()
              .orElseThrow());
    }

    Page<VcfRecord> vcfRecordPage =
        new PageImpl<>(vcfRecordList, pageable, vcfRecordIdsPage.getTotalElements());
    return mapVcfRecordPageToVcfRecordDtoPage(vcfRecordPage);
  }

  private Page<VcfRecordDto> mapVcfRecordPageToVcfRecordDtoPage(Page<VcfRecord> page) {
    return page.map(vcfRecordMapper::mapVcfRecordToVcfRecordDto);
  }
}
