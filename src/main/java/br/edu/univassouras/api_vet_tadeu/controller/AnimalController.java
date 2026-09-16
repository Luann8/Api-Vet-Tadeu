package br.edu.univassouras.api_vet_tadeu.controller;

import br.edu.univassouras.api_vet_tadeu.dto.AnimalRequestDTO;
import br.edu.univassouras.api_vet_tadeu.dto.AnimalResponseDTO;
import br.edu.univassouras.api_vet_tadeu.dto.StatusUpdateDTO;
import br.edu.univassouras.api_vet_tadeu.enums.Especie;
import br.edu.univassouras.api_vet_tadeu.enums.StatusAdocao;
import br.edu.univassouras.api_vet_tadeu.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/animais")
@CrossOrigin(origins = "*")
@Tag(name = "Animais", description = "Endpoints para gerenciamento e adoção de animais")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo animal", description = "Cadastra um animal para adoção no sistema com validação de campos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Animal cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
    })
    public ResponseEntity<AnimalResponseDTO> cadastrar(@Valid @RequestBody AnimalRequestDTO dto) {
        AnimalResponseDTO criado = animalService.cadastrar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criado.getId())
                .toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    @GetMapping
    @Operation(summary = "Consultar animais", description = "Retorna a lista de animais cadastrados, permitindo filtros opcionais por status, espécie ou nome.")
    @ApiResponse(responseCode = "200", description = "Lista de animais retornada com sucesso")
    public ResponseEntity<List<AnimalResponseDTO>> listar(
            @Parameter(description = "Filtrar por status (ex: DISPONIVEL, ADOTADO, EM_PROCESSO)")
            @RequestParam(required = false) StatusAdocao status,
            @Parameter(description = "Filtrar por espécie (ex: CACHORRO, GATO, OUTRO)")
            @RequestParam(required = false) Especie especie,
            @Parameter(description = "Filtrar por nome do animal")
            @RequestParam(required = false) String nome
    ) {
        List<AnimalResponseDTO> animais = animalService.listarTodos(status, especie, nome);
        return ResponseEntity.ok(animais);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar animal pelo ID", description = "Retorna os detalhes de um animal específico a partir de seu identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Animal encontrado"),
            @ApiResponse(responseCode = "404", description = "Animal não encontrado com o ID informado")
    })
    public ResponseEntity<AnimalResponseDTO> buscarPorId(@PathVariable Long id) {
        AnimalResponseDTO animal = animalService.buscarPorId(id);
        return ResponseEntity.ok(animal);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar dados de um animal", description = "Atualiza todos os dados de um animal existente identificado pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Animal atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Animal não encontrado com o ID informado")
    })
    public ResponseEntity<AnimalResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AnimalRequestDTO dto
    ) {
        AnimalResponseDTO atualizado = animalService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status de adoção", description = "Atualiza apenas o status de adoção de um animal (DISPONIVEL, EM_PROCESSO ou ADOTADO).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Animal não encontrado com o ID informado")
    })
    public ResponseEntity<AnimalResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateDTO dto
    ) {
        AnimalResponseDTO atualizado = animalService.atualizarStatus(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("/paginado")
    @Operation(summary = "Consultar animais com paginação", description = "Retorna os animais cadastrados de forma paginada e ordenável (ex: ?page=0&size=10&sort=nome,asc).")
    @ApiResponse(responseCode = "200", description = "Página de animais retornada com sucesso")
    public ResponseEntity<Page<AnimalResponseDTO>> listarPaginado(Pageable pageable) {
        Page<AnimalResponseDTO> pagina = animalService.listarPaginado(pageable);
        return ResponseEntity.ok(pagina);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um animal", description = "Remove o registro de um animal do sistema permanentemente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Animal excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Animal não encontrado com o ID informado")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        animalService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
