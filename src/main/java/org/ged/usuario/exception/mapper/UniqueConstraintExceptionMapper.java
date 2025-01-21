package org.ged.usuario.exception.mapper;


import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.ged.usuario.exception.ErrorResponse;
import org.hibernate.exception.ConstraintViolationException;

@Provider
public class UniqueConstraintExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        String errorMessage = getErrorMessage(exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(errorMessage))
                .build();
    }

    private String getErrorMessage(String message) {
        if (message.contains("usuario_cpf_key")) {
            return "Já existe CPF cadastrado na base.";
        } else if (message.contains("usuario_rg_key")) {
            return "Já existe RG cadastrado na base.";
        } else if (message.contains("usuario_nome_key")) {
            return "Já existe NOME cadastrado na base.";
        }
        return "Erro de violação de unicidade.";
    }
}
