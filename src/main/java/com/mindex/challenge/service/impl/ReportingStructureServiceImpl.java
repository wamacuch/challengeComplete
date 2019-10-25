package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);
    
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ReportingStructureService reportingStructureService;
    
    private ReportingStructure reportingStructure = new ReportingStructure();

    
    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Creating reportingStructure from id [{}]", id);
        
        int totalReports = 0;
        
        Employee employee = employeeService.read(id);
        
        if (employee.getDirectReports() != null ) {
        	
        	for (Employee directReport : employee.getDirectReports()) {
        		
        		totalReports += 1;
        		// recursively get the number of directReports for all employees contained by the input employee
        		totalReports += (reportingStructureService.read(directReport.getEmployeeId())).getNumberOfReports();
        	}
        } else {
        	
        	totalReports = 0;
        }
        
        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(totalReports);

        return reportingStructure;
    }

}
