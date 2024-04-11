package org.molgenis.vipreportappdb.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class VcfRecord {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private Chromosome chr;

  @Column(nullable = false)
  private Integer pos;

  @Column(nullable = false, length = 1024)
  private String ref;

  @ElementCollection
  @Column(nullable = false, length = 1024)
  private List<String> alt;

  @OneToMany(mappedBy = "vcfRecord", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Effect> effects = new HashSet<>();

  @OneToMany(mappedBy = "vcfRecord", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Genotype> genotypes = new HashSet<>();

  public void addEffect(Effect effect) {
    effects.add(effect);
    effect.setVcfRecord(this);
  }

  public void removeEffect(Effect effect) {
    effects.remove(effect);
    effect.setVcfRecord(null);
  }

  public void addGenotype(Genotype genotype) {
    genotypes.add(genotype);
    genotype.setVcfRecord(this);
  }

  public void removeEffect(Genotype genotype) {
    genotypes.remove(genotype);
    genotype.setVcfRecord(this);
  }
}
