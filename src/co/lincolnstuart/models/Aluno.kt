package co.lincolnstuart.models

/**
 * classe que representa um aluno que implementa uma pessoa.
 *
 * @author Lincoln Stuart
 * @since 25/09/2020
 */
class Aluno(
    val codigo: Int,
    override val nome: String,
    override val sobrenome: String
) : IPessoa {

    /**
     * singleton para simular um identity.
     *
     * @author Lincoln Stuart
     * @since 25/09/2020
     */
    companion object GeradorDeCodigoUnico {
        private var codigoDisponivel = 1
        fun getCodigoDisponivel() = codigoDisponivel++
    }

    override fun equals(other: Any?): Boolean {
        return (other as? Aluno)?.let {
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