package br.edu.univassouras.api_vet_tadeu.repository;

import br.edu.univassouras.api_vet_tadeu.enums.Especie;
import br.edu.univassouras.api_vet_tadeu.enums.StatusAdocao;
import br.edu.univassouras.api_vet_tadeu.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findByStatusAdocao(StatusAdocao statusAdocao);

    List<Animal> findByEspecie(Especie especie);

    List<Animal> findByStatusAdocaoAndEspecie(StatusAdocao statusAdocao, Especie especie);

    List<Animal> findByNomeContainingIgnoreCase(String nome);
}
