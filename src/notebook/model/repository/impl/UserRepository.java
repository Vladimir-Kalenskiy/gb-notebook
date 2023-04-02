package notebook.model.repository.impl;

import notebook.model.User;
import notebook.model.repository.GBRepository;
import notebook.util.DBConnector;
import notebook.util.mapper.impl.UserMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserRepository implements GBRepository {
    private final UserMapper mapper;
    private final DBConnector connector;

    public UserRepository(DBConnector connector, String separator) {
        this.mapper = new UserMapper(separator);
        this.connector = connector;
    }

    public UserRepository(DBConnector connector) {
        this(connector, " ");
    }

    @Override
    public List<String> readAll() {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(connector.DB_PATH);
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            if (line != null) {
                lines.add(line);
            }
            while (line != null) {
                // считываем остальные строки в цикле
                line = reader.readLine();
                if (line != null) {
                    lines.add(line);
                }
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    @Override
    public List<User> findAll() {
        List<String> lines = readAll();
        List<User> users = new ArrayList<>();
        for (String line : lines) {
            users.add(mapper.toOutput(line));
        }
        return users;
    }

    @Override
    public User create(User user) {
        List<User> users = findAll();
        long max = 0L;
        for (User u : users) {
            long id = u.getId();
            if (max < id) {
                max = id;
            }
        }
        long next = max + 1;
        user.setId(next);
        users.add(user);
        write(users);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        List<User> users = findAll();
        for (User user : users) {
            if (Objects.equals(user.getId(), id)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> update(Long id, User user) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        List<User> users = findAll();

        for (User user : users) {
            if (user.getId().equals(id)) {
                users.remove(user);
                write(users);
                return true;
            }
        }
        return false;
    }

    private void write(List<User> users) {
        List<String> lines = new ArrayList<>();
        for (User u : users) {
            lines.add(mapper.toInput(u));
        }

        try (FileWriter writer = new FileWriter(connector.DB_PATH, false)) {
            for (String line : lines) {
                writer.write(line);
                writer.append('\n');
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
