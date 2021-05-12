package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import static org.springframework.data.jpa.domain.Specification.where;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PermissionsRepository permissionsRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping(value = "/users")
    public List<User> getAll() {
//        Specification<User> specification = (root, query, builder) -> {
//            return builder.like(root.get(User_.FIRSTNAME), "%" + "sathish" + "%");
//        };
//        Specification<User> specification1 = (root, criteriaQuery, criteriaBuilder) -> {
//            return criteriaBuilder.equal(root.get(User_.LASTNAME), "kohli");
//        };
//
//        List<User> user = userRepository.findAll(where(specification).or(specification1));
        List<User> users = userRepository.findAll();
        return users;
    }

    @GetMapping(value = "/userrole")
    public String userRole() {
        return "YOU ARE AUTHORIZED BY USER ROLE";
    }

    @GetMapping(value = "/customrole")
    public String customRole() {
        return "YOU ARE AUTHORIZED BY CUSTOM ROLE PERMISSION";
    }

    @PostMapping(value = "/userone")
    public List<User> save() {

        Permissions permissions = new Permissions();
        permissions.setName("READ_PERMISSION");
        permissionsRepository.save(permissions);

        Permissions permissions1 = new Permissions();
        permissions1.setName("WRITE_PERMISSION");
        permissionsRepository.save(permissions1);

        Permissions permissions2 = new Permissions();
        permissions2.setName("DELETE_PERMISSION");
        permissionsRepository.save(permissions2);

        List<Permissions> permissionsList = Arrays.asList(permissions, permissions1, permissions2);

        Roles roles = new Roles();
        roles.setName("ROLE_USER");
        rolesRepository.save(roles);

        Roles roles1 = new Roles();
        roles1.setName("ROLE_ADMIN");
        rolesRepository.save(roles1);

        Roles roles2 = new Roles();
        roles2.setName("custom");
        roles2.setPermissions(permissionsList);
        rolesRepository.save(roles2);

        List<Roles> rolesList = new ArrayList<>();
        rolesList.add(roles);
        rolesList.add(roles1);
        rolesList.add(roles2);

        User user = new User();
        user.setFirstname("user");
        user.setLastname("service");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRoles(Arrays.asList(roles, roles1));
        userRepository.save(user);

        User user1 = new User();
        user1.setFirstname("admin");
        user1.setLastname("service");
        user1.setPassword(passwordEncoder.encode("password"));
        user1.setRoles(rolesList);
        userRepository.save(user1);

        return Arrays.asList(user, user1);
    }

    @PostMapping(value = "/attachment")
    public String addAttachment(@RequestParam("file") MultipartFile file) throws IOException {
        Path path = Paths.get(UserController.class.getResource("/").getPath() + new Date().getTime() +
                "_" + file.getOriginalFilename());
        Files.createFile(path);
        Files.write(path, file.getBytes());

        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/attachment/")
                .path(path.getFileName().toString())
                .toUriString();

        return url;
    }

    @GetMapping(value = "/attachment/{filename}")
    public ResponseEntity<Resource> getAttachment(@PathVariable String filename, HttpServletRequest request) throws IOException {
        Path path = Paths.get(UserController.class.getResource("/").getPath() + filename);
        Resource resource = new UrlResource(path.toUri());

        String content_type;
        content_type = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(content_type))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
