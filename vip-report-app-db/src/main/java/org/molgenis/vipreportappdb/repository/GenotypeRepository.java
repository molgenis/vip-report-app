package org.molgenis.vipreportappdb.repository;

import org.molgenis.vipreportappdb.model.Genotype;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenotypeRepository extends ListCrudRepository<Genotype, Integer> {}
