package com.example.Curso.Controller;

import com.example.Curso.banco.CursoDb;
import com.example.Curso.model.Aluno;
import com.example.Curso.model.Curso;

import java.util.List;
import java.util.Optional;

public class CursoController {

    CursoDb repository = new CursoDb();

    public List<Curso> getAll() {
        return repository.findAll();
    }

    public List<Curso> getByProfessor(String nomeProfessor) {
        return repository.findByProfessor(nomeProfessor);
    }

    public List<Curso> getBySala(int sala) {
        return repository.findBySala(sala);
    }

    public Optional<Curso> getById(int id) {
        return Optional.ofNullable(repository.getById(id));
    }

    public boolean insertBanco(Curso curso) {
        return repository.insert(curso);
    }

    public Optional<Curso> insertAluno(int idCurso, Aluno aluno) {
        Curso curso = repository.getById(idCurso);
        if (curso == null) {
            return Optional.empty();
        }
        repository.insertAluno(idCurso, aluno);
        return Optional.of(curso);
    }

    public String insertAlunoMelhorado(int idCurso, Aluno aluno) {
        Curso curso = repository.getById(idCurso);
        if (curso == null) {
            return "Curso não encontrado, para que aluno possa ser inserido!";
        }
        boolean result = repository.insertAlunoMelhorado(curso, aluno);
        if (result) {
            return "Aluno inserido com sucesso";
        }
        return "Não foi possível inserir aluno";
    }

    public Optional<Curso> update(int id, Curso curso) {
        boolean result = repository.update(id, curso);
        if (result) {
            return Optional.of(curso);
        }
        return Optional.empty();
    }

    public boolean updateAluno(int idCurso, int idAluno, Aluno aluno) {
        return repository.updateAluno(idCurso, idAluno, aluno);
    }

    public boolean delete(int id) {
        return repository.delete(id);
    }
}