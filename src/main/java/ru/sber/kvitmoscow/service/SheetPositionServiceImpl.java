package ru.sber.kvitmoscow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.kvitmoscow.model.SheetPosition;
import ru.sber.kvitmoscow.repository.SheetPositionRepository;

import java.util.List;

@Service
public class SheetPositionServiceImpl implements SheetPositionService {

    @Autowired
    private SheetPositionRepository sheetPositionRepository;

    @Override
    public SheetPosition save(SheetPosition entity) {
        return sheetPositionRepository.save(entity);
    }

    @Override
    public boolean delete(int id) {
        return sheetPositionRepository.delete(id) != 0;
    }

    @Override
    public SheetPosition get(int id) {
        return sheetPositionRepository.findById(id).orElse(null);
    }

    @Override
    public List<SheetPosition> getAll() {
        return sheetPositionRepository.findAll();
    }
}
