package co.lincolnstuart.models

import co.lincolnstuart.exceptions.*

/**
 * classe que representa um gerenciador de alunos, professores, matriculas e cursos.
 *
 * @author Lincoln Stuart
 * @since 25/09/2020
 */
class DigitalHouseManager {
    private val alunos = mutableListOf<Aluno>()
    private val professores = mutableListOf<Professor>()
    private val cursos = mutableListOf<Curso>()
    private val matriculas = mutableListOf<Matricula>()

    /**
     * método que cria e registra um curso baseado nos parâmetros.
     * obs1.: o código do curso não pode existir na lista de cursos.
     * obs2.: a quantidade máxima de alunos deve ser positiva.
     *
     * @param nome
     * @param codigoCurso
     * @param quantidadeMaximaDeAlunos
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    fun registrarCurso(nome: String, codigoCurso: Int, quantidadeMaximaDeAlunos: Int) {
        if (quantidadeMaximaDeAlunos < 0) {
            println("Curso $codigoCurso não cadastrado: Quantidade de horas do curso não pode ser menor que 0 (Zero) :/")
            return
        }
        val curso = Curso(codigoCurso, nome, quantidadeMaximaDeAlunos)
        if (cursos.contains(curso)) {
            println("Curso ${curso.codigo} não cadastrado: Código já cadastrado anteriormente :/")
            return
        }
        cursos.add(curso)
        println("Curso ${curso.codigo} cadastrado com sucesso!")
    }

    /**
     * método que exclui curso baseado no código do curso.
     * obs1.: o curso deve existir na coleção de cursos.
     * obs2.: o curso não pode ter nenhum aluno matriculado para que possa ser excluido.
     *
     * @param codigoCurso
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    fun excluirCurso(codigoCurso: Int) {
        cursos.firstOrNull {
            it.codigo == codigoCurso
        }?.let { cursoASerRemovido ->
            if (cursoASerRemovido.recuperarMatriculados().any()) {
                println("Curso $codigoCurso não removido: existem alunos matriculados neste curso :/")
                return
            }
            cursos.remove(cursoASerRemovido)
            println("Curso removido com sucesso!")
        } ?: println("Curso não removido: Curso $codigoCurso não existia na base :/")
    }

    /**
     * método que cria e registra um professor adjunto baseado nos parametros.
     * obs1.: o professor não deve existir na coleção de professores.
     * obs2.: a quantidade de horas deve ser positiva.
     *
     * @param nome
     * @param sobrenome
     * @param codigoProfessor
     * @param quantidadeDeHoras
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    fun registrarProfessorAdjunto(
        nome: String,
        sobrenome: String,
        codigoProfessor: Int,
        quantidadeDeHoras: Int
    ) {
        if (quantidadeDeHoras < 0) {
            println("Professor $codigoProfessor não cadastrado: Quantidade de horas de monitoria não pode ser menor que 0 (Zero) :/")
            return
        }
        val adjunto = ProfessorAdjunto(codigoProfessor, nome, sobrenome, quantidadeDeHoras)
        // verifica se existe o professor, senão o cadastra
        cadastraProfessorGenerico(adjunto)
    }

    /**
     * método que cria e registra um professor titular baseado nos parametros.
     * obs1.: o professor não deve existir na coleção de professores.
     *
     * @param nome
     * @param sobrenome
     * @param codigoProfessor
     * @param especialidade
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    fun registrarProfessorTitular(
        nome: String,
        sobrenome: String,
        codigoProfessor: Int,
        especialidade: String
    ) {
        val titular = ProfessorTitular(codigoProfessor, nome, sobrenome, especialidade)
        // verifica se existe o professor, senão o cadastra
        cadastraProfessorGenerico(titular)
    }

    /**
     * método que exclui um professor.
     * obs1.: o professor deve existir na coleção de professores.
     * obs2.: o professor não deve estar vinculado a um curso.
     *
     * @param codigoProfessor
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    fun excluirProfessor(codigoProfessor: Int) {
        professores.firstOrNull {
            it.codigo == codigoProfessor
        }?.let { professorASerRemovido ->
            cursos.forEach {
                if (it.verificarProfessorAlocadoAoCurso(codigoProfessor)) {
                    println("Professor não removido: Professor $codigoProfessor está alocado ao curso ${it.codigo} :/")
                    return
                }
            }
            professores.remove(professorASerRemovido)
            println("Professor removido com sucesso!")
        } ?: println("Professor não removido: Professor $codigoProfessor não existia na base :/")
    }

    /**
     * método que cria e registra um aluno na coleção de alunos.
     * obs1.: o aluno não deve existir na coleção de alunos.
     *
     * @param nome
     * @param sobrenome
     * @param codigoAluno
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    fun matricularAluno(
        nome: String,
        sobrenome: String,
        codigoAluno: Int
    ) {
        val aluno = Aluno(codigoAluno, nome, sobrenome)
        if (alunos.contains(aluno)) {
            println("Aluno $codigoAluno não cadastrado: Código já cadastrado anteriormente :/")
            return
        }
        alunos.add(aluno)
        println("Aluno $codigoAluno cadastrado com sucesso!")
    }

    /**
     * método que cria e registra uma matrícula de aluno em um curso.
     * obs1.: o aluno deve existir na coleção de alunos.
     * obs2.: o curso deve existir na coleção de cursos.
     * obs3.: o curso deve ter vagas disponíveis.
     * obs4.: o aluno não pode estar previamente matriculado no curso.
     *
     * @param codigoAluno
     * @param codigoCurso
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    fun matricularAluno(codigoAluno: Int, codigoCurso: Int) {
        try {
            val aluno = recuperarAluno(codigoAluno)
            val curso = recuperarCurso(codigoCurso)
            if (!adicionarUmAlunoEmUmCurso(curso, aluno)) return
            val matricula = Matricula(aluno, curso)
            matriculas.add(matricula)
            println("Matrícula do Aluno $codigoAluno no curso $codigoCurso cadastrada com sucesso!")
        } catch (exception: DadoNaoEncontradoException) {
            println(exception.message)
        }
    }

    /**
     * método que aloca professores em um curso.
     * obs1.: os professores, titular e adjunto, devem existir na coleção de professores.
     * obs2.: o curso deve existir na coleção de cursos.
     * obs3.: se já existir professor alocado, ele substitui.
     *
     * @param codigoCurso
     * @param codigoProfessorTitular
     * @param codigoProfessorAdjunto
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
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
            println("Professores $codigoProfessorTitular, $codigoProfessorAdjunto não alocados: ${exception.message}")
        }
    }

    /**
     * método privado que auxilia no cadastro de um professor registrando na coleção de professores.
     * obs1.: o professor não deve existir na coleção de professores.
     *
     * @param professor
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    private fun cadastraProfessorGenerico(professor: Professor) {
        if (professores.contains(professor)) {
            println("Professor ${professor.codigo} não cadastrado: Código já cadastrado anteriormente :/")
            return
        }
        professores.add(professor)
        println("Professor ${professor.codigo} cadastrado com sucesso!")
    }

    /**
     * método privado que auxilia na verificação da situação de um aluno se ele já está matriculado em um curso e o adiciona no curso.
     *
     * @param curso
     * @param aluno
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    private fun adicionarUmAlunoEmUmCurso(
        curso: Curso,
        aluno: Aluno
    ): Boolean {
        if (curso.verificarAlunoPertenceAoCurso(aluno)) {
            println("Matrícula não realizada: O aluno ${aluno.codigo} já está matriculado no curso ${curso.codigo} :/")
            return false
        }
        if (!curso.adicionarUmAluno(aluno)) {
            println("Matrícula não realizada: O curso não tem mais vagas :/")
            return false
        }
        return true
    }


    /**
     * método que desmatricula um aluno de um curso.
     * remove o aluno da lista de matriculados do curso.
     * remove a matricula da coleção de matriculas.
     * obs1.: o aluno deve existir na coleção de alunos.
     * obs2.: o curso deve existir na coleção de cursos.
     * obs3.: a matricula deve existir na coleção de matrículas.
     *
     * @param codigoAluno
     * @param codigoCurso
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    fun desvincularAlunoDoCurso(codigoAluno: Int, codigoCurso: Int) {
        try {
            val aluno = recuperarAluno(codigoAluno)
            val curso = recuperarCurso(codigoCurso)
            curso.excluirUmAluno(aluno)
            matriculas.remove(recuperarMatricula(aluno, curso))
            println("Matrícula removida com sucesso!")
        } catch (exception: DadoNaoEncontradoException) {
            println("Matrícula não removida: ${exception.message}")
        }
    }

    /**
     * método que desvincula um professor de um curso.
     * obs1.: o curso deve existir na coleção de cursos.
     * obs2.: o professor deve está associado como titular ou adjunto no curso.
     *
     * @param codigoProfessor
     * @param codigoCurso
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
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
                    println("Professor não desvinculado: Código do professor $codigoProfessor não encontrado no curso $codigoCurso :/")
                }
            }
        } catch (exception: DadoNaoEncontradoException) {
            println("Professor não desvinculado: ${exception.message}")
        }
    }

    /**
     * método privado que auxilia na recuperação do curso.
     *
     * @throws DadoNaoEncontradoException caso não o encontre
     * @param codigoCurso
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    private fun recuperarCurso(codigoCurso: Int): Curso {
        return cursos.firstOrNull {
            it.codigo == codigoCurso
        } ?: throw DadoNaoEncontradoException("Código do curso não encontrado :/")
    }

    /**
     * método privado que auxilia na recuperação do aluno.
     *
     * @throws DadoNaoEncontradoException caso não o encontre
     * @param codigoAluno
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    private fun recuperarAluno(codigoAluno: Int): Aluno {
        return alunos.firstOrNull {
            it.codigo == codigoAluno
        } ?: throw DadoNaoEncontradoException("Código do aluno não encontrado :/")
    }

    /**
     * método privado que auxilia na recuperação da matricula.
     *
     * @throws DadoNaoEncontradoException caso não a encontre
     * @param aluno
     * @param curso
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    private fun recuperarMatricula(
        aluno: Aluno,
        curso: Curso
    ): Matricula {
        return (matriculas.firstOrNull {
            it.aluno == aluno && it.curso == curso
        }
            ?: throw DadoNaoEncontradoException("Matrícula não encontrada :/"))
    }

    /**
     * método privado que auxilia na recuperação do professor titular.
     *
     * @throws DadoNaoEncontradoException caso não o encontre
     * @param codigoProfessor
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    private fun recuperarProfessorTitular(codigoProfessor: Int): ProfessorTitular {
        return professores.firstOrNull {
            it.codigo == codigoProfessor && it is ProfessorTitular
        }?.let { titular ->
            titular as ProfessorTitular
        } ?: throw DadoNaoEncontradoException("Código do titular não encontrado :/")
    }

    /**
     * método privado que auxilia na recuperação do professor adjunto.
     *
     * @throws DadoNaoEncontradoException caso não o encontre
     * @param codigoProfessor
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    private fun recuperarProfessorAdjunto(codigoProfessor: Int): ProfessorAdjunto {
        return professores.firstOrNull {
            it.codigo == codigoProfessor && it is ProfessorAdjunto
        }?.let { adjunto ->
            adjunto as ProfessorAdjunto
        } ?: throw DadoNaoEncontradoException("Código do adjunto não encontrado :/")
    }

    /**
     * método para impressão geral de todas as coleções.
     *
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    fun imprimirResumo() {
        imprimirResumoAlunos()
        imprimirResumoProfessores()
        imprimirResumoMatriculas()
        imprimirResumoCursos()
    }

    /**
     * método para impressão da coleção de cursos.
     *
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    private fun imprimirResumoCursos() {
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

    /**
     * método para impressão da coleção de alunos.
     *
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    private fun imprimirResumoAlunos() {
        println("#########################################")
        println("#          RESUMO DOS ALUNOS            #")
        println("#########################################")
        alunos.forEach {
            println(it)
            println("-----------------------------------------")
        }
        println("#########################################")
    }

    /**
     * método para impressão da coleção de professores.
     *
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    private fun imprimirResumoProfessores() {
        println("#########################################")
        println("#        RESUMO DOS PROFESSORES         #")
        println("#########################################")
        professores.forEach {
            println(it)
            println("-----------------------------------------")
        }
        println("#########################################")
    }

    /**
     * método para impressão da coleção de matrículas.
     *
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    private fun imprimirResumoMatriculas() {
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