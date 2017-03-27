package br.ufsc.inf.lapesd.criminal.report.api.repository;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import br.ufsc.inf.lapesd.criminal.report.api.model.CriminalReport;
import br.ufsc.inf.lapesd.criminal.report.api.model.Person;

@Component
public class ReportRepository {
    
    private List<String> list = new ArrayList<>();
    
    private java.util.Set<String> categories = new java.util.HashSet<String>();
    private java.util.Set<String> skinColors = new java.util.HashSet<String>();
    private java.util.Set<String> educations = new java.util.HashSet<String>();
    
    @Value("${dataDir}")
    private File dataDir;
    
    public CriminalReport loadCriminalReportWithoutSemantics(String idReport) throws FileNotFoundException {
        FileInputStream stream;
        try {
            stream = new FileInputStream(new File(dataDir, idReport + ".json"));
            InputStreamReader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            return new Gson().fromJson(reader, CriminalReport.class);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error");
        }
        
    }
    
    public CriminalReport loadCriminalReport(String idReport) throws FileNotFoundException {
        CriminalReport bo = loadCriminalReportWithoutSemantics(idReport);
        bo.applySemantics();
        return bo;
    }
    
    public List<CriminalReport> loadAllReports() throws FileNotFoundException {
        List<CriminalReport> listOfCriminalReport = new ArrayList<>();
        for (String idBO : list) {
            CriminalReport criminalReport = new CriminalReport();
            criminalReport.setIdBO(idBO);
            criminalReport.applySemantics();
            listOfCriminalReport.add(criminalReport);
        }
        return listOfCriminalReport;
    }
    
    public List<CriminalReport> loadAllReports(String category) throws FileNotFoundException {
        List<CriminalReport> listOfCriminalReport = new ArrayList<>();
        for (String idBO : list) {
            CriminalReport report = loadCriminalReport(idBO);
            if (category.equalsIgnoreCase(report.getCategoria())) {
                listOfCriminalReport.add(report);
            }
        }
        return listOfCriminalReport;
    }
    
    public List<CriminalReport> loadAllReportsByYear(String year) throws FileNotFoundException {
        List<CriminalReport> listOfCriminalReport = new ArrayList<>();
        for (String idBO : list) {
            CriminalReport report = loadCriminalReport(idBO);
            if (year.equalsIgnoreCase(report.getAno())) {
                listOfCriminalReport.add(report);
            }
        }
        return listOfCriminalReport;
    }
    
