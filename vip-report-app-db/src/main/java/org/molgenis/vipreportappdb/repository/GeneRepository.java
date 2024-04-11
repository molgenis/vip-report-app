package org.molgenis.vipreportappdb.repository;

import org.molgenis.vipreportappdb.model.Gene;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneRepository extends ListCrudRepository<Gene, Integer> {}
