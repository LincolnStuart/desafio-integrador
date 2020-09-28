package co.lincolnstuart.Models

import co.lincolnstuart.Exceptions.*

class DigitalHouseManager {
    private val alunos = mutableListOf<Aluno>()
    private val professores = mutableListOf<Professor>()
    private val cursos = mutableListOf<Curso>()
    private val matriculas = mutableListOf<Matricula>()

    fun registrarCurso(nome: String, codigoCurso: Int, quantidadeMaximaDeAlunos: Int) {
        if (quantidadeMaximaDeAlunos < 0) {
            println("Curso $codigoCurso não cadastrado: Quantidade de horas do curso não pode ser menor que 0 (Zero).")
            return
        }
        val curso = Curso(codigoCurso, nome, quantidadeMaximaDeAlunos)
        if (cursos.contains(curso)) {
            println("Curso ${curso.codigo} não cadastrado: Código já cadastrado anteriormente.")
            return
        }
        cursos.add(curso)
        println("Curso ${curso.codigo} cadastrado com sucesso!")
    }

    fun excluirCurso(codigoCurso: Int) {
        cursos.filter {
            it.codigo == codigoCurso
        }.firstOrNull()?.let { cursoASerRemovido ->
            if(cursoASerRemovido.recuperarMatriculados().any()){
                println("Curso $codigoCurso não pode ser removido: existem alunos matriculados neste curso!")
                return
            }
            cursos.remove(cursoASerRemovido)
            println("Curso removido com sucesso!")
            return
        }
        println("Curso não removido: Curso não existia na base!")
    }

    fun registrarProfessorAdjunto(
        nome: String,
        sobrenome: String,
        codigoProfessor: Int,
        quantidadeDeHoras: Int
    ) {
        if (quantidadeDeHoras < 0) {
            println("Professor $codigoProfessor não cadastrado: Quantidade de horas de monitoria não pode ser menor que 0 (Zero).")
            return
        }
        val adjunto = ProfessorAdjunto(codigoProfessor, nome, sobrenome, quantidadeDeHoras)
        cadastraProfessorGenerico(adjunto)
    }

    fun registrarProfessorTitular(
        nome: String,
        sobrenome: String,
        codigoProfessor: Int,
        especialidade: String
    ) {
        val titular = ProfessorTitular(codigoProfessor, nome, sobrenome, especialidade)
        cadastraProfessorGenerico(titular)
    }

    fun excluirProfessor(codigoProfessor: Int) {
        professores.filter {
            it.codigo == codigoProfessor
        }.firstOrNull()?.let { professorASerRemovido ->
            cursos.forEach {
                if(it.verificarProfessorAlocadoAoCurso(codigoProfessor)){
                    println("Professor não removido: Professor $codigoProfessor está alocado ao curso ${it.codigo}!")
                    return
                }
            }
            professores.remove(professorASerRemovido)
            println("Professor removido com sucesso!")
            return
        }
        println("Professor não removido: Professor $codigoProfessor existia na base!")
    }

    fun matricularAluno(
        nome: String,
        sobrenome: String,
        codigoAluno: Int
    ) {
        val aluno = Aluno(codigoAluno, nome, sobrenome)
        if (alunos.contains(aluno)) {
            println("Aluno $codigoAluno não cadastrado: Código já cadastrado anteriormente.")
            return
        }
        alunos.add(aluno)
        println("Aluno $codigoAluno cadastrado com sucesso!")
    }

    fun matricularAluno(codigoAluno: Int, codigoCurso: Int) {
        try {
            val aluno = recuperarAluno(codigoAluno)
            val curso = recuperarCurso(codigoCurso)
            if (verificarAlunoMatriculadoNoCurso(curso, aluno)) return
            if (curso.adicionarUmAluno(aluno)) {
                println("Não foi possível realizar a matricula pois o curso não tem mais vagas :/")
                return
            }
            val matricula = Matricula(aluno, curso)
            matriculas.add(matricula)
            println("Matrícula do Aluno $codigoAluno no curso $codigoCurso cadastrada com sucesso!")
        } catch (exception: DadoNaoEncontradoException) {
            println(exception.message)
        }
    }

    fun alocarProfessores(
        codigoCurso: Int,
        codigoProfessorTitular: Int,
        codigoProfessorAdjunto: Int
    ) {
        try {
            val curso = recuperarCurso(codigoCurso)
            val titular = recuperarProfessorTitular(codigoProfessorTitular)
            val adjunto = recuperarProfessorAdjunto(codigoProfessorAdjunto)
            //só inclui se forem encontrados tanto adjunto quanto titular
            curso.titular = titular
            curso.adjunto = adjunto
            println("Professores $codigoProfessorTitular, $codigoProfessorAdjunto alocados com sucessso no curso $codigoCurso!")
        } catch (exception: DadoNaoEncontradoException) {
            println(exception.message)
        }
    }

