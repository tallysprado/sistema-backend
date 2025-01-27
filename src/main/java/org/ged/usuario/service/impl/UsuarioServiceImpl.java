package org.ged.usuario.service.impl;

import io.netty.util.internal.StringUtil;
import jakarta.enterprise.context.RequestScoped;
import org.ged.aluno.entity.AlunoEntity;
import org.ged.aluno.entity.CoordenadorEntity;
import org.ged.aluno.entity.ProfessorEntity;
import org.ged.auth.dto.UserRegistrationRecord;
import org.ged.auth.service.KeycloakService;
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
import java.util.Optional;

@Service
@RequestScoped
public class UsuarioServiceImpl implements UsuarioService {


    private final UsuarioRepository repository;
    private final KeycloakService keycloakService;
    public UsuarioServiceImpl(UsuarioRepository repository, KeycloakService keycloakService) {
        this.repository = repository;
        this.keycloakService = keycloakService;
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
        String matricula = null;
        if (usuario.getCargo().equals(CargoEnum.ALUNO)) {
            AlunoEntity aluno = AlunoEntity
                    .builder()
                    .usuario(usuario)
                    .matricula(generateMatricula(usuario.getId(), CargoEnum.ALUNO))
                    .build();
            AlunoEntity.persist(aluno);
            matricula = aluno.getMatricula();
        } else if (usuario.getCargo().equals(CargoEnum.PROFESSOR)) {
            ProfessorEntity professor = ProfessorEntity
                    .builder()
                    .usuario(usuario)
                    .matricula(generateMatricula(usuario.getId(), CargoEnum.PROFESSOR))
                    .build();
            ProfessorEntity.persist(professor);
            matricula = professor.getMatricula();
        } else if (usuario.getCargo().equals(CargoEnum.COORDENADOR)) {
            CoordenadorEntity coordenador = CoordenadorEntity
                    .builder()
                    .usuario(usuario)
                    .matricula(generateMatricula(usuario.getId(), CargoEnum.COORDENADOR))
                    .build();
            CoordenadorEntity.persist(coordenador);
            matricula = coordenador.getMatricula();
        }
        if(usuario.isPersistent()){
            if(matricula!=null){
                this.keycloakService.
                        createUser(UserRegistrationRecord.builder().username(matricula)
                        .password("123")
                        .firstName(request.getNome().split(" ")[0])

                        .email(request.getEmail()).build());
            }
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

    @Override
    public UsuarioEntity update(Long id, UsuarioRequest request) {
        UsuarioEntity entity = repository.findById(id);
        entity.setNome(request.getNome());
        entity.setEmail(request.getEmail());
        entity.setCpf(request.getCpf());
        entity.setRg(request.getRg());
        entity.persistAndFlush();
        return entity;
    }

    @Override
    public void delete(Long matricula) {

        UsuarioEntity entity = UsuarioEntity.findById(matricula);
        String mat = null;
        if(entity.getAluno()!=null){
            mat = entity.getAluno().getMatricula();
            entity.getAluno().delete();
        }
        if(entity.getProfessor()!=null){
            mat = entity.getProfessor().getMatricula();
            entity.getProfessor().delete();
        }
        if(entity.getCoordenador()!=null){
            mat = entity.getCoordenador().getMatricula();
            entity.getCoordenador().delete();
        }
        entity.delete();
        if(mat!=null){
            keycloakService.deleteUser(mat);
        }
    }

    private String getMatricula(UsuarioEntity entity){
        if(entity.getCargo().equals(CargoEnum.ALUNO)){
            return entity.getAluno().getMatricula();
        }else if(entity.getCargo().equals(CargoEnum.PROFESSOR)){
            return entity.getProfessor().getMatricula();
        }else if(entity.getCargo().equals(CargoEnum.COORDENADOR)){
            return entity.getCoordenador().getMatricula();
        }
        return null;
    }

    private String generateMatricula(Long userId, CargoEnum cargo) {
        String prefix = cargo.equals(CargoEnum.ALUNO) ? "A" :
                cargo.equals(CargoEnum.PROFESSOR) ? "P" : "C";
        int year = LocalDate.now().getYear(); // Obtém o ano atual
        String paddedId = String.format("%03d", userId); // Left-pad com zeros (tamanho 3, ajustável)
        return prefix + year + paddedId;
    }


}
