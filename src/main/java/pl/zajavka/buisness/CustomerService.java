package pl.zajavka.buisness;

import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.buisness.DAO.CustomerDAO;
import pl.zajavka.domain.Customer;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;

    @Transactional
    public void issueInvoice(Customer customer) {
        customerDAO.issueInvoice(customer);
    }

    @Transactional
    public Customer findCustomer(String email) {
        Optional<Customer> customer = customerDAO.findByEmail(email);
        if (customer.isEmpty()) {
            throw new NotFoundException("Could not find customer by email: [%s]".formatted(email));
        }
        return customer.get();
    }

    @Transactional
    public void saveServiceRequest(Customer customer) {
        customerDAO.saveServiceRequest(customer);
    }

    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerDAO.saveCustomer(customer);
    }
}
