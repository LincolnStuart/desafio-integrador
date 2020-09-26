package co.lincolnstuart.Models

import co.lincolnstuart.Exceptions.*

class DigitalHouseManager {
    private val alunos = mutableListOf<Aluno>()
    private val professores = mutableListOf<Professor>()
    private val cursos = mutableListOf<Curso>()
    private val matriculas = mutableListOf<Matricula>()

    fun registrarCurso(nome: String, codigoCurso: Int, quantidadeMaximaDeAlunos: Int){
        if(quantidadeMaximaDeAlunos < 0){
            println("Curso $codigoCurso não cadastrado: Quantidade de horas do curso não pode ser menor que 0 (Zero).")
            return
        }
        val curso = Curso(codigoCurso, nome, quantidadeMaximaDeAlunos)
        if(cursos.contains(curso)){
            println("Curso ${curso.codigo} não cadastrado: Código já cadastrado anteriormente.")
            return
        }
        cursos.add(curso)
        println("Curso ${curso.codigo} cadastrado com sucesso!")
}

    fun excluirCurso(codigoCurso: Int){
        cursos.filter {
            it.codigo == codigoCurso
        }.firstOrNull()?.let { cursoASerRemovido ->
            cursos.remove(cursoASerRemovido)
        }
    }

    fun registrarProfessorAdjunto(nome: String,
                                  sobrenome: String,
                                  codigoProfessor: Int,
                                  quantidadeDeHoras: Int){
        if(quantidadeDeHoras < 0){
            println("Professor $codigoProfessor não cadastrado: Quantidade de horas de monitoria não pode ser menor que 0 (Zero).")
            return
        }
        val adjunto = ProfessorAdjunto(codigoProfessor, nome, sobrenome, quantidadeDeHoras)
        cadastraProfessor(adjunto)
    }

    fun registrarProfessorTitular(nome: String,
                                  sobrenome: String ,
                                  codigoProfessor: Int,
                                  especialidade: String ){
        val titular = ProfessorTitular(codigoProfessor, nome, sobrenome, especialidade)
        cadastraProfessor(titular)
    }

    private fun cadastraProfessor(professor: Professor) {
        if (professores.contains(professor)){
            println("Professor ${professor.codigo} não cadastrado: Código já cadastrado anteriormente.")
            return
        }
        professores.add(professor)
        println("Professor ${professor.codigo} cadastrado com sucesso!")
    }

    fun excluirProfessor(codigoProfessor: Int){
        professores.filter {
            it.codigo == codigoProfessor
        }.firstOrNull()?.let{professorASerRemovido ->
            professores.remove(professorASerRemovido)
        }
    }

    fun matricularAluno(nome: String ,
                        sobrenome: String ,
                        codigoAluno: Int){
        val aluno = Aluno(codigoAluno, nome, sobrenome)
        if(alunos.contains(aluno)) {
            println("Aluno $codigoAluno não cadastrado: Código já cadastrado anteriormente.")
            return
        }
        alunos.add(aluno)
        println("Aluno $codigoAluno cadastrado com sucesso!")
    }

    fun matricularAluno(codigoAluno: Int, codigoCurso: Int){
        try {
            val aluno  = getAluno(codigoAluno)
            val curso = getCurso(codigoCurso)
            curso.adicionaUmAluno(aluno)
            val matricula = Matricula(aluno, curso)
            matriculas.add(matricula)
        } catch (exception: CodigoNaoEncontradoException){
            println(exception.message)
        } catch (exception: CursoComLotacaoMaximaException){
            println("Não foi possível realizar a matricula pois o curso não tem mais vagas :/")
        }
    }

    fun alocarProfessores(codigoCurso: Int,
                          codigoProfessorTitular: Int,
                          codigoProfessorAdjunto: Int){
        try {
            val curso = getCurso(codigoCurso)
            val titular = getProfessorTitular(codigoProfessorTitular)
            val adjunto = getProfessorAdjunto(codigoProfessorAdjunto)
            //só inclui se forem encontrados tanto adjunto quanto titular
            curso.titular = titular
            curso.adjunto = adjunto
        } catch (exception: CodigoNaoEncontradoException){
            println(exception.message)
        }
    }

    private fun getCurso(codigoCurso: Int): Curso{
        return cursos.filter {
            it.codigo == codigoCurso
        }.firstOrNull()?:
        throw CodigoNaoEncontradoException("Código do aluno não encontrado :/")
    }

    private fun getAluno(codigoAluno: Int): Aluno{
        return alunos.filter {
            it.codigo == codigoAluno
        }.firstOrNull()?:
        throw CodigoNaoEncontradoException("Código do aluno não encontrado :/")
    }

    private fun getProfessorTitular(codigoProfessor: Int): ProfessorTitular{
        return professores.filter {
            it.codigo == codigoProfessor && it is ProfessorTitular
        }.firstOrNull()?.let{titular ->
            titular as ProfessorTitular
        }?:
        throw CodigoNaoEncontradoException("Código do titular não encontrado :/")
    }

    private fun getProfessorAdjunto(codigoProfessor: Int): ProfessorAdjunto{
        return professores.filter {
            it.codigo == codigoProfessor && it is ProfessorAdjunto
        }.firstOrNull()?.let{adjunto ->
            adjunto as ProfessorAdjunto
        }?:
        throw CodigoNaoEncontradoException("Código do adjunto não encontrado :/")
    }

    fun imprimeResumoCursos(){
        cursos.forEach {
            println(it)
            println(it.titular)
            println(it.adjunto)
            println(it.getMatriculados())
        }
    }

}