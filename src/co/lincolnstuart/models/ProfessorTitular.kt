package co.lincolnstuart.models

/**
 * classe que representa um professor titular, herdando de professor
 *
 * @author Lincoln Stuart
 * @since 25/09/2020
 */
class ProfessorTitular(
    override val codigo: Int,
    nome: String,
    sobrenome: String,
    var especialidade: String,
    tempoDeCasa: Int = 0
) : Professor(nome, sobrenome, tempoDeCasa) {

    override fun toString(): String {
        return "Professor Titular ${super.toString()}, especialista em $especialidade."
    }

}