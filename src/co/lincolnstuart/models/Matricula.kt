package co.lincolnstuart.models

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * classe que representa uma matricula de um aluno em um curso.
 *
 * @author Lincoln Stuart
 * @since 25/09/2020
 */
class Matricula(
    val aluno: Aluno,
    val curso: Curso
) {

    private val data: LocalDateTime = LocalDateTime.now()

    override fun equals(other: Any?): Boolean {
        return (other as? Matricula)?.let {
            aluno == it.aluno && curso == it.curso
        } ?: false
    }

    override fun hashCode(): Int {
        return aluno.hashCode() + curso.hashCode()
    }

    override fun toString(): String {
        return "Matrícula efetuada na data " +
                "${data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"))}. " +
                "Pelo $aluno No $curso."
    }

}