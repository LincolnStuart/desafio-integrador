package co.lincolnstuart

import co.lincolnstuart.models.*
import co.lincolnstuart.utils.*

fun main() {
    val manager = DigitalHouseManager()
    //cadastrando alunos
    mockAlunos.forEach {
        manager.matricularAluno(it.nome, it.sobrenome, it.codigo)
    }
    //cadastrando titulares
    mockProfessoresTitulares.forEach {
        manager.registrarProfessorTitular(it.nome, it.sobrenome, it.codigo, it.especialidade)
    }
    //cadastrando adjuntos
    mockProfessoresAdjuntos.forEach {
        manager.registrarProfessorAdjunto(it.nome, it.sobrenome, it.codigo, it.quantidadeDeHorasDeMonitoria)
    }
    //cadastrando cursos
    mockCursos.forEach {
        manager.registrarCurso(it.nome, it.codigo, it.quantidadeMaximaDeAlunos)
    }
    //alocando professores 1, 4 no curso 1
    manager.alocarProfessores(1, 1, 4)
    //tentando alocar um adjunto que não existe
    manager.alocarProfessores(2, 2, 3)
    //tentando excluir um professor alocado a um curso
    manager.excluirProfessor(1)
    //removendo curso
    manager.excluirCurso(2)
    //tentando remover curso inexistente
    manager.excluirCurso(3)
    //removendo outro curso
    manager.excluirCurso(4)
    //matriculando um aluno em um curso
    manager.matricularAluno(1, 1)
    //tentando matricular o mesmo aluno no mesmo curso
    manager.matricularAluno(1, 1)
    //tentando matricular um aluno em um curso que não tem mais vagas
    manager.matricularAluno(2, 1)
    //tentando excluir um curso com alunos matriculados
    manager.excluirCurso(1)
    //desvinculando um aluno de uma turma
    manager.desvincularAlunoDoCurso(1, 1)
    //tentando desvincular um professor que não existe de uma turma
    manager.desvincularProfessorDoCurso(6000, 1)
    //matriculando um novo aluno na turma, agora existe vaga
    manager.matricularAluno(2, 1)
    //excluindo professor
    manager.excluirProfessor(2)
    //tentando excluir professor que não existe
    manager.excluirProfessor(6000)
    //substituindo professores alocados
    manager.alocarProfessores(1, 3, 5)
    //imprimindo relatório
    manager.imprimirResumo()
}