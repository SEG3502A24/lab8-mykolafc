package seg3x02.employeeGql.resolvers

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import java.util.*

@Controller
class EmployeesResolver(private val employeeRepository: EmployeesRepository) {

    @QueryMapping
    fun employees(): List<Employee> = employeeRepository.findAll()

    @QueryMapping
    fun employeeById(@Argument id: String): Employee? =
        employeeRepository.findById(id).orElse(null)

    @QueryMapping
    fun employeeByEmail(@Argument email: String): Employee? =
        employeeRepository.findAll().find { it.email == email }

    @MutationMapping
    fun newEmployee(@Argument createEmployeeInput: CreateEmployeeInput): Employee {
        val employee = Employee(
            name = createEmployeeInput.name ?: throw Exception("Please enter a name"),
            dateOfBirth = createEmployeeInput.dateOfBirth ?: throw Exception("Please enter a valid date of birth"),
            city = createEmployeeInput.city ?: throw Exception("Please enter a valid city"),
            salary = createEmployeeInput.salary ?: throw Exception("Please enter a valid salary"),
            gender = createEmployeeInput.gender,
            email = createEmployeeInput.email
        )
        employee.id = UUID.randomUUID().toString()
        return employeeRepository.save(employee)
    }
}