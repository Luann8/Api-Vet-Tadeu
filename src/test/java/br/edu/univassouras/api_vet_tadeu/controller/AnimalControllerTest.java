package br.edu.univassouras.api_vet_tadeu.controller;

import br.edu.univassouras.api_vet_tadeu.dto.AnimalRequestDTO;
import br.edu.univassouras.api_vet_tadeu.enums.Especie;
import br.edu.univassouras.api_vet_tadeu.enums.PorteAnimal;
import br.edu.univassouras.api_vet_tadeu.enums.StatusAdocao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve listar todos os animais com status 200")
    void deveListarAnimais() throws Exception {
        mockMvc.perform(get("/api/animais")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @DisplayName("Deve cadastrar um animal com sucesso (201 Created)")
    void deveCadastrarAnimal() throws Exception {
        AnimalRequestDTO dto = new AnimalRequestDTO(
                "Max",
                Especie.CACHORRO,
                "Pastor Alemão",
                2,
                "Macho",
                PorteAnimal.GRANDE,
                "Animal esperto e companheiro",
                StatusAdocao.DISPONIVEL
        );

        mockMvc.perform(post("/api/animais")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Max"))
                .andExpect(jsonPath("$.especie").value("CACHORRO"))
                .andExpect(jsonPath("$.statusAdocao").value("DISPONIVEL"));
    }

    @Test
    @DisplayName("Deve buscar animal por ID existente (200 OK)")
    void deveBuscarAnimalPorId() throws Exception {
        mockMvc.perform(get("/api/animais/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Thor"));
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar animal com ID inexistente")
    void deveRetornar404AoBuscarInexistente() throws Exception {
        mockMvc.perform(get("/api/animais/99999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message", containsString("99999")));
    }

    @Test
    @DisplayName("Deve atualizar dados de um animal existente (200 OK)")
    void deveAtualizarAnimal() throws Exception {
        AnimalRequestDTO dto = new AnimalRequestDTO(
                "Thor Atualizado",
                Especie.CACHORRO,
                "Golden Retriever",
                4,
                "Macho",
                PorteAnimal.GRANDE,
                "Agora adotado e muito feliz",
                StatusAdocao.ADOTADO
        );

        mockMvc.perform(put("/api/animais/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Thor Atualizado"))
                .andExpect(jsonPath("$.statusAdocao").value("ADOTADO"));
    }

    @Test
    @DisplayName("Deve retornar 400 ao tentar cadastrar animal com campos inválidos")
    void deveRetornar400AoCadastrarInvalido() throws Exception {
        AnimalRequestDTO dtoInvalido = new AnimalRequestDTO(); // campos vazios

        mockMvc.perform(post("/api/animais")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.fieldErrors").isArray());
    }

    @Test
    @DisplayName("Deve excluir um animal com sucesso (204 No Content)")
    void deveExcluirAnimal() throws Exception {
        // Primeiro cria um animal para ser excluído
        AnimalRequestDTO dto = new AnimalRequestDTO(
                "Animal Para Deletar",
                Especie.GATO,
                "Siamês",
                1,
                "Fêmea",
                PorteAnimal.PEQUENO,
                "Teste exclusão",
                StatusAdocao.DISPONIVEL
        );

        String responseStr = mockMvc.perform(post("/api/animais")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Integer id = com.jayway.jsonpath.JsonPath.read(responseStr, "$.id");

        // Executa o DELETE
        mockMvc.perform(delete("/api/animais/" + id))
                .andExpect(status().isNoContent());

        // Confirma que não existe mais (404)
        mockMvc.perform(get("/api/animais/" + id))
                .andExpect(status().isNotFound());
    }
}
