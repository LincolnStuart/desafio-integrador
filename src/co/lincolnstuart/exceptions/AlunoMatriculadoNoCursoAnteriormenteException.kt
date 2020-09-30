package co.lincolnstuart.exceptions

/**
 * Exceção genérica para ser lançada quando um dado não for encontrado
 *
 * @author Lincoln Stuart
 * @since 25/09/2020
 */
class AlunoMatriculadoNoCursoAnteriormenteException(mensagem: String = "Aluno já matriculado no curso :/") :
    Exception(mensagem)