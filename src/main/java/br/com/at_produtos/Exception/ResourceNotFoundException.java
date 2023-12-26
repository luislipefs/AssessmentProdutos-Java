package br.com.at_produtos.Exception;
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
}