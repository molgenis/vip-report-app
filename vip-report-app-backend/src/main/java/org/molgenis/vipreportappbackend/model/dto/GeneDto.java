package org.molgenis.vipreportappbackend.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class GeneDto {
  Integer ncbiGeneId;
  String symbol;
}
