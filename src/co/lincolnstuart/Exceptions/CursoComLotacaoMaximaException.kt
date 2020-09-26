package co.lincolnstuart.Exceptions

class CursoComLotacaoMaximaException(mensagem: String = "Curso não permite cadastrar mas nenhum aluno :/")
    : Exception(mensagem)