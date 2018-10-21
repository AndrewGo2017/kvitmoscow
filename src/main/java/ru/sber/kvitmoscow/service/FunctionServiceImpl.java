package ru.sber.kvitmoscow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.kvitmoscow.model.Function;
import ru.sber.kvitmoscow.repository.FunctionRepository;

import java.util.List;

@Service
public class FunctionServiceImpl implements FunctionService {
    @Autowired
    private FunctionRepository functionRepository;

    @Override
    public Function save(Function entity) {
        return functionRepository.save(entity);
    }

    @Override
    public boolean delete(int id) {
        return functionRepository.delete(id) != 0;
    }

    @Override
    public Function get(int id) {
        return functionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Function> getAll() {
        return functionRepository.findAll();
    }
}
