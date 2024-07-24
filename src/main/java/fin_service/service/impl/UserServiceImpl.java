package fin_service.service.impl;

import fin_service.domain.model.User;
import fin_service.domain.repository.UserRepository;
import fin_service.service.UserService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    final private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findById(Long id) {

        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User create(User newUser) {

        if (repository.existsByAccountNumber(newUser.getAccount().getNumber())) {
            throw new IllegalArgumentException("This account number already exists");
        }
        return repository.save(newUser);
    }
}
