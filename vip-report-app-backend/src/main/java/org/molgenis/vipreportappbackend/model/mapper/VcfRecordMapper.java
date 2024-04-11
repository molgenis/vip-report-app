package org.molgenis.vipreportappbackend.model.mapper;

import org.molgenis.vipreportappbackend.model.*;
import org.molgenis.vipreportappbackend.model.dto.EffectDto;
import org.molgenis.vipreportappbackend.model.dto.GeneDto;
import org.molgenis.vipreportappbackend.model.dto.GenotypeDto;
import org.molgenis.vipreportappbackend.model.dto.VcfRecordDto;
import org.springframework.stereotype.Component;

@Component
public class VcfRecordMapper {
  public VcfRecordDto mapVcfRecordToVcfRecordDto(VcfRecord vcfRecord) {
    return VcfRecordDto.builder()
        .id(vcfRecord.getId())
        .chr(vcfRecord.getChr().getName())
        .pos(vcfRecord.getPos())
        .ref(vcfRecord.getRef())
        .alt(vcfRecord.getAlt())
        .effects(vcfRecord.getEffects().stream().map(this::mapEffectToEffectDto).toList())
        .genotypes(vcfRecord.getGenotypes().stream().map(this::mapGenotypeToGenotypeDto).toList())
        .build();
  }

  private EffectDto mapEffectToEffectDto(Effect effect) {
    String hgvsC = effect.getHgvsC();
    String hgvsP = effect.getHgvsP();
    StringBuilder stringBuilder = new StringBuilder();
    if (hgvsC != null) {
      stringBuilder.append(hgvsC);
      if (hgvsP != null) {
        stringBuilder.append('(').append(hgvsP).append(')');
      }
    }

    return EffectDto.builder()
        .allele(effect.getAllele())
        .consequences(effect.getConsequences().stream().map(Consequence::getName).toList())
        .gene(mapGeneToGeneDto(effect.getGene()))
        .capiceScore(effect.getCapiceScore())
        .hgvs(!stringBuilder.isEmpty() ? stringBuilder.toString() : null)
        .build();
  }

  private GenotypeDto mapGenotypeToGenotypeDto(Genotype genotype) {
    return GenotypeDto.builder().alleles(genotype.getAlleles()).build();
  }

  private GeneDto mapGeneToGeneDto(Gene gene) {
    return gene != null
        ? GeneDto.builder().ncbiGeneId(gene.getNcbiGeneId()).symbol(gene.getSymbol()).build()
        : null;
  }
}
