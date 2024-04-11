package org.molgenis.vipreportappdb.model;

import java.util.Objects;
import lombok.Getter;

@Getter
public enum Consequence {
  THREE_PRIME_UTR_VARIANT("3_prime_UTR_variant"),
  FIVE_PRIME_UTR_VARIANT("5_prime_UTR_variant"),
  CODING_SEQUENCE_VARIANT("coding_sequence_variant"),
  CODING_TRANSCRIPT_VARIANT("coding_transcript_variant"),
  DOWNSTREAM_GENE_VARIANT("downstream_gene_variant"),
  FEATURE_ELONGATION("feature_elongation"),
  FEATURE_TRUNCATION("feature_truncation"),
  FRAMESHIFT_VARIANT("frameshift_variant"),
  INCOMPLETE_TERMINAL_CODON_VARIANT("incomplete_terminal_codon_variant"),
  INFRAME_DELETION("inframe_deletion"),
  INFRAME_INSERTION("inframe_insertion"),
  INTERGENIC_VARIANT("intergenic_variant"),
  INTRON_VARIANT("intron_variant"),
  MATURE_MIRNA_VARIANT("mature_miRNA_variant"),
  MISSENSE_VARIANT("missense_variant"),
  NMD_TRANSCRIPT_VARIANT("NMD_transcript_variant"),
  NON_CODING_TRANSCRIPT_EXON_VARIANT("non_coding_transcript_exon_variant"),
  NON_CODING_TRANSCRIPT_VARIANT("non_coding_transcript_variant"),
  PROTEIN_ALTERING_VARIANT("protein_altering_variant"),
  REGULATORY_REGION_ABLATION("regulatory_region_ablation"),
  REGULATORY_REGION_AMPLIFICATION("regulatory_region_amplification"),
  REGULATORY_REGION_VARIANT("regulatory_region_variant"),
  SEQUENCE_VARIANT("sequence_variant"),
  SPLICE_ACCEPTOR_VARIANT("splice_acceptor_variant"),
  SPLICE_DONOR_5TH_BASE_VARIANT("splice_donor_5th_base_variant"),
  SPLICE_DONOR_REGION_VARIANT("splice_donor_region_variant"),
  SPLICE_DONOR_VARIANT("splice_donor_variant"),
  SPLICE_POLYPYRIMIDINE_TRACT_VARIANT("splice_polypyrimidine_tract_variant"),
  SPLICE_REGION_VARIANT("splice_region_variant"),
  START_LOST("start_lost"),
  START_RETAINED_VARIANT("start_retained_variant"),
  STOP_GAINED("stop_gained"),
  STOP_LOST("stop_lost"),
  STOP_RETAINED_VARIANT("stop_retained_variant"),
  SYNONYMOUS_VARIANT("synonymous_variant"),
  TF_BINDING_SITE_VARIANT("TF_binding_site_variant"),
  TFBS_ABLATION("TFBS_ablation"),
  TFBS_AMPLIFICATION("TFBS_amplification"),
  TRANSCRIPT_ABLATION("transcript_ablation"),
  TRANSCRIPT_AMPLIFICATION("transcript_amplification"),
  UPSTREAM_GENE_VARIANT("upstream_gene_variant");

  private final String name;

  Consequence(String name) {
    this.name = Objects.requireNonNull(name);
  }

  public static Consequence toConsequence(String name) {
    return switch (name) {
      case "3_prime_UTR_variant" -> THREE_PRIME_UTR_VARIANT;
      case "5_prime_UTR_variant" -> FIVE_PRIME_UTR_VARIANT;
      case "coding_sequence_variant" -> CODING_SEQUENCE_VARIANT;
      case "coding_transcript_variant" -> CODING_TRANSCRIPT_VARIANT;
      case "downstream_gene_variant" -> DOWNSTREAM_GENE_VARIANT;
      case "feature_elongation" -> FEATURE_ELONGATION;
      case "feature_truncation" -> FEATURE_TRUNCATION;
      case "frameshift_variant" -> FRAMESHIFT_VARIANT;
      case "incomplete_terminal_codon_variant" -> INCOMPLETE_TERMINAL_CODON_VARIANT;
      case "inframe_deletion" -> INFRAME_DELETION;
      case "inframe_insertion" -> INFRAME_INSERTION;
      case "intergenic_variant" -> INTERGENIC_VARIANT;
      case "intron_variant" -> INTRON_VARIANT;
      case "mature_miRNA_variant" -> MATURE_MIRNA_VARIANT;
      case "missense_variant" -> MISSENSE_VARIANT;
      case "NMD_transcript_variant" -> NMD_TRANSCRIPT_VARIANT;
      case "non_coding_transcript_exon_variant" -> NON_CODING_TRANSCRIPT_EXON_VARIANT;
      case "non_coding_transcript_variant" -> NON_CODING_TRANSCRIPT_VARIANT;
      case "protein_altering_variant" -> PROTEIN_ALTERING_VARIANT;
      case "regulatory_region_ablation" -> REGULATORY_REGION_ABLATION;
      case "regulatory_region_amplification" -> REGULATORY_REGION_AMPLIFICATION;
      case "regulatory_region_variant" -> REGULATORY_REGION_VARIANT;
      case "sequence_variant" -> SEQUENCE_VARIANT;
      case "splice_acceptor_variant" -> SPLICE_ACCEPTOR_VARIANT;
      case "splice_donor_5th_base_variant" -> SPLICE_DONOR_5TH_BASE_VARIANT;
      case "splice_donor_region_variant" -> SPLICE_DONOR_REGION_VARIANT;
      case "splice_donor_variant" -> SPLICE_DONOR_VARIANT;
      case "splice_polypyrimidine_tract_variant" -> SPLICE_POLYPYRIMIDINE_TRACT_VARIANT;
      case "splice_region_variant" -> SPLICE_REGION_VARIANT;
      case "start_lost" -> START_LOST;
      case "start_retained_variant" -> START_RETAINED_VARIANT;
      case "stop_gained" -> STOP_GAINED;
      case "stop_lost" -> STOP_LOST;
      case "stop_retained_variant" -> STOP_RETAINED_VARIANT;
      case "synonymous_variant" -> SYNONYMOUS_VARIANT;
      case "TF_binding_site_variant" -> TF_BINDING_SITE_VARIANT;
      case "TFBS_ablation" -> TFBS_ABLATION;
      case "TFBS_amplification" -> TFBS_AMPLIFICATION;
      case "transcript_ablation" -> TRANSCRIPT_ABLATION;
      case "transcript_amplification" -> TRANSCRIPT_AMPLIFICATION;
      case "upstream_gene_variant" -> UPSTREAM_GENE_VARIANT;
      default -> throw new IllegalArgumentException();
    };
  }
}
