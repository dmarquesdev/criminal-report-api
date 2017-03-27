package br.ufsc.inf.lapesd.criminal.report.api.endpoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import br.ufsc.inf.lapesd.criminal.report.api.model.CriminalReport;
import br.ufsc.inf.lapesd.criminal.report.api.service.ReportService;

@Path("map")
public class MapEndpoint {
    
    @Autowired
    private ReportService reportService;
    
    @Context
    private UriInfo uriInfo;
    

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response loadMap() {
    	
    	String url;
		try {
			url = URLDecoder.decode(uriInfo.getRequestUri().toASCIIString(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			url = uriInfo.getRequestUri().toASCIIString();
		}
    	List<CriminalReport> loadReport;
    	String map;
    	
    	try {
			loadReport = reportService.loadAllReportsFilteringURL(url);
			 map = buildMapLocations(loadReport);
	        
		} 
    	catch (IOException io) {
    		return Response.status(Status.NOT_FOUND).build();
		}
    	catch (IllegalArgumentException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    	return Response.ok(map).build();
    } 
    
    
    private String buildMapLocations(List<CriminalReport> loadAllReports) throws IOException {
    	List<String> locationsList = new ArrayList<>();
        String locations = "";
        
        for (CriminalReport criminalReport : loadAllReports) {
        	if(criminalReport.getLocal() != null) {
        		locations = criminalReport.getLocal().toString().replace("'", "");
        		locations = locations.toString().replace("\"", "");
        		locationsList.add(String.format("'%s'", locations));        		
        	}
        }       
        
        InputStream in = this.getClass().getResourceAsStream("/map.html");
        String map = read(in);
        map = map.replace("{locations}", locationsList.toString());
        return map;
    }
    
    

    public static String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

}
