package br.ufsc.inf.lapesd.criminal.report.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;

public class Person {
	
	@SerializedName("@type")
    protected List<String> type = new ArrayList<>();
	
	@SerializedName("@id")
	private String id;
    private String rg;
    private String nome;
    private String tipoEnvolvimento;
    private String naturalidade;
    private String nacionalidade;
    private String sexo;
    private String dataNascimento;
    private String idade;
    private String estadoCivil;
    private String instrucao;
    private String profissao;
    private String cutis;
    private String naturezasEnvolvidas;
    private String nodoId = UUID.randomUUID().toString();
    
    
    public List<String> getType() {
        return type;
    }

    public void applySemantics() {        
        this.type.add("foaf:Person\", \"schema:Person");


    }

    public String getId() {
		return rg;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoEnvolvimento() {
        return tipoEnvolvimento;
    }

    public void setTipoEnvolvimento(String tipoEnvolvimento) {
        this.tipoEnvolvimento = tipoEnvolvimento;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getInstrucao() {
        return instrucao;
    }

    public void setInstrucao(String instrucao) {
        this.instrucao = instrucao;
    }

    public String getCutis() {
        return cutis;
    }

    public void setCutis(String cutis) {
        this.cutis = cutis;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getNaturezasEnvolvidas() {
        return naturezasEnvolvidas;
    }

    public void setNaturezasEnvolvidas(String naturezasEnvolvidas) {
        this.naturezasEnvolvidas = naturezasEnvolvidas;
    }

	public String getLabel() {
		return nome;
	}

	public String getNodoId() {
		return nodoId;
	}

	

}