    private fun cadastraProfessorGenerico(professor: Professor) {
        if (professores.contains(professor)) {
            println("Professor ${professor.codigo} não cadastrado: Código já cadastrado anteriormente.")
            return
        }
        professores.add(professor)
        println("Professor ${professor.codigo} cadastrado com sucesso!")
    }

    private fun verificarAlunoMatriculadoNoCurso(
        curso: Curso,
        aluno: Aluno
    ): Boolean {
        if (curso.verificarAlunoPertenceAoCurso(aluno)) {
            println("O aluno ${aluno.codigo} já está matriculado no ${curso.codigo}")
            return true
        }
        return false
    }

    fun desvincularAlunoDoCurso(codigoAluno: Int, codigoCurso: Int) {
        try {
            val aluno = recuperarAluno(codigoAluno)
            val curso = recuperarCurso(codigoCurso)
            curso.excluirUmAluno(aluno)
            matriculas.remove(recuperarMatricula(aluno, curso))
            println("Matrícula removida com sucesso!")
        } catch (exception: DadoNaoEncontradoException) {
            println(exception.message)
        }
    }

    fun desvincularProfessorDoCurso(codigoProfessor: Int, codigoCurso: Int) {
        try {
            val curso = recuperarCurso(codigoCurso)
            when {
                curso.titular?.codigo == codigoProfessor -> {
                    curso.titular = null
                    println("Professor titular $codigoProfessor desvinculado do curso $codigoCurso com sucesso")
                }
                curso.adjunto?.codigo == codigoCurso -> {
                    curso.adjunto = null
                    println("Professor titular $codigoProfessor desvinculado do curso $codigoCurso com sucesso")
                }
                else -> {
                    println("Professor não desvinculado: Código do professor $codigoProfessor não encontrado no curso $codigoCurso")
                }
            }
        } catch (exception: DadoNaoEncontradoException) {
            println(exception.message)
        }
    }

    private fun recuperarCurso(codigoCurso: Int): Curso {
        return cursos.filter {
            it.codigo == codigoCurso
        }.firstOrNull() ?: throw DadoNaoEncontradoException("Código do curso não encontrado :/")
    }

    private fun recuperarAluno(codigoAluno: Int): Aluno {
        return alunos.filter {
            it.codigo == codigoAluno
        }.firstOrNull() ?: throw DadoNaoEncontradoException("Código do aluno não encontrado :/")
    }

    private fun recuperarMatricula(
        aluno: Aluno,
        curso: Curso
    ): Matricula {
        return (matriculas.filter {
            it.aluno == aluno && it.curso == curso
        }.firstOrNull()
            ?: throw DadoNaoEncontradoException("Matrícula não encontrada :/"))
    }

    private fun recuperarProfessorTitular(codigoProfessor: Int): ProfessorTitular {
        return professores.filter {
            it.codigo == codigoProfessor && it is ProfessorTitular
        }.firstOrNull()?.let { titular ->
            titular as ProfessorTitular
        } ?: throw DadoNaoEncontradoException("Código do titular não encontrado :/")
    }

    private fun recuperarProfessorAdjunto(codigoProfessor: Int): ProfessorAdjunto {
        return professores.filter {
            it.codigo == codigoProfessor && it is ProfessorAdjunto
        }.firstOrNull()?.let { adjunto ->
            adjunto as ProfessorAdjunto
        } ?: throw DadoNaoEncontradoException("Código do adjunto não encontrado :/")
    }

    fun imprimirResumo() {
        imprimirResumoAlunos()
        imprimirResumoProfessores()
        imprimirResumoMatriculas()
        imprimirResumoCursos()
    }

    fun imprimirResumoCursos() {
        println("#########################################")
        println("#          RESUMO DOS CURSOS            #")
        println("#########################################")
        cursos.forEach {
            println(it)
            println(it.titular)
            println(it.adjunto)
            println(it.recuperarMatriculados())
            println("-----------------------------------------")
        }
        println("#########################################")
    }

    fun imprimirResumoAlunos() {
        println("#########################################")
        println("#          RESUMO DOS ALUNOS            #")
        println("#########################################")
        alunos.forEach {
            println(it)
            println("-----------------------------------------")
        }
        println("#########################################")
    }

    fun imprimirResumoProfessores() {
        println("#########################################")
        println("#        RESUMO DOS PROFESSORES         #")
        println("#########################################")
        professores.forEach {
            println(it)
            println("-----------------------------------------")
        }
        println("#########################################")
    }

    fun imprimirResumoMatriculas() {
        println("#########################################")
        println("#         RESUMO DAS MATRICULAS         #")
        println("#########################################")
        matriculas.forEach {
            println(it)
            println("-----------------------------------------")
        }
        println("#########################################")
    }

}