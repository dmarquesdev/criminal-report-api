package br.ufsc.inf.lapesd.criminal.report.api.service;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufsc.inf.lapesd.criminal.report.api.model.CriminalReport;
import br.ufsc.inf.lapesd.criminal.report.api.model.Person;
import br.ufsc.inf.lapesd.criminal.report.api.repository.ReportRepository;

@Component
public class ReportService {
    
    @Autowired
    private ReportRepository reportRepository;

    public CriminalReport loadReport(String idReport) throws FileNotFoundException {
        return reportRepository.loadCriminalReport(idReport);
    }
    
    public List<CriminalReport> loadAllReports() throws FileNotFoundException {
        return reportRepository.loadAllReports();
    }
    
    public List<CriminalReport> loadAllReportsByCategory(String category) throws FileNotFoundException {
        return reportRepository.loadAllReports(category);
    }
    
    public List<CriminalReport> loadAllReportsByYear(String year) throws FileNotFoundException {
        return reportRepository.loadAllReportsByYear(year);
    }
    
    public java.util.Set<String> loadAllCategories() {
    	return reportRepository.getAllReportCategories();
    }
    
    public java.util.Set<String> loadAllSkinColors() {
    	return reportRepository.getAllSkinColors();
    }
    
    public java.util.Set<String> loadAllEducations() {
    	return reportRepository.getAllEducations();
    }
    
    
    public List<CriminalReport> loadAllReportsFilteringURL(String url) throws FileNotFoundException {
    	
    	Map<Method, String> mapMethodValue  = new HashMap<Method, String>();
    	
    	String[] parameters = url.substring(url.indexOf("?")+1).split("&");
    	
    	try {
	    	for (String param : parameters) {
				String[] p = param.split("=");
				
				switch (p[0].toLowerCase()) {
				
					case ("idreport"): 
						mapMethodValue.put(CriminalReport.class.getMethod("getIdBO"), p[1]);					
						break;
					
					case ("year"): 
						mapMethodValue.put(CriminalReport.class.getMethod("getAno"), p[1]);
						break;
					
					case ("category"):
					case ("criminalreportcategory"): 
						mapMethodValue.put(CriminalReport.class.getMethod("getCategoria"), p[1]);
						break;
					
					case ("skin"):
					case ("skinColor"):
						mapMethodValue.put(Person.class.getMethod("getCutis"), p[1]);
						break;
					
					case ("gender"):
						mapMethodValue.put(Person.class.getMethod("getSexo"), p[1]);
						break;				
					
					case ("education"):
						mapMethodValue.put(Person.class.getMethod("getInstrucao"), p[1]);
						break;
					
					case ("rg"):
					case ("personid"):
					case ("idnumber"):
						mapMethodValue.put(Person.class.getMethod("getRg"), p[1]);
						break;
					
					case ("age"):
						mapMethodValue.put(Person.class.getMethod("getIdade"), p[1]);
						break;
						
					case ("name"):
					case ("personName"):
						mapMethodValue.put(Person.class.getMethod("getNome"), p[1]);
						break;
						
					default:
						throw new IllegalArgumentException(p[0]+" is not a valid argument");	
				}
			}
    	}
	    catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Error");
		}
    	
    	return reportRepository.loadAllReportsFiltering(mapMethodValue);
    }
    
    public List<CriminalReport> loadAllPoints() throws FileNotFoundException {
        return reportRepository.loadAllPoints();
    }

}
