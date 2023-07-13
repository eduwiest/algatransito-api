package com.algaworks.algatransito.api.controller;
import com.algaworks.algatransito.domain.exception.NegocioException;
import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import com.algaworks.algatransito.domain.service.RegistroProprietarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

  //  @PersistenceContext
   // private EntityManager manager;
    //@Autowired (não boa pratica pois não é possivel teste de fora
    private final RegistroProprietarioService registroProprietarioService;
    private final ProprietarioRepository proprietarioRepository;
//melhor pratica
   // public ProprietarioController(ProprietarioRepository proprietarioRepository) {
   //     this.proprietarioRepository = proprietarioRepository;
  // }

    @GetMapping
    public List<Proprietario> listar() {
        //retorna um like com a palavra a ser buscada
        //return proprietarioRepository.findByNomeContaining("s");
        //return proprietarioRepository.findByNome("Maria Farias");
        return proprietarioRepository.findAll();

        //metodo didatico com todas as linhas
        //TypedQuery<Proprietario> query = manager
        //         .createQuery( qlString: "from Proprietario", Proprietario.class);

        // return query.getResultList();
        // return manager.createQuery( "from Proprietario", Proprietario.class)
        //.getResultList();
    }

    //pesquisa por um parâmetro
    @GetMapping("{proprietarioId}")
    public ResponseEntity<Proprietario> buscar(@PathVariable Long proprietarioId){
        return proprietarioRepository.findById(proprietarioId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

 }

     @PostMapping
     @ResponseStatus(HttpStatus.CREATED)
     public Proprietario adicionar(@Valid @RequestBody Proprietario proprietario){
        return registroProprietarioService.salvar(proprietario);
        //return proprietarioRepository.save(proprietario);
     }

    @PutMapping("/{proprietarioId}")
     public ResponseEntity<Proprietario> atualizar(@PathVariable Long proprietarioId, @RequestBody Proprietario proprietario) {

        if (!proprietarioRepository.existsById(proprietarioId)) {
            return ResponseEntity.notFound().build();
        }

        proprietario.setId(proprietarioId);
        Proprietario proprietarioAtualizado = registroProprietarioService.salvar(proprietario);

        return ResponseEntity.ok(proprietarioAtualizado);
     }

     @DeleteMapping("/{proprietarioId}")
     public ResponseEntity<Void> remover(@PathVariable Long proprietarioId){
        if(!proprietarioRepository.existsById(proprietarioId)){
            return ResponseEntity.notFound().build();
        }

        registroProprietarioService.excluir(proprietarioId);
        //proprietarioRepository.deleteById(proprietarioId);
        return ResponseEntity.noContent().build();
     }


}


