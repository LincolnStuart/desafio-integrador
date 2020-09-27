package co.lincolnstuart

import co.lincolnstuart.Models.*
import co.lincolnstuart.Utils.*

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
    manager.alocarProfessores(1, 1, 4)
    manager.alocarProfessores(2, 2, 3)
    manager.excluirCurso(2)
    manager.excluirCurso(3)
    manager.excluirCurso(4)
    manager.matricularAluno(1, 1)
    manager.matricularAluno(2, 1)
    manager.desvincularAlunoDoCurso(1, 1)
    manager.matricularAluno(2, 1)
    manager.excluirProfessor(5)
    manager.imprimirResumo()
}