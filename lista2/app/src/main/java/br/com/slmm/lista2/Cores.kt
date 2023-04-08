package br.com.slmm.lista2


class Cores (nome: String, valor: String){
    var nome: String = ""
        private set
    var valor: String = ""
        private set

    init {
        require(nome.trim().length > 0){
            "Informe um nome"
        }
        require( valor.trim().length > 0){
            "informe um valor"
        }
        this.nome = nome
        this.valor = valor
    }
}