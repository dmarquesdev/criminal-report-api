package br.ufsc.inf.lapesd.criminal.report.api.endpoint;

import java.io.FileNotFoundException;
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

import br.ufsc.inf.lapesd.criminal.report.api.model.Person;
import br.ufsc.inf.lapesd.criminal.report.api.service.PersonService;

@Path("person")
public class PersonEndpoint {

	@Autowired
    private PersonService personService;
    
    @Context
    private UriInfo uriInfo;
    
    
    @GET
    @Path("rg/{idPerson}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadPersonById(@PathParam("idPerson") String idPerson) {
       
        try {
            Person person = personService.loadPersonByRg(idPerson);
            if(person != null) {
            	setPersonId(person);
            	return Response.ok(new Gson().toJson(person)).build();
            } 
            else {
                return Response.status(Status.NOT_FOUND).build();
            }
        } catch (FileNotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("gender/{gender}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadPersonsByGenre(@PathParam("gender") String gender) {
       
        try {
            List<Person> persons = personService.loadAllPersonsByGender(gender);
            setPersonId(persons);
            return Response.ok(new Gson().toJson(persons)).build();
        } catch (FileNotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("skin/{skinColor}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadPersonsBySkinColor(@PathParam("skinColor") String skinColor) {
       
        try {
            List<Person> persons = personService.loadAllPersonsBySkinColor(skinColor);
            setPersonId(persons);
            return Response.ok(new Gson().toJson(persons)).build();
        } catch (FileNotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("education/{education}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadPersonsByEducation(@PathParam("education") String education) {
       
        try {
            List<Person> persons = personService.loadAllPersonsByEducation(education);
            setPersonId(persons);
            return Response.ok(new Gson().toJson(persons)).build();
        } catch (FileNotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    
    private void setPersonId(Person person) {
    	person.setId(uriInfo.getRequestUri().toASCIIString());
    }
    
    private void setPersonId(List<Person> persons) {
    	for (Person p: persons) {
    		p.setId(uriInfo.getRequestUri().toASCIIString());
    	}
    }
    
}
