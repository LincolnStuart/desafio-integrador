package co.lincolnstuart.Models

abstract class Professor(
    override val nome: String,
    override val sobrenome: String,
    var tempoDeCasa: Int
) : IPessoa {

    abstract val codigo: Int

    companion object GeradorDeCodigoUnico {
        private var _codigoDisponivel = 1
        fun getCodigoDisponivel() = _codigoDisponivel++
    }

    override fun equals(other: Any?): Boolean {
        return (other as? Professor)?.let {
            codigo == it.codigo
        } ?: false
    }

    override fun hashCode(): Int {
        return codigo.hashCode()
    }

    override fun toString(): String {
        return "$codigo: $nome $sobrenome, $tempoDeCasa ano(s) de casa"
    }

}