package br.edu.univassouras.api_vet_tadeu.service;

import br.edu.univassouras.api_vet_tadeu.dto.AnimalRequestDTO;
import br.edu.univassouras.api_vet_tadeu.dto.AnimalResponseDTO;
import br.edu.univassouras.api_vet_tadeu.dto.StatusUpdateDTO;
import br.edu.univassouras.api_vet_tadeu.enums.Especie;
import br.edu.univassouras.api_vet_tadeu.enums.StatusAdocao;
import br.edu.univassouras.api_vet_tadeu.exception.ResourceNotFoundException;
import br.edu.univassouras.api_vet_tadeu.model.Animal;
import br.edu.univassouras.api_vet_tadeu.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalService animalService;

    private Animal animal;
    private AnimalRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        animal = new Animal();
        animal.setId(1L);
        animal.setNome("Thor");
        animal.setEspecie(Especie.CACHORRO);
        animal.setStatusAdocao(StatusAdocao.DISPONIVEL);

        requestDTO = new AnimalRequestDTO();
        requestDTO.setNome("Thor Atualizado");
        requestDTO.setEspecie(Especie.CACHORRO);
        requestDTO.setStatusAdocao(StatusAdocao.EM_PROCESSO);
    }

    @Test
    @DisplayName("Deve buscar animal por ID com sucesso")
    void deveBuscarPorIdComSucesso() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        AnimalResponseDTO response = animalService.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Thor", response.getNome());
        verify(animalRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar ID inexistente")
    void deveLancarExcecaoQuandoIdNaoExiste() {
        when(animalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> animalService.buscarPorId(99L));
        verify(animalRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Deve cadastrar animal com sucesso")
    void deveCadastrarAnimalComSucesso() {
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);

        AnimalResponseDTO response = animalService.cadastrar(requestDTO);

        assertNotNull(response);
        verify(animalRepository, times(1)).save(any(Animal.class));
    }

    @Test
    @DisplayName("Deve atualizar animal com sucesso")
    void deveAtualizarAnimalComSucesso() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);

        AnimalResponseDTO response = animalService.atualizar(1L, requestDTO);

        assertNotNull(response);
        verify(animalRepository, times(1)).findById(1L);
        verify(animalRepository, times(1)).save(any(Animal.class));
    }

    @Test
    @DisplayName("Deve atualizar apenas o status de adoção do animal")
    void deveAtualizarStatusComSucesso() {
        StatusUpdateDTO statusDTO = new StatusUpdateDTO(StatusAdocao.ADOTADO);

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(animalRepository.save(any(Animal.class))).thenAnswer(i -> i.getArgument(0));

        AnimalResponseDTO response = animalService.atualizarStatus(1L, statusDTO);

        assertNotNull(response);
        assertEquals(StatusAdocao.ADOTADO, animal.getStatusAdocao());
        verify(animalRepository, times(1)).findById(1L);
        verify(animalRepository, times(1)).save(animal);
    }

    @Test
    @DisplayName("Deve excluir animal com sucesso quando ID existir")
    void deveExcluirAnimalComSucesso() {
        when(animalRepository.existsById(1L)).thenReturn(true);
        doNothing().when(animalRepository).deleteById(1L);

        assertDoesNotThrow(() -> animalService.excluir(1L));
        verify(animalRepository, times(1)).existsById(1L);
        verify(animalRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar excluir ID inexistente")
    void deveLancarExcecaoAoExcluirIdInexistente() {
        when(animalRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> animalService.excluir(99L));
        verify(animalRepository, times(1)).existsById(99L);
        verify(animalRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Deve retornar uma página de animais")
    void deveListarAnimaisPaginados() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Animal> paginaMock = new PageImpl<>(List.of(animal));

        when(animalRepository.findAll(pageable)).thenReturn(paginaMock);

        Page<AnimalResponseDTO> resultado = animalService.listarPaginado(pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        verify(animalRepository, times(1)).findAll(pageable);
    }
}