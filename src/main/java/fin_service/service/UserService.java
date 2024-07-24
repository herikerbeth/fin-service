package fin_service.service;

import fin_service.domain.model.User;

public interface UserService {

    User findById(Long id);

    User create(User newUser);
}
