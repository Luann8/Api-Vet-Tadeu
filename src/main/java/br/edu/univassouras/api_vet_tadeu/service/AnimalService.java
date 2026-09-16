package br.edu.univassouras.api_vet_tadeu.service;

import br.edu.univassouras.api_vet_tadeu.dto.AnimalRequestDTO;
import br.edu.univassouras.api_vet_tadeu.dto.AnimalResponseDTO;
import br.edu.univassouras.api_vet_tadeu.dto.StatusUpdateDTO;
import br.edu.univassouras.api_vet_tadeu.enums.Especie;
import br.edu.univassouras.api_vet_tadeu.enums.StatusAdocao;
import br.edu.univassouras.api_vet_tadeu.exception.ResourceNotFoundException;
import br.edu.univassouras.api_vet_tadeu.model.Animal;
import br.edu.univassouras.api_vet_tadeu.repository.AnimalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Transactional(readOnly = true)
    public List<AnimalResponseDTO> listarTodos(StatusAdocao status, Especie especie, String nome) {
        List<Animal> animais;

        if (nome != null && !nome.isBlank()) {
            animais = animalRepository.findByNomeContainingIgnoreCase(nome.trim());
        } else if (status != null && especie != null) {
            animais = animalRepository.findByStatusAdocaoAndEspecie(status, especie);
        } else if (status != null) {
            animais = animalRepository.findByStatusAdocao(status);
        } else if (especie != null) {
            animais = animalRepository.findByEspecie(especie);
        } else {
            animais = animalRepository.findAll();
        }

        return animais.stream()
                .map(AnimalResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AnimalResponseDTO buscarPorId(Long id) {
        Animal animal = buscarEntidadePorId(id);
        return new AnimalResponseDTO(animal);
    }

    @Transactional
    public AnimalResponseDTO cadastrar(AnimalRequestDTO dto) {
        Animal animal = new Animal();
        copiarDtoParaEntidade(dto, animal);
        Animal salvo = animalRepository.save(animal);
        return new AnimalResponseDTO(salvo);
    }

    @Transactional
    public AnimalResponseDTO atualizar(Long id, AnimalRequestDTO dto) {
        Animal animal = buscarEntidadePorId(id);
        copiarDtoParaEntidade(dto, animal);
        Animal atualizado = animalRepository.save(animal);
        return new AnimalResponseDTO(atualizado);
    }

    @Transactional
    public AnimalResponseDTO atualizarStatus(Long id, StatusUpdateDTO dto) {
        Animal animal = buscarEntidadePorId(id);
        animal.setStatusAdocao(dto.getStatusAdocao());
        Animal atualizado = animalRepository.save(animal);
        return new AnimalResponseDTO(atualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        animalRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<AnimalResponseDTO> listarPaginado(Pageable pageable) {
        return animalRepository.findAll(pageable)
                .map(AnimalResponseDTO::new);
    }

    public Animal buscarEntidadePorId(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    private void copiarDtoParaEntidade(AnimalRequestDTO dto, Animal animal) {
        animal.setNome(dto.getNome());
        animal.setEspecie(dto.getEspecie());
        animal.setRaca(dto.getRaca());
        animal.setIdade(dto.getIdade());
        animal.setSexo(dto.getSexo());
        animal.setPorte(dto.getPorte());
        animal.setDescricao(dto.getDescricao());
        if (dto.getStatusAdocao() != null) {
            animal.setStatusAdocao(dto.getStatusAdocao());
        }
    }
}
