package pl.zajavka.buisness;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.zajavka.buisness.DAO.CustomerDAO;
import pl.zajavka.domain.Address;
import pl.zajavka.domain.Customer;

import java.util.Optional;

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
            throw new RuntimeException("Could not find customer by email: [%s]".formatted(email));
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
