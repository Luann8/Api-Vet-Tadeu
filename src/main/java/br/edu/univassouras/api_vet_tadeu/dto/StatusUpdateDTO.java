package br.edu.univassouras.api_vet_tadeu.dto;

import br.edu.univassouras.api_vet_tadeu.enums.StatusAdocao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para atualização do status de adoção de um animal")
public class StatusUpdateDTO {

    @Schema(description = "Novo status da adoção", example = "ADOTADO")
    @NotNull(message = "O status da adoção é obrigatório (DISPONIVEL, EM_PROCESSO ou ADOTADO)")
    private StatusAdocao statusAdocao;

    public StatusUpdateDTO() {
    }

    public StatusUpdateDTO(StatusAdocao statusAdocao) {
        this.statusAdocao = statusAdocao;
    }

    public StatusAdocao getStatusAdocao() {
        return statusAdocao;
    }

    public void setStatusAdocao(StatusAdocao statusAdocao) {
        this.statusAdocao = statusAdocao;
    }
}


