package br.ufsc.inf.lapesd.criminal.report.api.service;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufsc.inf.lapesd.criminal.report.api.model.Person;
import br.ufsc.inf.lapesd.criminal.report.api.repository.ReportRepository;

@Component
public class PersonService {

	@Autowired
    private ReportRepository reportRepository;

    public Person loadPersonByRg(String idPerson) throws FileNotFoundException {
        return reportRepository.loadPersonByRg(idPerson);
    }
    
    public List<Person> loadAllPersonsByGender(String gender) throws FileNotFoundException {
        return reportRepository.loadAllPersonsByGender(gender);
    }
    
    public List<Person> loadAllPersonsBySkinColor(String skinColor) throws FileNotFoundException {
        return reportRepository.loadAllPersonsBySkinColor(skinColor);
    }
    
    public List<Person> loadAllPersonsByEducation(String education) throws FileNotFoundException {
        return reportRepository.loadAllPersonsByEducation(education);
    }
   
}
