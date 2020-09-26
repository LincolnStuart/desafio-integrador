package co.lincolnstuart.Utils

import co.lincolnstuart.Models.*


val mockProfessoresTitulares = listOf(
    ProfessorTitular(Professor.getCodigoDisponivel(),"Cesar", "Nascimento", "Android/Kotlin"),
    ProfessorTitular(Professor.getCodigoDisponivel(),"Ricardo", "Lecheta", "Flutter"),
    ProfessorTitular(Professor.getCodigoDisponivel(),"Eduardo", "Pires", "DotNet"),
    ProfessorTitular(2,"Lebron", "James", "3 pontos")
)
val mockProfessoresAdjuntos = listOf(
    ProfessorAdjunto(Professor.getCodigoDisponivel(),"Eduardo", "Misina", 50),
    ProfessorAdjunto(Professor.getCodigoDisponivel(),"Steve", "Wozniak", 5),
    ProfessorAdjunto(Professor.getCodigoDisponivel(),"Sancho", "Pança", -100)
)
val mockCursos = listOf(
    Curso(Curso.getCodigoDisponivel(), "Android", 1),
    Curso(Curso.getCodigoDisponivel(), "Flutter", 1),
    Curso(Curso.getCodigoDisponivel(), "Pilotando Carros de F1", -30),
    Curso(2, "Paraquedismo", 2),
    Curso(Curso.getCodigoDisponivel(), "Dotnet", 1)
)
val mockAlunos = listOf(
    Aluno(Aluno.getCodigoDisponivel(), "Lincoln", "Stuart"),
    Aluno(1, "João", "Ninguém"),
    Aluno(Aluno.getCodigoDisponivel(), "Antonielle", "Menezes"),
    Aluno(Aluno.getCodigoDisponivel(), "Bartolomeu", "Menezes"),
    Aluno(Aluno.getCodigoDisponivel(), "Bliss", "Menezes"),
    Aluno(Aluno.getCodigoDisponivel(), "Salomé", "Menezes")
)