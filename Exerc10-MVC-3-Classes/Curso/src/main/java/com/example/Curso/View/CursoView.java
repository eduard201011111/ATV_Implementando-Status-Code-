package com.example.Curso.View;

import com.example.Curso.Controller.CursoController;
import com.example.Curso.model.Aluno;
import com.example.Curso.model.Curso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/curso")
public class CursoView {

    CursoController cursoController = new CursoController();

    @GetMapping
    public ResponseEntity<List<Curso>> getCurso(
            @RequestParam(required = false) String nomeProfessor,
            @RequestParam(required = false) Integer sala)
    {
        List<Curso> cursos;
        if (nomeProfessor != null) {
            cursos = cursoController.getByProfessor(nomeProfessor);
        }
        else if (sala != null) {
            cursos = cursoController.getBySala(sala);
        }
        else {
            cursos = cursoController.getAll();
        }

        if (cursos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(cursos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> getById(@PathVariable int id) {
        Optional<Curso> curso = cursoController.getById(id);
        if (curso.isPresent()) {
            return new ResponseEntity<>(curso.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Boolean> insert(@RequestBody Curso curso) {
        boolean success = cursoController.insertBanco(curso);
        if (success) {
            return new ResponseEntity<>(success, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(success, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{idCurso}/aluno")
    public ResponseEntity<Curso> insertAluno(@RequestBody Aluno aluno, @PathVariable int idCurso) {
        Optional<Curso> cursoOpt = cursoController.insertAluno(idCurso, aluno);
        if (cursoOpt.isPresent()) {
            return new ResponseEntity<>(cursoOpt.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{idCurso}/alunoMelhorado")
    public ResponseEntity<String> insertAlunoMelhorado(@RequestBody Aluno aluno, @PathVariable int idCurso) {
        String response = cursoController.insertAlunoMelhorado(idCurso, aluno);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> update(@RequestBody Curso curso, @PathVariable int id) {
        Optional<Curso> updatedCurso = cursoController.update(id, curso);
        if (updatedCurso.isPresent()) {
            return new ResponseEntity<>(updatedCurso.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{idCurso}/aluno/{idAluno}")
    public ResponseEntity<Boolean> update(@PathVariable int idCurso, @PathVariable int idAluno, @RequestBody Aluno aluno) {
        boolean success = cursoController.updateAluno(idCurso, idAluno, aluno);
        if (success) {
            return new ResponseEntity<>(success, HttpStatus.OK);
        }
        return new ResponseEntity<>(success, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable int id) {
        boolean success = cursoController.delete(id);
        if (success) {
            return new ResponseEntity<>(success, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(success, HttpStatus.NOT_FOUND);
    }
}