package br.com.alura.forum.api.dto.response;

public class JwtResponse {
    private final String token;
    private final String tipo;

    public JwtResponse(String token, String tipo) {

        this.token = token;
        this.tipo = tipo;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
}
