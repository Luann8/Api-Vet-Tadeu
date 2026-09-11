package br.edu.univassouras.api_vet_tadeu.dto;

import br.edu.univassouras.api_vet_tadeu.enums.Especie;
import br.edu.univassouras.api_vet_tadeu.enums.PorteAnimal;
import br.edu.univassouras.api_vet_tadeu.enums.StatusAdocao;
import br.edu.univassouras.api_vet_tadeu.model.Animal;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Representação detalhada de um animal cadastrado")
public class AnimalResponseDTO {

    @Schema(description = "Identificador único do animal", example = "1")
    private Long id;

    @Schema(description = "Nome do animal", example = "Thor")
    private String nome;

    @Schema(description = "Espécie do animal", example = "CACHORRO")
    private Especie especie;

    @Schema(description = "Raça do animal", example = "Golden Retriever")
    private String raca;

    @Schema(description = "Idade em anos", example = "2")
    private Integer idade;

    @Schema(description = "Sexo do animal", example = "Macho")
    private String sexo;

    @Schema(description = "Porte do animal", example = "GRANDE")
    private PorteAnimal porte;

    @Schema(description = "Descrição ou observações", example = "Dócil, brincalhão, castrado e vacinado")
    private String descricao;

    @Schema(description = "Status atual de adoção", example = "DISPONIVEL")
    private StatusAdocao statusAdocao;

    @Schema(description = "Data e hora do cadastro", example = "2026-09-11 10:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataCadastro;

    public AnimalResponseDTO() {
    }

    public AnimalResponseDTO(Animal animal) {
        if (animal != null) {
            this.id = animal.getId();
            this.nome = animal.getNome();
            this.especie = animal.getEspecie();
            this.raca = animal.getRaca();
            this.idade = animal.getIdade();
            this.sexo = animal.getSexo();
            this.porte = animal.getPorte();
            this.descricao = animal.getDescricao();
            this.statusAdocao = animal.getStatusAdocao();
            this.dataCadastro = animal.getDataCadastro();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
