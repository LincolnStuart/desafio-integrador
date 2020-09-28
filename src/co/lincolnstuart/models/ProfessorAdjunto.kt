package co.lincolnstuart.models

/**
 * classe que representa um professor adjunto, herdando do professor
 *
 * @author Lincoln Stuart
 * @since 25/09/2020
 */
class ProfessorAdjunto(
    override val codigo: Int,
    nome: String,
    sobrenome: String,
    var quantidadeDeHorasDeMonitoria: Int,
    tempoDeCasa: Int = 0
) : Professor(nome, sobrenome, tempoDeCasa) {

    override fun toString(): String {
        return "Professor Adjunto ${super.toString()}, $quantidadeDeHorasDeMonitoria hora(s) de monitoria."
    }

}