    public List<CriminalReport> loadAllReportsFiltering(Map<Method, String> mapMethodValue, boolean semantics) throws FileNotFoundException {
        List<CriminalReport> listOfCriminalReport = new ArrayList<>();
        String methodReturnp = "", filterValuep = "";
        try {
            
            bo:
            for (String idBO : list) {
                CriminalReport report = (semantics) ? loadCriminalReport(idBO) : loadCriminalReportWithoutSemantics(idBO);
                
                boolean flag = false;
                
                map:
                for (Method method : mapMethodValue.keySet()) {
                    
                    if (method.getDeclaringClass() == CriminalReport.class) {
                        String methodReturn = (String) method.invoke(report);
                        String filterValue = mapMethodValue.get(method);
                        if (!filterValue.equalsIgnoreCase(methodReturn)) {
                            //If one filter fails, ignore the criminalReport and try to get the next report
                            continue bo;
                        }
                        flag = true;
                    } else if (method.getDeclaringClass() == Person.class) {
                        if (!report.getPartesEnvolvidas().isEmpty()) {
                            for (Person person : report.getPartesEnvolvidas()) {
                                methodReturnp = (String) method.invoke(person);
                                filterValuep = mapMethodValue.get(method);
                                if (!filterValuep.equalsIgnoreCase(methodReturnp)) {
                                    //If no person has this attribute, ignore this report and try to get the next report
                                    continue bo;
                                }
                            }
                            flag = true;
                        } else {
                            flag = false;
                        }
                    }
                }
                if (flag) {
                    listOfCriminalReport.add(report);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error");
        }
        
        return listOfCriminalReport;
    }

    /**
     * *********
     * PERSON **********
     */
    public Person loadPersonByRg(String idPerson) throws FileNotFoundException {
        for (String idBO : list) {
            CriminalReport report = loadCriminalReport(idBO);
            for (Person person : report.getPartesEnvolvidas()) {
                if (idPerson.equalsIgnoreCase(person.getRg())) {
                    person.applySemantics();
                    return person;
                }
            }
        }
        return null;
    }
    
    public List<Person> loadAllPersonsByGender(String gender) throws FileNotFoundException {
        List<Person> listOfPersons = new ArrayList<>();
        for (String idBO : list) {
            CriminalReport report = loadCriminalReport(idBO);
            for (Person person : report.getPartesEnvolvidas()) {
                if (gender.equalsIgnoreCase(person.getSexo())) {
                    person.applySemantics();
                    listOfPersons.add(person);
                }
            }
        }
        return listOfPersons;
    }
    
    public List<Person> loadAllPersonsBySkinColor(String skinColor) throws FileNotFoundException {
        List<Person> listOfPersons = new ArrayList<>();
        for (String idBO : list) {
            CriminalReport report = loadCriminalReport(idBO);
            for (Person person : report.getPartesEnvolvidas()) {
                if (skinColor.equalsIgnoreCase(person.getCutis())) {
                    person.applySemantics();
                    listOfPersons.add(person);
                }
            }
        }
        return listOfPersons;
    }
    
    public List<Person> loadAllPersonsByEducation(String education) throws FileNotFoundException {
        List<Person> listOfPersons = new ArrayList<>();
        for (String idBO : list) {
            CriminalReport report = loadCriminalReport(idBO);
            for (Person person : report.getPartesEnvolvidas()) {
                if (education.equalsIgnoreCase(person.getInstrucao())) {
                    person.applySemantics();
                    listOfPersons.add(person);
                }
            }
        }
        return listOfPersons;
    }
    
    @PostConstruct
    private void loadBOs() throws FileNotFoundException {
        System.out.println("Loading Criminal Reports...");
        
        if (!dataDir.exists()) {
            throw new FileNotFoundException(dataDir.getPath());
        }
        
        File[] bOs = dataDir.listFiles(new FileFilter() {
            private final Pattern pattern = Pattern.compile(".*\\.json$");
            
            @Override
            public boolean accept(File file) {
                Matcher matcher = pattern.matcher(file.getName());
                return matcher.matches();
            }
        });
        
        for (File file : bOs) {
            list.add(file.getName().replaceAll("\\.json", ""));
        }
        
        System.out.println("Criminal Reports loaded.");
        
        System.out.println("Loading Categories, SkinColors and Educations");
        //Load all the report types (categories) from criminal reports
        for (String idBO : list) {
            CriminalReport report = loadCriminalReport(idBO);
            categories.add(report.getCategoria());
            for (Person p : loadCriminalReport(idBO).getPartesEnvolvidas()) {
                skinColors.add(p.getCutis());
                educations.add(p.getInstrucao());
            }
        }
        System.out.println("Done.");
    }
    
    public java.util.Set<String> getAllReportCategories() {
        return categories;
    }
    
    public Set<String> getAllSkinColors() {
        return skinColors;
    }
    
    public Set<String> getAllEducations() {
        return educations;
    }

    //Test
    public void applyFilters(String year, String category, String skin, String gender, String education) {
        
        List<String> listIdsBOs = list.stream().filter(idBO -> {
            try {
                CriminalReport cr = loadCriminalReport(idBO);
                return (year.equalsIgnoreCase(cr.getAno())
                        && category.equalsIgnoreCase(cr.getCategoria())
                        && cr.getPartesEnvolvidas().stream()
                                .anyMatch(p -> skin.equalsIgnoreCase(p.getCutis())
                                && gender.equalsIgnoreCase(p.getSexo())
                                && education.equalsIgnoreCase(p.getInstrucao())
                                ));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return false;
        })
                .collect(Collectors.toList());
        
        System.out.println(listIdsBOs);
    }

    // Points
    public List<CriminalReport> loadAllPoints() throws FileNotFoundException {
        List<CriminalReport> points = new ArrayList<>();
        for (String idBO : list) {
            CriminalReport fullCriminalReport
                    = loadCriminalReportWithoutSemantics(idBO);
            CriminalReport point = new CriminalReport();
            point.setIdBO(idBO);
            point.setLat(fullCriminalReport.getLat());
            point.setLon(fullCriminalReport.getLon());
            points.add(point);
        }
        return points;
    }
    
    public List<CriminalReport> loadAllPointsFiltering(Map<Method, String> params) throws FileNotFoundException {
        List<CriminalReport> fullFiltered = loadAllReportsFiltering(params, false);
        return fullFiltered
                .stream()
                .map(cr -> new CriminalReport(cr.getIdBO(), cr.getLat(), cr.getLon()))
                .collect(Collectors.toList());
    }
    
}
