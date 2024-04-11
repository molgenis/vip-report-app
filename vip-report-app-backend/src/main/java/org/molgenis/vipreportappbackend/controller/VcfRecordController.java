package org.molgenis.vipreportappbackend.controller;

import cz.jirutka.rsql.parser.RSQLParser;
import lombok.RequiredArgsConstructor;
import org.molgenis.vipreportappbackend.model.VcfRecord;
import org.molgenis.vipreportappbackend.model.dto.VcfRecordDto;
import org.molgenis.vipreportappbackend.service.VcfRecordService;
import org.molgenis.vipreportappbackend.utils.CustomRsqlVisitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vcf")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VcfRecordController {
  private final VcfRecordService vcfRecordService;

  @GetMapping
  public Page<VcfRecordDto> home(
      @RequestParam(value = "q", required = false) String rsqlQuery,
      @RequestParam(value = "p", required = false) Integer pageNumber,
      @RequestParam(value = "s", required = false) String sortStr) {
    Sort sort;
    if (sortStr != null && !sortStr.isEmpty()) {
      int index = sortStr.indexOf(':');

      if (index != -1) {
        String sortField = sortStr.substring(0, index);
        String sortDirection = sortStr.substring(sortStr.indexOf(':') + 1);
        if (sortDirection.equals("desc")) {
          sort = Sort.by(Sort.Direction.DESC, sortField);
        } else if (sortDirection.equals("asc")) {
          sort = Sort.by(Sort.Direction.ASC, sortField);
        } else {
          throw new IllegalArgumentException();
        }
      } else {
        sort = Sort.by(sortStr);
      }
    } else {
      sort = null;
    }
    Pageable pageable =
        sort != null
            ? PageRequest.of(pageNumber != null ? pageNumber : 0, 10, sort)
            : PageRequest.of(pageNumber != null ? pageNumber : 0, 10);

    Page<VcfRecordDto> vcfRecordPage;
    if (rsqlQuery != null) {
      Specification<VcfRecord> specification =
          new RSQLParser().parse(rsqlQuery).accept(new CustomRsqlVisitor<>());
      vcfRecordPage = vcfRecordService.findAll(specification, pageable);
    } else {
      vcfRecordPage = vcfRecordService.findAll(pageable);
    }
    return vcfRecordPage;
  }
}
