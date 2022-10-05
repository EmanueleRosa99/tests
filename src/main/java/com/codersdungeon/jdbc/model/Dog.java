package com.codersdungeon.jdbc.model;

public class Dog {
    public int id;
    public String nome;
    public String razza;

    public Dog() {
    }

    public Dog(String nome, String razza) {
        this.nome = nome;
        this.razza = razza;
    }

    public Dog(int id, String nome, String razza) {
        this.id = id;
        this.nome = nome;
        this.razza = razza;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRazza() {
        return razza;
    }

    public void setRazza(String razza) {
        this.razza = razza;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", razza='" + razza + '\'' +
                '}';
    }
}
