package br.ufsc.inf.lapesd.criminal.report.api.endpoint;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import br.ufsc.inf.lapesd.criminal.report.api.model.CriminalReport;
import br.ufsc.inf.lapesd.criminal.report.api.model.Person;
import br.ufsc.inf.lapesd.criminal.report.api.service.ReportService;

@Path("report")
public class CriminalReportEndpoint {

    @Autowired
    private ReportService reportService;
    
    @Context
    private UriInfo uriInfo;

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadCriminalReportsFilters() {
    	
    	String url;
		try {
			url = URLDecoder.decode(uriInfo.getRequestUri().toASCIIString(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			url = uriInfo.getRequestUri().toASCIIString();
		}
    	List<CriminalReport> loadReport;
    	
        try {
        	
        	if(url.contains("report?"))
        		loadReport = reportService.loadAllReportsFilteringURL(url);
        	else 
        		loadReport = reportService.loadAllReports();
        	
        	//Set Report ID
        	for (CriminalReport criminalReport : loadReport)
                criminalReport.setId(uriInfo.getRequestUri().toASCIIString().replaceFirst("/report?.*", "/report/") + criminalReport.getIdBO());
        	
        	//Set Person ID if not null
        	for (CriminalReport criminalReport : loadReport) {				
        		for (Person p : criminalReport.getPartesEnvolvidas()) {
        			if(p.getId() != null)
        				p.setId(uriInfo.getRequestUri().toASCIIString().replaceFirst("/report?.*", "/report?rg=") + p.getId());
        		}
			}
            return Response.ok(new Gson().toJson(loadReport)).build();
        } catch (FileNotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
        catch (IllegalArgumentException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }	
    }
    
    
    @GET
    @Path("{idReport}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadCriminalReport(@PathParam("idReport") String idReport) {
       
        try {
            CriminalReport loadReport = reportService.loadReport(idReport);
            loadReport.setId(uriInfo.getRequestUri().toASCIIString());
            for (Person p : loadReport.getPartesEnvolvidas()) {
            	if(p.getId() != null)
            		p.setId(uriInfo.getRequestUri().toASCIIString().replaceFirst("/report/.*", "/person/rg/") + p.getId());
			}
            Response response = Response.ok(new Gson().toJson(loadReport)).build();
//            response.getHeaders().add("Access-Control-Allow-Origin", "*");
            return response;
        } catch (FileNotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    
    
    @GET
    @Path("criminalReportCategories")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadCategories() {       
        java.util.Set<String> allCategories = reportService.loadAllCategories();
        return Response.ok(new Gson().toJson(allCategories)).build();
    }
    
    @GET
    @Path("skinColors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadSkinColors() {       
        java.util.Set<String> allColors = reportService.loadAllSkinColors();
        return Response.ok(new Gson().toJson(allColors)).build();
    }
    
    @GET
    @Path("educations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadEducations() {       
        java.util.Set<String> allEducations = reportService.loadAllEducations();
        return Response.ok(new Gson().toJson(allEducations)).build();
    }
    

}
