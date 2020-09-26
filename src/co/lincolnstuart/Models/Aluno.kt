package co.lincolnstuart.Models

import co.lincolnstuart.Interfaces.Pessoa

class Aluno(val codigo: Int,
            override val nome: String,
            override val sobrenome: String)
    : Pessoa {

    companion object GeradorDeCodigoUnico{
        private var _codigoDisponivel = 1
        fun getCodigoDisponivel() = _codigoDisponivel++
    }

    override fun equals(other: Any?): Boolean {
        return (other as? Aluno)?.let{
            codigo == it.codigo
        } ?: false
    }

    override fun hashCode(): Int {
        return codigo.hashCode()
    }

    override fun toString(): String {
        return "Aluno $codigo: $nome $sobrenome"
    }

}