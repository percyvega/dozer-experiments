package com.percyvega;

import com.github.dozermapper.core.Mapper;
import com.percyvega.common.EmployeeDTO;
import com.percyvega.model.Consultant;
import com.percyvega.model.Contractor;
import com.percyvega.model.Employee;
import com.percyvega.model.Person;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Period;

import static org.junit.Assert.assertEquals;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {

    private static final String COMPANY_NAME = "Staff For You, Inc.";
    private static final String FULL_NAME = "Percy Vega";

    private Employee employee = new Employee().setId(1).setName(FULL_NAME).setSalary(100);
    private EmployeeDTO employeeDTO = new EmployeeDTO().setId(1).setName(FULL_NAME).setSalary(100);
    private Person person = new Person().setId(1).setName(FULL_NAME);
    private Consultant consultant = new Consultant().setId(1).setSalary(100).setConsultingFirmName(COMPANY_NAME);
    private Contractor contractor = new Contractor().setId(1).setWages(100).setAgencyName(COMPANY_NAME).setContractPeriod(Period.ofMonths(3));

    private Employee employeeFromEmployeeDTO = new Employee().setId(1).setName(FULL_NAME).setSalary(100);
    private EmployeeDTO employeeDTOfromEmployee = new EmployeeDTO().setId(1).setName(FULL_NAME).setSalary(100);
    private Employee employeeFromPerson = new Employee().setId(1).setName(FULL_NAME);
    private Person personFromEmployee = new Person().setId(1).setName(FULL_NAME);
    private Employee employeeFromConsultant = new Employee().setId(1).setSalary(100);
    private Consultant consultantFromEmployee = new Consultant().setId(1).setSalary(100);
    private Contractor contractorFromConsultant = new Contractor().setId(1).setWages(100).setAgencyName(COMPANY_NAME);
    private Consultant consultantFromContractor = new Consultant().setId(1).setSalary(100).setConsultingFirmName(COMPANY_NAME);

    @Autowired
    private Mapper defaultMapper;

    @Autowired
    private Mapper xmlMapper;

    @Autowired
    private Mapper programmaticMapper;

    @Test
    public void same_everything() {
        EmployeeDTO employeeDTOMapped = defaultMapper.map(employee, EmployeeDTO.class);
        assertEquals(employeeDTOMapped, employeeDTOfromEmployee);

        Employee employeeMapped = defaultMapper.map(employeeDTO, Employee.class);
        assertEquals(employeeMapped, employeeFromEmployeeDTO);
    }

    @Test
    public void one_class_is_missing_a_field() {
        Employee employeeMapped = defaultMapper.map(person, Employee.class);
        assertEquals(employeeMapped, employeeFromPerson);

        Person personMapped = defaultMapper.map(this.employee, Person.class);
        assertEquals(personMapped, personFromEmployee);
    }

    @Test
    public void both_classes_are_missing_a_field() {
        Employee employeeMapped = defaultMapper.map(consultant, Employee.class);
        assertEquals(employeeMapped, employeeFromConsultant);

        Consultant consultantMapped = defaultMapper.map(employee, Consultant.class);
        assertEquals(consultantMapped, consultantFromEmployee);
    }

    @Test
    public void fields_with_different_names() {
        Contractor contractorMapped = xmlMapper.map(consultant, Contractor.class);
        assertEquals(contractorMapped, contractorFromConsultant);

        Consultant consultantMapped = xmlMapper.map(contractor, Consultant.class);
        assertEquals(consultantMapped, consultantFromContractor);
    }

    @Test
    public void programmatic_mapper() {
        Contractor contractorMapped = programmaticMapper.map(consultant, Contractor.class);
        assertEquals(contractorMapped, contractorFromConsultant);

        Consultant consultantMapped = programmaticMapper.map(contractor, Consultant.class);
        assertEquals(consultantMapped, consultantFromContractor);
    }
}