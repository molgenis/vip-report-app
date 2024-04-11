package org.molgenis.vipreportappbackend.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Genotype {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  private VcfRecord vcfRecord;

  @ElementCollection
  @Column(nullable = false, length = 1024)
  private List<String> alleles;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Genotype)) return false;
    return id != null && id.equals(((Genotype) o).getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
