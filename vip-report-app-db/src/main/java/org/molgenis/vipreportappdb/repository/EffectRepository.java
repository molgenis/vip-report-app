package org.molgenis.vipreportappdb.repository;

import org.molgenis.vipreportappdb.model.Effect;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EffectRepository extends ListCrudRepository<Effect, Long> {}
