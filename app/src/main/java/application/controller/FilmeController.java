package application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import application.model.Filme;
import application.repository.FilmeRepository;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeRepository filmeRepo;


    @GetMapping
    public Iterable<Filme> getFilmes(){
        return filmeRepo.findAll();
    }


    @GetMapping("/{id}")
    public Filme getFilme(@PathVariable Long id){
        Optional<Filme> resultado = filmeRepo.findById(id);

        if(resultado.isEmpty()){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Filme não encontrado"
            );
        };
        return resultado.get();
    }

    @PostMapping
    public Filme postFilme(@RequestBody Filme filme){
        return filmeRepo.save(filme);
    }

    @PutMapping("/{id}")
    public Filme atualizarFilme(@PathVariable Long id, @RequestBody Filme body){
        Optional<Filme> resultado = filmeRepo.findById(id);

        if(resultado.isEmpty()){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Filme não encontrado"
            );
        };

        if(body.getNomeFilme().isEmpty()){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Campo Nome do Filme é obrigatório"
            );
        };

        if(body.getGenero().isEmpty()){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Campo gênero é obrigatório"
            );
        };
        

        resultado.get().setNomeFilme(body.getNomeFilme());
        resultado.get().setGenero(body.getGenero());
        return filmeRepo.save(resultado.get());
    }

    @DeleteMapping("/{id}")
    public void deleteFilme(@PathVariable Long id){;
        if(!filmeRepo.existsById(id)){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Filme não encontrado"
            );
        }
        filmeRepo.deleteById(id);
    }
    
}