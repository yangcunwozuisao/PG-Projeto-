package ps2.restapidb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaRepo disciplinaRepo;

    @GetMapping
    public Iterable<Disciplina> getAllDisciplinas() {
        return disciplinaRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> getDisciplinaById(@PathVariable("id") long id) {
        Optional<Disciplina> disciplinaOptional = disciplinaRepo.findById(id);
        return disciplinaOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Disciplina> createDisciplina(@RequestBody Disciplina disciplina) {
        Disciplina savedDisciplina = disciplinaRepo.save(disciplina);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDisciplina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> updateDisciplina(@PathVariable("id") long id, @RequestBody Disciplina updatedDisciplina) {
        Optional<Disciplina> existingDisciplinaOptional = disciplinaRepo.findById(id);
        if (existingDisciplinaOptional.isPresent()) {
            Disciplina existingDisciplina = existingDisciplinaOptional.get();
            existingDisciplina.setNome(updatedDisciplina.getNome());
            existingDisciplina.setDescricao(updatedDisciplina.getDescricao());
            existingDisciplina.setProfessorResponsavel(updatedDisciplina.getProfessorResponsavel());
            disciplinaRepo.save(existingDisciplina);
            return ResponseEntity.ok(existingDisciplina);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisciplina(@PathVariable("id") long id) {
        if (disciplinaRepo.existsById(id)) {
            disciplinaRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
