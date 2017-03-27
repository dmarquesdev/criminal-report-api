package br.ufsc.inf.lapesd.criminal.report.api.endpoint;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import br.ufsc.inf.lapesd.criminal.report.api.model.CriminalReport;
import br.ufsc.inf.lapesd.criminal.report.api.service.ReportService;

@Path("points")
public class PointsEndpoint {

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
        	
//        	if(url.contains("points?"))
//        		loadReport = reportService.loadAllReportsFilteringURL(url);
//        	else 
//        		loadReport = reportService.loadAllReports();
                loadReport = reportService.loadAllPoints();
                
            return Response.ok(new Gson().toJson(loadReport)).build();
        } catch (FileNotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
        catch (IllegalArgumentException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }	
    }

}
