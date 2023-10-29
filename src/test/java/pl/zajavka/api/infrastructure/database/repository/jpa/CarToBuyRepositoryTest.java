package pl.zajavka.api.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import pl.zajavka.infrastructure.database.entity.CarToBuyEntity;
import pl.zajavka.infrastructure.database.repository.jpa.CarToBuyJpaRepository;
import pl.zajavka.integration.configuration.PersistenceContainerTestConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.zajavka.api.util.EntityFixtures.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CarToBuyRepositoryTest {

    private CarToBuyJpaRepository carToBuyJpaRepository;

    @Test
    void thatCarCanBeSavedCorrectly() {
        // given
        List<CarToBuyEntity> cars = List.of(someCar1(), someCar2(), someCar3());
        carToBuyJpaRepository.saveAllAndFlush(cars);

        // when
        List<CarToBuyEntity> availableCars = carToBuyJpaRepository.finaAvailableCars();

        // then
        assertThat(availableCars).hasSize(9);
    }
}
