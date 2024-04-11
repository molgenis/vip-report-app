package org.molgenis.vipreportappbackend.model.dto;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class EffectDto {
  String allele;
  List<String> consequences;
  GeneDto gene;
  Float capiceScore;
  String hgvs;
}
