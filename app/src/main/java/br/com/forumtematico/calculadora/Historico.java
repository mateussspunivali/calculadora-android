package br.com.forumtematico.calculadora;

public class Historico implements java.io.Serializable {
    private int id;
    private double primeiro_numero;
    private double segundo_numero;
    private String operecao;
    private double resultado;

    public Historico() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrimeiro_numero() {
        return primeiro_numero;
    }

    public void setPrimeiro_numero(double primeiro_numero) {
        this.primeiro_numero = primeiro_numero;
    }

    public double getSegundo_numero() {
        return segundo_numero;
    }

    public void setSegundo_numero(double segundo_numero) {
        this.segundo_numero = segundo_numero;
    }

    public String getOperecao() {
        return operecao;
    }

    public void setOperecao(String operecao) {
        this.operecao = operecao;
    }

    public double getResultado() {
        return resultado;
    }

    public void setResultado(double resultado) {
        this.resultado = resultado;
    }
}
