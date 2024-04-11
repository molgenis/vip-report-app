package org.molgenis.vipreportappdb.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Effect {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private VcfRecord vcfRecord;

  @Column(length = 1024)
  private String allele;

  @ElementCollection private List<Consequence> consequences;

  @ManyToOne private Gene gene;

  @Column(length = 1024)
  private String hgvsC;

  private String hgvsP;

  private Float capiceScore;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Effect)) return false;
    return id != null && id.equals(((Effect) o).getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
