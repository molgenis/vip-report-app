package org.molgenis.vipreportappdb;

import htsjdk.variant.variantcontext.Allele;
import htsjdk.variant.variantcontext.VariantContext;
import htsjdk.variant.vcf.VCFIterator;
import htsjdk.variant.vcf.VCFIteratorBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.molgenis.vipreportappdb.model.*;
import org.molgenis.vipreportappdb.service.VcfRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppCommandLineRunner implements CommandLineRunner {
  private static final Logger LOGGER = LoggerFactory.getLogger(AppCommandLineRunner.class);

  private final VcfRecordService vcfRecordService;

  @Override
  public void run(String... args) {
    if (args.length != 1) {
      LOGGER.info("missing arg");
      return;
    }

    int batchSize = 1000;
    List<VcfRecord> vcfRecords = new ArrayList<>(batchSize);
    Path vcfPath = Path.of(args[0]);
    try (VCFIterator vcfIterator = new VCFIteratorBuilder().open(vcfPath)) {
      int count = 0;
      while (vcfIterator.hasNext()) {
        VariantContext variantContext = vcfIterator.next();
        VcfRecord vcfRecord = variantContextToVcfRecord(variantContext);
        vcfRecords.add(vcfRecord);
        count++;

        if (vcfRecords.size() == batchSize) {
          System.out.println(count);
          vcfRecordService.saveVcfRecords(vcfRecords);
          vcfRecords.clear();
        }
      }
      if (!vcfRecords.isEmpty()) {
        vcfRecordService.saveVcfRecords(vcfRecords);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private VcfRecord variantContextToVcfRecord(VariantContext variantContext) {
    VcfRecord vcfRecord = new VcfRecord();
    vcfRecord.setChr(variantContextToChromosome(variantContext));
    vcfRecord.setPos(variantContext.getStart());
    vcfRecord.setRef(variantContext.getReference().getDisplayString());
    vcfRecord.setAlt(
        variantContext.getAlternateAlleles().stream()
            .map(htsjdk.variant.variantcontext.Allele::getDisplayString)
            .toList());
    variantContextToEffects(variantContext).forEach(vcfRecord::addEffect);
    variantContextToGenotypes(variantContext).forEach(vcfRecord::addGenotype);
    return vcfRecord;
  }

  private Chromosome variantContextToChromosome(VariantContext variantContext) {
    String contig = variantContext.getContig();
    return Chromosome.toChromosome(contig);
  }

  private Map<String, Gene> geneMap = new HashMap<>();

  private List<Effect> variantContextToEffects(VariantContext variantContext) {
    Object obj = variantContext.getAttribute("CSQ");
    List<String> csqTokens;
    if (obj == null) {
      csqTokens = List.of();
    } else if (obj instanceof List) {
      csqTokens = (List<String>) obj;
    } else {
      csqTokens = List.of((String) obj);
    }

    List<Effect> effects = new ArrayList<>();
    for (String csqToken : csqTokens) {
      String[] tokens = csqToken.split("\\|", -1);
      Effect effect = new Effect();

      // allele
      String alleleToken = tokens[0];
      if (!alleleToken.isEmpty()) {
        effect.setAllele(alleleToken);
      }

      // consequence
      String[] consequenceTokens = tokens[1].split("&", -1);
      List<Consequence> consequences = new ArrayList<>();
      for (String consequenceToken : consequenceTokens) {
        if (consequenceToken.isEmpty()) continue;
        consequences.add(Consequence.toConsequence(consequenceToken));
      }
      effect.setConsequences(consequences);

      // gene
      String geneToken = tokens[3];
      String ncbiGeneIdToken = tokens[4];
      if (geneToken != null && !geneToken.isEmpty()) {
        Gene gene = geneMap.get(geneToken);
        if (gene == null) {
          gene = new Gene();
          gene.setNcbiGeneId(Integer.parseInt(ncbiGeneIdToken));
          gene.setSymbol(geneToken);

          gene = vcfRecordService.saveGene(gene);

          geneMap.put(geneToken, gene);
        }
        effect.setGene(gene);
      }

      // capice
      String capiceToken = tokens[52];
      if (capiceToken != null && !capiceToken.isEmpty()) {
        effect.setCapiceScore(Float.valueOf(capiceToken));
      }

      // hgvsc
      String hgvsC = tokens[10];
      if (hgvsC != null && !hgvsC.isEmpty()) {
        effect.setHgvsC(hgvsC);
      }
      // hgvsp
      String hgvsP = tokens[11];
      if (hgvsP != null && !hgvsP.isEmpty()) {
        effect.setHgvsP(hgvsP);
      }
      effects.add(effect);
    }

    return effects;
  }

  private List<Genotype> variantContextToGenotypes(VariantContext variantContext) {
    List<Genotype> genotypes = new ArrayList<>();
    for (htsjdk.variant.variantcontext.Genotype vcfGenotype : variantContext.getGenotypes()) {
      Genotype genotype = new Genotype();
      genotype.setAlleles(vcfGenotype.getAlleles().stream().map(Allele::getDisplayString).toList());
      genotypes.add(genotype);
    }
    return genotypes;
  }
}
