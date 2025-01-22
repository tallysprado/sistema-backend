package org.ged.usuario.service.impl;

import io.netty.util.internal.StringUtil;
import jakarta.enterprise.context.RequestScoped;
import org.ged.aluno.entity.AlunoEntity;
import org.ged.aluno.entity.CoordenadorEntity;
import org.ged.aluno.entity.ProfessorEntity;
import org.ged.usuario.api.v1.request.UsuarioRequest;
import org.ged.usuario.entity.UsuarioEntity;
import org.ged.usuario.enums.CargoEnum;
import org.ged.usuario.repository.UsuarioRepository;
import org.ged.usuario.service.UsuarioService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequestScoped
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UsuarioEntity save(UsuarioRequest request) {
        UsuarioEntity usuario = UsuarioEntity
                .builder()
                .email(request.getEmail())
                .cpf(request.getCpf())
                .rg(request.getRg())
                .cargo(request.getCargo())
                .nome(request.getNome())
                .build();
        UsuarioEntity.persist(usuario);
        if (usuario.getCargo().equals(CargoEnum.ALUNO)) {
            AlunoEntity aluno = AlunoEntity
                    .builder()
                    .usuario(usuario)
                    .matricula(generateMatricula(usuario.getId(), CargoEnum.ALUNO))
                    .build();
            AlunoEntity.persist(aluno);
        } else if (usuario.getCargo().equals(CargoEnum.PROFESSOR)) {
            ProfessorEntity professor = ProfessorEntity
                    .builder()
                    .usuario(usuario)
                    .matricula(generateMatricula(usuario.getId(), CargoEnum.PROFESSOR))
                    .build();
            ProfessorEntity.persist(professor);
        } else if (usuario.getCargo().equals(CargoEnum.COORDENADOR)) {
            CoordenadorEntity coordenador = CoordenadorEntity
                    .builder()
                    .usuario(usuario)
                    .matricula(generateMatricula(usuario.getId(), CargoEnum.COORDENADOR))
                    .build();
            CoordenadorEntity.persist(coordenador);
        }
        return usuario;
    }

    @Override
    public List<UsuarioEntity> findByFilter(UsuarioRequest filter) {
        return this.repository.findByFilter(filter);
    }

    @Override
    public List<UsuarioEntity> findAll() {
        return UsuarioEntity.listAll();
    }

    private String generateMatricula(Long userId, CargoEnum cargo) {
        String prefix = cargo.equals(CargoEnum.ALUNO) ? "A" :
                cargo.equals(CargoEnum.PROFESSOR) ? "P" : "C";
        int year = LocalDate.now().getYear(); // Obtém o ano atual
        String paddedId = String.format("%03d", userId); // Left-pad com zeros (tamanho 3, ajustável)
        return prefix + year + paddedId;
    }


}
