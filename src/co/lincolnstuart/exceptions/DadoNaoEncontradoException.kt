package co.lincolnstuart.exceptions

/**
 * Exceção genérica para ser lançada quando um dado não for encontrado
 *
 * @author Lincoln Stuart
 * @since 25/09/2020
 */
class DadoNaoEncontradoException(mensagem: String = "Código não encontrado :/") : Exception(mensagem)