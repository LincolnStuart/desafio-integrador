package co.lincolnstuart.models

/**
 * classe abstrata que representa uma generalização de professor.
 *
 * @author Lincoln Stuart
 * @since 25/09/2020
 */
abstract class Professor(
    override val nome: String,
    override val sobrenome: String,
    var tempoDeCasa: Int
) : IPessoa {

    abstract val codigo: Int

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