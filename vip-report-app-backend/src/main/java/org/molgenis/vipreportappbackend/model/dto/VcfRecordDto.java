package org.molgenis.vipreportappbackend.model.dto;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class VcfRecordDto {
  Long id;
  String chr;
  Integer pos;
  String ref;
  List<String> alt;
  List<EffectDto> effects;
  List<GenotypeDto> genotypes;
}
