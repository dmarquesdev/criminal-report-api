package br.ufsc.inf.lapesd.criminal.report.api.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class CriminalReport {

    @SerializedName("@context")
    protected Map<String, String> context = new LinkedHashMap<>();

    @SerializedName("@id")
    private String id;

    protected String idBO;
    protected String numero;
    protected String idDelegacia;
    protected String tipoBoletim;
    protected String dependencia;
    protected String local;
    protected String lat;
    protected String lon;
    protected String tipoLocal;
    protected String circunscricao;
    protected String dataOcorrencia;
    protected String turnoOcorrencia;
    protected String dataComunicacao;
    protected String horaComunicacao;
    protected String dataElaboracao;
    protected String horaElaboracao;
    protected String flagrante;
    protected String examesRequisitados;
    protected String solucao;
    protected List<Person> partesEnvolvidas = new ArrayList<>();
    protected List<CriminalReportType> naturezas = new ArrayList<>();
    protected String numeroBoPrincipal;
    protected String anoBoPrincipal;
    protected String nomeDelegaciaBoPrincipal;
    protected String categoria;
    protected String nome;

    protected String location;

    public CriminalReport(String idBO, String lat, String lon, String local, 
            String dataOcorrencia, String categoria, String nome) {
        this.idBO = idBO;
        this.lat = lat;
        this.lon = lon;
        this.categoria = categoria;
        this.local = local;
        this.dataOcorrencia = dataOcorrencia;
        this.nome = nome;
    }

    public CriminalReport() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void applySemantics() {

        //Ontologies
        this.context.put("ssp", "http://exemple.com.br/ssp-ontology/");
        this.context.put("dbo", "http://dbpedia.org/ontology/");
        this.context.put("schema", "https://schema.org/");
        this.context.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");

        //Criminal Report
        this.context.put("idBO", "ssp:reportID");
        this.context.put("numero", "ssp:reportNumber");
        this.context.put("idDelegacia", "ssp:policeStationID");
        this.context.put("tipoBoletim", "ssp:reportType");
        this.context.put("dependencia", "ssp:dependency");
        this.context.put("local", "dbo:address");
        this.context.put("tipoLocal", "ssp:addressType");
        this.context.put("circunscricao", "ssp:circumscription");
        this.context.put("dataOcorrencia", "ssp:occurenceDate");
        this.context.put("turnoOcorrencia", "ssp:occurenceShift");
        this.context.put("dataComunicacao", "ssp:caommunicationDate");
        this.context.put("horaComunicacao", "ssp:communicationTime");
        this.context.put("dataElaboracao", "ssp:fulfillmentDate");
        this.context.put("horaElaboracao", "ssp:fulfillmentTime");
        this.context.put("flagrante", "ssp:flagrant");
        this.context.put("examesRequisitados", "ssp:orderedTests");
        this.context.put("solucao", "ssp:solutino");
        this.context.put("numeroBoPrincipal", "ssp:mainReportNumber");
        this.context.put("anoBoPrincipal", "ssp:mainReportYear");
        this.context.put("nomeDelegaciaBoPrincipal", "ssp:mainReportPoliceStation");
        this.context.put("categoria", "ssp:criminalReportCategory");
        this.context.put("location", "schema:location");

        //Person
        this.context.put("partesEnvolvidas", "dbo:victims");
        this.context.put("rg", "dbo:idNumber");
        this.context.put("nome", "foaf:name");
        this.context.put("tipoEnvolvimento", "ssp:involvementType");
        this.context.put("naturalidade", "dbo:birthPlace");
        this.context.put("nacionalidade", "dbo:nationality");
        this.context.put("sexo", "foaf:gender");
        this.context.put("dataNascimento", "dbo:birthYear");
        this.context.put("idade", "foaf:age");
        this.context.put("estadoCivil", "ssp:maritalStatus");
        this.context.put("instrucao", "dbo:education");
        this.context.put("profissao", "dbo:occupation");
        this.context.put("cutis", "dbo:skinColor");

        for (Person persons : this.partesEnvolvidas) {
            persons.applySemantics();
        }

        //Nature of Crime
        this.context.put("naturezas", "ssp:natureOfCrime");
        this.context.put("descricao", "ssp:description");
        this.context.put("especie", "ssp:type");
    }

    public String getAno() {
        String year = "";
        if (this.dataOcorrencia != null) {
            if (dataOcorrencia.length() == 4) {
                year = dataOcorrencia;
            } else {
                year = dataOcorrencia.substring(dataOcorrencia.length() - 4);
            }
        }
        return year;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public void setTipoBoletim(String tipoBoletim) {
        this.tipoBoletim = tipoBoletim;
    }

    public String getTipoBoletim() {
        return tipoBoletim;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTipoLocal() {
        return tipoLocal;
    }

    public void setTipoLocal(String tipoLocal) {
        this.tipoLocal = tipoLocal;
    }

    public String getCircunscricao() {
        return circunscricao;
    }

    public void setCircunscricao(String circunscricao) {
        this.circunscricao = circunscricao;
    }

    public String getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(String dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public String getDataComunicacao() {
        return dataComunicacao;
    }

    public void setDataComunicacao(String dataComunicacao) {
        this.dataComunicacao = dataComunicacao;
    }

    public String getHoraComunicacao() {
        return horaComunicacao;
    }

    public void setHoraComunicacao(String horaComunicacao) {
        this.horaComunicacao = horaComunicacao;
    }

    public String getDataElaboracao() {
        return dataElaboracao;
    }

    public void setDataElaboracao(String dataElaboracao) {
        this.dataElaboracao = dataElaboracao;
    }

    public String getHoraElaboracao() {
        return horaElaboracao;
    }

    public void setHoraElaboracao(String horaElaboracao) {
        this.horaElaboracao = horaElaboracao;
    }

    public String getFlagrante() {
        return flagrante;
    }

    public void setFlagrante(String flagrante) {
        this.flagrante = flagrante;
    }

    public String getExamesRequisitados() {
        return examesRequisitados;
    }

    public void setExamesRequisitados(String examesRequisitados) {
        this.examesRequisitados = examesRequisitados;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public String getIdDelegacia() {
        return idDelegacia;
    }

    public void setIdDelegacia(String idDelegacia) {
        this.idDelegacia = idDelegacia;
    }

    public String getTurnoOcorrencia() {
        return turnoOcorrencia;
    }

    public void setTurnoOcorrencia(String turnoOcorrencia) {
        this.turnoOcorrencia = turnoOcorrencia;
    }

    public void setIdBO(String idBO) {
        this.idBO = idBO;
    }

    public String getIdBO() {
        return idBO;
    }

    public String getNumeroBoPrincipal() {
        return numeroBoPrincipal;
    }

    public void setNumeroBoPrincipal(String numeroBoPrincipal) {
        this.numeroBoPrincipal = numeroBoPrincipal;
    }

    public String getAnoBoPrincipal() {
        return anoBoPrincipal;
    }

    public void setAnoBoPrincipal(String anoBoPrincipal) {
        this.anoBoPrincipal = anoBoPrincipal;
    }

    public String getNomeDelegaciaBoPrincipal() {
        return nomeDelegaciaBoPrincipal;
    }

    public void setNomeDelegaciaBoPrincipal(String nomeDelegaciaBoPrincipal) {
        this.nomeDelegaciaBoPrincipal = nomeDelegaciaBoPrincipal;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<Person> getPartesEnvolvidas() {
        return partesEnvolvidas;
    }

    public void setPartesEnvolvidas(List<Person> partesEnvolvidas) {
        this.partesEnvolvidas = partesEnvolvidas;
    }

    public List<CriminalReportType> getNaturezas() {
        return naturezas;
    }

    public void setNaturezas(List<CriminalReportType> naturezas) {
        this.naturezas = naturezas;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getNome() {
        if (nome == null && !partesEnvolvidas.isEmpty()) {
            nome = partesEnvolvidas.get(0).getNome();
        }
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
