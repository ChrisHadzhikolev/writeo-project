package com.example.writeo.controllerService.services;

import com.example.writeo.controllerService.interfaces.IRevenueService;
import com.example.writeo.exception.JPAException;
import com.example.writeo.model.Article;
import com.example.writeo.model.Revenue;
import com.example.writeo.repository.ArticleRepository;
import com.example.writeo.repository.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RevenueService implements IRevenueService {
    @Autowired
    private RevenueRepository revenueRepository;


    @Override
    public List<Revenue> findAll() throws JPAException {
        try{
            List<Revenue> revenues= revenueRepository.findAll();
            if (revenues.isEmpty()) return null;
            return revenueRepository.findAll();
        }catch (Exception e){
            throw new JPAException();
        }
    }

    @Override
    public Revenue save(Revenue revenue){
        if (revenue != null) {
            return revenueRepository.save(revenue);
        }else throw new NullPointerException();
    }

    @Override
    public Optional<Revenue> findById(long id) {
        if(id >= 0L){
            return revenueRepository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(long id) {
        revenueRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        revenueRepository.deleteAll();
    }

    public Revenue updateRevenue(Revenue changedRevenue, long id){
        Optional<Revenue> revenueData = revenueRepository.findById(id);

        if (revenueData.isPresent()) {
            changedRevenue.setId(id);
            revenueRepository.save(changedRevenue);
            return changedRevenue;
        } else {
            throw new NullPointerException();
        }
    }
}
