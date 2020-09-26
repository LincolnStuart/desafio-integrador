package co.lincolnstuart.Models

import co.lincolnstuart.Exceptions.CursoComLotacaoMaximaException

class Curso(val codigo: Int,
            var nome: String,
            var quantidadeMaximaDeAlunos: Int,
            var titular: ProfessorTitular? = null,
            var adjunto: ProfessorAdjunto? = null){

    private val matriculados = mutableListOf<Aluno>()

    companion object GeradorDeCodigoUnico{
        private var _codigoDisponivel = 1
        fun getCodigoDisponivel() = _codigoDisponivel++
    }

    fun adicionaUmAluno(umAluno: Aluno){
        if(quantidadeMaximaDeAlunos > matriculados.size)
            matriculados.add(umAluno)
        else
            throw CursoComLotacaoMaximaException()
    }

    fun excluirUmAluno(umAluno: Aluno){
        matriculados.remove(umAluno)
    }

    fun alunoPertenceAoCurso(umAluno: Aluno): Boolean{
        return matriculados.filter {
            it == umAluno
        }.any()
    }

    fun getMatriculados(): List<Aluno>{
        return matriculados.toList()
    }

    override fun equals(other: Any?): Boolean {
        return (other as? Curso)?.let{
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