package br.edu.univassouras.api_vet_tadeu.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Long id) {
        super("Animal não encontrado com o ID: " + id);
    }
}
