package milestone1.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import milestone1.domain.Botiga;

public interface RepositoriBotigues extends JpaRepository<Botiga, Integer> {}
