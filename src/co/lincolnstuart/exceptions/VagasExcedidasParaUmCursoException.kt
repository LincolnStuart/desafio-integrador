package co.lincolnstuart.exceptions

/**
 * Exceção genérica para ser lançada quando um dado não for encontrado
 *
 * @author Lincoln Stuart
 * @since 25/09/2020
 */
class VagasExcedidasParaUmCursoException(mensagem: String = "Não existem mais vagas no curso :/") : Exception(mensagem)