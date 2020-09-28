package co.lincolnstuart.Models

class Curso(
    val codigo: Int,
    var nome: String,
    var quantidadeMaximaDeAlunos: Int,
    var titular: ProfessorTitular? = null,
    var adjunto: ProfessorAdjunto? = null
) {

    private val matriculados = mutableListOf<Aluno>()

    companion object GeradorDeCodigoUnico {
        private var _codigoDisponivel = 1
        fun getCodigoDisponivel() = _codigoDisponivel++
    }

    fun adicionarUmAluno(umAluno: Aluno): Boolean {
        if (quantidadeMaximaDeAlunos > matriculados.size) {
            matriculados.add(umAluno)
            return true
        } else
            return false
    }

    fun excluirUmAluno(umAluno: Aluno) {
        matriculados.remove(umAluno)
    }

    fun verificarAlunoPertenceAoCurso(umAluno: Aluno): Boolean {
        return matriculados.filter {
            it == umAluno
        }.any()
    }

    fun recuperarMatriculados(): List<Aluno> {
        return matriculados.toList()
    }

    fun verificarProfessorAlocadoAoCurso(codigoProfessor: Int): Boolean{
        titular?.let {
            if(it.codigo == codigoProfessor)
                return true
        }
        adjunto?.let {
            if(it.codigo == codigoProfessor)
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