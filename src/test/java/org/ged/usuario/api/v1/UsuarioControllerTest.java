package org.ged.usuario.api.v1;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.ged.aluno.entity.AlunoEntity;
import org.ged.usuario.api.v1.request.UsuarioRequest;
import org.ged.usuario.entity.UsuarioEntity;
import org.ged.usuario.enums.CargoEnum;
import org.ged.usuario.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UsuarioControllerTest {

    @InjectMock
    UsuarioService service;
    @Inject
    UsuarioController controller;

    private UsuarioEntity usuario;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioEntity();
        usuario.setId(1L);
        usuario.setNome("Nome");
        usuario.setEmail("Email");
        usuario.setCpf("Cpf");
        usuario.setRg("Rg");
        AlunoEntity aluno = new AlunoEntity();
        aluno.setId(1L);
        aluno.setMatricula("A2025001");
        aluno.setUsuario(usuario);
        usuario.setAluno(aluno);
        usuario.setCargo(CargoEnum.ALUNO);
    }

    /**
     * Testa o método findAll
     */
    @Test
    @TestSecurity(user = "testUser", roles = {"coordenador"})
    void findAll() {
        AlunoEntity alunoMock = Mockito.mock(AlunoEntity.class);
        Mockito.when(alunoMock.getMatricula()).thenReturn("A12345");

        UsuarioEntity usuarioMock = Mockito.mock(UsuarioEntity.class);
        Mockito.when(usuarioMock.getNome()).thenReturn("Nome");
        Mockito.when(usuarioMock.getEmail()).thenReturn("Email");
        Mockito.when(usuarioMock.getCpf()).thenReturn("Cpf");
        Mockito.when(usuarioMock.getRg()).thenReturn("Rg");
        Mockito.when(usuarioMock.getCargo()).thenReturn(CargoEnum.ALUNO);
        Mockito.when(usuarioMock.getId()).thenReturn(1L);
        Mockito.when(usuarioMock.getAluno()).thenReturn(alunoMock);

        List<UsuarioEntity> mockList = new ArrayList<>();
        mockList.add(usuarioMock);
        Mockito.when(service.findAll()).thenReturn(mockList);

        Response response = controller.findAll();

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());

        List<UsuarioEntity> entity = (List<UsuarioEntity>) response.getEntity();
        assertFalse(entity.isEmpty());
        assertEquals("Nome", entity.get(0).getNome());
        assertEquals("Email", entity.get(0).getEmail());
        assertEquals("Cpf", entity.get(0).getCpf());
        assertEquals("Rg", entity.get(0).getRg());
        assertEquals(CargoEnum.ALUNO, entity.get(0).getCargo());
        assertEquals(1L, entity.get(0).getId());
        assertEquals('A', entity.get(0).getAluno().getMatricula().charAt(0));
    }


    @Test
    void findByFilter() {
        UsuarioRequest filter = new UsuarioRequest();
        List<UsuarioEntity> list = new ArrayList<>();
        list.add(usuario);
        filter.setMatricula("A2025001");

        Mockito.when(service.findByFilter(filter)).thenReturn(new ArrayList<>());
        Response response = controller.findByFilter(filter);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        List<UsuarioEntity> entity = (List<UsuarioEntity>) response.getEntity();
        assertTrue(entity.isEmpty());

    }

    @Test
    void save() {
    }
}