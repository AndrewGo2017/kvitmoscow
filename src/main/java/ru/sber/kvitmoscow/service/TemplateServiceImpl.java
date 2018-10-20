package ru.sber.kvitmoscow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.kvitmoscow.model.Template;
import ru.sber.kvitmoscow.repository.TemplateRepository;
import ru.sber.kvitmoscow.to.FileMainFieldTo;

import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    private TemplateRepository templateRepository;

    @Override
    public Template save(Template entity) {
        return templateRepository.save(entity);
    }

    @Override
    public boolean delete(int id) {
        return templateRepository.delete(id) != 0;
    }

    @Override
    public Template get(int id) {
        return templateRepository.findById(id).orElse(null);
    }

    @Override
    public List<Template> getAll() {
        return templateRepository.findAll();
    }
}
