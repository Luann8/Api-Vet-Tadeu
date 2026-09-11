package br.edu.univassouras.api_vet_tadeu.dto;

import br.edu.univassouras.api_vet_tadeu.enums.Especie;
import br.edu.univassouras.api_vet_tadeu.enums.PorteAnimal;
import br.edu.univassouras.api_vet_tadeu.enums.StatusAdocao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro ou atualização de um animal")
public class AnimalRequestDTO {

    @Schema(description = "Nome do animal", example = "Thor")
    @NotBlank(message = "O nome do animal é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @Schema(description = "Espécie do animal", example = "CACHORRO")
    @NotNull(message = "A espécie do animal é obrigatória (CACHORRO, GATO ou OUTRO)")
    private Especie especie;

    @Schema(description = "Raça do animal", example = "Golden Retriever")
    @NotBlank(message = "A raça do animal é obrigatória")
    private String raca;

    @Schema(description = "Idade aproximada em anos", example = "2")
    @NotNull(message = "A idade do animal é obrigatória")
    @Min(value = 0, message = "A idade não pode ser negativa")
    private Integer idade;

    @Schema(description = "Sexo do animal", example = "Macho")
    @NotBlank(message = "O sexo do animal é obrigatório (ex: Macho, Fêmea)")
    private String sexo;

    @Schema(description = "Porte do animal", example = "GRANDE")
    @NotNull(message = "O porte do animal é obrigatório (PEQUENO, MEDIO ou GRANDE)")
    private PorteAnimal porte;

    @Schema(description = "Descrição sobre histórico, vacinas e temperamento", example = "Dócil, brincalhão, castrado e vacinado")
    private String descricao;

    @Schema(description = "Status da adoção", example = "DISPONIVEL")
    private StatusAdocao statusAdocao;

    public AnimalRequestDTO() {
    }

    public AnimalRequestDTO(String nome, Especie especie, String raca, Integer idade,
                            String sexo, PorteAnimal porte, String descricao, StatusAdocao statusAdocao) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.sexo = sexo;
        this.porte = porte;
        this.descricao = descricao;
        this.statusAdocao = statusAdocao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public PorteAnimal getPorte() {
        return porte;
    }

    public void setPorte(PorteAnimal porte) {
        this.porte = porte;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusAdocao getStatusAdocao() {
        return statusAdocao;
    }

    public void setStatusAdocao(StatusAdocao statusAdocao) {
        this.statusAdocao = statusAdocao;
    }
}
