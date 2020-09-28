package co.lincolnstuart.models

/**
 * Classe que representa um curso e manipula principalmente alunos matriculas e professores.
 *
 * @author Lincoln Stuart
 * @since 25/09/2020
 */
class Curso(
    val codigo: Int,
    var nome: String,
    var quantidadeMaximaDeAlunos: Int,
    var titular: ProfessorTitular? = null,
    var adjunto: ProfessorAdjunto? = null
) {

    private val matriculados = mutableListOf<Aluno>()

    /**
     * singleton para simular um identity
     *
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    companion object GeradorDeCodigoUnico {
        private var codigoDisponivel = 1
        fun getCodigoDisponivel() = codigoDisponivel++
    }

    /**
     * método que adiciona um aluno ao curso, se tiver vaga
     * @param umAluno
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    fun adicionarUmAluno(umAluno: Aluno): Boolean {
        return if (quantidadeMaximaDeAlunos > matriculados.size) {
            matriculados.add(umAluno)
            true
        } else
            false
    }

    /**
     * método que exclui um aluno de um curso caso exista
     * @param umAluno
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    fun excluirUmAluno(umAluno: Aluno) {
        matriculados.remove(umAluno)
    }

    /**
     * método que verifica se o aluno pertence ao curso atual
     * @param umAluno
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    fun verificarAlunoPertenceAoCurso(umAluno: Aluno): Boolean {
        return matriculados.filter {
            it == umAluno
        }.any()
    }

    /**
     * getter de matriculados
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    fun recuperarMatriculados(): List<Aluno> {
        return matriculados.toList()
    }

    /**
     * método que verifica se um professor, seja ele titular ou adjunto, está vinculado no curso
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    fun verificarProfessorAlocadoAoCurso(codigoProfessor: Int): Boolean {
        titular?.let {
            if (it.codigo == codigoProfessor)
                return true
        }
        adjunto?.let {
            if (it.codigo == codigoProfessor)
                return true
        }
        return false
    }

    override fun equals(other: Any?): Boolean {
        return (other as? Curso)?.let {
            codigo == it.codigo
        } ?: false
    }

    override fun hashCode(): Int {
        return codigo.hashCode()
    }

    override fun toString(): String {
        return "Curso $codigo: $nome (Lotação: ${matriculados.size}/$quantidadeMaximaDeAlunos)."
    }

}